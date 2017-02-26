package com.memorychallenger.projects.memorychallenger.models;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 2/19/2017.
 */

public class Label {
    protected String name;
    protected ArrayList<String> questionIds;
    protected String separator;

    public Label() {}

    public Label(String name, String separator, ArrayList<String> questionIds) {
        this.name = name;
        this.separator = separator;
        this.questionIds = questionIds;
    }

    public Label(String labelStr, String separator) {
        String[] split = labelStr.split(separator);
        if (split.length < 2) throw new IllegalArgumentException("Label String invalid!");
        this.name = split[0];
        this.questionIds = new ArrayList<String>();
        for (int i = 1; i < split.length; i++) {
            questionIds.add(split[i]);
        }
        this.separator = separator;
    }

    public ArrayList<String> getQuestionIds() {
        return this.questionIds;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.name.toLowerCase();
    }

    @Override
    public String toString() {
        String text = this.name;
        for (String id : questionIds) {
            text += separator + id.toString();
        }
        return text;
    }

    @Override
    public Label clone() {
        return new Label(name, separator, questionIds);
    }


}
