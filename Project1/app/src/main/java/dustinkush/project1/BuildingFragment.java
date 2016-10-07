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

public class BuildingFragment extends Fragment
{
    private static final String TAG = "BuildingFragment";
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
        new Question(R.string.buildings_question_371main, 1),
        new Question(R.string.buildings_question_baderhall, 1),
        new Question(R.string.buildings_question_bankushall, 1),
        new Question(R.string.buildings_question_carriagehouse, 1),

        //Start Multiple Choice
        new Question(R.string.buildings_question_this_is_what, 1),
        new Question(R.string.buildings_question_this_is_what, 1),
        new Question(R.string.buildings_question_this_is_what, 1),

        //Start enter name
        new Question(R.string.buildings_question_name, 1),
        new Question(R.string.buildings_question_name, 1),
        new Question(R.string.buildings_question_name, 1),
    };

    private int[] imageList = new int[]
    {
        R.drawable.buildings_371main,
        R.drawable.buildings_baderhall,
        R.drawable.buildings_bankushall,
        R.drawable.buildings_carriagehouse,

        //Start Multiple Choice At 5th Question
        R.drawable.buildings_cushinghall,
        R.drawable.buildings_hillhall,
        R.drawable.buildings_jensenhall,

        //Start enter name at 8th question
        R.drawable.buildings_lymanhall,
        R.drawable.buildings_mcdonaldhall,
        R.drawable.buildings_northhouse,
    };

    private int[] multipleChoiceAnswers = new int[]
    {
        R.string.buildings_question_cushinghall,
        R.string.buildings_question_hillhall,
        R.string.buildings_question_jensenhall,
        R.string.buildings_question_lymanhall,
    };

    private String[] enterNameAnswers = new String[]
    {
        "Lyman Hall",
        "McDonald Hall",
        "North House"
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

        //Building Score File
        String filenameBuilding= "project1scorefilebuilding.txt";
        FileOutputStream outputStreamBuilding;
        int readInScoreBuilding = 0;
        String readInStringFirstBuilding = "";
        String readInStringSecondBuilding = "";
        int totalScoreToWriteBuilding = 0;

        try
        {
            FileInputStream fin = getActivity().openFileInput(filenameBuilding);

            while( (readInScoreBuilding = fin.read()) != -1)
            {
                readInStringFirstBuilding = readInStringFirstBuilding + Character.toString((char) readInScoreBuilding);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            readInScoreBuilding = new Integer(Integer.parseInt(readInStringFirstBuilding));
            totalScoreToWriteBuilding = mNumberOfQuestionsCorrect + readInScoreBuilding;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String totalScoreBuilding = Integer.toString(totalScoreToWriteBuilding);

        try
        {
            outputStreamBuilding = getActivity().openFileOutput(filenameBuilding, Context.MODE_PRIVATE);
            outputStreamBuilding.write(totalScoreBuilding.getBytes());
            outputStreamBuilding.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            FileInputStream fin = getActivity().openFileInput(filenameBuilding);

            while( (readInScoreBuilding = fin.read()) != -1)
            {
                readInStringSecondBuilding = readInStringSecondBuilding + Character.toString((char) readInScoreBuilding);
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

        mMessagesTextView.setText("This Rounds Score: " + mNumberOfQuestionsCorrect + "\nYour total building score is: " + readInStringSecondBuilding + "\nYour total score is: " + readInStringSecond);
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

            mTopLeft.setImageResource(imageList[mCurrentIndex]);
            mTopRight.setImageResource(imageList[randomInt1]);
            mBottomLeft.setImageResource(imageList[randomInt2]);
            mBottomRight.setImageResource(imageList[randomInt3]);
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

            mMultipleChoiceA.setText(multipleChoiceAnswers[mCurrentIndex - 4]);
            mMultipleChoiceB.setText(multipleChoiceAnswers[randomInt1]);
            mMultipleChoiceC.setText(multipleChoiceAnswers[randomInt2]);
            mMultipleChoiceD.setText(multipleChoiceAnswers[randomInt3]);

            mTopLeft.setColorFilter(null);
            mTopRight.setVisibility(View.INVISIBLE); //Make it invisible
            mBottomLeft.setVisibility(View.INVISIBLE); //Make it invisible
            mBottomRight.setVisibility(View.INVISIBLE); //Make it invisible

            mTopLeft.setImageResource(imageList[mCurrentIndex]);
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
            mTopLeft.setImageResource(imageList[mCurrentIndex]);
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
        String correctAnswer = enterNameAnswers[mCurrentIndex - 7].toString();

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
                mMessagesTextView.setText("The Buildings Name Starts With 'Lym'");
            }
            else if(mCurrentIndex == 8)
            {
                mMessagesTextView.setText("The Buildings Name Starts With 'McD'");
            }
            else if(mCurrentIndex == 9)
            {
                mMessagesTextView.setText("The Buildings Name Starts With 'Nor'");
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
