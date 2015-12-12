package com.example.chloe.bceo.exception;

/**
 * Created by chloeshim on 12/11/15.
 */
public class ImageNotLoadedException extends Exception{

    public ImageNotLoadedException(String message){
        super(message);
    }

    public String getMessage(){
        return super.getMessage();
    }
}
