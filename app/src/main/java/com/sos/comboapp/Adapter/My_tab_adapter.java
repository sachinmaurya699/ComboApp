package com.sos.comboapp.Adapter;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class My_tab_adapter extends FragmentPagerAdapter
{

    public My_tab_adapter(@NonNull FragmentManager fm, int behavior)
    {

        super(fm, behavior);
    }

    @Override
    public int getCount()
    {

        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {

        return null;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {

        return false;
    }
}
