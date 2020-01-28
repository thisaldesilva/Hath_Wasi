package com.example.fypui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

    }

    public void play_game(View view){
        Intent intent = new Intent(this, splash.class);
        startActivity(intent);
    }

    public void view_scores(View view){
        Intent intent = new Intent(this, scores_page.class);
        startActivity(intent);
    }

    public void view_instructions(View view) {
        Intent intent = new Intent(this, instructions_page.class);
        startActivity(intent);
    }
}

