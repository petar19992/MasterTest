package com.example.classProperties;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by PETAR-PC on 9/30/2017.
 */

public class Function
{
    public String name;
    public ArrayList<String> args = new ArrayList<>();
    public int startIndex;
    public int endIndex;

    public void setArgs(String line, int i, int i1)
    {
        this.startIndex = i;
        this.endIndex = i1+1;
        String arguments = line.substring(startIndex, endIndex-1);
        if (arguments.length() > 0)
        {
            String[] args = arguments.split(",");
            Collections.addAll(this.args, args);
        }
    }
}
