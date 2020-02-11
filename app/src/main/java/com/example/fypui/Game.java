package com.example.fypui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.airbnb.lottie.LottieAnimationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {



    private static Game ourInstance;

    private static Player singlePlayer;
    private static Player teamPlayer1;
    private static Player teamPlayer2;

    private static Player humanPlayer;
    private static AbComputerPlayer cpu1;
    private static AbComputerPlayer cpu2;
    private static int singlePlayerScore;
    private static int teamScore;
    private static GameRound[] playedRounds;
    private static int numberOfRoundsPlayed;
    private static Player startPlayer;
    private static boolean invalidCardByHuman;
    private static Card com2Card;
    private static Player[] players;
    private static int playerTurnIndex;
    private static boolean gameFinish;
    private static Sounds sounds;


    private static String trumps;
    public Activity activity;


    private Game(Activity _activity, Player singlePlayer, Player teamPlayer1, Player teamPlayer2, Player humanPlayer,
                 AbComputerPlayer cpu1, AbComputerPlayer cpu2, Player startPlayer, String trumps) {
        this.singlePlayer = singlePlayer;
        this.teamPlayer1 = teamPlayer1;
        this.teamPlayer2 = teamPlayer2;
        this.singlePlayerScore = 0;
        this.playedRounds = new GameRound[12];
        this.numberOfRoundsPlayed = 0;
        this.cpu1 = cpu1;
        this.cpu2 = cpu2;
        this.startPlayer = startPlayer;
        this.trumps = trumps;
        this.humanPlayer = humanPlayer;
        this.activity = _activity;
        this.invalidCardByHuman =  false;
        this.players = new Player[]{humanPlayer, cpu2, cpu1};
        this.playerTurnIndex = 0;
        this.gameFinish = false;
        this.sounds = new Sounds(activity);

    }

    private Game(){}

    public static Game getInstance(Activity _activity, Player singlePlayer, Player teamPlayer1, Player teamPlayer2, Player humanPlayer, AbComputerPlayer cpu1, AbComputerPlayer cpu2, Player startPlayer, String trumps) {


        if(ourInstance == null) {
            ourInstance = new Game(_activity, singlePlayer, teamPlayer1, teamPlayer2, humanPlayer, cpu1, cpu2, startPlayer, trumps);
        }
        return ourInstance;
    }

    public static Game getInstance() {

        return ourInstance;
    }


    Card c1, c2;

    public void alterInstance(Player singlePlayer, Player teamPlayer1, AbComputerPlayer teamPlayer2, Player humanPlayer, AbComputerPlayer cpu1, AbComputerPlayer cpu2, Player startPlayer, String trumps) {
        this.singlePlayer = singlePlayer;
        this.teamPlayer1 = teamPlayer1;
        this.teamPlayer2 = teamPlayer2;
        this.singlePlayerScore = 0;
        this.teamScore = 0;
        this.playedRounds = new GameRound[12];
        this.numberOfRoundsPlayed = 0;
        this.cpu1 = cpu1;
        this.cpu2 = cpu2;
        this.startPlayer = startPlayer;
        this.trumps = trumps;
        this.humanPlayer = humanPlayer;
        this.invalidCardByHuman =  false;
        this.gameFinish = false;

    }


    public void updataRoundNumber(){
        final TextView roundNumber = activity.findViewById(R.id.textViewRoundTag);
        roundNumber.setText(ScoreBoard.getNumberOfScores() + 1 + "/10 Rounds");
    }

    public void  playNextMove(Card selectedCard){

        game_page.cardTouch(false);

        final ImageView com1 =  this.activity.findViewById(R.id.com1Card);
        final ImageView com2 =  this.activity.findViewById(R.id.com2Card);
        final ImageView playerPlaceholder =  this.activity.findViewById(R.id.playCard);

        if(this.numberOfRoundsPlayed == 0){
            if(startPlayer.getName() == "Computer Player 1")
            {

            }

            else if(startPlayer.getName() == "Computer Player 2"){

            }

            else{

            }
        }

        Log.println( Log.ERROR, "TAG", "startPlayer.getName() :" + startPlayer.getName()  );
        Log.println( Log.ERROR, "TAG", "this.numberOfRoundsPlayed :" + this.numberOfRoundsPlayed);

        if( (this.numberOfRoundsPlayed == 0 && startPlayer.getName() != "Computer Player 1" && startPlayer.getName() != "Computer Player 2" )|| (( this.numberOfRoundsPlayed > 0 && this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() != "Computer Player 1" && this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() != "Computer Player 2") )){

            try {

                game_page.cardTouch(false);

                Log.d( "TAG", "Entered previous winner Human");
                Log.println( Log.ERROR, "TAG", "Try block 1.." );

                Log.println(Log.ERROR, "TAG", "passing trumps in block 1 : " + this.trumps);



                GameRound gameRound = new GameRound( this.cpu1, this.cpu1.selectSmallestCardFromCategory(selectedCard.getCategory()),
                        this.cpu2, this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory()),
                        this.humanPlayer, selectedCard, selectedCard.getCategory() , this.trumps );

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                invalidCardByHuman = false;

                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                Log.println( Log.ERROR, "TAG", "afterrr" );

                com1.setImageResource(this.playedRounds[numberOfRoundsPlayed-1].getCompPlayer1Card().getImageSource());
                com2.setImageResource(this.playedRounds[numberOfRoundsPlayed-1].getCompPlayer2Card().getImageSource());
                com1.setVisibility(View.INVISIBLE);
                com2.setVisibility(View.INVISIBLE);


                this.cpu1.getCardDeck().remove(this.cpu1.selectSmallestCardFromCategory(selectedCard.getCategory()));
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu2.getCardDeck().remove(this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory()));
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining()-1);

                cpu1.displayDetails();
                cpu2.displayDetails();

                // add animation to the PC players cards.
                final Animation animationLr = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);
                final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        com2.startAnimation(animationRl);
                        animationRl.setAnimationListener(new Animation.AnimationListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationStart(Animation animation) {
                                sounds.cardClick();

                                ComputerPlayerCardViews.hideCardFromPlayer2();

                                com2.setVisibility(View.VISIBLE);
                                com2.setImageAlpha(1000);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                //com2.setScaleX(com2.getScaleX());

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }, 1500);



                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        com1.startAnimation(animationLr);
                        animationLr.setAnimationListener(new Animation.AnimationListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationStart(Animation animation) {
                                sounds.cardClick();
                                ComputerPlayerCardViews.hideCardFromPlayer1();

                                com1.setVisibility(View.VISIBLE);
                                com1.setImageAlpha(1000);
                            }

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }, 3000);


//
//                ObjectAnimator rotataeAnimation1 = ObjectAnimator.ofFloat(com1, "rotation", 0f, 360f);
//                ObjectAnimator rotataeAnimation2 = ObjectAnimator.ofFloat(com2, "rotation", 0f, 360f);
//                rotataeAnimation1.setDuration(1500);
//                rotataeAnimation2.setDuration(2000);

//                final AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.playTogether(rotataeAnimation1, rotataeAnimation2);
//                animatorSet.start();

                //Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(winner);
                    }
                }, 2500);



                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){

                    moveForwardWithCpuWin();

                }
                else {

                    //Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sounds.cardCollect();

                            com1.setVisibility(View.INVISIBLE);
                            com2.setVisibility(View.INVISIBLE);
                            playerPlaceholder.setVisibility(View.INVISIBLE);
                            game_page.cardTouch(true);

                        }
                    }, 6000);
                }

                Log.println( Log.ERROR, "TAG", "inside the try 1, at the very end" );

            } catch (Exception e) {
                //gameExceptions.printStackTrace();
                //write the pop code for the invalid card selection
                numberOfRoundsPlayed--;

                Log.println( Log.ERROR, "TAG", "on the first catch block" );
                popUpDialog("Invalid Card type!", "Card Selection");
                this.invalidCardByHuman = true;
                game_page.cardTouch(true);


            }


            //depending upon the winner move forward


        }

        else if(( (this.numberOfRoundsPlayed == 0 &&  (startPlayer.getName() == "Computer Player 1") )|| ((this.numberOfRoundsPlayed > 0 && this.playedRounds[numberOfRoundsPlayed-1].getWinner().getName() == "Computer Player 1" )))){

            try {

                Log.println(Log.ERROR, "TAG", "Rolly");

                Log.println(Log.ERROR, "TAG", "passing trumps in block 1 : " + this.trumps);

                if(this.invalidCardByHuman == false) {
                    com2Card = this.cpu2.selectSmallestCardFromCategory(selectedCard.getCategory());
                    Log.println(Log.ERROR, "TAG", "inside the if condition");
                }

                Log.println(Log.ERROR, "TAG", "outside if condition");

                GameRound gameRound = new GameRound( this.cpu1, c1,
                        this.cpu2, com2Card,
                        this.humanPlayer, selectedCard,c1.getCategory() ,this.trumps );

                Log.println(Log.ERROR, "TAG", "after game round object");

                invalidCardByHuman = false;

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                //setComputerCardsToImageView(c1, com2Card, com1, com2);
                com2.setImageResource(com2Card.getImageSource());
                com2.setVisibility(View.INVISIBLE);

                final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        com2.startAnimation(animationRl);
                        animationRl.setAnimationListener(new Animation.AnimationListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationStart(Animation animation) {
                                sounds.cardClick();
                                ComputerPlayerCardViews.hideCardFromPlayer2();

                                com2.setVisibility(View.VISIBLE);
                                com2.setImageAlpha(1000);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                //com2.setScaleX(com2.getScaleX());

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }, 2500);



                this.cpu1.getCardDeck().remove(c1);
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu2.getCardDeck().remove(com2Card);
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining()-1);

                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();

                //Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(winner);
                    }
                }, 2500);

                Log.println( Log.ERROR, "TAG", "inside try block 2" );


                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){

                    moveForwardWithCpuWin();

                }
                else {

                    //Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sounds.cardCollect();
                            com1.setVisibility(View.INVISIBLE);
                            com2.setVisibility(View.INVISIBLE);
                            playerPlaceholder.setVisibility(View.INVISIBLE);
                            game_page.cardTouch(true);
                        }
                    }, 6000);
                }

                Log.println( Log.ERROR, "TAG", "inside the try 2, at the very end" );


            } catch (Exception e) {
                Log.d("TAG", "on the second catch block");
                //gameExceptions.printStackTrace();

                //numberOfRoundsPlayed--;

                popUpDialog("Invalid Card type!", "Card Selection");
                this.invalidCardByHuman = true;
                game_page.cardTouch(true);
            }


        }
        else{

            try {

                Log.println( Log.ERROR, "TAG", "Try block 3.." );

                Log.println(Log.ERROR, "TAG", "passing trumps in block 1 : " + this.trumps);

                GameRound gameRound = new GameRound( this.cpu1, c1,
                        this.cpu2, c2,
                        this.humanPlayer, selectedCard,c2.getCategory() ,this.trumps );

                invalidCardByHuman = false;

                this.playedRounds[this.numberOfRoundsPlayed++] = gameRound;

                if(numberOfRoundsPlayed == 0) {
                    setComputerCardsToImageView(c1, c2, com1, com2);
                }

                this.cpu1.getCardDeck().remove(c1);
                this.cpu1.setNumberOfCardsRemaining(cpu1.getNumberOfCardsRemaining()-1);

                this.cpu1.getCardDeck().remove(c2);
                this.cpu2.setNumberOfCardsRemaining(cpu2.getNumberOfCardsRemaining()-1);

                final Player winner = this.playedRounds[numberOfRoundsPlayed-1].getWinner();


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateScore(winner);
                    }
                }, 2500);

                //Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sounds.cardCollect();

                        com1.setVisibility(View.INVISIBLE);
                        com2.setVisibility(View.INVISIBLE);
                        playerPlaceholder.setVisibility(View.INVISIBLE);

                        if(winner.getName() != "Computer Player 1" && winner.getName() != "Computer Player 2") {
                            game_page.cardTouch(true);
                        }

                    }
                }, 6000);

                Log.println( Log.ERROR, "TAG", "inside try block 3" );

                if(this.playedRounds[numberOfRoundsPlayed-1].getWinner() instanceof AbComputerPlayer){

                    moveForwardWithCpuWin();

                }
                else {

                    //Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            com1.setVisibility(View.INVISIBLE);
                            com2.setVisibility(View.INVISIBLE);
                            playerPlaceholder.setVisibility(View.INVISIBLE);
                            game_page.cardTouch(true);

                        }
                    }, 6000);
                }

                Log.println( Log.ERROR, "TAG", "inside the try 3, at the very end" );

            }
            catch (Exception e) {
                Log.d("TAG", "on the third catch block");
                popUpDialog("Invalid Card type!", "Card Selection");

                this.invalidCardByHuman = true;
                game_page.cardTouch(true);
                //gameExceptions.printStackTrace();
            }

        }

    }

    public void setComputerCardsToImageView(Card cardLeft, Card cardRight, final ImageView leftView, final ImageView rightView){


        ComputerPlayerCardViews.hideCardFromPlayer1();
        ComputerPlayerCardViews.hideCardFromPlayer2();
        leftView.setImageResource(cardLeft.getImageSource());
        rightView.setImageResource(cardRight.getImageSource());
        leftView.setVisibility(View.VISIBLE);
        rightView.setVisibility(View.VISIBLE);


//        final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);
//        final Animation animationLr = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rightView.startAnimation(animationRl);
//                animationRl.setAnimationListener(new Animation.AnimationListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        rightView.setVisibility(View.VISIBLE);
//                        rightView.setImageAlpha(1000);
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        //com2.setScaleX(com2.getScaleX());
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//            }
//        }, 1500);
//
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                leftView.startAnimation(animationLr);
//                animationLr.setAnimationListener(new Animation.AnimationListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        leftView.setVisibility(View.VISIBLE);
//                        leftView.setImageAlpha(1000);
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        //com2.setScaleX(com2.getScaleX());
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//            }
//        }, 3000);


    }



    public void moveForwardWithCpuWin(){

        //move forward only if the current game is not finished

        game_page.cardTouch(false);

        if(numberOfRoundsPlayed < 12 && gameFinish == false) {

            final AnimatorSet animatorSet = new AnimatorSet();

            final ImageView com1 = this.activity.findViewById(R.id.com1Card);
            final ImageView com2 = this.activity.findViewById(R.id.com2Card);
            final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);

            Log.println(Log.ERROR, "TAG", "Late night testing2 ");
//
//        ObjectAnimator ani = ObjectAnimator.ofFloat(com2, "rotation", 0f, 360f);
//        ani.setDuration(1500);
//        animatorSet.play(ani);
//        animatorSet.start();

            final GameRound pr = this.playedRounds[numberOfRoundsPlayed - 1];

            if (this.playedRounds[numberOfRoundsPlayed - 1].getWinner().getName() == cpu1.getName()) {

                //ImageView com1 =  this.activity.findViewById(R.id.com1Card);
                //ImageView com2 =  this.activity.findViewById(R.id.com2Card);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(gameFinish == false) {

                            playerPlaceholder.setVisibility(View.INVISIBLE);
                            com2.setVisibility(View.INVISIBLE);

                            c1 = ((AbComputerPlayer) pr.getWinner()).selectHighestCard();
                            com1.setImageResource(c1.getImageSource());
                            com1.setVisibility(View.VISIBLE);

                            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);

                            com1.startAnimation(animation);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    sounds.cardClick();
                                    ComputerPlayerCardViews.hideCardFromPlayer1();

                                    com1.setImageAlpha(1000);
                                }

                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }

                            });


                            //cpu1.getCardDeck().remove(c1);
                            //cpu1.setNumberOfCardsRemaining(cpu1.numberOfCardsRemaining-1);

//                    game_page.cardTouch(true);
//                    ObjectAnimator ani = ObjectAnimator.ofFloat(com2, "rotation", 0f, 360f);
//                    ani.setDuration(1500);
//                    animatorSet.play(ani);
//                    animatorSet.start();
                        }
                        game_page.cardTouch(true);
                    }

                }, 6000);

                //com1.setImageResource(((AbComputerPlayer) pr.getWinner()).selectHighestCard().getImageSource());

            } else {


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (gameFinish == false) {

                            playerPlaceholder.setVisibility(View.INVISIBLE);


                            c2 = ((AbComputerPlayer) pr.getWinner()).selectHighestCard();
                            c1 = cpu1.selectSmallestCardFromCategory(c2.getCategory());

                            com2.setImageResource(c2.getImageSource());
                            com1.setImageResource(c1.getImageSource());
                            com2.setVisibility(View.INVISIBLE);
                            com1.setVisibility(View.INVISIBLE);

                            final Animation animationLr = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);
                            final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);


                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    com2.startAnimation(animationRl);
                                    animationRl.setAnimationListener(new Animation.AnimationListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            sounds.cardClick();

                                            ComputerPlayerCardViews.hideCardFromPlayer2();

                                            com2.setVisibility(View.VISIBLE);
                                            com2.setImageAlpha(1000);

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            //com2.setScaleX(com2.getScaleX());

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                }
                            }, 1500);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    com1.startAnimation(animationLr);
                                    animationLr.setAnimationListener(new Animation.AnimationListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            sounds.cardClick();

                                            com1.setVisibility(View.VISIBLE);
                                            com1.setImageAlpha(1000);
                                            ComputerPlayerCardViews.hideCardFromPlayer1();

                                        }

                                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        @Override
                                        public void onAnimationEnd(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                    game_page.cardTouch(true);

                                }
                            }, 3000);


                            //cpu1.getCardDeck().remove(c1);
                            //cpu1.setNumberOfCardsRemaining(cpu1.numberOfCardsRemaining-1);
                            //cpu2.getCardDeck().remove(c2);
                            //cpu2.setNumberOfCardsRemaining(cpu2.numberOfCardsRemaining-1);


//                    ObjectAnimator ani = ObjectAnimator.ofFloat(com2, "rotation", 0f, 360f);
//                    ani.setDuration(1500);
//                    animatorSet.play(ani);
//                    animatorSet.start();
                        }
                        //game_page.cardTouch(true);
                    }
                }, 6000);

            }
        }
    }

    public void moveForwardWithCpuWin(Player player){

        if(this.numberOfRoundsPlayed < 12) {

            final AnimatorSet animatorSet = new AnimatorSet();

            final ImageView com1 = this.activity.findViewById(R.id.com1Card);
            final ImageView com2 = this.activity.findViewById(R.id.com2Card);
            final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);

            Log.println(Log.ERROR, "TAG", "Late night testing2 ");
//
//        ObjectAnimator ani = ObjectAnimator.ofFloat(com2, "rotation", 0f, 360f);
//        ani.setDuration(1500);
//        animatorSet.play(ani);
//        animatorSet.start();


            if (player.getName() == cpu1.getName()) {

                //ImageView com1 =  this.activity.findViewById(R.id.com1Card);
                //ImageView com2 =  this.activity.findViewById(R.id.com2Card);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        playerPlaceholder.setVisibility(View.INVISIBLE);
                        com2.setVisibility(View.INVISIBLE);

                        c1 = cpu1.selectHighestCard();
                        com1.setImageResource(c1.getImageSource());
                        com1.setVisibility(View.VISIBLE);

                        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);

                        com1.startAnimation(animation);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationStart(Animation animation) {
                                sounds.cardClick();
                                ComputerPlayerCardViews.hideCardFromPlayer1();

                                com1.setImageAlpha(1000);
                            }

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });


                        //cpu1.getCardDeck().remove(c1);
                        //cpu1.setNumberOfCardsRemaining(cpu1.numberOfCardsRemaining-1);

//                    game_page.cardTouch(true);
//                    ObjectAnimator ani = ObjectAnimator.ofFloat(com2, "rotation", 0f, 360f);
//                    ani.setDuration(1500);
//                    animatorSet.play(ani);
//                    animatorSet.start();
                        game_page.cardTouch(true);

                    }
                }, 2000);

                //com1.setImageResource(((AbComputerPlayer) pr.getWinner()).selectHighestCard().getImageSource());

            } else {


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playerPlaceholder.setVisibility(View.INVISIBLE);

                        c2 = cpu2.selectHighestCard();
                        c1 = cpu1.selectSmallestCardFromCategory(c2.getCategory());

                        com2.setImageResource(c2.getImageSource());
                        com1.setImageResource(c1.getImageSource());
                        com2.setVisibility(View.INVISIBLE);
                        com1.setVisibility(View.INVISIBLE);

                        final Animation animationLr = AnimationUtils.loadAnimation(activity, R.anim.lefttoright);
                        final Animation animationRl = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);


                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                com2.startAnimation(animationRl);
                                animationRl.setAnimationListener(new Animation.AnimationListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        sounds.cardClick();
                                        ComputerPlayerCardViews.hideCardFromPlayer2();

                                        com2.setVisibility(View.VISIBLE);
                                        com2.setImageAlpha(1000);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        //com2.setScaleX(com2.getScaleX());

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }
                        }, 1500);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                com1.startAnimation(animationLr);
                                animationLr.setAnimationListener(new Animation.AnimationListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        sounds.cardClick();
                                        ComputerPlayerCardViews.hideCardFromPlayer1();

                                        com1.setVisibility(View.VISIBLE);
                                        com1.setImageAlpha(1000);
                                    }

                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                game_page.cardTouch(true);

                            }
                        }, 3000);


                        //cpu1.getCardDeck().remove(c1);
                        //cpu1.setNumberOfCardsRemaining(cpu1.numberOfCardsRemaining-1);
                        //cpu2.getCardDeck().remove(c2);
                        //cpu2.setNumberOfCardsRemaining(cpu2.numberOfCardsRemaining-1);


//                    ObjectAnimator ani = ObjectAnimator.ofFloat(com2, "rotation", 0f, 360f);
//                    ani.setDuration(1500);
//                    animatorSet.play(ani);
//                    animatorSet.start();
                        //game_page.cardTouch(true);
                    }
                }, 5000);

            }
        }
    }


    public void updateScore(Player winningPlayer ){


        if(this.singlePlayer.getName() == winningPlayer.getName()){
            final TextView playerScorePlaceHolder =  this.activity.findViewById(R.id.textViewMyScore);
            int previousScore = Integer.parseInt((String) playerScorePlaceHolder.getText());
            previousScore++;
            this.singlePlayerScore++;
            String score =  Integer.toString(previousScore);
            playerScorePlaceHolder.setText(score);

            if (this.singlePlayerScore == 7){

                if(this.singlePlayer.getName() == "Computer Player 1"){
                    ScoreBoard.getInstance().setScore(new GameScore(0,2,0));
                    losingAnimation();
                    openDialog();
                }
                else if(this.singlePlayer.getName() == "Computer Player 2"){
                    ScoreBoard.getInstance().setScore(new GameScore(0,0,2));
                    losingAnimation();
                    openDialog();
                }
                else {
                    ScoreBoard.getInstance().setScore(new GameScore(2,0,0));
                    winningAnimation();
                    openDialog();
                }

            }

        }else {
            final TextView playerScorePlaceHolder =  this.activity.findViewById(R.id.textViewOpponentScore);
            int previousScore = Integer.parseInt((String) playerScorePlaceHolder.getText());
            previousScore++;
            this.teamScore++;
            String score =  Integer.toString(previousScore);
            playerScorePlaceHolder.setText(score);

            if (this.teamScore == 7){

                if(this.singlePlayer.getName() == "Computer Player 1"){
                    ScoreBoard.getInstance().setScore(new GameScore(1,0,1));
                    winningAnimation();
                    openDialog();
                }
                else if(this.singlePlayer.getName() == "Computer Player 2"){
                    ScoreBoard.getInstance().setScore(new GameScore(1,1,2));
                    winningAnimation();
                    openDialog();
                }
                else {
                    ScoreBoard.getInstance().setScore(new GameScore(0,1,1));
                    losingAnimation();
                    openDialog();
                }
            }
        }

        if (this.teamScore == 6 && this.singlePlayerScore == 6){
            this.gameFinish = true;
            LottieAnimationView anim = this.activity.findViewById(R.id.draw);
            anim.setVisibility(LottieAnimationView.VISIBLE);
            ScoreBoard.getInstance().setScore(new GameScore(0,0,0));
            openDialog();

        }
    }

    public void winnigOrLoosingAnimationOff(){
        LottieAnimationView anim1 = this.activity.findViewById(R.id.confetti1);
        anim1.setVisibility(LottieAnimationView.INVISIBLE);

        LottieAnimationView anim2 = this.activity.findViewById(R.id.confetti2);
        anim2.setVisibility(LottieAnimationView.INVISIBLE);

        LottieAnimationView anim3 = this.activity.findViewById(R.id.sadface);
        anim3.setVisibility(LottieAnimationView.INVISIBLE);

        LottieAnimationView anim = this.activity.findViewById(R.id.draw);
        anim.setVisibility(LottieAnimationView.INVISIBLE);
    }

    public void winningAnimation(){
        this.gameFinish = true;
        Sounds.playWin();
        LottieAnimationView anim1 = this.activity.findViewById(R.id.confetti1);
        anim1.setVisibility(LottieAnimationView.VISIBLE);

        LottieAnimationView anim2 = this.activity.findViewById(R.id.confetti2);
        anim2.setVisibility(LottieAnimationView.VISIBLE);
    }

    public void losingAnimation(){
        this.gameFinish = true;
        LottieAnimationView anim1 = this.activity.findViewById(R.id.sadface);
        anim1.setVisibility(LottieAnimationView.VISIBLE);
    }

    public void popUpDialog(String message, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity,R.style.AlertDialogStyle);
        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();

    }



    public void createNewGame(Player human, AbComputerPlayer comPlayer1, AbComputerPlayer comPlayer2){

        //first turn off any winning or loosing animations
        //then set the score labels to 0
        winnigOrLoosingAnimationOff();
        final TextView scorelabel1 =  this.activity.findViewById(R.id.textViewMyScore);
        final TextView scorelabel2 =  this.activity.findViewById(R.id.textViewOpponentScore);
        scorelabel1.setText("0");
        scorelabel2.setText("0");

        final TextView scoreLabel = (TextView) activity.findViewById(R.id.textViewMyTeam);
        final TextView myLabel = (TextView) activity.findViewById(R.id.textViewOpponent);

        //Player askingPlayer = players[this.playerTurnIndex++];


        Log.println( Log.ERROR, "TAG", "Player turn index : " + playerTurnIndex);

        int checkIndex = ++playerTurnIndex;

        Log.println( Log.ERROR, "TAG", "Check index index : " + checkIndex);


        //animations for the clearing old game and
        //creating the new game should come-up here


        ComputerPlayerCardViews.makeAllCardsVisible();


        //create new card-deck and player instances for the new game
        game_page.startGame();

        Game game = new Game();

        boolean trigger = false;

        //for player two (CPU) given the chance first
        if(checkIndex % 3 == 1){
            if(SelectingTrumpComPlayer.getChances(cpu2)){

                Log.println(Log.ERROR, "TAG", "Player 2 Before selecting trumps" + this.trumps);

                this.trumps = SelectingTrumpComPlayer.getTrump(cpu2);

                passTrumpToTheInterface(this.trumps);

                Log.println(Log.ERROR, "TAG", "Player 2 After selecting trumps: " + this.trumps);

                Toast.makeText(activity.getApplicationContext(), "Computer player 2 selected trump as " + this.trumps, Toast.LENGTH_LONG).show();
                scoreLabel.setText(comPlayer2.getName());
                myLabel.setText("My Team");

                game.alterInstance( cpu2, humanPlayer, cpu1, humanPlayer, cpu1, cpu2, cpu2, this.trumps);

                Log.println(Log.ERROR, "TAG", "in the new instance trumps: " + this.trumps);

                game_page.cardTouch(true);

                moveForwardWithCpuWin(cpu2);
            }
            else{
                checkIndex ++;
            }

        }

        //for player one (CPU) given the chance
         if(checkIndex % 3 == 2){

            if(SelectingTrumpComPlayer.getChances(cpu1)){

                Log.println(Log.ERROR, "TAG", "Player 1 Before selecting trumps: " + this.trumps);

                this.trumps = SelectingTrumpComPlayer.getTrump(cpu1);
                passTrumpToTheInterface(this.trumps);

                Log.println(Log.ERROR, "TAG", "Player 1 After selecting trumps: " + this.trumps);
                
                Toast.makeText(activity.getApplicationContext(), "Computer player 1 selected trump as " + this.trumps, Toast.LENGTH_LONG).show();
                scoreLabel.setText(comPlayer1.getName());
                myLabel.setText("My Team");

                game.alterInstance( cpu1, humanPlayer, cpu2, humanPlayer, cpu1, cpu2, cpu1, this.trumps);

                Log.println(Log.ERROR, "TAG", "in the new instance trumps: " + this.trumps);

                game_page.cardTouch(false);


                moveForwardWithCpuWin(cpu1);

            }
            else {
                checkIndex++;
            }

        }

        if(checkIndex % 3 == 0){
            openCanDialog( human,  comPlayer1,  comPlayer2);
            scoreLabel.setText("My Team");
            myLabel.setText("Opponent");

            game.alterInstance( humanPlayer, cpu1, cpu2, humanPlayer, cpu1, cpu2, humanPlayer, this.trumps);

        }




    }



    public void openCanDialog(final Player human, final AbComputerPlayer comPlayer1, final AbComputerPlayer comPlayer2){
        AlertDialog.Builder getChances = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
        getChances.setMessage("Can you win 7 chances?")
                .setTitle("♠ ♥ ♣ ♦")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectTrump();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createNewGame( human,  comPlayer1,  comPlayer2);
                    }
                });

        AlertDialog dialog = getChances.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();
    }


    private void selectTrump(){



        this.trumps = null;
        AlertDialog.Builder chooseTrump = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogLayout = inflater.inflate(R.layout.activity_popup_window, null);
        String[] items = {"♠ Spades", "♥ Hearts", "♣ Clubs", "♦ Diamonds"};
        chooseTrump.setTitle("Select your Trump.")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passTrumpToTheInterface(which);
                        game_page.cardTouch(true);
                    }
                })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d( "TAG", "Inside on click : " + trumps);
                        if (trumps == null || trumps.isEmpty()) {
                            Toast.makeText(activity.getApplicationContext(), "Please select a trump to continue!", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "the trump selected: " + trumps);
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
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();
    }

    public void setCardVisibility(boolean visibility){
        final ImageView com1 = this.activity.findViewById(R.id.com1Card);
        final ImageView com2 = this.activity.findViewById(R.id.com2Card);
        final ImageView playerPlaceholder = this.activity.findViewById(R.id.playCard);

        if(visibility == false){
            com1.setVisibility(View.INVISIBLE);
            com2.setVisibility(View.INVISIBLE);
            playerPlaceholder.setVisibility(View.INVISIBLE);
        }
        else {
            com1.setVisibility(View.VISIBLE);
            com2.setVisibility(View.VISIBLE);
            playerPlaceholder.setVisibility(View.VISIBLE);

        }
    }



    public void openDialog(){

        if(ScoreBoard.getInstance().gameFinish()){
            ourInstance = null;
            Intent intent = new Intent(activity, scores_page.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        }
        else {

            Log.println(Log.ERROR, "TAG", "Inside openDialog");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setCardVisibility(false);
                    AlertDialog.Builder getChances = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
                    getChances.setMessage("Would you like to play the next round?")
                            .setTitle("♠ ♥ ♣ ♦")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    createNewGame(humanPlayer, cpu1, cpu2);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ourInstance = null;
                                    activity.finish();
                                }


                            });

                    AlertDialog dialog = getChances.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
                    dialog.show();
                }
            }, 3000);

            Log.println(Log.ERROR, "TAG", "Exiting openDialog");
        }

    }


    public void passTrumpToTheInterface(String which){

        final TextView textViewTrump = (TextView) activity.findViewById(R.id.trumpSelected);
        textViewTrump.setVisibility(View.VISIBLE);
        switch (which){
            case "spades":
                this.trumps = "spades";
                Log.d( "TAG", "Spades Selected: " + trumps);
                textViewTrump.setText("♠");
                break;
            case "hearts":
                this.trumps = "hearts";
                Log.d( "TAG", "Hearts Selected: " + trumps);
                textViewTrump.setText("♥");
                break;
            case "clubs":
                this.trumps = "clubs";
                Log.d( "TAG", "Clubs Selected: " + trumps);
                textViewTrump.setText("♣");
                break;
            case "diamonds":
                this.trumps = "diamonds";
                Log.d( "TAG", "Diamonds Selected: " + trumps);
                textViewTrump.setText("♦");
                break;
        }
    }

    public void passTrumpToTheInterface(int which){

        final TextView textViewTrump = (TextView) activity.findViewById(R.id.trumpSelected);
        textViewTrump.setVisibility(View.VISIBLE);
        switch (which){
            case 0:
                this.trumps = "spades";
                Log.d( "TAG", "Spades Selected: " + this.trumps);
                textViewTrump.setText("♠");
                break;
            case 1:
                this.trumps = "hearts";
                Log.d( "TAG", "Hearts Selected: " + this.trumps);
                textViewTrump.setText("♥");
                break;
            case 2:
                this.trumps = "clubs";
                Log.d( "TAG", "Clubs Selected: " + this.trumps);
                textViewTrump.setText("♣");
                break;
            case 3:
                this.trumps = "diamonds";
                Log.d( "TAG", "Diamonds Selected: " + this.trumps);
                textViewTrump.setText("♦");
                break;
        }
    }




    public static String getTrumps() {
        return trumps;
    }

    public static void setTrumps(String trumps) {
        Game.trumps = trumps;
    }

    public static Player getSinglePlayer() {
        return singlePlayer;
    }

    public static void setSinglePlayer(Player singlePlayer) {
        Game.singlePlayer = singlePlayer;
    }

    public static Player getTeamPlayer1() {
        return teamPlayer1;
    }

    public static void setTeamPlayer1(Player teamPlayer1) {
        Game.teamPlayer1 = teamPlayer1;
    }

    public static Player getTeamPlayer2() {
        return teamPlayer2;
    }

    public static void setTeamPlayer2(Player teamPlayer2) {
        Game.teamPlayer2 = teamPlayer2;
    }

    public static int getSinglePlayerScore() {
        return singlePlayerScore;
    }

    public static void setSinglePlayerScore(int singlePlayerScore) {
        Game.singlePlayerScore = singlePlayerScore;
    }

    public static int getTeamScore() {
        return teamScore;
    }

    public static void setTeamScore(int teamScore) {
        Game.teamScore = teamScore;
    }

    public static AbComputerPlayer getCpu1() {
        return cpu1;
    }

    public static void setCpu1(AbComputerPlayer cpu1) {
        Game.cpu1 = cpu1;
    }

    public static AbComputerPlayer getCpu2() {
        return cpu2;
    }

    public static void setCpu2(AbComputerPlayer cpu2) {
        Game.cpu2 = cpu2;
    }

    public static GameRound[] getPlayedRounds() {
        return playedRounds;
    }

    public static void setPlayedRounds(GameRound[] playedRounds) {
        Game.playedRounds = playedRounds;
    }

    public static int getNumberOfRoundsPlayed() {
        return numberOfRoundsPlayed;
    }

    public static void setNumberOfRoundsPlayed(int numberOfRoundsPlayed) {
        Game.numberOfRoundsPlayed = numberOfRoundsPlayed;
    }

    public static Player getStartPlayer() {
        return startPlayer;
    }

    public static void setStartPlayer(Player startPlayer) {
        Game.startPlayer = startPlayer;
    }


    public static Player getHumanPlayer() {
        return humanPlayer;
    }

    public static void setHumanPlayer(Player humanPlayer) {
        Game.humanPlayer = humanPlayer;
    }

    public static boolean getinvalidCardByHuman(){ return invalidCardByHuman; }

    public static void setinvalidCardByHuman( boolean invalidCardByHuman) { Game.invalidCardByHuman = invalidCardByHuman; }

    public static Game getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Game ourInstance) {
        Game.ourInstance = ourInstance;
    }

}
