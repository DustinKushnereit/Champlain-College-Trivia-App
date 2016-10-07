package dustinkush.project1;

import android.support.v4.app.Fragment;


public class MainActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new CategoryFragment();
    }
}
