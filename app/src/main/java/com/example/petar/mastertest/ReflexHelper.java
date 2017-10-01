package com.example.petar.mastertest;

import android.util.Log;

import java.lang.reflect.Method;

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

    public int callFunc(int num)
    {
        return 0;
    }

    public void callByName(Object obj, String funcName) throws Exception
    {
        int i = 5;
        Class<?>[] classes = new Class<?>[]{Integer.class};
        Method method = getClass().getMethod(funcName, classes);
        int result = (int) method.invoke(obj, i);
        Log.i("", "");

    }
}
