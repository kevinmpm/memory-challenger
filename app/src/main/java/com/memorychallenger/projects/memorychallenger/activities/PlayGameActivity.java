package com.memorychallenger.projects.memorychallenger.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.memorychallenger.projects.memorychallenger.R;
import com.memorychallenger.projects.memorychallenger.models.Label;
import com.memorychallenger.projects.memorychallenger.models.Question;
import com.memorychallenger.projects.memorychallenger.models.TextAnswerQuestion;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayGameActivity extends AppCompatActivity {
    public static String LABELS_KEY = "labels_key";
    public static String NUM_QUESTIONS_KEY = "num_q_key";

    private static int DEFAULT_NUM_QUESTIONS = 5;

    private Question[] questions;
    int curQuestion = 0;

    private Button submitButton;
    private  TextView isCorrectText;
    private TextView correctAnswerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setup(intent.getIntExtra(NUM_QUESTIONS_KEY, DEFAULT_NUM_QUESTIONS), (String[]) intent.getExtras().get(LABELS_KEY));
        setContentView(R.layout.activity_play_game);

        submitButton = ((Button) findViewById(R.id.submit_button));
        isCorrectText = ((TextView) findViewById(R.id.is_correct_text));
        correctAnswerText = ((TextView) findViewById(R.id.correct_answer_text));

        askQuestion();
    }

    private void askQuestion() {
        if (curQuestion >= questions.length) {
            //startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            String la = "la".equals(questions[curQuestion].getType()) ? " (LA)" : "";
            ((TextView) findViewById(R.id.question_text)).setText(questions[curQuestion].getQuestion() + la);
            ((EditText) findViewById(R.id.answer_edit)).setText("");
            isCorrectText.setVisibility(View.INVISIBLE);
            correctAnswerText.setVisibility(View.INVISIBLE);
        }
    }

    public void submitAnswer(View view) {
        //either submit answer, or if answer already submitted, continue to next q
        if (getString(R.string.button_submit).equalsIgnoreCase(submitButton.getText().toString())) {
            String inAnswer = ((EditText) findViewById(R.id.answer_edit)).getText().toString();
            String answer = ((TextAnswerQuestion) questions[curQuestion]).getAnswer();

            isCorrectText.setVisibility(View.VISIBLE);
            if (inAnswer.equalsIgnoreCase(answer)) {
                //TODO score
                isCorrectText.setText("Correct!");
            } else {
                isCorrectText.setText("Dumbass!");
            }
            correctAnswerText.setVisibility(View.VISIBLE);
            isCorrectText.setVisibility(View.VISIBLE);
            correctAnswerText.setText(answer);
            submitButton.setText(getString(R.string.button_continue));
        } else {
            submitButton.setText(getString(R.string.button_submit));
            curQuestion++;
            askQuestion();
        }


    }

        //TODO implement equal distribution toggle
    private void setup(int num_q, String[] labels) {
        questions = new Question[num_q];
        HashMap<String, Label> labelMap = new HashMap<String, Label>(labels.length);
        int n = 0;
        while (n < num_q) {
            int curVal = n;
            for (String labelStr : labels) {
                labelStr = labelStr.toLowerCase();
                if (n >= num_q) break;
                if (!MainActivity.labelMap.containsKey(labelStr)) continue;
                if (!labelMap.containsKey(labelStr)) {
                    labelMap.put(labelStr, MainActivity.labelMap.get(labelStr).clone());
                }
                Label label = labelMap.get(labelStr);
                if (label.getQuestionIds().size() == 0) continue;
                questions[n++] = MainActivity.questionMap.get(label.getQuestionIds()
                        .remove(((Double)(Math.random()*label.getQuestionIds().size())).intValue()));
            }
            // no more available questions (n didn't increase)
            if (n == curVal) break;
        }
    }
}
