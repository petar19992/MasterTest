package com.example;

import com.example.classProperties.Function;
import com.example.classProperties.Par;
import com.example.classProperties.ScannedClass;
import com.example.compiler.InMemoryJavaCompiler;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CodeCryptoGenerator
{
    public static void main(String[] args)
    {
        String assets = "D:\\ASProjects\\MasterTest\\app\\src\\main\\assets";
        if (args == null || args.length < 1)
        {
            //com.example.petar.mastertest.
            args = new String[]{"com.example.petar.mastertest.MainActivity2",
                    "D:\\ASProjects\\MasterTest\\app\\src\\main\\java\\com\\example\\petar\\mastertest\\MainActivity2.java"};
//            args = new String[]{"com.example.MainActivity", "D:\\ASProjects\\MasterTest\\prebuildlib\\src\\main\\java\\com\\example"};
//            return;
        }

        File file = new File(args[1]); //Putanja do klase MainActivity
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()),
                    Charset.defaultCharset());

            StringBuffer stringBuffer = new StringBuffer();
            for (String line : lines)
            {
                stringBuffer.append(line + "\n");
                System.out.println(line);
            }
            String classAsString = stringBuffer.toString();
            String className = args[0]; //Ime klase
            Class<?> helloClass = InMemoryJavaCompiler.newInstance().ignoreWarnings().compile(className, classAsString);

            ScannedClass scannedClass = new ScannedClass();
            scannedClass.className = helloClass.getName();
            scannedClass.realClass = helloClass;
            Field[] fields = helloClass.getDeclaredFields();
            if (fields != null)
            {
                for (int i = 0; i < fields.length; i++)
                {
                    scannedClass.addVariable(fields[i]);
                }
            }
            StringBuffer ouput = new StringBuffer();
            for (Class c = helloClass; c != null; c = c.getSuperclass())
            {
                for (Method method : c.getDeclaredMethods())
                {
                    if (c.getName().equals(className))
                    {
                        scannedClass.methods.add(method);
                    }
                }
            }

            File myFoo = new File(args[1]);
            FileOutputStream fooStream = new FileOutputStream(myFoo, false); // true to append
            for (String line : lines)
            {
                if (line.contains("\""))
                {
                    for (int j = -1; (j = line.indexOf("\"", j + 1)) != -1; j++)
                    {
                        int nextShow = line.indexOf("\"", j + 1);
                        String newLine = line.substring(j + 1, nextShow);
                        Par pair = scannedClass.commitChange(line,newLine,j,nextShow+1);
                        line = pair.linija;
                        j = pair.j;
                    }
                }
                for (int i = 0; i < scannedClass.methods.size(); i++)
                {
                    Method method = scannedClass.methods.get(i);
                    //Proverimo da li je u toj liniji implementacija funkcije ili je njen poziv
                    if (line.contains(method.getName()) && !line.contains(method.getGenericReturnType() + " "
                            + method.getName() + "(") && !line.contains("super." + method.getName() + "("))
                    {

                        for (int j = -1; (j = line.indexOf(method.getName() + "(", j + 1)) != -1; j++)
                        {
                            System.out.println(method.getName() + ": Line: " + (i + 1) + ", col: " + (j + 1));
                            Function function = getStringForChange(method.getName(), j, line);
                            if (function != null)
                            {
                                function.returnType = method.getReturnType();
                                Par pair = scannedClass.commitChange(line, function);
                                line = pair.linija;
                                j = pair.j;
//                                j = -1;
                            }
                        }
                    }
                }
                fooStream.write(line.getBytes());
                fooStream.write("\n".getBytes());
                ouput.append(line);
            }
            fooStream.close();


            myFoo = new File(assets);
            if (!myFoo.exists())
            {
                myFoo.mkdirs();
            }
            myFoo = new File(assets + "\\maste.txt");
            fooStream = new FileOutputStream(myFoo, false);
            scannedClass.writeAsset(fooStream);
            fooStream.close();
            myFoo = new File(assets + "\\strings.txt");
            fooStream = new FileOutputStream(myFoo, false);
            scannedClass.writeStrings(fooStream);
            fooStream.close();

            System.out.println("PROBAAAAAAAAAAAAAAAAAAAA");

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Function getStringForChange(String methodName, int startIndex, String line)
    {
        char[] lineChar = line.toCharArray();
        int counter = 1;
        int i = startIndex + methodName.length() + 1;
        for (; i < line.length(); i++)
        {
            if (lineChar[i] == '(')
            {
                counter++;
            }
            else if (lineChar[i] == ')')
            {
                counter--;
            }
            if (counter == 0)
            {
                Function function = new Function();
                function.name = methodName;
                function.setArgs(line, startIndex, i);
                return function;
            }
        }
        return null;
    }
}
