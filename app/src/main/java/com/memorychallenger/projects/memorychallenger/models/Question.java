package com.memorychallenger.projects.memorychallenger.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kevin on 2/9/2017.
 */

public class Question implements QuestionInterface{
    protected String question;
    protected String type;
    protected String separator;

    public Question() {}

    public Question(String content, String separator) {
        String[] split = content.split(separator);
        type = split[0];
        question = split[1];
        this.separator = separator;
    }

    @Override
    public String toString() {
        return this.type + this.separator + this.question;
    }

    public String getId() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return new String(md.digest((question + type).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getQuestion() {
        return question;
    }

    public String getType() {
        return type;
    }
}
