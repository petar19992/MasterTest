package com.example.classProperties;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by PETAR-PC on 9/30/2017.
 */

public class ScannedClass
{
    public Class realClass;
    public ArrayList<Field> variables=new ArrayList<>();
    public ArrayList<Method> methods=new ArrayList<>();

    public void addVariable(Field field)
    {
        variables.add(field);
    }

}
