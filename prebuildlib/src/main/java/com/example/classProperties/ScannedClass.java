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
    StringBuffer assetFile = new StringBuffer(); //Ovde se cuva sadrzaj koji ce se kriptovan upisati u assets fajl
    StringBuffer stringFile = new StringBuffer(); //Ovde se cuva sadrzaj koji ce se kriptovan upisati u string fajl
    int assetLineCount = 0; //Brojimo koliko smo linija upisali u assets fajl
    int sringLineCount = 0; //Brojimo koliko smo linija upisali u string fajl
    public Class realClass; //Instanca klase
    public ArrayList<Field> variables = new ArrayList<>(); //Sve promenljive koje su eksplicitno pisane u ovoj klasi a nisu nasledjene
    public ArrayList<Method> methods = new ArrayList<>(); //Sve metode koje su eksplicitno pisane u ovoj klasi a nisu nasledjene
    public String className = "MainActivity"; //Ime klase

    public void addVariable(Field field)
    {
        variables.add(field);
    }

    public Par commitChange(String line, String str, int strStartIndex, int strEndIndex)
    {
        stringFile.append(str).append("\n");
        String newLine ="ReflexHelper.getInstance().getString(this," + (sringLineCount++) + ")";
        line = line.replace(line.substring(strStartIndex, strEndIndex), newLine);
        int lastJ = line.lastIndexOf(newLine);
        return new Par(line, lastJ);
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
            newLine = "((" + function.returnType.getName() + ")ReflexHelper.getInstance().callFunc(" + className + ".this," + (assetLineCount++) + "))";
        }
        else
        {
            newLine = "ReflexHelper.getInstance().callFunc(" + className + ".this," + (assetLineCount++) + ")";
        }
        line = line.replace(line.substring(function.startIndex, function.endIndex), newLine);
        int lastJ = line.lastIndexOf(newLine);
        return new Par(line, lastJ);
    }

    public void writeAsset(FileOutputStream fooStream)
    {
        try
        {
            String cryptedString = /*AESenc.encrypt(*/assetFile.toString()/*)*/;
            fooStream.write(cryptedString.getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void writeStrings(FileOutputStream fooStream)
    {
        try
        {
            String cryptedString = /*AESenc.encrypt(*/stringFile.toString()/*)*/;
            fooStream.write(cryptedString.getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
