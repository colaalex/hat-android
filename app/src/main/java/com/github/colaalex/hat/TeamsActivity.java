package com.github.colaalex.hat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class TeamsActivity extends AppCompatActivity {
    //TODO footer in recycler

    ArrayList<Team> teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        teams = new ArrayList<>();

        final RecyclerView recyclerView = findViewById(R.id.rv_teams);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TeamsAdapter(teams));

        Button addButton = findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teams.add(new Team(null));
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        Button goButton = findViewById(R.id.btn_start);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGame();
            }
        });
    }

    private void goToGame() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("teams", teams);
        startActivity(intent);
        finish();
    }

}
