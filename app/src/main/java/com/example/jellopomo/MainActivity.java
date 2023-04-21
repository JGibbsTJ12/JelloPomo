package com.example.jellopomo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private long startTime;
    private long pomTime = 1500000;
    private long b1Time = 300000;
    private long b2Time = 900000;

    private int pomcount = 0;
    private String current;

    private TextView countdown_text;
    private Button startpausebttn;
    private Button skipbttn;
    private TextView work;
    private TextView break1;
    private TextView break2;

    private CountDownTimer countDownTimer;

    private boolean timerRunning;

    private long timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdown_text = findViewById(R.id.countdown_text);
        startpausebttn = findViewById(R.id.startpausebttn);
        skipbttn = findViewById(R.id.skipbttn);
        work = findViewById(R.id.work);
        break1 = findViewById(R.id.break1);
        break2 = findViewById(R.id.break2);

        startpausebttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        skipbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipTimer();
            }
        });
    }

    private void startTimer() {
        if(pomcount == 0){
            current = "b2";
            startTime = pomTime;
            pomoCheck();
        }
        timeLeft = startTime;
        System.out.println(current);
        System.out.println(pomcount);
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startpausebttn.setText("Start");
                pomoCheck();
            }
        }.start();

        timerRunning = true;
        startpausebttn.setText("pause");
        skipbttn.setVisibility(View.VISIBLE);
    }
    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        startpausebttn.setText("Start");
        skipbttn.setVisibility(View.INVISIBLE);
    }

    private void skipTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        startpausebttn.setText("Start");
        pomoCheck();
        System.out.println(current);
        System.out.println(pomcount);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

        countdown_text.setText(timeLeftFormatted);
    }

    private void pomoCheck() {
        switch(current) {
            case "b1":
                current = "pom2";
                break1.setBackgroundColor(Color.TRANSPARENT);
                work.setBackgroundColor(Color.parseColor("#6e8641"));
                startTime = pomTime;
                break;
            case "b2":
                current = "pom";
                break2.setBackgroundColor(Color.TRANSPARENT);
                work.setBackgroundColor(Color.parseColor("#6e8641"));
                pomcount++;
                startTime = pomTime;
                break;
            case "pom":
                current = "b1";
                work.setBackgroundColor(Color.TRANSPARENT);
                break1.setBackgroundColor(Color.parseColor("#6e8641"));
                startTime = b1Time;
                break;
            case "pom2":
                current = "b2";
                work.setBackgroundColor(Color.TRANSPARENT);
                break2.setBackgroundColor(Color.parseColor("#6e8641"));
                startTime = b2Time;
        }
    }
}