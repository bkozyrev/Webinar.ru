package com.bkozyrev.webinar;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final int CAMERA_ROTATE_DEGREE = 90;

    private boolean mPreviewIsRunning;

    private SurfaceHolder mHolder;
    private Camera mCamera;

    private Context mContext;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mContext = context;

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(CAMERA_ROTATE_DEGREE);
        } catch (IOException e) {
            Toast.makeText(mContext, "Error setting camera preview", Toast.LENGTH_SHORT).show();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPreview();
        mCamera.release();
        mCamera = null;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        startPreview();
    }

    public void startPreview() {
        if (!mPreviewIsRunning && (mCamera != null)) {
            mCamera.startPreview();
            mPreviewIsRunning = true;
        }
    }

    public void stopPreview() {
        if (mPreviewIsRunning && (mCamera != null)) {
            mCamera.stopPreview();
            mPreviewIsRunning = false;
        }
    }
}
