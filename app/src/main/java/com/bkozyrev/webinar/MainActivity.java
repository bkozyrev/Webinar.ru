package com.bkozyrev.webinar;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final float FAB_ALPHA_WHILE_NOT_RECORDING = 1.0f;
    private static final float FAB_ALPHA_WHILE_RECORDING = 0.7f;

    private Camera mCamera;
    private CameraPreview mPreview;

    private boolean mIsRecording;

    private FrameLayout mFrameLayout;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFrameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mIsRecording = false;

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsRecording) {
                    startRecording();
                } else {
                    stopRecording();
                }
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        stopRecording();
    }

    public void startRecording(){
        mFab.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_videocam_black_24dp));
        mFab.setAlpha(FAB_ALPHA_WHILE_RECORDING);

        mCamera = getCameraInstance();
        mPreview = new CameraPreview(getBaseContext(), mCamera);
        mFrameLayout.addView(mPreview);

        mIsRecording = true;
    }

    public void stopRecording(){
        mFab.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_videocam_white_24dp));
        mFab.setAlpha(FAB_ALPHA_WHILE_NOT_RECORDING);
        mFrameLayout.removeView(mPreview);

        mIsRecording = false;
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
