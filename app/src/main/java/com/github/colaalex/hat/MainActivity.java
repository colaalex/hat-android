package com.github.colaalex.hat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private static final long TURN_DURATION = 30000;
    private static final int CIRCLES_COUNT = 3;
    public static final String RESULT_KEY = "result_key";

    private int circle = 0;
    private int teamNumber = 0;
    private int currentTeamScore;
    private Team[] teams;
    private List<String> words = new ArrayList<>();
    private List<String> skippedWords = new ArrayList<>();
    private Random random = new Random();
    private CountDownTimer countDownTimer;

    private TextView textTurn;
    private TextView textWord;
    private TextView timer;

    private ConstraintLayout layoutSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        fillTeams();
        resetWords();

        Button goButton = findViewById(R.id.btn_go);
        Button guessedButton = findViewById(R.id.btn_guess);
        Button skipButton = findViewById(R.id.btn_skip);
        textTurn = findViewById(R.id.txt_turn);
        layoutSplash = findViewById(R.id.layout_splash);
        textWord = findViewById(R.id.txt_word);
        timer = findViewById(R.id.timer);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTurn();
            }
        });

        guessedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guessed(textWord.getText().toString());
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipped(textWord.getText().toString());
            }
        });

        showSplash();
    }

    void startTurn() {
        countDownTimer = new CountDownTimer(TURN_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                timer.setText(getString(R.string.time_format, minutes, seconds));
            }

            @Override
            public void onFinish() {
                endTurn();
            }
        }.start();

        textWord.setText(words.get(random.nextInt(words.size())));

        layoutSplash.setVisibility(View.GONE);
    }

    void guessed(String word) {
        words.remove(word);
        currentTeamScore++;
        if (words.size() > 0)
            textWord.setText(words.get(random.nextInt(words.size())));
        else
            endTurn();
    }

    void skipped(String word) {
        words.remove(word);
        skippedWords.add(word);
        if (words.size() > 0)
            textWord.setText(words.get(random.nextInt(words.size())));
        else
            endTurn();
    }

    void endTurn() {
        countDownTimer.cancel();
        teams[teamNumber].addPoints(currentTeamScore);
        teamNumber++;
        currentTeamScore = 0;
        if (teamNumber < teams.length && !(words.isEmpty() && skippedWords.isEmpty())) {
            showSplash();
            words.addAll(skippedWords);
            skippedWords.clear();
        } else {
            endCircle();
        }
    }

    void endCircle() {
        circle++;
        if (circle < CIRCLES_COUNT) {
            showSplash();
            teamNumber = 0;
            resetWords();
        } else {
            endGame();
        }
    }

    void showSplash() {
        textTurn.setText(getString(R.string.team_turn, teams[teamNumber].getName()));
        layoutSplash.setVisibility(View.VISIBLE);
    }

    void endGame() {
        String resultText = getResultText();
        // pass data to the next ResultActivity here
        finish();
    }

    String getResultText() {
        Arrays.sort(teams);
        StringBuilder builder = new StringBuilder();
        for (Team team : teams) {
            builder.append(getString(R.string.result_format, team.getName(), team.getScore()));
        }
        return builder.toString();
    }

    void resetWords() {
        String[] words = getResources().getStringArray(R.array.words);
        skippedWords.clear();
        this.words.clear();
        this.words.addAll(Arrays.asList(words));
    }

    void fillTeams() {
        String[] teamStrings = getResources().getStringArray(R.array.teams);
        teams = new Team[teamStrings.length];
        for (int i = 0; i < teamStrings.length; i++) {
            teams[i] = new Team(teamStrings[i]);
        }
    }
}