package com.memorychallenger.projects.memorychallenger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.memorychallenger.projects.memorychallenger.R;

public class PreGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        EditText labelsText = (EditText) findViewById(R.id.label_names_text);
        String[] labels = setLabels(labelsText.getText().toString());
        if (labels.length == 0) return;
        int numQuestions = Integer.parseInt(((EditText) findViewById(R.id.num_questions_text)).getText().toString());
        if (numQuestions <= 0) return;
        intent.putExtra(PlayGameActivity.LABELS_KEY, labels);
        intent.putExtra(PlayGameActivity.NUM_QUESTIONS_KEY, numQuestions);
        startActivity(intent);
        finish();
    }

    private String[] setLabels(String labelsText) {
        String[] split = labelsText.split(";");
        if (split.length == 0) {
            return new String[0];
        }
        String[] labels = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            //TODO create utility to clean label
            labels[i] = split[i].trim().toLowerCase();
            if (labels[i].length() == 0) {
                return new String[0];
            }
        }
        return labels;
    }

}
