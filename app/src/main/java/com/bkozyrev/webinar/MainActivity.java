package com.bkozyrev.webinar;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mPreview;

    private boolean isRecording;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        isRecording = false;

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRecording) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_videocam_black_24dp));
                    mCamera = getCameraInstance();
                    mPreview = new CameraPreview(getBaseContext(), mCamera);
                    frameLayout.addView(mPreview);
                    //mPreview.startPreview();
                    isRecording = true;
                }
                else{
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_videocam_white_24dp));
                    frameLayout.removeView(mPreview);
                    //mPreview.stopPreview();
                    isRecording = false;
                }

                Log.d("Camera use", "" + isRecording);
            }
        });
    }

    public Camera getCameraInstance(){
        Camera camera = null;
        try {
            camera = Camera.open();
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(), "Camera is not available", Toast.LENGTH_SHORT).show();
        }
        return camera;
    }
}
