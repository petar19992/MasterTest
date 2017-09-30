package com.example;

import com.example.compiler.InMemoryJavaCompiler;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CodeCryptoGenerator
{
    public static void main(String[] args)
    {
        if (args == null || args.length < 1)
        {
            //com.example.petar.mastertest.
            args = new String[]{"com.example.petar.mastertest.MainActivity", "D:\\ASProjects\\MasterTest\\app\\src\\main\\java\\com\\example\\petar\\mastertest\\MainActivity.java"};
//            args = new String[]{"com.example.MainActivity", "D:\\ASProjects\\MasterTest\\prebuildlib\\src\\main\\java\\com\\example"};
//            return;
        }
        File file = new File(args[1]);

        try
        {
            URL url = file.toURL();          // file:/c:/myclasses/
            /*URL[] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);
            Class cls = cl.loadClass(args[0]);*/

            List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()),
                    Charset.defaultCharset());

            StringBuffer stringBuffer = new StringBuffer();
            for (String line : lines)
            {
                stringBuffer.append(line + "\n");
                System.out.println(line);
            }
            String className="com.example.petar.mastertest.MainActivity";
            Class<?> helloClass = InMemoryJavaCompiler.newInstance().ignoreWarnings().compile("com.example.petar.mastertest.MainActivity", stringBuffer.toString());
            for (Class c = helloClass; c != null; c = c.getSuperclass()) {
                for (Method method : c.getDeclaredMethods()) {
                    if (c.getName().equals(className)) {
                        System.out.println(c.getName() + "." + method.getName());
                    }
                }
            }
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
}
