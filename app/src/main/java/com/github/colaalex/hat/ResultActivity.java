package com.github.colaalex.hat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String result = intent.getStringExtra(MainActivity.RESULT_KEY);

        Button newGameButton = findViewById(R.id.btn_new);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

        TextView textResult = findViewById(R.id.txt_result);
        textResult.setText(String.format(getString(R.string.result), result));
    }

    void restartGame() {
        Intent restartIntent = new Intent(this, MainActivity.class);
        startActivity(restartIntent);
        finish();
    }


}
