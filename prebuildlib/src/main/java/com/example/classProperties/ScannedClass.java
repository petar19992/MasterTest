package com.example.classProperties;
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

    public void addVariable(Field field)
    {
        variables.add(field);
    }

    public Par commitChange(String line, Function function)
    {
        assetFile.append(function.name).append(";");
        for (int i = 0; i < function.args.size(); i++)
        {
            assetFile.append(function.args.get(i)).append(",");
        }
        assetFile.append("\n");
        String newLine="ReflexHelper.getInstance().callFunc("+(assetLineCount++)+")";
        line=line.replace(line.substring(function.startIndex,function.endIndex),newLine);
        int lastJ=line.lastIndexOf(newLine);
        return new Par(line,lastJ);
    }
}
