package com.sos.comboapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register_Activity extends AppCompatActivity
{

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://console.firebase.google.com/project/comboapp-8b9df/database/comboapp-8b9df-default-rtdb/data/~2F");
    private EditText editText_name;
    private EditText editText_phone_number;
    private EditText editText_email;
    private AppCompatButton submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inilized();

    }

    private void inilized()
    {
        editText_name=findViewById(R.id.my_name);
        editText_phone_number=findViewById(R.id.my_phone_number);
        editText_email=findViewById(R.id.my_email);
        submit_button=findViewById(R.id.my_btn);

        submit_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                String Name=editText_name.getText().toString();
                String Email=editText_email.getText().toString();
                String Phone_number=editText_phone_number.getText().toString();


                if(Name.isEmpty() || Email.isEmpty() || Phone_number.isEmpty())
                {
                    Toast.makeText(Register_Activity.this, "All Field Required", Toast.LENGTH_SHORT).show();

                }else
                {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {

                            if(snapshot.child("users").hasChild(Phone_number))
                            {
                                Toast.makeText(Register_Activity.this, "Mobile allready exit", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                databaseReference.child("users").child("email").setValue(editText_email);
                                databaseReference.child("users").child("name").setValue(editText_email);

                                Toast.makeText(Register_Activity.this, "success", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });

                }

            }
        });



    }
}