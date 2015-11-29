package com.questions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.excelread.ExcelRead;
import com.finish.FinalScreen;
import com.finish.Finish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import jxl.write.WriteException;
import login.lawersinc.com.lawersinc.R;

public class EnglishActivity extends AppCompatActivity {

    TextView question_textView;
    CheckBox moption1, moption2, moption3, moption4;
    Button submitAnswer, speak;
    ExcelRead excelRead;

    private ArrayList<String> qiDs, qTexts, corAns, op1, op2, op3, op4;
    private Integer currentQuestionIndex;
    public static Integer level;
    public static Integer cutoff;

    //answer arrays
    private ArrayList<String> qid;
    private ArrayList<String> answerChoice;

    /*Variables for TextToSpeak Settings*/
    private TextToSpeech tts;
    public static String TextToSpeak = "";
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    ArrayList<String> matches;

    private void init() {
        question_textView = (TextView) findViewById(R.id.equestion_textView);
//        moption1 = (CheckBox) findViewById(R.id.eoption1);
//        moption2 = (CheckBox) findViewById(R.id.eoption2);
//        moption3 = (CheckBox) findViewById(R.id.eoption3);
//        moption4 = (CheckBox) findViewById(R.id.eoption4);
        submitAnswer = (Button) findViewById(R.id.esubmit_button);
        speak = (Button) findViewById(R.id.espeak_now);
        matches = new ArrayList<>();
        qid = new ArrayList<String>();
        answerChoice = new ArrayList<String>();
        level = 1;
        cutoff = 5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.english_question_template);
        init();

        if (level == 1) {
            excelRead = new ExcelRead("/ngo/exam_english_1.xls");
            qiDs = excelRead.qiDs; qTexts=excelRead.qTexts; corAns=excelRead.corAns; op1=excelRead.op1;
            op2 = excelRead.op2; op3 = excelRead.op3; op4 = excelRead.op4;
        } else if (level == 2) {
            excelRead = new ExcelRead("/ngo/exam_english_1.xls");
            qiDs = excelRead.qiDs; qTexts=excelRead.qTexts; corAns=excelRead.corAns; op1=excelRead.op1;
            op2 = excelRead.op2; op3 = excelRead.op3; op4 = excelRead.op4;
        } else {
            excelRead = new ExcelRead("/ngo/exam_english_1.xls");
            qiDs = excelRead.qiDs; qTexts=excelRead.qTexts; corAns=excelRead.corAns; op1=excelRead.op1;
            op2 = excelRead.op2; op3 = excelRead.op3; op4 = excelRead.op4;
        }

        if (qiDs.size() > 0) {
            if (qTexts.size()>=1) {
                question_textView.setText(qTexts.get(0));
                //moption1.setText((String) op1.get(0));
                //moption2.setText((String) op2.get(0));
                //moption3.setText((String) op3.get(0));
                //moption4.setText((String) op4.get(0));

                currentQuestionIndex = 0;
            }
        }

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiate(getApplicationContext());
            }
        });


        submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (corAns != null) {
                    //String correctAnswer = corAns.get(currentQuestionIndex);

//                    if (moption1.isChecked() && moption1.getText().equals(correctAnswer)) {
//                        qid.add(qiDs.get(currentQuestionIndex));
//                        answerChoice.add("y");
//                    } else if (moption2.isChecked() && moption2.getText().equals(correctAnswer)) {
//                        qid.add(qiDs.get(currentQuestionIndex));
//                        answerChoice.add("y");
//                    } else if (moption3.isChecked() && moption3.getText().equals(correctAnswer)) {
//                        qid.add(qiDs.get(currentQuestionIndex));
//                        answerChoice.add("y");
//                    } else if (moption4.isChecked() && moption4.getText().equals(correctAnswer)) {
//                        qid.add(qiDs.get(currentQuestionIndex));
//                        answerChoice.add("y");
//                    } else {
//                        qid.add(qiDs.get(currentQuestionIndex));
//                        answerChoice.add("n");
//                    }

                    //validate
                    String [] possibleWords = corAns.get(currentQuestionIndex).split(",");
                    boolean flag = false;
                    for (String word: possibleWords) {
                        String tword = word.trim();
                        tword = tword.replace(" ", "");
                        //Toast.makeText(getApplicationContext(), tword, Toast.LENGTH_LONG).show();
                        if (matches.contains(tword)) {

                            qid.add(qiDs.get(currentQuestionIndex));
                            answerChoice.add("y");
                            flag = true;
                        }
                    }

                    if (!flag) {
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

        int length = questionsId.size();
        int i = currentQuestionIndex;
        //for (int i =0; i< length; i++) {
            String question = (String) questions.get(i);

            if (i == length -1) {
                //got to finish page

                try {
                    if (level == 1) {
                        excelRead.write(qid, answerChoice, "/ngo/results_english_1.xls");
                        level++;
                    } else if (level == 2) {
                        excelRead.write(qid, answerChoice, "/ngo/results_english_1.xls");
                        level++;
                    } else {
                        excelRead.write(qid, answerChoice, "/ngo/results_english_1.xls");
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
                    intent.putExtra("examType", "english");
                    intent.putExtra("level", level);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(this, Finish.class);
                    intent.putExtra("pass", "false");
                    intent.putExtra("examType", "english");
                    intent.putExtra("level", level);
                    startActivity(intent);

                }

            }
            else {
                if (questionId == questionsId.get(i) && question != null && choice1.get(i + 1) != null && choice2.get(i + 1) != null && choice3.get(i + 1) != null && choice4.get(i + 1) != null) {
                    question_textView.setText((String) questions.get(currentQuestionIndex+1));
                    //moption1.setText((String) choice1.get(i + 1));
                    //moption2.setText((String) choice2.get(i + 1));
                    //moption3.setText((String) choice3.get(i + 1));
                    //moption4.setText((String) choice4.get(i + 1));
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



    //speech

    public static void startVoiceRecognitionActivity(Activity activity) {

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition");
//        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //intent.addFlags(Intent.FLAG_RECEIVER_NO_ABORT);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //activity.startActivity(intent);
        Log.i("p", "k");

        activity.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);


    }

    public void speakOut(String text) {
        text += "Speak now";
        Log.i("tts", tts.toString());
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
        while (tts.isSpeaking()) ;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onAct", "entered");
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            // Fill the list view with the strings the recognizer thought it could have heard
            matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            Log.i("onAct2", "OK2");
            Log.i("matches", matches.get(0));
//            list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                    matches));
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    public void initiate(final Context context) {

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.i("TTS", "This Language is not supported");
                    } else {
                        speakOut(TextToSpeak);

                    }

                } else {
                    Log.i("TTS", "Initilization Failed!");
                }
            }
        });

        tts.setOnUtteranceProgressListener(utteranceProgressListener);
    }

    String TAG = "lauda";
    UtteranceProgressListener utteranceProgressListener = new UtteranceProgressListener() {

        @Override
        public void onStart(String utteranceId) {
            Log.d(TAG, "onStart ( utteranceId :" + utteranceId + " ) ");
        }

        @Override
        public void onError(String utteranceId) {
            Log.d(TAG, "onError ( utteranceId :" + utteranceId + " ) ");
        }

        @Override
        public void onDone(String utteranceId) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
        }
    };

}