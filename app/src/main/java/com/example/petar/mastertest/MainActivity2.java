package com.example.petar.mastertest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by PETAR-PC on 12/6/2017.
 */

public class MainActivity2
        extends Activity
        implements View.OnClickListener
{
    private TextView resultTxtView;
    private EditText inputTxtView;
    private TextView resultLabel;
    private Button factorielBtn;
    private Button randomMultipleBtn;
    private Button randomFunc;

    /*private int factorielFunc(int a)
    {
        if (a != 1)
        {
            return a * factorielFunc(a - 1);
        }
        else
        {
            return 1;
        }
    }*/

    private void findViews()
    {
       /* resultTxtView = (TextView) findViewById(R.id.resultTxtView);
        inputTxtView = (EditText) findViewById(R.id.inputTxtView);
        resultLabel = (TextView) findViewById(R.id.resultLabel);
        factorielBtn = (Button) findViewById(R.id.factorielBtn);
        randomMultipleBtn = (Button) findViewById(R.id.randomMultipleBtn);*/
        resultTxtView = (TextView) findViewById(getResources().getIdentifier("resultTxtView", "id", getPackageName()));
        inputTxtView = (EditText) findViewById(getResources().getIdentifier("inputTxtView", "id", getPackageName()));
        resultLabel = (TextView) findViewById(getResources().getIdentifier("resultLabel", "id", getPackageName()));
        factorielBtn = (Button) findViewById(getResources().getIdentifier("factorielBtn", "id", getPackageName()));
        randomMultipleBtn = (Button) findViewById(getResources().getIdentifier("randomMultipleBtn", "id", getPackageName()));
        randomFunc = (Button) findViewById(getResources().getIdentifier("randomFunc", "id", getPackageName()));
        factorielBtn.setOnClickListener(this);
        randomMultipleBtn.setOnClickListener(this);
        randomFunc.setOnClickListener(this);
    }

    private int getRandomNumber(int limit)
    {
        return new Random().nextInt(limit);
    }

    private int randomFuncion(int number)
    {
        return new Random().nextInt(number) + 5 + new Random().nextInt(number);
    }

    private int randomMultiple(int a, int randomNumber)
    {
        return a * randomNumber;
    }

    int number;
    int randomNuber;
    @Override
    public void onClick(View v)
    {
        String text = inputTxtView.getText().toString();
        if (text != null && !text.equals(""))
        {
            number = Integer.valueOf(text);
            int result = 0;
            long time= System.currentTimeMillis();
            android.util.Log.i("Time Class ", " Start time: "+time);

            for(int i=0;i<100;i++)
            {
//                if (v == factorielBtn)
                {
                    result = randomFuncion(number);
                }
//                else if (v == randomMultipleBtn)
                {
                    randomNuber = getRandomNumber(100);
                    result = randomMultiple(number, randomNuber);
                }
//                else if (v == randomFunc)
                {
                    result = randomFuncion(number);
                }
            }
            android.util.Log.i("Time Class ", " End time: "+System.currentTimeMillis());
            resultTxtView.setText("" + result);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getResources().getIdentifier("activity_main", "layout", getPackageName()));
        findViews();
    }
}

