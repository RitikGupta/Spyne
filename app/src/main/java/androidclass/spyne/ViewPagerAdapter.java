package androidclass.spyne;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hp on 10/25/2020.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int numOftabs;

    public ViewPagerAdapter(FragmentManager supportFragmentManager, int tabCount) {
        super(supportFragmentManager);
        numOftabs=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment= new WelcomeFragment();
                break;
            case 1:
                fragment= new AboutFragment();
                break;
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return numOftabs;
    }

}
