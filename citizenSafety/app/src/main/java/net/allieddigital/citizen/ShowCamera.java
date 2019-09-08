package net.allieddigital.citizen;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Akash on 7/23/2015.
 */
public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    public ShowCamera(Context context) {
        super(context);
    }
    private Camera thecam;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            thecam.setPreviewDisplay(holder);
            thecam.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }



}
