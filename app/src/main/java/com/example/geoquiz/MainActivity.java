package com.example.geoquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView text_view_questions;
    private Button button_true;
    private Button button_false;
    private Button button_next;
    private Button cheat_button;
    private Button button_previous;

    private static final String TAG = "ololo";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;

    private Questions[] questions = new Questions[]{
            new Questions(R.string.question_australia,true),
            new Questions(R.string.question_oceans,true),
            new Questions(R.string.question_mideast,false),
            new Questions(R.string.question_africa,false),
            new Questions(R.string.question_americas,true),
            new Questions(R.string.question_asia,true),
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState !=null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }
        Log.d(TAG, "onCreate: ");
        initAndBuildViews();
    }

    private void initAndBuildViews() {

        text_view_questions = findViewById(R.id.text_view_questions);
        button_true = findViewById(R.id.button_true);
        button_false = findViewById(R.id.button_false);
        button_next = findViewById(R.id.button_next);
        button_previous = findViewById(R.id.button_previous);
        cheat_button = findViewById(R.id.cheat_button);


        cheat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = questions[mCurrentIndex].ismAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this,answerIsTrue);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex +1) % questions.length;
                mIsCheater = false;
                updateQuestion();
            }
        });
            updateQuestion();


        button_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        button_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue  = questions[mCurrentIndex].ismAnswerTrue();
        int messageResId = 0;
        if (mIsCheater){
            messageResId = R.string.judgment_toast;
        }else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        Log.d(TAG, "updateQuestion: ",new Exception());
        int question = questions[mCurrentIndex].getmTextResId();
        text_view_questions.setText(question);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
