package com.example.petar.mastertest;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by PETAR-PC on 9/30/2017.
 */

public class ReflexHelper
{
    public static ReflexHelper getInstance()
    {
        if (instance == null)
        {
            instance = new ReflexHelper();
        }
        return instance;
    }

    private static ReflexHelper instance;

    public Object callFunc(Activity activity, int num)
    {
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(
                    new InputStreamReader(activity.getAssets().open("maste.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            int counter = 0;
            while ((mLine = reader.readLine()) != null)
            {
                if (counter == num)
                {
                    String[] parts = mLine.split(";");
                    if (reader != null)
                    {
                        try
                        {
                            reader.close();
                        } catch (IOException e)
                        {
                            //log the exception
                        }
                    }
                    if (parts.length > 2)
                    {
                        return callByName(activity, parts[1], parts[2]);
                    }
                    else
                    {
                        return callByName(activity, parts[1], "");
                    }
                }
                else
                {
                    counter++;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            //log the exception
        } finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                } catch (IOException e)
                {
                    //log the exception
                }
            }
        }
        return 0;
    }

    public Object callByName(Object obj, String funcName, String args) throws Exception
    {
        args = args.replaceAll("\\s+", "");
        String[] arguments = args.split(",");
        ArrayList<Class<?>> arrayList = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < arguments.length; i++)
        {
            if (arguments[i] != null && !arguments[i].equals(""))
            {
                String arg = arguments[i];
                if (isInteger(arg))
                {
                    arrayList.add(int.class);
                    values.add(Integer.valueOf(arg));
                }
                else
                {
                    int value = obj.getClass().getField(arg).getInt(obj);
                    arrayList.add(int.class);
                    values.add(value);
                }
            }
        }

        Class<?>[] classes = new Class[arrayList.size()];
        Object[] objs=values.toArray();
        for (int i = 0; i < arrayList.size(); i++)
        {
            classes[i] = arrayList.get(i);
        }
        Method method;
        if (classes.length > 0)
        {
            method = obj.getClass().getDeclaredMethod(funcName, classes);
        }
        else
        {
            method = obj.getClass().getDeclaredMethod(funcName);
        }
        if (values.size() > 0)
        {
            return method.invoke(obj, objs);
        }
        else
        {
            return method.invoke(obj);
        }


    }

    public boolean isInteger(String str)
    {
        int size = str.length();

        for (int i = 0; i < size; i++)
        {
            if (!Character.isDigit(str.charAt(i)))
            {
                return false;
            }
        }

        return size > 0;
    }
}
