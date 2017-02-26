package com.memorychallenger.projects.memorychallenger.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.memorychallenger.projects.memorychallenger.R;
import com.memorychallenger.projects.memorychallenger.helpers.IOHelper;
import com.memorychallenger.projects.memorychallenger.models.Label;
import com.memorychallenger.projects.memorychallenger.models.Question;
import com.memorychallenger.projects.memorychallenger.models.TextAnswerQuestion;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateQuestionActivity extends AppCompatActivity {
    private String questionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        //default to short answer
        resetVisibility();
        ((RadioGroup) findViewById(R.id.radio_question_type)).check(R.id.radio_short_answer);
        setShortAnswerVisibility(View.VISIBLE);
        questionType = "sa";
    }

    public void createQuestion(View view) {
        String labels = ((EditText) findViewById(R.id.label_name)).getText().toString();
        String questText = ((EditText) findViewById(R.id.question_edit)).getText().toString();
        String answer = getAnswer();
        Question q = new TextAnswerQuestion(this.questionType, questText, answer, getString(R.string.separator));
        //Read text from file
        save(labels.split(";"), q);
        finish();
    }

    private String getAnswer() {
        switch(this.questionType) {
            case "sa":
                return ((EditText) findViewById(R.id.short_answer_text)).getText().toString();
            case "la":
                return ((EditText) findViewById(R.id.long_answer_text)).getText().toString();
        }
        return "";
    }

    private boolean save(String[] labels, Question question) {
        if (MainActivity.labelMap == null || MainActivity.questionMap == null) return false;
        MainActivity.questionMap.put(question.getId(), question);
        for (String labelName : labels) {
            Label label = MainActivity.labelMap.get(labelName.toLowerCase());
            if (label != null) {
                label.getQuestionIds().add(question.getId());
                MainActivity.labelMap.put(label.getId(), label);
            } else {
                ArrayList<String> questions = new ArrayList<String>();
                questions.add(question.getId());
                label = new Label(labelName, getString(R.string.separator), questions);
                MainActivity.labelMap.put(labelName, label);
            }
        }
        //cast label/question to object
        String labelOutput = IOHelper.generateFileContent((HashMap<String, Object>)(HashMap<String, ?>) MainActivity.labelMap);
        String questionOutput = IOHelper.generateFileContent((HashMap<String, Object>)(HashMap<String, ?>) MainActivity.questionMap);
        try {
            FileOutputStream outputStream = openFileOutput(getString(R.string.label_file), Context.MODE_PRIVATE);
            outputStream.write(labelOutput.getBytes());
            outputStream.close();

            outputStream = openFileOutput(getString(R.string.question_file), Context.MODE_PRIVATE);
            outputStream.write(questionOutput.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        resetVisibility();

        switch(view.getId()) {
            case R.id.radio_short_answer:
                if (checked) {
                    setShortAnswerVisibility(View.VISIBLE);
                    questionType = "sa";
                    break;
                }
            case R.id.radio_long_answer:
                if (checked) {
                    setLongAnswerVisibility(View.VISIBLE);
                    questionType = "la";
                    break;
                }
            case R.id.radio_multiple_choice:
                if (checked) {
                    setMultipleChoiceVisibility(View.VISIBLE);
                    questionType = "mc";
                    break;
                }
        }
    }

    private void setMultipleChoiceVisibility(int visibility) {
       // layout.findViewById(R.id.)

    }

    private void setShortAnswerVisibility(int visibility) {
        findViewById(R.id.short_answer_text).setVisibility(visibility);
    }

    private void setLongAnswerVisibility(int visibility) {
        findViewById(R.id.long_answer_text).setVisibility(visibility);
    }


    private void resetVisibility() {
        setMultipleChoiceVisibility(View.INVISIBLE);
        setShortAnswerVisibility(View.INVISIBLE);
        setLongAnswerVisibility(View.INVISIBLE);
    }
}


