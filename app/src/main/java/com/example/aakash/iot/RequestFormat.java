package com.example.aakash.iot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RequestFormat extends AppCompatActivity implements ViewSwitcher.ViewFactory {

    ImageSwitcher lamp_0,lamp_1;
    ImageButton b_0,b_1,b_all;
    ActionRequest action =new ActionRequest();
    static DocumentBuilderFactory factory;
    static DocumentBuilder builder;
    static HashMap state=new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_format);

        factory = DocumentBuilderFactory.newInstance();
        //Get Initial state of lamps
        String response=null;
        try {
            response=action.operation("getStateDirect","0");
            builder=factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource( new StringReader(response)));
            NodeList nodeList = document.getElementsByTagName("bool");
            Node node = nodeList.item(0);
            state.put("LAMP_0",node.getAttributes().getNamedItem("val").getNodeValue());
            response=action.operation("getStateDirect","1");
            builder=factory.newDocumentBuilder();
            document = builder.parse(new InputSource( new StringReader(response)));
            nodeList = document.getElementsByTagName("bool");
            node=nodeList.item(0);
            state.put("LAMP_1",node.getAttributes().getNamedItem("val").getNodeValue());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Here display original images according to state.
        viewSetup();

        b_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //String response=action.operation("switchON","0");
                    action.operation("toggle","0");
                    if(state.get("LAMP_0").equals("true")) {
                        state.put("LAMP_0","false");
                        lamp_0.setImageResource(R.drawable.lamp_off);
                    }
                else if(state.get("LAMP_0").equals("false")) {
                    state.put("LAMP_0","true");
                    lamp_0.setImageResource(R.drawable.lamp_on);
                }

            }
        });
        b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.operation("toggle","1");
                if(state.get("LAMP_1").equals("true")) {
                    state.put("LAMP_1","false");
                    lamp_1.setImageResource(R.drawable.lamp_off);
                }
                else if(state.get("LAMP_1").equals("false")) {
                    state.put("LAMP_1","true");
                    lamp_1.setImageResource(R.drawable.lamp_on);
                }
            }
        });
        b_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.operation("allToggle");
                if(state.get("LAMP_0").equals("true") && state.get("LAMP_1").equals("true")) {
                    state.put("LAMP_0","false");
                    state.put("LAMP_1","false");
                    lamp_0.setImageResource(R.drawable.lamp_off);
                    lamp_1.setImageResource(R.drawable.lamp_off);
                }
                else if(state.get("LAMP_0").equals("false") && state.get("LAMP_1").equals("false")) {
                    state.put("LAMP_0","true");
                    state.put("LAMP_1","true");
                    lamp_0.setImageResource(R.drawable.lamp_on);
                    lamp_1.setImageResource(R.drawable.lamp_on);
                }
                else if(state.get("LAMP_0").equals("false") || state.get("LAMP_1").equals("false")) {
                    state.put("LAMP_0","true");
                    state.put("LAMP_1","true");
                    lamp_0.setImageResource(R.drawable.lamp_on);
                    lamp_1.setImageResource(R.drawable.lamp_on);
                }

            }
        });
    }

    void viewSetup()
    {

        b_0=(ImageButton)findViewById(R.id.lamp_0);
        b_1=(ImageButton)findViewById(R.id.lamp_1);
        b_all=(ImageButton)findViewById(R.id.lamp_all);
        lamp_0=(ImageSwitcher)findViewById(R.id.imageSwitcher1);
        lamp_1=(ImageSwitcher)findViewById(R.id.imageSwitcher2);
        lamp_0.setFactory(this);
        lamp_1.setFactory(this);
        lamp_0.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        lamp_0.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
        lamp_1.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        lamp_1.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
        if(state.get("LAMP_0").equals("false")) lamp_0.setImageResource(R.drawable.lamp_off);
        else lamp_0.setImageResource(R.drawable.lamp_on);

        if(state.get("LAMP_1").equals("false")) lamp_1.setImageResource(R.drawable.lamp_off);
        else lamp_1.setImageResource(R.drawable.lamp_on);

    }

    @Override
    public View makeView() {
        ImageView myView=new ImageView(getApplicationContext());
        return myView;
    }
}
