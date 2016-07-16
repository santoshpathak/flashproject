package santosh.com.flashproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.DialogInterface;

import static android.util.Log.*;

public class MainActivity extends AppCompatActivity {

    private Boolean isTorchOn;
    private CameraManager mCameraManager;
    private String mCameraId;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button switchButton = (Button)findViewById(R.id.buttonSwitch);
        isTorchOn = false;

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                w("myApp", "Switch clicked");
                boolean hasFlash = isFlashAvailable();
                System.out.println("myApp " + hasFlash);
                if(hasFlash){
                    mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    System.out.println("myApp inside if");
                    try {
                        mCameraId = mCameraManager.getCameraIdList()[0];
                        System.out.println("myApp " + mCameraId);

                        try {
                            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            //mp.start();

                            if(!isTorchOn) {
                                mCameraManager.setTorchMode(mCameraId, true);
                                System.out.println("myApp insidetry");
                                isTorchOn = true;
                            }else{
                                mCameraManager.setTorchMode(mCameraId, false);
                                isTorchOn = false;
                            }
                                //playOnOffSound();
                                //mTorchOnOffButton.setImageResource(R.drawable.on);
                            //}
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public boolean isFlashAvailable(){
        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {

            AlertDialog alert = new AlertDialog.Builder(this)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                    System.exit(0);
                }
            });
            alert.show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
