package com.sos.comboapp.Firbase;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sos.comboapp.Calss.Constant;
import com.sos.comboapp.MainActivity;
import com.sos.comboapp.Utilites.PreferenceManager;
import com.sos.comboapp.databinding.ActivitySignUpBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class SignUp_Activity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private PreferenceManager preferenceManager;
    private String encode_image;
    private EditText editText_name;
    private EditText editText_email;
    private EditText editText_phone_number;
    private Button button_submit;
    private String encodeing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager= new PreferenceManager(getApplicationContext());

        setlinener();

    }

    private void setlinener() {
        binding.mySignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.mySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isvalidateails()) {
                    signup();
                }
            }
        });
        binding.floatingAction.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }
    private void signup()
    {
        loading(true);
        FirebaseFirestore firestore= FirebaseFirestore.getInstance();
        HashMap<String,Object> user= new HashMap<>();
        user.put(Constant.Key_Name,binding.myNameEdit.getText().toString());
                user.put(Constant.Key_Email,binding.myEmailEdit.getText().toString());
        user.put(Constant.Key_Password,binding.myEditPassword.getText().toString());
        user.put(Constant.Key_IMAGE,encode_image);
        firestore.collection(Constant.Key_collection_users)
                .add(user)
                .addOnSuccessListener(documentReference ->
                {
                    preferenceManager.putBoolean(Constant.Key_IS_SIGNED_IN,true);
                    preferenceManager.putString(Constant.Key_USER_ID,documentReference.getId());
                    preferenceManager.putString(Constant.Key_Name,binding.myNameEdit.getText().toString());
                    preferenceManager.putString(Constant.Key_IMAGE,encodeing);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                })
                .addOnFailureListener(e -> {
                    loading(false);
                    showText(e.getMessage());

                });

    }
    private String encodeImage(Bitmap bitmap)
    {
        int previewwidth=150;
        int previewheight = bitmap.getHeight() * previewwidth / bitmap.getWidth();
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,previewwidth,previewheight,false);
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] bytes= byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),result -> {

                if(result.getResultCode() == RESULT_OK)
                {
                    if(result.getData() !=null)
                    {
                        Uri image_uri= result.getData().getData();
                        try{
                            InputStream inputStream= getContentResolver().openInputStream(image_uri);
                            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                            binding.myImageSignup.setImageBitmap(bitmap);
                            encodeing =encodeImage(bitmap);
                        }catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }

            }
    ) ;

    private void showText(String message) {
        Toast.makeText(SignUp_Activity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isvalidateails()
    {
        if (encodeing == null)
        {
            showText("selecte profile image");
            return false;

        } else if (binding.myNameEdit.getText().toString().trim().isEmpty()) {
            showText("Enter the name");
            return false;

        } else if (binding.myEmailEdit.getText().toString().trim().isEmpty()) {
            showText("Enter the  email ");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.myEmailEdit.getText().toString()).matches()) {
            showText("Enter the valid email");

        } else if (binding.myConPass.getText().toString().trim().isEmpty()) {
            showText("Enter the conform password");
        } else if (!binding.myEditPassword.getText().toString().equals(binding.myConPass.getText().toString())) {
            showText("password and conform password must be same");
        } else
            {
            return true;
        }
        return false;
    }
    private void loading(Boolean isLoading)
    {
        if(isLoading)
        {
            binding.mySubmit.setVisibility(View.INVISIBLE);
            binding.progressBar1.setVisibility(View.VISIBLE);
        }else
        {
            binding.mySubmit.setVisibility(View.INVISIBLE);
            binding.progressBar1.setVisibility(View.VISIBLE);
        }


    }

}