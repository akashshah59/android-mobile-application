package net.allieddigital.citizen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class registration extends Activity  {

/*This class provides a registration page for users who have newly opened the application*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //final Intent email = new Intent(Intent.ACTION_SEND);
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText number = (EditText) findViewById(R.id.number);
        final EditText mail = (EditText) findViewById(R.id.mail);

        ImageButton ib=(ImageButton)findViewById(R.id.imageButton);

        //general checkup


        SharedPreferences sharedPref = getSharedPreferences("STARTING_FILE",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("FIRST_TIME",false);
        editor.commit();



        //adding email defaults
       // email.setData(Uri.parse("mailTo:"));
        //email.setType("message/rfc822");


        //code for button logic
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().equals("")) {
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(getApplicationContext(), " Name is required", duration).show();
                    //You can Toast a message here that the Username is Empty
                }

                if (number.getText().toString().trim().equals("")) {
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(getApplicationContext(), " Number is required", duration).show();
                    //You can Toast a message here that the Phone Number is Empty
                }

                if (mail.getText().toString().trim().equals("")) {

                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(getApplicationContext(), " email-id is required", duration).show();

                    //You can Toast a message here that the Email-id is Empty
                }

                if (password.getText().toString().trim().equals("")) {

                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(getApplicationContext(), " Password is required", duration).show();
                    //You can Toast a message here that the PASSWORD is Empty
                } else {
                    editor.putString("NAME", name.getText().toString());
                    editor.commit();
                    editor.putString("PASSWORD", password.getText().toString());
                    editor.commit();
                    editor.putString("NUMBER",number.getText().toString());
                    editor.commit();
                    editor.putString("MAIL", mail.getText().toString());
                    editor.commit();

           /*       email.putExtra(Intent.EXTRA_EMAIL, "akashshah59@gmail.com");
                    email.putExtra(Intent.EXTRA_SUBJECT, "Registration details");
                    email.putExtra(Intent.EXTRA_TEXT,name.getText().toString().toUpperCase());

                    startActivity(Intent.createChooser(email, "Sending email..."));
                    Log.d("mail:", "Sent! Hurray");*/
                    Log.d("Name", name.getText().toString());
                    Log.d("Status","Jumping to the Camera class");
                    Intent i = new Intent(getApplicationContext(), existing.class);
                    startActivity(i);
                    finish();
                }
            }
        });
       }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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

    public void onDestroy() {
        super.onDestroy();
        android.os.Debug.stopMethodTracing();

    }


}
