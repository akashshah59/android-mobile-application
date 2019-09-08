package net.allieddigital.citizen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class Welcome extends Activity
{
    public static boolean bool = true;
    LocationManager service ;
    String providername ;
    GpsStatus status;
    gps g=new gps(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        final Intent intent = new Intent(this, existing.class);
        final Intent reg = new Intent(this, registration.class);
        Criteria c=new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setPowerRequirement(Criteria.ACCURACY_LOW);
        c.setAltitudeRequired(false);
        c.setSpeedRequired(false);
        c.setCostAllowed(false);       //monetary cost not allowed



        this.service=(LocationManager)getSystemService(LOCATION_SERVICE);
        providername = service.getBestProvider(c, true);

        boolean enabled = service.isProviderEnabled(providername);

                    /*Location manager kick start implementation*/
        if (!enabled)
        {
            Intent settings_intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settings_intent);
        }

        Location loc=service.getLastKnownLocation(providername);
      //  this.service.requestLocationUpdates(providername, 400, 0, this.g);


        //not at all redundant

        //Code to check whether application has been opened for the first time or no
        SharedPreferences shared = getSharedPreferences("STARTING_FILE", MODE_PRIVATE);
        bool = shared.getBoolean("FIRST_TIME", true);
        button.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                if (bool)
                    startActivity(reg);
                if (!bool)
                    startActivity(intent);
            }
        });

    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


            if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void onResume()
    {
        super.onResume();
        this.service.getGpsStatus(status);
    }
}
