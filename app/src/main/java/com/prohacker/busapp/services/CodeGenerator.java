package com.prohacker.busapp.services;

import java.util.Random;

public class CodeGenerator {
    private String codeMessage;

    public CodeGenerator(){
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        // this will convert any number sequence into 6 character.
        codeMessage = String.format("%04d", number);
    }
    public String getCodeMessage(){
        return codeMessage;
    };
}
