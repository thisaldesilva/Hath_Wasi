package com.example.fypui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.w3c.dom.Text;

public class scores_page extends AppCompatActivity {

    private static TextView[][] score = new TextView[10][3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_scores_page);

        //get the game scores into scores variable
        //this variable consist of gameScore objects that each old marks for each player for a entire game-round
        ScoreBoard scoresBoard = ScoreBoard.getInstance();
        GameScore scores[] = scoresBoard.getScores();

        score[0][0] = findViewById(R.id.game1_player);
        score[0][1] = findViewById(R.id.game1_cpu1);
        score[0][2] = findViewById(R.id.game1_cpu2);
        score[1][0] = findViewById(R.id.game2_player);
        score[1][1] = findViewById(R.id.game2_cpu1);
        score[1][2] = findViewById(R.id.game2_cpu2);
        score[2][0] = findViewById(R.id.game3_player);
        score[2][1] = findViewById(R.id.game3_cpu1);
        score[2][2] = findViewById(R.id.game3_cpu2);
        score[3][0] = findViewById(R.id.game4_player);
        score[3][1] = findViewById(R.id.game4_cpu1);
        score[3][2] = findViewById(R.id.game4_cpu2);
        score[4][0] = findViewById(R.id.game5_player);
        score[4][1] = findViewById(R.id.game5_cpu1);
        score[4][2] = findViewById(R.id.game5_cpu2);
        score[5][0] = findViewById(R.id.game6_player);
        score[5][1] = findViewById(R.id.game6_cpu1);
        score[5][2] = findViewById(R.id.game6_cpu2);
        score[6][0] = findViewById(R.id.game7_player);
        score[6][1] = findViewById(R.id.game7_cpu1);
        score[6][2] = findViewById(R.id.game7_cpu2);
        score[7][0] = findViewById(R.id.game8_player);
        score[7][1] = findViewById(R.id.game8_cpu1);
        score[7][2] = findViewById(R.id.game8_cpu2);
        score[8][0] = findViewById(R.id.game9_player);
        score[8][1] = findViewById(R.id.game9_cpu1);
        score[8][2] = findViewById(R.id.game9_cpu2);
        score[9][0] = findViewById(R.id.game10_player);
        score[9][1] = findViewById(R.id.game10_cpu1);
        score[9][2] = findViewById(R.id.game10_cpu2);


        for (int i = 0; i < 10; i++) {

            if (i >= scoresBoard.getNumberOfScores()) {
                score[i][0].setText(" - ");
                score[i][1].setText(" - ");
                score[i][2].setText(" - ");
            } else {
                score[i][0].setText(String.valueOf(scores[i].getHuman()));
                score[i][1].setText(String.valueOf(scores[i].getComputerPlayer1()));
                score[i][2].setText(String.valueOf(scores[i].getComputerPlayer2()));
            }
        }

        setMedal();

    }


    public void setMedal() {
        int[] scores = ScoreBoard.getTotals();


        LottieAnimationView[] medals = new LottieAnimationView[]{this.findViewById(R.id.playerMedal),
                this.findViewById(R.id.cpu1Medal), this.findViewById(R.id.cpu2Medal)};

        LottieAnimationView playerMedal = (LottieAnimationView) this.findViewById(R.id.playerMedal);
        playerMedal.setVisibility(LottieAnimationView.VISIBLE);

        LottieAnimationView cpu1Medal = (LottieAnimationView) this.findViewById(R.id.cpu1Medal);
        cpu1Medal.setVisibility(LottieAnimationView.VISIBLE);

        LottieAnimationView cpu2Medal = (LottieAnimationView) this.findViewById(R.id.cpu2Medal);
        cpu2Medal.setVisibility(LottieAnimationView.VISIBLE);

        int max = scores[0];


        if(scores[0] == 0 && scores[1] == 0 && scores[2] == 0){
            medals[0].setVisibility(View.INVISIBLE);
            medals[1].setVisibility(View.INVISIBLE);
            medals[2].setVisibility(View.INVISIBLE);
        }

        else {

            for (int i = 1; i < scores.length; i++) {

                if (scores[i] > max) {
                    max = scores[i];
                    if (i == 2) {
                        medals[i - 2].setVisibility(View.INVISIBLE);
                    }
                    medals[i - 1].setVisibility(View.INVISIBLE);
                } else if (scores[i] < max) {
                    medals[i].setVisibility(View.INVISIBLE);
                }
            }
        }

    }
}