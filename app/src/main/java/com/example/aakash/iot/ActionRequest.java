package com.example.aakash.iot;
import android.app.Notification;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by AAKASH on 7/13/2017.
 */

public class ActionRequest extends AsyncTask<String,String,String> {

    private ActionRequest thread_executor;

    public String ThreadExecutor(String ...action)
    {
        thread_executor=new ActionRequest();
        try {
            return thread_executor.execute(action).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String operation(String ...action)
    {
        String url;
        String resourceID="0";
        if(action.length>1) resourceID=action[1];
        String action_type="getStateDirect";
        switch(action[0]){
            case "getState":  url="http://192.168.1.36:8080/~/mn-cse/mn-name/LAMP_"+resourceID+"/DATA/la";action_type="getState";break;
            case "getStateDirect": url="http://192.168.1.36:8080/~/mn-cse/mn-name/LAMP_"+resourceID+"?op=getStateDirect&lampid=LAMP_"+resourceID;action_type="getStateDirect";break;
            case "switchON":url ="http://192.168.1.36:8080/~/mn-cse/mn-name/LAMP_"+resourceID+"?op=setOn&lampid=LAMP_"+resourceID;action_type="switchON";break;
            case "switchOFF":url="http://192.168.1.36:8080/~/mn-cse/mn-name/LAMP_"+resourceID+"?op=setOff&lampid=LAMP_"+resourceID;action_type="switchOFF";break;
            case "toggle":url = "http://192.168.1.36:8080/~/mn-cse/mn-name/LAMP_" + resourceID + "?op=toggle&lampid=LAMP_" + resourceID;action_type="toggle";break;
            case "setON":url="http://192.168.1.36:8080/~/mn-se/mn-name/LAMP_ALL?op=allOn";action_type="setON";break;
            case "setOFF":url="http://192.168.1.36:8080/~/mn-cse/mn-name/LAMP_ALL?op=allOff";action_type="setOFF";break;
            case "allToggle":url="http://192.168.1.36:8080/~/mn-cse/mn-name/LAMP_ALL?op=allToggle";action_type="allToggle";break;
            default: url="<ISSUE IN COMMAND>";
        }
        return ThreadExecutor(url,action_type);
    }

    @Override
    protected String doInBackground(String... params) {
        String response="";
        try {
            Log.d("CONNECTING TO URL",params[0]);
            URL in_cse=new URL(params[0]);
            HttpURLConnection myConnection =(HttpURLConnection) in_cse.openConnection();
            if(params[1].equals("getState")) myConnection.setRequestMethod("GET");
            else myConnection.setRequestMethod("POST");
            myConnection.setRequestProperty("X-M2M-Origin","admin:admin");
            myConnection.setRequestProperty("Accept","application/xml");
            if (myConnection.getResponseCode() == 200) {
                // Success
                // Further processing here
                InputStream responseBody = myConnection.getInputStream();
                InputStreamReader responseBodyReader=new InputStreamReader(responseBody,"UTF-8");
                int r;
                char temp;
                while((r=responseBodyReader.read())!=-1)
                {
                    temp = (char)r;
                    response+=temp+"";
                }
            } else {
                Log.d("Error","CONNECTION NOT ESTABLISHED");
            }
        } catch (MalformedURLException e) {
            //Toast printing
            e.printStackTrace();
        } catch (IOException e) {
            //Toast Printing
            e.printStackTrace();
        }
        return response;
    }
    @Override
    protected void onPostExecute(String result) {
        //Communication for setting views in the main thread and stuff can happen here.
    }
}
