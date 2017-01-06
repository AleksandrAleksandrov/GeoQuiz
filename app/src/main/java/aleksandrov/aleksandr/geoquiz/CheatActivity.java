package aleksandrov.aleksandr.geoquiz;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "aleksandrov.aleksandr.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOW = "aleksandrov.aleksandr.geoquiz.answer_shown";

    private static final String SAVE_STATE_KEY = "save_state_cheat_activity";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView, mAPILevelTextView;
    private Button mShowAnswer;

    private Intent data = null;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOW, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answerTextVeiw);

        mAPILevelTextView = (TextView) findViewById(R.id.apiLevelTextView);
        mAPILevelTextView.append(getString(R.string.api_level) + " " + Build.VERSION.SDK_INT);

        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheat();
            }
        });

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(SAVE_STATE_KEY, false)) {
                doCheat();
            }
        }
    }

    private void doCheat() {
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = mShowAnswer.getWidth() / 2;
            int cy = mShowAnswer.getHeight() / 2;
            float radius = mShowAnswer.getWidth();
            Animator animator = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
//                super.onAnimationEnd(animator);
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.start();
        } else {
            mAnswerTextView.setVisibility(View.VISIBLE);
            mShowAnswer.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (data != null) {
            outState.putBoolean(SAVE_STATE_KEY, wasAnswerShown(data));
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOW, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
