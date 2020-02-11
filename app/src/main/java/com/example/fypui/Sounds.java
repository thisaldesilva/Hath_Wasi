package com.example.fypui;

import android.app.Activity;
import android.media.MediaPlayer;

public class Sounds {
    private static boolean onOff;
    private static Activity activity;

    Sounds(Activity activity){
        this.onOff = true;
        this.activity = activity;
    }


    private static void playWin(){
        if(onOff){
            final MediaPlayer player = MediaPlayer.create( activity, R.raw.win);
            player.start();
        }
    }

    private static void playLost(){
        if(onOff){
            final MediaPlayer player = MediaPlayer.create( activity, R.raw.click_sound);
            player.start();
        }
    }

    static void cardClick(){
        if(onOff){
            final MediaPlayer player = MediaPlayer.create( activity, R.raw.click_sound);
            player.start();
        }
    }


    static void cardCollect(){
        if(onOff){
            final MediaPlayer player = MediaPlayer.create( activity, R.raw.click_sound);
            player.start();
        }
    }







}
