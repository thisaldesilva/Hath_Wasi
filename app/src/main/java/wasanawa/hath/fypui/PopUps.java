/*
package com.example.fypui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class PopUps {


    public static void openDialog(final Activity activity, final TextView textViewTrump, final String trump, final AbComputerPlayer comPlayer2,
                                  final AbComputerPlayer comPlayer1, final Player human, final Game game) {

        boolean playerAsking = false;

        AlertDialog.Builder getChances = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
        getChances.setMessage("Can you win 7 chances?")
                .setTitle("♠ ♥ ♣ ♦")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playerAsking = true;
                        selectTrump(activity, textViewTrump, trump);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(activity.getApplicationContext(), "yoohooosidojoioo " + trump, Toast.LENGTH_SHORT).show();

                        final TextView scoreLabel = (TextView) findViewById(R.id.textViewMyTeam);
                        final TextView myLabel = (TextView) findViewById(R.id.textViewOpponent);

                        Game game = Game.getInstance();
                        if (playerAsking == false) {
                            if (SelectingTrumpComPlayer.getChances(comPlayer2)) {
                                //trump = SelectingTrumpComPlayer.getTrump(comPlayer2);
                                passTrumpToTheInterface(trump, textViewTrump, trump);
                                playerAsking = true;
                                Toast.makeText(activity.getApplicationContext(), "Computer player 2 selected trump as " + trump, Toast.LENGTH_LONG).show();
                                scoreLabel.setText(comPlayer2.getName());
                                myLabel.setText("My Team");

                                game.alterInstance(comPlayer2, human, comPlayer1, human, comPlayer1, comPlayer2, comPlayer2, trump);
                                game.moveForwardWithCpuWin(comPlayer2);

                            } else if (SelectingTrumpComPlayer.getChances(comPlayer1)) {
                                trump = SelectingTrumpComPlayer.getTrump(comPlayer1);
                                passTrumpToTheInterface(trump);
                                playerAsking = true;
                                Toast.makeText(activity.getApplicationContext(), "Computer player 1 selected trump as " + trump, Toast.LENGTH_LONG).show();
                                scoreLabel.setText(comPlayer1.getName());
                                myLabel.setText("My Team");

                                game.alterInstance(comPlayer1, human, comPlayer2, human, comPlayer1, comPlayer2, comPlayer1, trump);
                                game.moveForwardWithCpuWin(comPlayer1);

                            } else {
                                Toast.makeText(activity.getApplicationContext(), "yoohooooo " + trump, Toast.LENGTH_LONG).show();
                                openDialog();

                                DeckOfCards card = new DeckOfCards();
                                human = new Player("Donald Trump", card);

                                comPlayer1 = new ComputerPlayerAgresive("Computer Player 1", card);
                                comPlayer2 = new ComputerPlayerAgresive("Computer Player 2", card);


                                comPlayer1.displayDetails();
                                comPlayer2.displayDetails();


                                AnimatorSet s = new AnimatorSet();
                                ArrayList<Animator> animations = new ArrayList<Animator>();


                                for (int i = 0; i < 12; i++) {
                                    cardArray[i].setImageResource(human.getCardImagePathFromIndex(i));

                                    final int j = i;
                                    ObjectAnimator animator = ObjectAnimator.ofFloat(cardArray[j], "translationY", 100f);
                                    animations.add(animator);
                                }
                                s.setDuration(200);
                                s.playSequentially(animations);
                                s.start();


                                imageToCardMap = imageViewToCardMap(human, cardArray);

                                //create the game with the starting player set as human
                                game.alterInstance(human, comPlayer1, comPlayer2, human, comPlayer1, comPlayer2, human, trump);


                                // game.playNextMove(null);
                            }
                        }

                    }
                });

        AlertDialog dialog = getChances.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }


    private static void selectTrump(final Activity activity, final TextView textViewTrump, final String trump) {

        AlertDialog.Builder chooseTrump = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogLayout = inflater.inflate(R.layout.activity_popup_window, null);
        String[] items = {"♠ Spades", "♥ Hearts", "♣ Clubs", "♦ Diamonds"};
        chooseTrump.setTitle("Select your Trump.")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passTrumpToTheInterface(which, textViewTrump, trump);
                    }
                })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TAG", "Inside on click : " + trump);
                        if (trump == null || trump.isEmpty()) {
                            Toast.makeText(activity.getApplicationContext(), "Please select a trump to continue!", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "the trump selected: " + trump);
                            selectTrump(activity, textViewTrump, trump);
                        } else {
                            dialog.dismiss();
                        }

                    }
                });

        AlertDialog dialog = chooseTrump.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }


    public static void passTrumpToTheInterface(int which, TextView textViewTrump, String trump) {

        //final TextView textViewTrump = (TextView) findViewById(R.id.trumpSelected);
        textViewTrump.setVisibility(View.VISIBLE);
        switch (which) {
            case 0:
                trump = "spades";
                Log.d("TAG", "Spades Selected: " + trump);
                textViewTrump.setText("♠");
                break;
            case 1:
                trump = "hearts";
                Log.d("TAG", "Hearts Selected: " + trump);
                textViewTrump.setText("♥");
                break;
            case 2:
                trump = "clubs";
                Log.d("TAG", "Clubs Selected: " + trump);
                textViewTrump.setText("♣");
                break;
            case 3:
                trump = "diamonds";
                Log.d("TAG", "Diamonds Selected: " + trump);
                textViewTrump.setText("♦");
                break;
        }

    }





    public static void passTrumpToTheInterface(String which ,TextView textViewTrump, String trump){

        //final TextView textViewTrump = (TextView) findViewById(R.id.trumpSelected);
        textViewTrump.setVisibility(View.VISIBLE);
        switch (which){
            case "spades":
                trump = "spades";
                Log.d( "TAG", "Spades Selected: " + trump);
                textViewTrump.setText("♠");
                break;
            case "hearts":
                trump = "hearts";
                Log.d( "TAG", "Hearts Selected: " + trump);
                textViewTrump.setText("♥");
                break;
            case "clubs":
                trump = "clubs";
                Log.d( "TAG", "Clubs Selected: " + trump);
                textViewTrump.setText("♣");
                break;
            case "diamonds":
                trump = "diamonds";
                Log.d( "TAG", "Diamonds Selected: " + trump);
                textViewTrump.setText("♦");
                break;
        }
    }

}
*/
