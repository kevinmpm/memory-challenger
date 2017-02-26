package com.memorychallenger.projects.memorychallenger.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.memorychallenger.projects.memorychallenger.R;
import com.memorychallenger.projects.memorychallenger.models.Label;
import com.memorychallenger.projects.memorychallenger.models.Question;
import com.memorychallenger.projects.memorychallenger.models.TextAnswerQuestion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static HashMap<String, Question> questionMap = new HashMap<String, Question> ();
    //key is lowercase label name
    public static HashMap<String, Label> labelMap = new HashMap<String, Label>();

    public void loadQuestionsFromFile() {
        try {
            //TODO initialize with size
            questionMap = new HashMap<String, Question> ();
            BufferedReader reader;
            try {
                FileInputStream fin = openFileInput(getString(R.string.question_file));
                reader = new BufferedReader(new InputStreamReader(fin));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(getString(R.string.separator));
                if (split.length < 2) continue;
                switch(split[0]) {
                    case "sa":
                        Question q = new TextAnswerQuestion(line, getString(R.string.separator));
                        questionMap.put(q.getId(), q);
                        break;
                    case "la":
                        q = new TextAnswerQuestion(line, getString(R.string.separator));
                        questionMap.put(q.getId(), q);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLabelsFromFile() {
        try {
            //TODO initialize with size
            labelMap = new HashMap<String, Label>();
            BufferedReader reader;
            try {
                FileInputStream fin = openFileInput(getString(R.string.label_file));
                reader = new BufferedReader(new InputStreamReader(fin));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Label label = new Label(line, getString(R.string.separator));
                    labelMap.put(label.getId(), label);
                } catch (IllegalArgumentException e) {
                    //bad format..
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadQuestionsFromFile();
        loadLabelsFromFile();
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(DisplayMessageActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void createQuestion(View view) {
        Intent intent = new Intent(this, CreateQuestionActivity.class);
        startActivity(intent);
    }

    public void playGame(View view) {
        Intent intent = new Intent(this, PreGameActivity.class);
        startActivity(intent);
    }
}
