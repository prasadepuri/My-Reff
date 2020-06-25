package me.prasad.myreff;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private final int numberOfTabs;
    public Context context;
    public PagerAdapter(FragmentManager fm, int NumOfTabs)
    {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numberOfTabs= NumOfTabs;
        this.context=context;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new ForYou();
            case 1:
                return new Politics();
            case 2:
               return new Education();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {

        return numberOfTabs;
    }
}
