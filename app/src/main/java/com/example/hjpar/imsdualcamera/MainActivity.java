package com.example.hjpar.imsdualcamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private Camera mFirstCamera;
    private Camera mSecondCamera;
    private Camera mThirdCamera;
    private Camera mFourthCamera;

    private BackCameraPreview mFirstCamPreview;
    private FrontCameraPreview mSecondCamPreview;
    private ThirdCameraPreview mThirdCamPreview;
    private FourthCameraPreview mFourthCamPreview;

    public static String TAG = "QuadCamActivity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        Log.i(TAG, "Number of cameras: " + Camera.getNumberOfCameras());

        // Create an instance of Camera
        mFirstCamera = getCameraInstance(0);
        // Create back camera Preview view and set it as the content of our activity.
        mFirstCamPreview = new BackCameraPreview(this, mFirstCamera);
        FrameLayout backPreview = (FrameLayout) findViewById(R.id.back_camera_preview);
        backPreview.addView(mFirstCamPreview);

        mSecondCamera = getCameraInstance(1);
        mSecondCamPreview = new FrontCameraPreview(this, mSecondCamera);
        FrameLayout frontPreview = (FrameLayout) findViewById(R.id.front_camera_preview);
        frontPreview.addView(mSecondCamPreview);

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

        Button camBtn1 = (Button) findViewById(R.id.btn1);
        camBtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBtn1Click();
            }
        });

        Button camBtn2 = (Button) findViewById(R.id.btn2);
        camBtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBtn2Click();
            }
        });

        Button camBtn3 = (Button) findViewById(R.id.btn3);
        camBtn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBtn3Click();
            }
        });

        Button camBtn4 = (Button) findViewById(R.id.btn4);
        camBtn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBtn4Click();
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean onBtn1Click(){
        mSecondCamera.stopPreview();
        mThirdCamera.stopPreview();
        mFourthCamera.stopPreview();
        mFirstCamPreview.capture(new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    String outUriStr = MediaStore.Images.Media.insertImage(getContentResolver(),
                            bitmap, "Captured Image", "Captured Image using Camera1.");
                    if (outUriStr == null) {
                        Log.d(TAG, "CAM1 image insert failed.");
                        return;
                    } else {
                        Uri outUri = Uri.parse(outUriStr);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                    }
                    Toast.makeText(getApplicationContext(), "CAM1 image saved successfully.", Toast.LENGTH_LONG).show();
                    // restart
                    camera.startPreview();
                    mSecondCamera.startPreview();
                    mThirdCamera.startPreview();
                    mFourthCamera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, "Failed to insert CAM1 image.", e);
                }
            }
        });
        return true;
    }

    public boolean onBtn2Click(){
        mFirstCamera.stopPreview();
        mThirdCamera.stopPreview();
        mFourthCamera.stopPreview();
        mSecondCamPreview.capture(new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    String outUriStr = MediaStore.Images.Media.insertImage(getContentResolver(),
                            bitmap, "Captured Image", "Captured Image using Camera2.");
                    if (outUriStr == null) {
                        Log.d(TAG, "CAM2 image insert failed.");
                        return;
                    } else {
                        Uri outUri = Uri.parse(outUriStr);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                    }
                    Toast.makeText(getApplicationContext(), "CAM2 image saved successfully.", Toast.LENGTH_LONG).show();
                    // restart
                    camera.startPreview();
                    mFirstCamera.startPreview();
                    mThirdCamera.startPreview();
                    mFourthCamera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, "Failed to insert CAM2 image.", e);
                }
            }
        });
        return true;
    }

    public boolean onBtn3Click(){
        mFirstCamera.stopPreview();
        mSecondCamera.stopPreview();
        mFourthCamera.stopPreview();
        mThirdCamPreview.capture(new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    String outUriStr = MediaStore.Images.Media.insertImage(getContentResolver(),
                            bitmap, "Captured Image", "Captured Image using Camera3.");
                    if (outUriStr == null) {
                        Log.d(TAG, "CAM3 Image insert failed.");
                        return;
                    } else {
                        Uri outUri = Uri.parse(outUriStr);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                    }
                    Toast.makeText(getApplicationContext(), "CAM3 image saved successfully.", Toast.LENGTH_LONG).show();
                    // restart
                    camera.startPreview();
                    mFirstCamera.startPreview();
                    mSecondCamera.startPreview();
                    mFourthCamera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, "Failed to insert CAM3 image.", e);
                }
            }
        });
        return true;
    }

    public boolean onBtn4Click(){
        mFirstCamera.stopPreview();
        mSecondCamera.stopPreview();
        mThirdCamera.stopPreview();
        mFourthCamPreview.capture(new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    String outUriStr = MediaStore.Images.Media.insertImage(getContentResolver(),
                            bitmap, "Captured Image", "Captured Image using Camera4.");
                    if (outUriStr == null) {
                        Log.d(TAG, "CAM4 image insert failed.");
                        return;
                    } else {
                        Uri outUri = Uri.parse(outUriStr);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri));
                    }
                    Toast.makeText(getApplicationContext(), "CAM4 image saved successfully.", Toast.LENGTH_LONG).show();
                    // restart
                    camera.startPreview();
                    mFirstCamera.startPreview();
                    mSecondCamera.startPreview();
                    mThirdCamera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, "Failed to insert CAM4 image.", e);
                }
            }
        });
        return true;
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

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Permision denied", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "Should show request permission rational.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
