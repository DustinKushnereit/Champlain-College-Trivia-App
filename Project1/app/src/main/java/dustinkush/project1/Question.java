package dustinkush.project1;

/**
 * Created by DustinK on 10/3/2016.
 */

public class Question
{
    private int mTextResId;
    private int mWhichNumberIsCorrect;
    private boolean mDidCheat;

    public Question(int textResId, int correctNumber)
    {
        mTextResId = textResId;
        mWhichNumberIsCorrect = correctNumber;
    }

    public int getTextResId()
    {
        return mTextResId;
    }

    public void setTextResId(int textResId)
    {
        mTextResId = textResId;
    }

    public int correctPictureID()
    {
        return mWhichNumberIsCorrect;
    }

    public void setAnswerTrue(int correctNumber)
    {
        mWhichNumberIsCorrect = correctNumber;
    }
}