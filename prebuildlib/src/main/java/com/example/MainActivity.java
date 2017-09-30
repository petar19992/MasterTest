package com.example;


import java.util.Random;

public class MainActivity
{


    private int factorielFunc(int a)
    {
        if (a != 1)
        {
            return a * factorielFunc(a - 1);
        }
        else
        {
            return 1;
        }
    }

    private int getRandomNumber(int limit)
    {
        return new Random().nextInt(limit);
    }

    private int randomFuncion(int number)
    {
        return factorielFunc(number) + 5 + randomMultiple(number, 4);
    }

    private int randomMultiple(int a, int randomNumber)
    {
        return a * randomNumber;
    }

}
