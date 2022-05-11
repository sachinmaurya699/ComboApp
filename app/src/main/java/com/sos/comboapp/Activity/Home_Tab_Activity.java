package com.sos.comboapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.sos.comboapp.R;
import com.sos.comboapp.databinding.ActivityHomeTabBinding;

public class Home_Tab_Activity extends AppCompatActivity
{

    private ViewPager2 viewPager2;
    private ActivityHomeTabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        binding=ActivityHomeTabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //binding.myViewPager.

    }
}