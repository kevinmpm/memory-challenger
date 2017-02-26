package com.memorychallenger.projects.memorychallenger.models;

/**
 * Created by Kevin on 2/9/2017.
 */
import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TextAnswerQuestion extends Question implements QuestionInterface {
    protected String answer;
    //id;type;question;answer
    public TextAnswerQuestion(String content, String delimiter) {
        super(content, delimiter);
        answer = content.split(delimiter)[2];
    }

    public TextAnswerQuestion(String type, String question, String answer, String separator) {
        this.type = type;
        this.question = question;
        this.answer = answer;
        this.separator = separator;
    }

    @Override
    public String getId() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] byteData = md.digest((question + type + answer).getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return this.type + this.separator + this.question + this.separator + this.answer;
    }

    public String getAnswer() {
        return answer;
    }
}
