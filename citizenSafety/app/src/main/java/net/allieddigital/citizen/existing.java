package net.allieddigital.citizen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class existing extends Activity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private static Uri f;
    private static int MEDIA_TYPE_IMAGE=1;
    private static int TAKEPHOTO=CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE;
    private Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //private static int MEDIA_TYPE_VIDEO=2;

    LocationManager service ;
    String providername ;
    GpsStatus status;
    gps g=new gps(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbuilt);


        //---------------------------------------------------------GPS CODE----------------------------------------------------------------------------
        Criteria c=new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setPowerRequirement(Criteria.ACCURACY_LOW);
        c.setAltitudeRequired(false);
        c.setSpeedRequired(false);
        c.setCostAllowed(false); //monetary cost not allowed

        this.service=(LocationManager)getSystemService(LOCATION_SERVICE);
        providername = service.getBestProvider(c, true);
        boolean enabled = service.isProviderEnabled(providername);
        if (!enabled)
        {
            Intent settings_intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settings_intent);
        }

        Location loc=service.getLastKnownLocation(providername);
        this.service.requestLocationUpdates(providername, 1000, 0, this.g);

        //------------------------------------------------------CAMERA STUFF--------------------------------------------------------------------

        Log.d("Inside","Calling getOutput from Oncreate");
        f = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, f);
        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        //-----------------------------------------------CAMERA STUFF ENDS------------------------------------------------------------



    }


    //---------------------------------------------------------------------------------------------FILE CODE-------------------------------------------------------------------------------------------------

    public static Uri getOutputMediaFileUri(int type){
        Log.d("Inside","Uri function");
        return Uri.fromFile(getOutputMediaFile(type));

    }

    /** Create a File for saving an image or video */
   public static File getOutputMediaFile(int type){
        Log.d("Inside","File function");
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Allied");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        Log.d("Path",mediaStorageDir.getPath());
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists())
       {
            Log.d("Status", "Allied not seen yet");
            if (! mediaStorageDir.mkdirs()){
                Log.d("Citizen", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        }
        else {
            return null;
        }
    //    Log.d("Error","Not here");
        return mediaFile;

   }

//--------------------------------------------------------------------------------------------FILE CODE ENDS-------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_existing, menu);
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
    protected void onResume(){
    super.onResume();
        this.service.getGpsStatus(status);








        //--------------------------------------------------PRIORITY CODE---------------------------------------------------------------------
        final ImageButton red=(ImageButton)findViewById(R.id.RED);
        final ImageButton yellow=(ImageButton)findViewById(R.id.YELLOW);
        final ImageButton green=(ImageButton)findViewById(R.id.GREEN);
        final TextView tv=(TextView)findViewById(R.id.textView8);
        final Intent email = new Intent(Intent.ACTION_SEND,Uri.parse("mailTo:ravindradeshpande@hotmail.com"));
        SharedPreferences preferences=getSharedPreferences("STARTING_FILE",MODE_PRIVATE);
        final String name = preferences.getString("NAME","null");
        String password=preferences.getString("PASSWORD","null");
        String number=preferences.getString("NUMBER","null");
        String mail=preferences.getString("MAIL","null");
        String latitude=preferences.getString("LATITUDE","0.0");
        String longitude=preferences.getString("LONGITUDE","0.0");

       final String whole2=name.concat("\n" + password + "\n" + mail + "\n" + number + "\n"+ latitude + "\n" + longitude);



        email.setData(Uri.parse("mailTo:"));
        //just to start clients
        email.setType("text/plain");

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.putExtra(Intent.EXTRA_SUBJECT,"URGENT EVENT");
                //email.putExtra(Intent.EXTRA_EMAIL,"akashshah59@gmail.com");
                email.putExtra(Intent.EXTRA_EMAIL,new String[] {"ravindradeshpande@hotmail.com",""});
                email.putExtra(Intent.EXTRA_TEXT, whole2);
                if(f!=null) {
                    email.putExtra(Intent.EXTRA_STREAM, f);
                    tv.setText(R.string.taken1);
                }else
                    tv.setText(R.string.taken2);
                startActivity(Intent.createChooser(email, "Choose email client"));
            }
        });


        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.putExtra(android.content.Intent.EXTRA_SUBJECT, "EVENT");
                email.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {"ravindradeshpande@hotmail.com",""});
                email.putExtra(android.content.Intent.EXTRA_TEXT,whole2);
                if(f!=null) {
                    email.putExtra(Intent.EXTRA_STREAM, f);
                    tv.setText(R.string.taken1);
                }else
                    tv.setText(R.string.taken2);
                startActivity(Intent.createChooser(email, "Choose email client"));

            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.putExtra(Intent.EXTRA_SUBJECT, "NOT URGENT EVENT");
                email.putExtra(Intent.EXTRA_EMAIL,new String[] {"ravindradeshpande@hotmail.com",""});
                if(f!=null) {
                    email.putExtra(Intent.EXTRA_STREAM, f);
                    tv.setText(R.string.taken1);
                }else
                    tv.setText(R.string.taken2);
                email.putExtra(Intent.EXTRA_TEXT,whole2);
                startActivity(Intent.createChooser(email, "Choose email client (Preferably GMail)"));
                       }
        });
    }




    }
