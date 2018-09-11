package com.github.colaalex.hat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;


public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        ArrayList<Team> teams = (ArrayList<Team>) intent.getSerializableExtra("teams");
        Collections.sort(teams);

        RecyclerView recyclerView = findViewById(R.id.rv_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ResultAdapter(teams));

        Button newGameButton = findViewById(R.id.btn_new);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });
    }

    void restartGame() {
        Intent restartIntent = new Intent(this, MainActivity.class);
        startActivity(restartIntent);
        finish();
    }


}
