package com.sos.comboapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfig.Builder;
import androidx.camera.core.impl.VideoCaptureConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import com.sos.comboapp.R;
import com.sos.comboapp.databinding.ActivityTexcherViewBinding;

import java.io.IOException;

public class Texcher_view_Activity extends AppCompatActivity
{

       private  ActivityTexcherViewBinding binding;
       private  Camera mCamera;
       private int front = 1;
       private int back = 2;
       private int camara = front;
       private boolean isPrivate = false;
       private UseCaseConfig.Builder builder;
       private PreviewConfig previewConfig;
       private Preview preview;
       private UseCaseConfig.Builder builder1;
       private VideoCaptureConfig videoCaptureConfig;
       private VideoCapture videoCapture;
      // private CameraX.LensFacing lensFacing = CameraX.LensFacing.FRONT;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding=ActivityTexcherViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initCamera();
        initListner();

        binding.myCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent= new Intent(Intent.ACTION_PICK);
                startActivity(intent);

            }
        });
    }

    private void initListner()
    {

       /* binding.myCamera.setOnClickListener(v -> {
            if (camara==front)
            {
                camara = back;
                lensFacing = CameraX.LensFacing.BACK;
            } else {
                camara = front;
                lensFacing = CameraX.LensFacing.FRONT;
            }
            CameraX.unbindAll();
            initCamera();
        });
        binding.lytPrivacy.setOnClickListener(v -> {
            isPrivate = !isPrivate;*/

            if (isPrivate) {
               /* binding.imgLock.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lock));
                binding.tvPrivacy.setText("Private");*/

            } else {
              /*  binding.imgLock.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.unlock));
                binding.tvPrivacy.setText("Public");*/
            }
        }
       /* binding.btnClose.setOnClickListener(v -> onBackPressed());
        binding.btnLive.setOnClickListener(v -> {
            startActivity(new Intent(this, HostLiveActivity.class));
            finish();
        });
*/


    @SuppressLint("RestrictedApi")
    private void initCamera()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    1);
        } else {


            /*TextureView viewFinder = binding.viewFinder;
            int ratio = AspectRatio.RATIO_4_3;
            builder = new UseCaseConfig.Builder();
            previewConfig = builder.setTargetAspectRatio(ratio)
                    .setLensFacing(lensFacing)
                    .setTargetRotation(Surface.ROTATION_90)
                    .build();
            preview = AutoFitPreviewBuilder.Companion.build(previewConfig, viewFinder);
            builder1 = new UseCaseConfig.Builder();
            videoCaptureConfig = builder1.setTargetAsctRatio(ratio)
                    .setLensFacing(lensFacing)
                    .setVideoFrameRate(24)
                    .setTargetRotation(Surface.ROTATION_0)
                    .build();
            videoCapture = new VideoCapture(videoCaptureConfig);
            CameraX.bindToLifecycle(this, preview, videoCapture);
        */
        }



    }


}