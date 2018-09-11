package com.github.colaalex.hat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private int turn;
    private ArrayList<Team> teams = new ArrayList<>();
    private List<String> words = new ArrayList<>(); //пока что захардкодил
    private int currentScore;
    private Resources res;
    private Random random; //нужен будет для случайного выбора элемента из массива

    private Chronometer chronometer;
    private TextView textTurn;
    private TextView textCounter;
    private TextView textWord;

    private Button guessButton;
    private Button skipButton;

    private ConstraintLayout layoutSplash;
    private LinearLayout layoutQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        res = getResources();
        fillList(); //тупой костыль и хардкод

        Button goButton = findViewById(R.id.btn_go);
        guessButton = findViewById(R.id.btn_guess);
        skipButton = findViewById(R.id.btn_skip);
        chronometer = findViewById(R.id.chronometer);
        textTurn = findViewById(R.id.txt_turn);
        random = new Random();
        layoutSplash = findViewById(R.id.layout_splash);
        layoutQuiz = findViewById(R.id.layout_quiz);
        textCounter = findViewById(R.id.txt_count);
        textWord = findViewById(R.id.txt_word);

        turn = 0;

        textTurn.setText(String.format(res.getString(R.string.team_turn), teams.get(0).getName()));

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTurn();
            }
        });
    }

    void startTurn() {
        layoutSplash.setVisibility(View.INVISIBLE);
        layoutQuiz.setVisibility(View.VISIBLE);

        chronometer.setCountDown(true);
        chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * 15);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();

                if (elapsedMillis >= 0) {
                    endTurn();
                }
            }
        });

        currentScore = 0;
        textCounter.setText(String.format(res.getString(R.string.words_guessed), currentScore));

        textWord.setText(words.get(random.nextInt(words.size())));

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessed(textWord.getText().toString());
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipped();
            }
        });

        chronometer.start();
    }

    void guessed(String word) {
        words.remove(word);
        currentScore++;
        textCounter.setText(String.format(res.getString(R.string.words_guessed), currentScore));
        if (words.size() > 0)
            textWord.setText(words.get(random.nextInt(words.size())));
        else
            endGame();
    }

    void skipped() {
        textWord.setText(words.get(random.nextInt(words.size())));
    }

    void endTurn() {
        chronometer.stop();
        teams.get(turn).setScore(currentScore);
        currentScore = 0;
        layoutQuiz.setVisibility(View.INVISIBLE);
        turn++;
        textTurn.setText(String.format(res.getString(R.string.team_turn), teams.get(turn).getName()));
        layoutSplash.setVisibility(View.VISIBLE);
    }

    void endGame() {
        chronometer.stop();
        teams.get(turn).setScore(currentScore);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("teams", teams);
        startActivity(intent);
        finish();
    }

    void fillList() {
        words.add("w1");
        words.add("w2");
        words.add("w3");
        words.add("w4");

        String[] teamStrings = res.getStringArray(R.array.teams);
        for (String teamString : teamStrings) {
            teams.add(new Team(teamString));
        }
    }
}
