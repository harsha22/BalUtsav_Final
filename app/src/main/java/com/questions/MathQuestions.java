package com.questions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.excelread.ExcelRead;
import com.finish.FinalScreen;
import com.finish.Finish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import jxl.write.WriteException;
import login.lawersinc.com.lawersinc.R;

public class MathQuestions extends AppCompatActivity {

    TextView question_textView;
    CheckBox moption1, moption2, moption3, moption4;
    Button submitAnswer;
    ExcelRead excelRead;

    private ArrayList<String> qiDs, qTexts, corAns, op1, op2, op3, op4;
    private Integer currentQuestionIndex;
    public static Integer level;
    private Integer cutoff;

    //answer arrays
    private ArrayList<String> qid;
    private ArrayList<String> answerChoice;

    private void init() {
        question_textView = (TextView) findViewById(R.id.mquestion_textView);
        moption1 = (CheckBox) findViewById(R.id.moption1);
        moption2 = (CheckBox) findViewById(R.id.moption2);
        moption3 = (CheckBox) findViewById(R.id.moption3);
        moption4 = (CheckBox) findViewById(R.id.moption4);
        submitAnswer = (Button) findViewById(R.id.msubmit_button);
        qid = new ArrayList<String>();
        answerChoice = new ArrayList<String>();
        level = 1;
        cutoff = 5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_question_template);

        init();

        setLevel();

        submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (corAns != null) {
                    String correctAnswer = corAns.get(currentQuestionIndex);

                    if (moption1.isChecked() && moption1.getText().equals(correctAnswer)) {
                        qid.add(qiDs.get(currentQuestionIndex));
                        answerChoice.add("y");
                    } else if (moption2.isChecked() && moption2.getText().equals(correctAnswer)) {
                        qid.add(qiDs.get(currentQuestionIndex));
                        answerChoice.add("y");
                    } else if (moption3.isChecked() && moption3.getText().equals(correctAnswer)) {
                        qid.add(qiDs.get(currentQuestionIndex));
                        answerChoice.add("y");
                    } else if (moption4.isChecked() && moption4.getText().equals(correctAnswer)) {
                        qid.add(qiDs.get(currentQuestionIndex));
                        answerChoice.add("y");
                    } else {
                        qid.add(qiDs.get(currentQuestionIndex));
                        answerChoice.add("n");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please select atleast one option", Toast.LENGTH_LONG).show();
                }

                populateNext(qiDs.get(currentQuestionIndex),
                        qiDs, qTexts, corAns, op1, op2, op3, op4);
                currentQuestionIndex++;
            }
        });
    }



    public void loadData(ArrayList questionsId,
                                  ArrayList questions,
                                  ArrayList answer,
                                  ArrayList choice1,
                                  ArrayList choice2,
                                  ArrayList choice3,
                                  ArrayList choice4
    ) {

        int length = questionsId.size();

        for (int i =0; i< length; i++) {
            String question = (String) questions.get(i);
            if (question != null) {
                question_textView.setText(question);
                moption1.setText((String) choice1.get(i));
                moption2.setText((String) choice2.get(i));
                moption3.setText((String) choice3.get(i));
                moption4.setText((String) choice4.get(i));
            }

        }
    }

    public int randomNum(int length) {

        Random r = new Random();
        int i1 = r.nextInt(length);
        return i1;
    }

    private void populateNext(String questionId,
                              ArrayList questionsId,
                              ArrayList questions,
                              ArrayList answer,
                              ArrayList choice1,
                              ArrayList choice2,
                              ArrayList choice3,
                              ArrayList choice4) {

        moption1.setChecked(false);
        moption2.setChecked(false);
        moption3.setChecked(false);
        moption4.setChecked(false);

        int length = questionsId.size();
        //Toast.makeText(getApplicationContext(), length, Toast.LENGTH_LONG);

        //for (int i =0; i< length; i++) {
            String question = (String) questions.get(currentQuestionIndex);

            if (currentQuestionIndex == length -1) {
                //got to finish page
                //Toast.makeText(getApplicationContext(), "IF" + length, Toast.LENGTH_LONG).show();
                try {
                    if (level == 1) {
                        excelRead.write(qid, answerChoice, "/ngo/math_results_lvl1.xls");
                        level++;
                    } else if (level == 2) {
                        excelRead.write(qid, answerChoice, "/ngo/math_results_lvl2.xls");
                        level++;
                    } else {
                        excelRead.write(qid, answerChoice, "/ngo/math_results_lvl1.xls");
                        level++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }


                //get correct total
                if (totalCorrectAnswered(answerChoice) >= cutoff) {

                    Intent intent = new Intent(this, Finish.class);
                    intent.putExtra("pass", "true");
                    intent.putExtra("examType", "math");
                    intent.putExtra("level", level);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(this, Finish.class);
                    intent.putExtra("pass", "false");
                    intent.putExtra("examType", "math");
                    intent.putExtra("level", level);
                    startActivity(intent);

                }

            }
            else {
                //Toast.makeText(getApplicationContext(), "Else" + length, Toast.LENGTH_LONG).show();
                int i = currentQuestionIndex;
                if (questionId == questionsId.get(i) && question != null && choice1.get(i + 1) != null && choice2.get(i + 1) != null && choice3.get(i + 1) != null && choice4.get(i + 1) != null) {
                    question_textView.setText((String) questions.get(i + 1));
                    moption1.setText((String) choice1.get(i + 1));
                    moption2.setText((String) choice2.get(i + 1));
                    moption3.setText((String) choice3.get(i + 1));
                    moption4.setText((String) choice4.get(i + 1));
                }
            }
        //}

    }

    private int totalCorrectAnswered(ArrayList<String> answerChoice) {
        int correct=0;
        for (String val: answerChoice) {
            if (val.equalsIgnoreCase("y"))
                correct++;
        }
        return correct;
    }

    private void setLevel() {
        if (level == 1) {
            excelRead = new ExcelRead("/ngo/exam_math_1.xls");
            qiDs = excelRead.qiDs; qTexts=excelRead.qTexts; corAns=excelRead.corAns; op1=excelRead.op1;
            op2 = excelRead.op2; op3 = excelRead.op3; op4 = excelRead.op4;
        } else if (level == 2) {
            excelRead = new ExcelRead("/ngo/exam_math_2.xls");
            qiDs = excelRead.qiDs; qTexts=excelRead.qTexts; corAns=excelRead.corAns; op1=excelRead.op1;
            op2 = excelRead.op2; op3 = excelRead.op3; op4 = excelRead.op4;
        } else {
            excelRead = new ExcelRead("/ngo/exam_math_1.xls");
            qiDs = excelRead.qiDs; qTexts=excelRead.qTexts; corAns=excelRead.corAns; op1=excelRead.op1;
            op2 = excelRead.op2; op3 = excelRead.op3; op4 = excelRead.op4;
        }

        if (qiDs.size() > 0) {
            if (qTexts.size()>=1 && op1.size()>=1 && op2.size()>=1 && op3.size()>=1 && op4.size()>=1) {
                question_textView.setText(qTexts.get(0));
                moption1.setText((String) op1.get(0));
                moption2.setText((String) op2.get(0));
                moption3.setText((String) op3.get(0));
                moption4.setText((String) op4.get(0));

                currentQuestionIndex = 0;
            }
        }
    }
 }
