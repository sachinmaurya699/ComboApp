package com.sos.comboapp.My_Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sos.comboapp.R;
import com.sos.comboapp.databinding.FragmentBottomBinding;

public class Bottom_Fragment extends BottomSheetDialogFragment
{

    private FragmentBottomBinding binding;
    private Button button_submit;


    public Bottom_Fragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.bottom_design, container, false);
        button_submit=view.findViewById(R.id.next_product1);
        button_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), "hello fragment", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}