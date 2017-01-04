package aleksandrov.aleksandr.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button buttonTrue, buttonFalse;
    private ImageButton mNextButton, mPreviousButton;
    private TextView mQuestionTextView;

    private static String KEY_INDEX = "index";

    private Question[] mQuestionsBank = new Question[] {
        new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_azia, true)
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        buttonTrue = (Button) findViewById(R.id.btnTrue);
        buttonTrue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                checkAnswer(true);
            }
        });
        buttonFalse = (Button) findViewById(R.id.btnFalse);
        buttonFalse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (mCurrentIndex > 0) {
                mCurrentIndex = mCurrentIndex - 1;
                updateQuestion();
                }
            }
        });

        updateQuestion();
    }

    /**
     *
     * @param userPressedTrue
     */
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
        int messageId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageId = R.string.correct_toast;
        } else {
            messageId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }
}
