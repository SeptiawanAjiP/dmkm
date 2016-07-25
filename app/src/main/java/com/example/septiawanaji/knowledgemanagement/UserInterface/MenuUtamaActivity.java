package com.example.septiawanaji.knowledgemanagement.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.septiawanaji.knowledgemanagement.Objek.AtributName;
import com.example.septiawanaji.knowledgemanagement.R;
import com.example.septiawanaji.knowledgemanagement.SessionManager.SessionManager;

import java.util.HashMap;

/**
 * Created by Septiawan Aji on 6/28/2016.
 */
public class MenuUtamaActivity extends AppCompatActivity {
    ImageButton tulis,lihat,mining,statistik;
    TextView nama;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        sm = new SessionManager(getApplicationContext());

        HashMap<String,String> hashMap= sm.getUserSession();
        tulis = (ImageButton)findViewById(R.id.imageButtonTulis);
        lihat = (ImageButton)findViewById(R.id.imageButtonOrang);
        mining = (ImageButton)findViewById(R.id.imageButtonMining);
        statistik = (ImageButton)findViewById(R.id.imageButtonStatistik);
        nama = (TextView)findViewById(R.id.namaMenuUtama);

        nama.setText(hashMap.get(AtributName.getNAMA()));
        tulis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateSentences.class);
                startActivity(intent);
                finish();
            }
        });

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MenuUtamaActivity.this, "Belum Tersedia", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),UserList.class);
                startActivity(intent);
            }
        });

        mining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuUtamaActivity.this, "Belum Tersedia", Toast.LENGTH_SHORT).show();
            }
        });

        statistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuUtamaActivity.this, "Belum Tersedia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_setting_logout){
            sm.deleteSession();
            Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}
