package com.example.fypui;

public class GameExceptions extends Exception {
     public GameExceptions(String cardtype){
        super(cardtype);
    }

    public GameExceptions() {

    }
}
