package net.allieddigital.citizen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Akash on 7/9/2015.
 Implementing the LocationListener for constant gps updates
 */
public class gps implements LocationListener {
    double lon=0.0,lat=0.0;
    private Context mcontext;
    public gps()
    {
        Log.d("Inside","Default Constructor");
    }
    public gps(Context context)
    {
        this.mcontext=context;
    }

    @Override
    public void onLocationChanged(Location location) {
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences("STARTING_FILE",Context.MODE_PRIVATE);
        Log.d("Inside","onLocationChanged");
        TextView tv=(TextView)((Activity)mcontext).findViewById(R.id.location);
        if(location==null)
            tv.setText("Location cannot be changed");
        else
            tv.setText("Location can be changed");

        lon=location.getLongitude();
        lat=location.getLatitude();
        TextView tv1=(TextView)((Activity)mcontext).findViewById(R.id.location1);
        TextView tv2=(TextView)((Activity)mcontext).findViewById(R.id.location2);
        Log.d("Location Update","Changing");
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("LATITUDE",Double.toString(lat));
        editor.commit();
        editor.putString("LONGITUDE",Double.toString(lon));
        editor.commit();
        tv1.setText(Double.toString(lat));
        tv2.setText(Double.toString(lon));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationManager lm= (LocationManager)mcontext.getSystemService(Context.LOCATION_SERVICE);
    /*code to change provider: not complete */
        if(status== LocationProvider.OUT_OF_SERVICE && status==LocationProvider.TEMPORARILY_UNAVAILABLE) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, this);
        }
        }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }




}
