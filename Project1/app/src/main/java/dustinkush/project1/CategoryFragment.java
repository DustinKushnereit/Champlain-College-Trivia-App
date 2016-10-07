package dustinkush.project1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileOutputStream;


/**
 * Created by DustinK on 9/30/2016.
 */

public class CategoryFragment extends Fragment
{
    private static final String TAG = "FacultyFragment";

    private ImageButton mTopLeft;
    private ImageButton mTopRight;
    private ImageButton mBottomLeft;

    private Button mNewGameButton;

    private void doorPressed (int doorPressed)
    {
        if(doorPressed == 1 && mTopLeft.isEnabled())
        {
            mTopLeft.setColorFilter(R.color.material_grey_100);
            mTopLeft.setEnabled(false);
            switchFragment(1);
        }
        else if(doorPressed == 2 && mTopRight.isEnabled())
        {
            mTopRight.setColorFilter(R.color.material_grey_100);
            mTopRight.setEnabled(false);
            switchFragment(2);
        }
        else if(doorPressed == 3 && mBottomLeft.isEnabled())
        {
            mBottomLeft.setColorFilter(R.color.material_grey_100);
            mBottomLeft.setEnabled(false);
            switchFragment(3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        mTopLeft = (ImageButton) view.findViewById(R.id.top_left);
        mTopLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                doorPressed(1);
            }
        });

        mTopRight = (ImageButton) view.findViewById(R.id.top_right);
        mTopRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                doorPressed(2);
            }
        });

        mBottomLeft = (ImageButton) view.findViewById(R.id.bottom_left);
        mBottomLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doorPressed(3);
            }
        });

        mNewGameButton = (Button) view.findViewById(R.id.new_game_button);
        mNewGameButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                newGame();
            }
        });

        return view;
    }

    public void newGame()
    {
        resetFaculty();
        resetBuilding();
        resetBurlington();
        resetTotal();

        int messageResId  = R.string.scores_reset;
        Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT).show();
    }

    private void resetFaculty()
    {
        String filenameFaculty = "project1scorefilefaculty.txt";
        FileOutputStream outputStream;
        int totalScoreToWrite = 0;
        String totalScore = Integer.toString(totalScoreToWrite);

        getActivity().deleteFile(filenameFaculty);

        try
        {
            outputStream = getActivity().openFileOutput(filenameFaculty, Context.MODE_PRIVATE);
            outputStream.write(totalScore.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void resetBuilding()
    {
        String filenameBuilding= "project1scorefilebuilding.txt";
        FileOutputStream outputStream;
        int totalScoreToWrite = 0;
        String totalScore = Integer.toString(totalScoreToWrite);

        getActivity().deleteFile(filenameBuilding);

        try
        {
            outputStream = getActivity().openFileOutput(filenameBuilding, Context.MODE_PRIVATE);
            outputStream.write(totalScore.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void resetBurlington()
    {
        String filenameBurlington = "project1scorefileburlington.txt";
        FileOutputStream outputStream;
        int totalScoreToWrite = 0;
        String totalScore = Integer.toString(totalScoreToWrite);

        getActivity().deleteFile(filenameBurlington);

        try
        {
            outputStream = getActivity().openFileOutput(filenameBurlington, Context.MODE_PRIVATE);
            outputStream.write(totalScore.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void resetTotal()
    {
        String filename = "project1scorefile.txt";
        FileOutputStream outputStream;
        int totalScoreToWrite = 0;
        String totalScore = Integer.toString(totalScoreToWrite);

        getActivity().deleteFile(filename);

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
    }

    public void switchFragment(int categoryChosen)
    {
        if(categoryChosen == 1)
        {
            Fragment fragment = new FacultyFragment();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
        else if(categoryChosen == 2)
        {
            Fragment fragment = new BuildingFragment();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
        else if(categoryChosen == 3)
        {
            Fragment fragment = new BurlingtonFragment();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }
}
