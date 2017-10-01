package com.example.classProperties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by PETAR-PC on 9/30/2017.
 */

public class ScannedClass
{
    StringBuffer assetFile = new StringBuffer();
    int assetLineCount = 0;
    public Class realClass;
    public ArrayList<Field> variables = new ArrayList<>();
    public ArrayList<Method> methods = new ArrayList<>();
    public String className="MainActivity";

    public void addVariable(Field field)
    {
        variables.add(field);
    }

    public Par commitChange(String line, Function function)
    {
        if (function.returnType != null)
        {
            assetFile.append(function.returnType.getName()).append(";");
        }
        assetFile.append(function.name).append(";");
        for (int i = 0; i < function.args.size(); i++)
        {
            assetFile.append(function.args.get(i)).append(",");
        }
        assetFile.append("\n");
        String newLine = "";
        if (function.returnType != null && !function.returnType.getName().equals("void"))
        {
            newLine = "((" + function.returnType.getName() + ")ReflexHelper.getInstance().callFunc("+className+".this," + (assetLineCount++) + "))";
        }
        else
        {
            newLine = "ReflexHelper.getInstance().callFunc(" +className+".this," + (assetLineCount++) + ")";
        }
        line = line.replace(line.substring(function.startIndex, function.endIndex), newLine);
        int lastJ = line.lastIndexOf(newLine);
        return new Par(line, lastJ);
    }

    public void writeAsset(FileOutputStream fooStream)
    {
        try
        {
            fooStream.write(assetFile.toString().getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
