package dustinkush.project1;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Random;

/**
 * Created by DustinK on 9/30/2016.
 */

public class FacultyFragment extends Fragment
{
    private static final String TAG = "FacultyFragment";
    private static final String KEY_INDEX = "index";

    private ImageButton mTopLeft;
    private ImageButton mTopRight;
    private ImageButton mBottomLeft;
    private ImageButton mBottomRight;

    private Button mResetButton;
    private Button mNextButton;
    private Button mMultipleChoiceA;
    private Button mMultipleChoiceB;
    private Button mMultipleChoiceC;
    private Button mMultipleChoiceD;
    private Button mCheckAnswerButton;
    private Button mHintButton;

    private TextView mMessagesTextView;
    private TextView mNumberCorrectTextView;

    private Boolean mNextButtonHasBeenPressed = false;
    private int mNumberOfQuestionsCorrect = 0;
    private EditText mEnterNameTextBox;
    private int mCurrentIndex = 0;
    private boolean mHintUsed = false;

    private MediaPlayer mCorrectAnswerSound;
    private MediaPlayer mWrongAnswerSound;

    private Question[] mQuestionBank = new Question[]
    {
        new Question(R.string.faculty_question_bemis, 1),
        new Question(R.string.faculty_question_mayer, 1),
        new Question(R.string.faculty_question_chen, 1),
        new Question(R.string.faculty_question_ferguson, 1),

        //Start Multiple Choice
        new Question(R.string.faculty_question_this_is_who, 1),
        new Question(R.string.faculty_question_this_is_who, 1),
        new Question(R.string.faculty_question_this_is_who, 1),

        //Start enter name
        new Question(R.string.faculty_question_enter_last_name, 1),
        new Question(R.string.faculty_question_enter_last_name, 1),
        new Question(R.string.faculty_question_enter_last_name, 1),
    };

    private int[] facultyImageList = new int[]
    {
        R.drawable.faculty_bemisgreg,
        R.drawable.faculty_bobmayer,
        R.drawable.faculty_chen,
        R.drawable.faculty_fergusonjonathan,

        //Start Multiple Choice At 5th Question
        R.drawable.faculty_hallbrian,
        R.drawable.faculty_lange,
        R.drawable.faculty_lawsondean,

        //Start enter name at 8th question
        R.drawable.faculty_manleyjoseph,
        R.drawable.faculty_rogatejohn,
        R.drawable.faculty_warrensides,
    };

    private int[] facultyMultipleChoiceAnswers = new int[]
    {
        R.string.faculty_question_hall,
        R.string.faculty_question_lange,
        R.string.faculty_question_lawson,
        R.string.faculty_question_manley,
    };

    private String[] facultyEnterLastNameAnswers = new String[]
    {
        "Manley",
        "Rogate",
        "Sides"
    };

    private void updateQuestion()
    {
        mNextButtonHasBeenPressed = false;

        if(mCurrentIndex >= 10)
            scoreScreen();
        else
        {
            int question = mQuestionBank[mCurrentIndex].getTextResId();
            mMessagesTextView.setText(question);
            setPictures();
        }
    }

    private void scoreScreen()
    {
        mMultipleChoiceA.setEnabled(false);
        mMultipleChoiceA.setVisibility(View.INVISIBLE);
        mMultipleChoiceB.setEnabled(false);
        mMultipleChoiceB.setVisibility(View.INVISIBLE);
        mMultipleChoiceC.setEnabled(false);
        mMultipleChoiceC.setVisibility(View.INVISIBLE);
        mMultipleChoiceD.setEnabled(false);
        mMultipleChoiceD.setVisibility(View.INVISIBLE);

        mTopLeft.setEnabled(false);
        mTopRight.setEnabled(false);
        mBottomLeft.setEnabled(false);
        mBottomRight.setEnabled(false);
        mTopLeft.setVisibility(View.INVISIBLE);
        mTopRight.setVisibility(View.INVISIBLE);
        mBottomLeft.setVisibility(View.INVISIBLE);
        mBottomRight.setVisibility(View.INVISIBLE);

        mEnterNameTextBox.setEnabled(false);
        mEnterNameTextBox.setVisibility(View.INVISIBLE);

        mCheckAnswerButton.setEnabled(false);
        mCheckAnswerButton.setVisibility(View.INVISIBLE);

        //Faculty Score File
        String filenameFaculty = "project1scorefilefaculty.txt";
        FileOutputStream outputStreamFaculty;
        int readInScoreFaculty = 0;
        String readInStringFirstFaculty = "";
        String readInStringSecondFaculty = "";
        int totalScoreToWriteFaculty = 0;

        try
        {
            FileInputStream fin = getActivity().openFileInput(filenameFaculty);

            while( (readInScoreFaculty = fin.read()) != -1)
            {
                readInStringFirstFaculty = readInStringFirstFaculty + Character.toString((char) readInScoreFaculty);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            readInScoreFaculty = new Integer(Integer.parseInt(readInStringFirstFaculty));
            totalScoreToWriteFaculty = mNumberOfQuestionsCorrect + readInScoreFaculty;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String totalScoreFaculty = Integer.toString(totalScoreToWriteFaculty);

        try
        {
            outputStreamFaculty = getActivity().openFileOutput(filenameFaculty, Context.MODE_PRIVATE);
            outputStreamFaculty.write(totalScoreFaculty.getBytes());
            outputStreamFaculty.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            FileInputStream fin = getActivity().openFileInput(filenameFaculty);

            while( (readInScoreFaculty = fin.read()) != -1)
            {
                readInStringSecondFaculty = readInStringSecondFaculty + Character.toString((char) readInScoreFaculty);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //Total Score File
        String filename = "project1scorefile.txt";
        FileOutputStream outputStream;
        int readInScore = 0;
        String readInStringFirst = "";
        String readInStringSecond = "";
        int totalScoreToWrite = 0;

        try
        {
            FileInputStream fin = getActivity().openFileInput(filename);

            while( (readInScore = fin.read()) != -1)
            {
                readInStringFirst = readInStringFirst + Character.toString((char) readInScore);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            //readInScore = Integer.parseInt(readInString);
            readInScore = new Integer(Integer.parseInt(readInStringFirst));
            totalScoreToWrite = mNumberOfQuestionsCorrect + readInScore;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String totalScore = Integer.toString(totalScoreToWrite);

        try
        {
            outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(totalScore.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            FileInputStream fin = getActivity().openFileInput(filename);

            while( (readInScore = fin.read()) != -1)
            {
                readInStringSecond = readInStringSecond + Character.toString((char) readInScore);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        mMessagesTextView.setText("This Rounds Score: " + mNumberOfQuestionsCorrect + "\nYour total faculty score is: " + readInStringSecondFaculty + "\nYour total score is: " + readInStringSecond);
    }

    private void setPictures()
    {
        if(mCurrentIndex < 4)
        {
            mMultipleChoiceA.setEnabled(false);
            mMultipleChoiceA.setVisibility(View.INVISIBLE);
            mMultipleChoiceB.setEnabled(false);
            mMultipleChoiceB.setVisibility(View.INVISIBLE);
            mMultipleChoiceC.setEnabled(false);
            mMultipleChoiceC.setVisibility(View.INVISIBLE);
            mMultipleChoiceD.setEnabled(false);
            mMultipleChoiceD.setVisibility(View.INVISIBLE);

            mTopLeft.setEnabled(true);
            mTopRight.setEnabled(true);
            mBottomLeft.setEnabled(true);
            mBottomRight.setEnabled(true);

            mTopLeft.setColorFilter(null);
            mTopRight.setColorFilter(null);
            mBottomLeft.setColorFilter(null);
            mBottomRight.setColorFilter(null);

            Random random = new Random();
            int randomInt1 = random.nextInt(4 - 0) + 0;
            int randomInt2 = random.nextInt(4 - 0) + 0;
            int randomInt3 = random.nextInt(4 - 0) + 0;

            while (randomInt1 == mCurrentIndex) {
                random = new Random();
                randomInt1 = random.nextInt(4 - 0) + 0;
            }

            while (randomInt2 == mCurrentIndex || randomInt2 == randomInt1) {
                random = new Random();
                randomInt2 = random.nextInt(4 - 0) + 0;
            }

            while (randomInt3 == mCurrentIndex || randomInt3 == randomInt1 || randomInt3 == randomInt2) {
                random = new Random();
                randomInt3 = random.nextInt(4 - 0) + 0;
            }

            mTopLeft.setImageResource(facultyImageList[mCurrentIndex]);
            mTopRight.setImageResource(facultyImageList[randomInt1]);
            mBottomLeft.setImageResource(facultyImageList[randomInt2]);
            mBottomRight.setImageResource(facultyImageList[randomInt3]);
        }
        else if(mCurrentIndex < 7)
        {
            mTopLeft.setEnabled(false);

            mMultipleChoiceA.setEnabled(true);
            mMultipleChoiceA.setVisibility(View.VISIBLE);
            mMultipleChoiceB.setEnabled(true);
            mMultipleChoiceB.setVisibility(View.VISIBLE);
            mMultipleChoiceC.setEnabled(true);
            mMultipleChoiceC.setVisibility(View.VISIBLE);
            mMultipleChoiceD.setEnabled(true);
            mMultipleChoiceD.setVisibility(View.VISIBLE);

            Random random = new Random();
            int randomInt1 = random.nextInt(4 - 0) + 0;
            int randomInt2 = random.nextInt(4 - 0) + 0;
            int randomInt3 = random.nextInt(4 - 0) + 0;

            while (randomInt1 == mCurrentIndex - 4) {
                random = new Random();
                randomInt1 = random.nextInt(4 - 0) + 0;
            }

            while (randomInt2 == mCurrentIndex - 4 || randomInt2 == randomInt1) {
                random = new Random();
                randomInt2 = random.nextInt(4 - 0) + 0;
            }

            while (randomInt3 == mCurrentIndex - 4 || randomInt3 == randomInt1 || randomInt3 == randomInt2) {
                random = new Random();
                randomInt3 = random.nextInt(4 - 0) + 0;
            }

            mMultipleChoiceA.setText(facultyMultipleChoiceAnswers[mCurrentIndex - 4]);
            mMultipleChoiceB.setText(facultyMultipleChoiceAnswers[randomInt1]);
            mMultipleChoiceC.setText(facultyMultipleChoiceAnswers[randomInt2]);
            mMultipleChoiceD.setText(facultyMultipleChoiceAnswers[randomInt3]);

            mTopLeft.setColorFilter(null);
            mTopRight.setVisibility(View.INVISIBLE); //Make it invisible
            mBottomLeft.setVisibility(View.INVISIBLE); //Make it invisible
            mBottomRight.setVisibility(View.INVISIBLE); //Make it invisible

            mTopLeft.setImageResource(facultyImageList[mCurrentIndex]);
        }
        else
        {
            mEnterNameTextBox.setEnabled(true);
            mEnterNameTextBox.setVisibility(View.VISIBLE);

            mCheckAnswerButton.setEnabled(true);
            mCheckAnswerButton.setVisibility(View.VISIBLE);

            mMultipleChoiceA.setEnabled(false);
            mMultipleChoiceA.setVisibility(View.INVISIBLE);
            mMultipleChoiceB.setEnabled(false);
            mMultipleChoiceB.setVisibility(View.INVISIBLE);
            mMultipleChoiceC.setEnabled(false);
            mMultipleChoiceC.setVisibility(View.INVISIBLE);
            mMultipleChoiceD.setEnabled(false);
            mMultipleChoiceD.setVisibility(View.INVISIBLE);

            mTopLeft.setColorFilter(null);
            mTopLeft.setImageResource(facultyImageList[mCurrentIndex]);
        }
    }

    private void checkAnswer(int picturePressed)
    {
        int correctNumber = mQuestionBank[mCurrentIndex].correctPictureID();

        int messageResId = 0;

        if (picturePressed == correctNumber)
        {
            messageResId = R.string.correct_toast;
            mNumberOfQuestionsCorrect++;
            mCorrectAnswerSound.start();
        }
        else
        {
            messageResId = R.string.incorrect_toast;
            mWrongAnswerSound.start();
        }

        mNumberCorrectTextView.setText(mNumberOfQuestionsCorrect + "/10 Correct");
        mNextButtonHasBeenPressed = true;

        if(mCurrentIndex < 10)
            mCurrentIndex++;

        Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT).show();
    }

    private void checkAnswerString(String answerGiven)
    {
        String correctAnswer = facultyEnterLastNameAnswers[mCurrentIndex - 7].toString();

        correctAnswer = correctAnswer.toLowerCase();
        answerGiven = answerGiven.toLowerCase();

        int messageResId = 0;

        if (answerGiven.equals(correctAnswer))
        {
            messageResId = R.string.correct_toast;
            mNumberOfQuestionsCorrect++;
            mCorrectAnswerSound.start();
        }
        else
        {
            messageResId = R.string.incorrect_toast;
            mWrongAnswerSound.start();
        }

        mNumberCorrectTextView.setText(mNumberOfQuestionsCorrect + "/10 Correct");
        mNextButtonHasBeenPressed = true;
        mCheckAnswerButton.setEnabled(false);
        mEnterNameTextBox.setEnabled(false);
        mEnterNameTextBox.setText("");

        if(mCurrentIndex < 10)
            mCurrentIndex++;

        Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT).show();
    }

    private void picturePressed (int picturePressed)
    {
        if(picturePressed == 1)
        {
            mTopLeft.setColorFilter(R.color.material_grey_100);
            mTopLeft.setEnabled(false);
            mTopRight.setEnabled(false);
            mBottomLeft.setEnabled(false);
            mBottomRight.setEnabled(false);

            mMultipleChoiceA.setEnabled(false);
            mMultipleChoiceB.setEnabled(false);
            mMultipleChoiceC.setEnabled(false);
            mMultipleChoiceD.setEnabled(false);

            checkAnswer(picturePressed);
        }
        else if(picturePressed == 2)
        {
            //mTopRight.setImageResource(android.R.color.transparent); //Remove its position
            mTopRight.setColorFilter(R.color.material_grey_100);
            mTopLeft.setEnabled(false);
            mTopRight.setEnabled(false);
            mBottomLeft.setEnabled(false);
            mBottomRight.setEnabled(false);

            mMultipleChoiceA.setEnabled(false);
            mMultipleChoiceB.setEnabled(false);
            mMultipleChoiceC.setEnabled(false);
            mMultipleChoiceD.setEnabled(false);

            checkAnswer(picturePressed);
        }
        else if(picturePressed == 3)
        {
            mBottomLeft.setColorFilter(R.color.material_grey_100);
            mTopLeft.setEnabled(false);
            mTopRight.setEnabled(false);
            mBottomLeft.setEnabled(false);
            mBottomRight.setEnabled(false);

            mMultipleChoiceA.setEnabled(false);
            mMultipleChoiceB.setEnabled(false);
            mMultipleChoiceC.setEnabled(false);
            mMultipleChoiceD.setEnabled(false);

            checkAnswer(picturePressed);
        }
        else if(picturePressed == 4)
        {
            mBottomRight.setColorFilter(R.color.material_grey_100);
            mTopLeft.setEnabled(false);
            mTopRight.setEnabled(false);
            mBottomLeft.setEnabled(false);
            mBottomRight.setEnabled(false);

            mMultipleChoiceA.setEnabled(false);
            mMultipleChoiceB.setEnabled(false);
            mMultipleChoiceC.setEnabled(false);
            mMultipleChoiceD.setEnabled(false);
            checkAnswer(picturePressed);
        }
    }

    private void giveHint()
    {
        if(!mHintUsed)
        {
            mHintUsed = true;

            if(mCurrentIndex < 4)
            {
                mBottomRight.setEnabled(false);
                mBottomRight.setColorFilter(R.color.material_grey_100);
            }
            else if (mCurrentIndex < 7 && mCurrentIndex > 3)
            {
                mMultipleChoiceD.setEnabled(false);
            }
            else if(mCurrentIndex == 7)
            {
                mMessagesTextView.setText("His name starts with 'Man'");
            }
            else if(mCurrentIndex == 8)
            {
                mMessagesTextView.setText("His name starts with 'Rog'");
            }
            else if(mCurrentIndex == 9)
            {
                mMessagesTextView.setText("His name starts with 'Sid'");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        mCorrectAnswerSound = MediaPlayer.create(getActivity(), R.raw.correctanswer);
        mWrongAnswerSound = MediaPlayer.create(getActivity(), R.raw.wronganswer);

        mMessagesTextView = (TextView) view.findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mMessagesTextView.setText(question);

        mNumberCorrectTextView = (TextView) view.findViewById(R.id.number_correct_text_view);
        mNumberCorrectTextView.setText(mNumberOfQuestionsCorrect + "/10 Correct");

        mCheckAnswerButton = (Button) view.findViewById(R.id.check_answer_button);
        mCheckAnswerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow( getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                checkAnswerString(mEnterNameTextBox.getText().toString());
            }
        });

        mEnterNameTextBox = (EditText) view.findViewById(R.id.text_box);
        mEnterNameTextBox.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                mCheckAnswerButton.setEnabled(mEnterNameTextBox.getText().toString().length() > 1);
            }
        });

        mCheckAnswerButton.setEnabled(false);
        mCheckAnswerButton.setVisibility(View.INVISIBLE);
        mEnterNameTextBox.setEnabled(false);
        mEnterNameTextBox.setVisibility(View.INVISIBLE);

        mResetButton = (Button) view.findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //getActivity().recreate();
                switchFragment();
            }
        });

        mNextButton = (Button) view.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mNextButtonHasBeenPressed)
                    updateQuestion();
            }
        });

        mTopLeft = (ImageButton) view.findViewById(R.id.top_left);
        mTopLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                picturePressed(1);
            }
        });

        mTopRight = (ImageButton) view.findViewById(R.id.top_right);
        mTopRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                picturePressed(2);
            }
        });

        mBottomLeft = (ImageButton) view.findViewById(R.id.bottom_left);
        mBottomLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                picturePressed(3);
            }
        });

        mBottomRight = (ImageButton) view.findViewById(R.id.bottom_right);
        mBottomRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                picturePressed(4);
            }
        });

        mMultipleChoiceA = (Button) view.findViewById(R.id.multiple_choice_a_button);
        mMultipleChoiceA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                picturePressed(1);
            }
        });

        mMultipleChoiceB = (Button) view.findViewById(R.id.multiple_choice_b_button);
        mMultipleChoiceB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                picturePressed(2);
            }
        });

        mMultipleChoiceC = (Button) view.findViewById(R.id.multiple_choice_c_button);
        mMultipleChoiceC.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                picturePressed(3);
            }
        });

        mMultipleChoiceD = (Button) view.findViewById(R.id.multiple_choice_d_button);
        mMultipleChoiceD.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                picturePressed(4);
            }
        });

        mHintButton = (Button) view.findViewById(R.id.hint_button);
        mHintButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                giveHint();
            }
        });

        setPictures();

        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        return view;
    }

    public void switchFragment()
    {
        Fragment fragment = new CategoryFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
