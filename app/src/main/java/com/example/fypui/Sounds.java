package com.example.fypui;

import android.app.Activity;
import android.media.MediaPlayer;

public class Sounds {
    private static boolean onOff;
    private static Activity activity;
    private static MediaPlayer player;
    //private MediaPlayer player;

    Sounds(Activity activity){
        this.onOff = true;
        this.activity = activity;
        this.player = MediaPlayer.create(activity, R.raw.click_sound);
    }


   public static void playWin(){
        if(onOff){
            playSound(R.raw.win);
        }
    }

    public static void playLost(){
        if(onOff){
            playSound(R.raw.sadsound);
        }
    }

   public static void cardClick(){
        if(onOff){
            playSound(R.raw.click_sound);
        }
    }


   public static void cardCollect(){
        if(onOff){
            playSound(R.raw.card_collectt);

        }
    }


    private static void playSound(int soundID){
        final MediaPlayer mp = MediaPlayer.create( activity ,soundID);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
    }




}
