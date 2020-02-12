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


    static void playWin(){
        if(onOff){
            MediaPlayer player = MediaPlayer.create( activity, R.raw.win);
            player.start();
            player = null;
        }
    }

    private static void playLost(){
        if(onOff){
            MediaPlayer player = MediaPlayer.create( activity, R.raw.click_sound);
            player.start();
            player = null;
        }
    }

    static void cardClick(){
        if(onOff){
            MediaPlayer player = MediaPlayer.create( activity, R.raw.click_sound);
            player.start();
            player = null;
        }
    }


    static void cardCollect(){
        if(onOff){
            MediaPlayer player = MediaPlayer.create( activity, R.raw.click_sound);
            player.start();
            player = null;
        }
    }







}
