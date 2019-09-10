package project.android_projects.com.autoscrollviewpager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AutoScrollPagerAdapter extends FragmentPagerAdapter {


    public AutoScrollPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public Fragment getItem(int position) {
        return SlideFragment.getInstance(position + 1);
    }
}
