package com.example.jellopomo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //Time Variables
    private long startTime;
    private long pomTime = 1500000;
    private long b1Time = 300000;
    private long b2Time = 900000;

    //Pomodoro Tracking Variables
    private int pomcount = 0;
    private String current;

    //UI Elements
    private TextView countdown_text;
    private Button startpausebttn;
    private Button skipbttn;
    private TextView work;
    private TextView break1;
    private TextView break2;
    private TextView motd;
    private TextView doroCount;

    //Countdown Timer
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeft;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI Init
        countdown_text = findViewById(R.id.countdown_text);
        startpausebttn = findViewById(R.id.startpausebttn);
        skipbttn = findViewById(R.id.skipbttn);
        work = findViewById(R.id.work);
        break1 = findViewById(R.id.break1);
        break2 = findViewById(R.id.break2);
        motd = findViewById(R.id.motd);
        doroCount = findViewById(R.id.doroCount);

        motd.setText("MOTD" + newMotd());

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        //Start Button On Click
        startpausebttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
                mp.start();
            }
        });

        //Skip Button on Click
        skipbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipTimer();
            }
        });
    }

    private void startTimer() {
        //Pomodoro Check
        if(pomcount == 0){
            current = "b2";
            startTime = pomTime;
            pomoCheck();
        }

        //Timer
        timeLeft = startTime;
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
                motd.setText("MOTD: " + newMotd());
                doroCount.setText("Count: " + pomcount);
            }
        }.start();
        timerRunning = true;
        startpausebttn.setText("pause");
        skipbttn.setVisibility(View.VISIBLE);

    }
    private void pauseTimer() {
        //Pauses Timer and Hides Skip Button
        countDownTimer.cancel();
        timerRunning = false;
        startpausebttn.setText("Start");
    }

    private void skipTimer() {
        //Skips Timer, Changes MOTD and Updates Pomodoro Count
        countDownTimer.cancel();
        timerRunning = false;
        startpausebttn.setText("Start");
        pomoCheck();
        motd.setText(newMotd());
        skipbttn.setVisibility(View.INVISIBLE);
        doroCount.setText("Count: " + pomcount);
    }

    private void updateCountDownText() {
        //Updates the UI Values for the Timer
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

        countdown_text.setText(timeLeftFormatted);
    }

    private void pomoCheck() {
        //Tracks
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

    private String newMotd(){
        String msgs[] = {
                "Pomodoro was developed by Francesco Cirillo in the late 1980s.",
                "This app uses a revised form of Pomodoro that allows for shorter blocks of work.",
                "Pomodoro allows for intense focus while reducing burnout and distractions.",
                "Physical sounds associated with starting the timer help with motivation.",
                "Don't waste your pomodoro time! If you finished a task, review it.",
                "Make the most of your breaks! Work on a small hobby or read a book.",
                "For studying, try Active Recall! Try to recall what you just studied without looking at it.",
                "For studying, try Spaced Repetition! It works really well with Pomodoro.",
                "For programming, try Decomposition! Break your problem down into it's smallest parts before solving them.",
                "                             Have a great day!",
                "                           You're doing amazing!",
                "                               Keep it up!",
                "                           Reach your goals!",
                "                               Stay positive!"
        };
        Random r = new Random();
        int n = r.nextInt(14);
        return msgs[n];
    }
}