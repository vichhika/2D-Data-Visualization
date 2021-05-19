package controller.action;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import model.DataSet;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;

public class GLListener implements GLEventListener {

    DataSet dataSet;
    StringBuilder viewSelected;
    int x0 = -600;
    int y0 = -350;

    public GLListener(DataSet dataSet,StringBuilder viewSelected){
        this.dataSet = dataSet;
        this.viewSelected = viewSelected;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClearColor(1,1,1,1);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        Set keys = dataSet.keySet();
        HashMap<Object, Double> percentage = new HashMap<>();
        HashMap<Object, Double> radians = new HashMap<>();
        double total = 0.0;
        Double totalRad = 0.0;
        for(Object key:keys) total += Double.valueOf(String.valueOf(dataSet.get(key)));
        for(Object key:keys) percentage.put(key,100*(Float.valueOf(String.valueOf(dataSet.get(key)))/total));
        for(Object key:keys) radians.put(key,Math.toRadians(360 * (Double.valueOf(String.valueOf(dataSet.get(key)))/total)));
        for(Object key:keys) totalRad += radians.get(key);

        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

        if(dataSet.size() == 0)
        {
            TextRenderer textRenderer = new TextRenderer(new Font("Verdana",Font.BOLD,24));
            textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
            textRenderer.setColor(Color.black);
            textRenderer.draw("Please load the data.",280,320);
            textRenderer.endRendering();
        }
        else if(viewSelected.toString().equals("Pie Chart"))
        {
            Double currentRad = totalRad;
            Double unitRad = totalRad/360;
            for(Object key:keys)
            {
                gl2.glColor3f(dataSet.getLabelColor((String) key).get(0),dataSet.getLabelColor((String) key).get(1),dataSet.getLabelColor((String) key).get(2));
                gl2.glBegin(GL2.GL_POLYGON);
                gl2.glVertex2f(0,0);
                for(double i = currentRad ; i > -1 ; i-=unitRad)
                {
                    if(i > currentRad-radians.get(key))
                    {
                        float x = (float) (0 + 450*Math.cos(i));
                        float y = (float) (0 + 450*Math.sin(i));
                        gl2.glVertex2f(x,y);

                    }else
                    {
                        gl2.glVertex2f(0,0);
                        gl2.glEnd();
                        currentRad -= radians.get(key);
                        break;
                    }
                }
            }
            currentRad = totalRad;
            for(Object key: keys)
            {
                Double i = currentRad - (radians.get(key)/2);
                TextRenderer textRenderer = new TextRenderer(new Font("Verdana",Font.PLAIN,13));
                textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
                textRenderer.setColor(Color.black);
                textRenderer.draw(String.format("%s ",String.valueOf(key).subSequence(0,3)),(int)(400+Math.round(220*Math.cos(i))),(int) (300+Math.round(220*Math.sin(i))));
                textRenderer.draw(String.format("(%.2f%%)",percentage.get(key)),(int)(400+Math.round(220*Math.cos(i))),(int) (305+Math.round(220*Math.sin(i)))-20);
                textRenderer.endRendering();
                currentRad -= radians.get(key);

            }
        }
        else if(viewSelected.toString().equals("Bar Chart"))
        {
            drawAxis(glAutoDrawable);
            int unitX = Math.round(1200/dataSet.size());
            int currentX = x0+unitX;
            for (Object key:keys)
            {
                int y1 = Math.round(featureScaling(y0,y0+900, Float.valueOf(String.valueOf(dataSet.get(key))),dataSet.getMaximunValue(),dataSet.getMinimunValue()));
                drawBarX(glAutoDrawable,currentX,y0,currentX,y1, dataSet.getLabelColor((String) key).get(0),dataSet.getLabelColor((String) key).get(1),dataSet.getLabelColor((String) key).get(2),unitX/4);
                TextRenderer textRenderer = new TextRenderer(new Font("Verdana",Font.PLAIN,12));
                textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
                textRenderer.setColor(Color.black);
                textRenderer.draw(((String) key).subSequence(0,3), 390+(currentX/2),110);
                textRenderer.endRendering();
                currentX += unitX;

            }
            for(int i = (int) dataSet.getMinimunValue(); i <= dataSet.getMaximunValue(); i+=dataSet.getMaximunValue()/10)
            {
                int height = Math.round(featureScaling(120,570,i,dataSet.getMaximunValue(),dataSet.getMinimunValue()));
                TextRenderer textRenderer = new TextRenderer(new Font("Verdana",Font.PLAIN,12));
                textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
                textRenderer.setColor(Color.black);
                textRenderer.draw(String.valueOf(i), 60,height);
                textRenderer.endRendering();
            }
        }
        else if(viewSelected.toString().equals("Column Chart"))
        {
            drawAxis(glAutoDrawable);
            int unitY = Math.round(900/dataSet.size());
            int currentY = y0+unitY;
            for(Object key:keys)
            {
                int x1 = Math.round(featureScaling(x0,x0+1200,Float.valueOf(String.valueOf(dataSet.get(key))),dataSet.getMaximunValue(),dataSet.getMinimunValue()));
                drawBarY(glAutoDrawable,x0,currentY,x1,currentY,dataSet.getLabelColor((String) key).get(0),dataSet.getLabelColor((String) key).get(1),dataSet.getLabelColor((String) key).get(2),unitY/4);
                TextRenderer textRenderer = new TextRenderer(new Font("Verdana",Font.PLAIN,12));
                textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
                textRenderer.setColor(Color.black);
                textRenderer.draw(((String) key).subSequence(0,3), 60,300+(currentY/2));
                textRenderer.endRendering();
                currentY += unitY;
            }
            for(int i = (int) dataSet.getMinimunValue(); i <= dataSet.getMaximunValue(); i+=dataSet.getMaximunValue()/10)
            {
                int width = Math.round(featureScaling(100,700,i,dataSet.getMaximunValue(),dataSet.getMinimunValue()));
                TextRenderer textRenderer = new TextRenderer(new Font("Verdana",Font.PLAIN,12));
                textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
                textRenderer.setColor(Color.black);
                textRenderer.draw(String.valueOf(i), width,110);
                textRenderer.endRendering();
            }

        }
        else if (viewSelected.toString().equals("Scatter Plot"))
        {
            drawAxis(glAutoDrawable);
            int unitX = Math.round(1200/dataSet.size());
            int currentX = x0+unitX;
            for (Object key:keys)
            {
                int y1 = Math.round(featureScaling(y0,y0+900, Float.valueOf(String.valueOf(dataSet.get(key))),dataSet.getMaximunValue(),dataSet.getMinimunValue()));
                //drawBarX(glAutoDrawable,currentX,y0,currentX,y1, dataSet.getLabelColor((String) key).get(0),dataSet.getLabelColor((String) key).get(1),dataSet.getLabelColor((String) key).get(2),unitX/4);
                drawCircle(glAutoDrawable,currentX,y1,unitX/6,dataSet.getLabelColor((String) key).get(0),dataSet.getLabelColor((String) key).get(1),dataSet.getLabelColor((String) key).get(2));
                TextRenderer textRenderer = new TextRenderer(new Font("Verdana",Font.PLAIN,12));
                textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
                textRenderer.setColor(Color.black);
                textRenderer.draw(((String) key).subSequence(0,3), 390+(currentX/2),110);
                textRenderer.endRendering();
                currentX += unitX;

            }
            for(int i = (int) dataSet.getMinimunValue(); i <= dataSet.getMaximunValue(); i+=dataSet.getMaximunValue()/10)
            {
                int height = Math.round(featureScaling(120,570,i,dataSet.getMaximunValue(),dataSet.getMinimunValue()));
                TextRenderer textRenderer = new TextRenderer(new Font("Verdana",Font.PLAIN,12));
                textRenderer.beginRendering(glAutoDrawable.getSurfaceWidth(),glAutoDrawable.getSurfaceHeight());
                textRenderer.setColor(Color.black);
                textRenderer.draw(String.valueOf(i), 60,height);
                textRenderer.endRendering();
            }

        }
        gl2.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        gl2.glOrtho(-800,800,-600,600,0,1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);

    }

    public void drawAxis(GLAutoDrawable glAutoDrawable)
    {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glColor3f(0,0,0);
        gl2.glBegin(GL2.GL_LINES);
        gl2.glVertex2f(x0,y0);
        gl2.glVertex2f(x0+1200,y0);
        gl2.glVertex2f(x0,y0);
        gl2.glVertex2f(x0,y0+900);
        gl2.glEnd();
    }

    public void drawBarX(GLAutoDrawable glAutoDrawable,int x0,int y0,int x1,int y1,float r,float g,float b,float barWidth)
    {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glColor3f(r,g,b);
        gl2.glBegin(GL2.GL_POLYGON);
        gl2.glVertex2f(x0-barWidth,y0);
        gl2.glVertex2f(x0-barWidth,y1);
        gl2.glVertex2f(x0+barWidth,y1);
        gl2.glVertex2f(x1+barWidth,y0);
        gl2.glEnd();
    }

    public void drawBarY(GLAutoDrawable glAutoDrawable,int x0,int y0,int x1,int y1,float r,float g,float b,float barWidth)
    {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glColor3f(r,g,b);
        gl2.glBegin(GL2.GL_POLYGON);
        gl2.glVertex2f(x0,y0+barWidth);
        gl2.glVertex2f(x0,y1-barWidth);
        gl2.glVertex2f(x1,y1-barWidth);
        gl2.glVertex2f(x1,y0+barWidth);
        gl2.glEnd();
    }

    public void drawCircle(GLAutoDrawable glAutoDrawable,float x0,float y0,int radius,float r,float g,float b)
    {
        float x,y;
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glColor3f(r,g,b);
        gl2.glBegin(GL2.GL_POLYGON);
        for(float i = 0; i < 6.28319; i+=0.0174533)
        {
            x = (float) (x0+radius*Math.cos(i));
            y = (float) (y0+radius*Math.sin(i));
            gl2.glVertex2f(x,y);
        }
        gl2.glEnd();
    }

    private float featureScaling(float a,float b,float x,float max,float min){
        return a + ((x-min)*(b-a))/(max-min);
    }
}
