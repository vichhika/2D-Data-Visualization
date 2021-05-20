package model;

import java.util.ArrayList;
import java.util.HashMap;

public class DataSet extends HashMap{

    private HashMap<String, ArrayList<Float>> labelrgb = new HashMap<>();

    public void setData(String data){
        String[] readline = data.split("\n");
        ArrayList<String> strings = new ArrayList<>();
        for(String x:readline){
            String[] x_split = x.split(", *");
            for(String y:x_split){
                strings.add(y);
            }
        }
        for(int i = 0;i<strings.size();i+=2){
            try {
                Double.valueOf(strings.get(i+1));
            }catch (Exception e){
                System.out.println("Label "+strings.get(i)+" filtered out.");
                continue;

            }
            ArrayList<Float> colors = new ArrayList<>();
            this.put(strings.get(i),Double.valueOf(strings.get(i+1)));
            colors.add((float) Math.random());
            colors.add((float) Math.random());
            colors.add((float) Math.random());
            this.labelrgb.put(strings.get(i),colors);
        }

    }

    public ArrayList<Float> getLabelColor(String key){
        return this.labelrgb.get(key);
    }
    public float getMinimunValue()
    {
        float b = 0;
        for(Object key:this.keySet())
        {
           if(b > Float.valueOf(String.valueOf(this.get(key)))) b = Float.valueOf(String.valueOf(this.get(key)));
        }
        return b;
    }

    public float getMaximunValue()
    {
        float b = 0;
        for(Object key:this.keySet())
        {
            if(b < Float.valueOf(String.valueOf(this.get(key)))) b = Float.valueOf(String.valueOf(this.get(key)));
        }
        return b;
    }
}
