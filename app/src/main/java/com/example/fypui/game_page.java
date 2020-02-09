package com.example.fypui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class game_page extends AppCompatActivity {

    static HashMap<Integer, Card> imageToCardMap;
    private static ImageView[] cardArray = new ImageView[12];

    String trump;

    static AbComputerPlayer comPlayer1;
    static AbComputerPlayer comPlayer2;
    static Player human;
    private boolean playerAsking = false;
    private static int roundNumber = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.game_page);




        cardArray[0] = findViewById(R.id.playerCard1);
        cardArray[1] = findViewById(R.id.playerCard2);
        cardArray[2] = findViewById(R.id.playerCard3);
        cardArray[3] = findViewById(R.id.playerCard4);
        cardArray[4] = findViewById(R.id.playerCard5);
        cardArray[5] = findViewById(R.id.playerCard6);
        cardArray[6] = findViewById(R.id.playerCard7);
        cardArray[7] = findViewById(R.id.playerCard8);
        cardArray[8] = findViewById(R.id.playerCard9);
        cardArray[9] = findViewById(R.id.playerCard10);
        cardArray[10] = findViewById(R.id.playerCard11);
        cardArray[11] = findViewById(R.id.playerCard12);

        startGame();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openDialog();
            }
        }, 3000);



        //create the game with the starting player set as human
        Game game =  Game.getInstance(this, human, comPlayer1, comPlayer2, human, comPlayer1, comPlayer2, human, trump);







    }


    public static void startGame(){
        DeckOfCards card = new DeckOfCards();
        human = new Player("Donald Trump", card);

        comPlayer1 = new ComputerPlayerAgresive("Computer Player 1", card);
        comPlayer2 = new ComputerPlayerAgresive("Computer Player 2", card);


        comPlayer1.displayDetails();
        comPlayer2.displayDetails();


        AnimatorSet s = new AnimatorSet();
        ArrayList<Animator> animations = new ArrayList<Animator>() ;


        for (int i = 0; i<12; i++){
            cardArray[i].setImageResource(human.getCardImagePathFromIndex(i));
            cardArray[i].setVisibility(View.VISIBLE);
            final int j = i;
            ObjectAnimator animator = ObjectAnimator.ofFloat(cardArray[j], "translationY", 100f );
            animations.add(animator);
        }
        s.setDuration(200);
        s.playSequentially(animations);
        s.start();

        roundNumber = 0;

        imageToCardMap = imageViewToCardMap(human, cardArray);

        Game game =  Game.getInstance();
        game.setCpu1(comPlayer1);
        game.setCpu2(comPlayer2);
        game.setHumanPlayer(human);

    }


    private void selectTrump(){

        AlertDialog.Builder chooseTrump = new AlertDialog.Builder(game_page.this, R.style.AlertDialogStyle);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogLayout = inflater.inflate(R.layout.activity_popup_window, null);
        String[] items = {"♠ Spades", "♥ Hearts", "♣ Clubs", "♦ Diamonds"};
        chooseTrump.setTitle("Select your Trump.")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passTrumpToTheInterface(which);
                    }
                })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d( "TAG", "Inside on click : " + trump);
                        if (trump == null || trump.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please select a trump to continue!", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "the trump selected: " + trump);
                            selectTrump();
                        }
                        else{
                            dialog.dismiss();
                        }

                    }
                });

        AlertDialog dialog = chooseTrump.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void passTrumpToTheInterface(int which){

        final TextView textViewTrump = (TextView) findViewById(R.id.trumpSelected);
        textViewTrump.setVisibility(View.VISIBLE);
        switch (which){
            case 0:
                trump = "spades";
                Log.d( "TAG", "Spades Selected: " + trump);
                textViewTrump.setText("♠");
                break;
            case 1:
                trump = "hearts";
                Log.d( "TAG", "Hearts Selected: " + trump);
                textViewTrump.setText("♥");
                break;
            case 2:
                trump = "clubs";
                Log.d( "TAG", "Clubs Selected: " + trump);
                textViewTrump.setText("♣");
                break;
            case 3:
                trump = "diamonds";
                Log.d( "TAG", "Diamonds Selected: " + trump);
                textViewTrump.setText("♦");
                break;
        }
    }

    public void passTrumpToTheInterface(String which){

        final TextView textViewTrump = (TextView) findViewById(R.id.trumpSelected);
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
    public void openDialog(){
        AlertDialog.Builder getChances = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        getChances.setMessage("Can you win 7 chances?")
                .setTitle("♠ ♥ ♣ ♦")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playerAsking = true;
                        selectTrump();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "yoohooosidojoioo " + trump, Toast.LENGTH_SHORT).show();

                        final TextView scoreLabel = (TextView) findViewById(R.id.textViewMyTeam);
                        final TextView myLabel = (TextView) findViewById(R.id.textViewOpponent);

                        Game game = Game.getInstance();
                        if (playerAsking == false) {
                            if (SelectingTrumpComPlayer.getChances(comPlayer2)) {
                                trump = SelectingTrumpComPlayer.getTrump(comPlayer2);
                                passTrumpToTheInterface(trump);
                                playerAsking = true;
                                Toast.makeText(getApplicationContext(), "Computer player 2 selected trump as " + trump, Toast.LENGTH_LONG).show();
                                scoreLabel.setText(comPlayer2.getName());
                                myLabel.setText("My Team");

                                game.alterInstance( comPlayer2, human, comPlayer1, human, comPlayer1, comPlayer2, comPlayer2, trump);
                                game.moveForwardWithCpuWin(comPlayer2);

                            } else if (SelectingTrumpComPlayer.getChances(comPlayer1)) {
                                trump = SelectingTrumpComPlayer.getTrump(comPlayer1);
                                passTrumpToTheInterface(trump);
                                playerAsking = true;
                                Toast.makeText(getApplicationContext(), "Computer player 1 selected trump as " + trump, Toast.LENGTH_LONG).show();
                                scoreLabel.setText(comPlayer1.getName());
                                myLabel.setText("My Team");

                                game.alterInstance( comPlayer1, human, comPlayer2, human, comPlayer1, comPlayer2, comPlayer1, trump);
                                game.moveForwardWithCpuWin(comPlayer1);

                            } else {
                                Toast.makeText(getApplicationContext(), "yoohooooo " + trump, Toast.LENGTH_LONG).show();
                                openDialog();

                                DeckOfCards card = new DeckOfCards();
                                human = new Player("Donald Trump", card);

                                comPlayer1 = new ComputerPlayerAgresive("Computer Player 1", card);
                                comPlayer2 = new ComputerPlayerAgresive("Computer Player 2", card);


                                comPlayer1.displayDetails();
                                comPlayer2.displayDetails();


                                AnimatorSet s = new AnimatorSet();
                                ArrayList<Animator> animations = new ArrayList<Animator>() ;


                                for (int i = 0; i<12; i++){
                                    cardArray[i].setImageResource(human.getCardImagePathFromIndex(i));

                                    final int j = i;
                                    ObjectAnimator animator = ObjectAnimator.ofFloat(cardArray[j], "translationY", 100f );
                                    animations.add(animator);
                                }
                                s.setDuration(200);
                                s.playSequentially(animations);
                                s.start();



                                imageToCardMap = imageViewToCardMap(human, cardArray);

                                //create the game with the starting player set as human
                                game.alterInstance( human, comPlayer1, comPlayer2, human, comPlayer1, comPlayer2, human, trump);


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

    public static void playNewGame(Activity activity){
        //put would like to play the next round pop here

        roundNumber++;

        if(roundNumber % 3 == 0 ){
        }
        else if(roundNumber % 3 == 1){


        }
        else {

        }




    }



    private static HashMap< Integer, Card> imageViewToCardMap(Player player, ImageView[] views){

        int numOfCards = player.getNoOfCards();
        HashMap<Integer, Card> imageToCardMap = new HashMap<>();

        for (int i = 0; i < numOfCards; i++){
            imageToCardMap.put( views[i].getId() , player.getCardFromIndex(i) );
            Log.println( Log.ERROR, "TAG", "Adding Cards - " + views[i].getId() + "Card type and details - " + player.getCardFromIndex(i).getCategory() + " " + player.getCardFromIndex(i).getCardId() );

        }

        return imageToCardMap;

    }

    public void cardSelected(final View v){
        cardTouch(false);

        Log.println( Log.ERROR, "TAG", "Selected ImageView ID: " + v.getId());
        final Card selectedCard = imageToCardMap.get(v.getId());


        //remove the card from the user card deck
        //v.setVisibility(View.INVISIBLE);

//      int val = v.getId();
        Log.println( Log.ERROR, "TAG", "Should come Here");
        Log.println( Log.ERROR, "TAG", "Should come after this " + selectedCard.getCategory() + " Ko yako error eka" );
        Log.println( Log.ERROR, "TAG", "Should come after this " + selectedCard.getImageSource() + " path eka yako" );

//        ImageView img =  findViewById(R.id.playerCard1);
//        img.setImageResource(selectedCard.getImageSource());



//        ImageView image =  findViewById(R.id.playCard);
//        image.setImageResource(selectedCard.getImageSource());
//        image.setVisibility(View.VISIBLE);



        //human.getPlayerCards().remove(selectedCard);
        //human.setNumberOfCardsRemaining(human.getNumberOfCardsRemaining()-1);

        final Game game =  Game.getInstance();

        game.setTrumps(trump);
        game.playNextMove(selectedCard);

        if (game.getinvalidCardByHuman() == false ){

//                int startHeight = v.getHeight();
//                int startLeft = v.getLeft();
//                int startRight = v.getRight();

                final ImageView image =  findViewById(R.id.playCard);
                image.setImageResource(selectedCard.getImageSource());
                image.setVisibility(View.VISIBLE);

                Animation animation = AnimationUtils.loadAnimation(this, R.anim.sample_anim);

                image.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onAnimationStart(Animation animation) {
                        final MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.click_sound);
                        player.start();

                        image.setImageAlpha(1000);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onAnimationEnd(Animation animation) {

                        Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    image.setImageAlpha(0);
                                }
                            }, 5700);

                        //image.setImageResource(R.drawable.clubs6);

                        //image.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                //image.startAnimation(animation);
                //image.setVisibility(View.INVISIBLE);


                final int topPosition = image.getHeight();
                int leftPosition = image.getLeft();
                int rightPosition = image.getRight();

//                image.setTop(startHeight);
//                image.setLeft(startLeft);
//                image.setRight(startRight);

                human.getPlayerCards().remove(selectedCard);
                human.setNumberOfCardsRemaining(human.getNumberOfCardsRemaining()-1);

                //remove the card from the user card deck
                v.setVisibility(View.INVISIBLE);


//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                   // ObjectAnimator slideFromBottom = ObjectAnimator.ofFloat(image, "x", topPosition);
//                   // ObjectAnimator slideFromLeft = ObjectAnimator.ofFloat(image, "y", topPosition);
//                   // AnimatorSet aniSet = new AnimatorSet();
//                   // aniSet.playSequentially(slideFromBottom, slideFromLeft);
//                   // aniSet.start();
//                }
//            }, 0);

        }









    }


    public static void cardTouch(boolean onOrOff){
        for (int i = 0; i < 12; i++){
            cardArray[i].setClickable(onOrOff);
        }
    }





}