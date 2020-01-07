package com.example.geoquiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "answers_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "is_show";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context context, boolean answersIsTrue){
        Intent intent = new Intent(context,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answersIsTrue);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView =  findViewById(R.id.answer_text_view);
        mShowAnswerButton =  findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.button_true);
                } else {
                    mAnswerTextView.setText(R.string.button_false);
                }
                setAnswerShowResult(true);

                int cx  = mShowAnswerButton.getWidth() /2;
                int cy = mShowAnswerButton.getHeight() /2;
                float radius = mShowAnswerButton.getWidth();
                Animator animator = ViewAnimationUtils.createCircularReveal(mShowAnswerButton,cx,cy,radius,0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mShowAnswerButton.setVisibility(View.INVISIBLE);
                    }
                });
                animator.start();
            }
        });
    }

    private void setAnswerShowResult(boolean isAnswerShown) {
        Intent intentData = new Intent();
        intentData.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,intentData);
    }
    public static boolean wasAnswerShown(Intent intentResult){
        return intentResult.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }
}
