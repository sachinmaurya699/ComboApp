package com.sos.comboapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sos.comboapp.Adapter.UserAdapter;
import com.sos.comboapp.Calss.Constant;
import com.sos.comboapp.Lisener.Userlistener;
import com.sos.comboapp.Model.User;
import com.sos.comboapp.R;
import com.sos.comboapp.Utilites.PreferenceManager;
import com.sos.comboapp.databinding.ActivityUserBinding;

import java.util.ArrayList;
import java.util.List;

public class User_Activity extends AppCompatActivity implements Userlistener
{

    private ActivityUserBinding binding;
    private PreferenceManager manager;
    private  UserAdapter user_adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding=ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView=findViewById(R.id.user_recyclerview);
        recyclerView.setHasFixedSize(true);
        userList= new ArrayList<>();
        manager= new PreferenceManager(getApplicationContext());
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setlistener();
        getUser();



    }
    private void setlistener()
    {
        binding.backArrow.setOnClickListener(view -> {
            onBackPressed();
        });
    }
    private void getUser()
    {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constant.Key_collection_users)
                .get()
                .addOnCompleteListener(task ->
                {
                    loading(false);

                    String currentUserId = manager.getString(Constant.Key_USER_ID);
                    if(task.isSuccessful() && task.getResult() !=null)
                    {
                        List<User> users= new ArrayList<>();

                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult())
                        {
                            if(currentUserId.equals(queryDocumentSnapshot.getId()))
                            {
                               continue;
                            }
                            User user= new User();
                            user.name=queryDocumentSnapshot.getString(Constant.Key_Name);
                            user.email=queryDocumentSnapshot.getString(Constant.Key_Email);
                            user.image=queryDocumentSnapshot.getString(Constant.Key_IMAGE);
                            user.token=queryDocumentSnapshot.getString(Constant.Key_FCM_TOKEN);
                            user.id=queryDocumentSnapshot.getId();
                            users.add(user);
                        }
                        if(users.size() > 0)
                        {
                           /* UserAdapter userAdapter = new UserAdapter(users,this);*/
                           /// binding.userRecyclerview.setAdapter(userAdapter);
                            binding.userRecyclerview.setVisibility(View.VISIBLE);

                           // Log.d("all_data_size",String.valueOf(users.size()));
                           /* user_adapter= new UserAdapter(this,users,user -> {

                                Intent intent= new Intent(getApplicationContext(),Chat_Activity.class);
                                intent.putExtra(Constant.Key_USER_ID,user);
                                startActivity(intent);
                                finish();

                            });*/
                           /* UserAdapter userAdapter = new UserAdapter(this, userList, new Userlistener()
                            {
                                @Override
                                public void onUserClicked(User user)
                                {
                                    SharedPreferences preferences=getSharedPreferences("",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=preferences.edit();
                                    editor.putString("")
                                }
                            });*/

                        }else
                        {
                            showErrorMessage();
                        }
                    }
                    else
                    {
                        showErrorMessage();
                    }
                });
    }
    private void showErrorMessage()
    {
        binding.myTextviewTop.setText(String.format("%s","No User Available"));
        binding.myTextviewTop.setVisibility(View.VISIBLE);
    }
    private  void loading(Boolean isLoading)
    {
        if(isLoading)
        {
            binding.myProgressBar.setVisibility(View.VISIBLE);
        }else
        {
            binding.myProgressBar.setVisibility(View.GONE);

        }

    }

    @Override
    public void onUserClicked(User user)
    {


    }
}