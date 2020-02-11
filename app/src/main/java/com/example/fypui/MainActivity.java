package com.example.fypui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
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
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void view_scores(View view){
        Intent intent = new Intent(this, scores_page.class);
        startActivity(intent);
    }

    public void view_instructions(View view) {
        Intent intent = new Intent(this, instructions_page.class);
        startActivity(intent);
    }

    public void exit_game(View view) {

            Log.println( Log.ERROR, "TAG", "Inside openDialog exit game" );

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final AlertDialog.Builder exitGame = new AlertDialog.Builder( getApplicationContext(), R.style.AlertDialogStyle);
                    exitGame.setMessage("Do you want to Exit?")
                            .setTitle("♠ ♥ ♣ ♦")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.exit(0);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }



                            });

                    AlertDialog dialog = exitGame.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
                    dialog.show();
                }
            }, 3000);

            Log.println( Log.ERROR, "TAG", "The exit tag" );
    }
}

