package com.example.hjpar.imsdualcamera;

import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.FrameLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private Camera mBackCamera;
    private Camera mFrontCamera;
    private Camera mThirdCamera;
    private Camera mFourthCamera;

    private BackCameraPreview mBackCamPreview;
    private FrontCameraPreview mFrontCamPreview;
    private ThirdCameraPreview mThirdCamPreview;
    private FourthCameraPreview mFourthCamPreview;

    public static String TAG = "DualCamActivity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Number of cameras: " + Camera.getNumberOfCameras());

        // Create an instance of Camera
        mBackCamera = getCameraInstance(0);
        // Create back camera Preview view and set it as the content of our activity.
        mBackCamPreview = new BackCameraPreview(this, mBackCamera);
        FrameLayout backPreview = (FrameLayout) findViewById(R.id.back_camera_preview);
        backPreview.addView(mBackCamPreview);

        mFrontCamera = getCameraInstance(1);
        mFrontCamPreview = new FrontCameraPreview(this, mFrontCamera);
        FrameLayout frontPreview = (FrameLayout) findViewById(R.id.front_camera_preview);
        frontPreview.addView(mFrontCamPreview);

        // Create an instance of Camera
        mThirdCamera = getCameraInstance(2);
        // Create Third camera Preview view and set it as the content of our activity.
        mThirdCamPreview = new ThirdCameraPreview(this, mThirdCamera);
        FrameLayout thirdPreview = (FrameLayout) findViewById(R.id.third_camera_preview);
        thirdPreview.addView(mThirdCamPreview);

        mFourthCamera = getCameraInstance(3);
        mFourthCamPreview = new FourthCameraPreview(this, mFourthCamera);
        FrameLayout fourthPreview = (FrameLayout) findViewById(R.id.fourth_camera_preview);
        fourthPreview.addView(mFourthCamPreview);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dual_cam, menu);
        return true;
    }


    public static Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId); // attempt to get a Camera instance
            Camera.Parameters params = c.getParameters();
            // Check what resolutions are supported by your camera
            //List<Size> sizes = params.getSupportedPictureSizes();
            // Iterate through all available resolutions and choose one.
// The chosen resolution will be stored in mSize.
            //Size mSize;
            /*for (Size size : sizes) {
                Log.i(TAG, "Available resolution: " + size.getWidth() + " " + size.getHeight());
                mSize = size;
            }
            Log.i(TAG, "Chosen resolution: " + mSize.width + " " + mSize.getHeight());
            */
            //params.setPictureSize(mSize.getWidth(), mSize.getHeight());
            //c.setParameters(params);

        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            Log.e(TAG, "Camera " + cameraId + " not available! " + e.toString());
        }
        return c; // returns null if camera is unavailable
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
