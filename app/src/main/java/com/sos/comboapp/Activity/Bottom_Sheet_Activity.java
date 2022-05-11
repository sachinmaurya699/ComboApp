package com.sos.comboapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sos.comboapp.My_Fragment.Bottom_Fragment;
import com.sos.comboapp.R;
import com.sos.comboapp.databinding.ActivityBottomSheetBinding;

public class Bottom_Sheet_Activity extends AppCompatActivity
{
    private ActivityBottomSheetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding=ActivityBottomSheetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextProduct9.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bottom_Fragment blankFragment1 = new  Bottom_Fragment();
                blankFragment1.show(getSupportFragmentManager(),blankFragment1.getTag());
                //BottomSheetBehavior.from(this).setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

    }

    private void showBottomSheetDialog()
    {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_design);

        bottomSheetDialog.show();
    }
}