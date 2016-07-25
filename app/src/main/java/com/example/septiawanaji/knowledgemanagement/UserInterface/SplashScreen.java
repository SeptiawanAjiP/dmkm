package com.example.septiawanaji.knowledgemanagement.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.septiawanaji.knowledgemanagement.MainActivity;
import com.example.septiawanaji.knowledgemanagement.Objek.AtributName;
import com.example.septiawanaji.knowledgemanagement.R;
import com.example.septiawanaji.knowledgemanagement.SessionManager.SessionManager;

import java.util.HashMap;

/**
 * Created by Septiawan Aji on 6/22/2016.
 */
public class SplashScreen extends AppCompatActivity {
    SessionManager sessionManager;
    HashMap<String,String> hm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sessionManager = new SessionManager(getApplicationContext());
        hm = sessionManager.getUserSession();
        Log.d("HM ",hm.toString());
        Thread splash = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                    if(hm.get(AtributName.getNAMA())==null){
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(getApplicationContext(),MenuUtamaActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }
}
