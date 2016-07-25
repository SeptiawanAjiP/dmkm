package com.example.septiawanaji.knowledgemanagement.UserInterface;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.septiawanaji.knowledgemanagement.Koneksi.ConverterParameter;
import com.example.septiawanaji.knowledgemanagement.Koneksi.JSONParser;
import com.example.septiawanaji.knowledgemanagement.Koneksi.Server;
import com.example.septiawanaji.knowledgemanagement.Objek.AtributName;
import com.example.septiawanaji.knowledgemanagement.Objek.User;
import com.example.septiawanaji.knowledgemanagement.R;
import com.example.septiawanaji.knowledgemanagement.SessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Septiawan Aji on 6/22/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText nim,password;
    private Button login;
    User user;
    Server server;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nim = (EditText)findViewById(R.id.input_nim);
        password = (EditText)findViewById(R.id.input_password);
        login = (Button)findViewById(R.id.login);

        user = new User();
        sessionManager = new SessionManager(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setNim(nim.getText().toString());
                user.setPassword(password.getText().toString());
                if(sessionManager.getAlamatServer()!=""){
                    if(!user.getNim().isEmpty()){
                        if(!user.getPassword().isEmpty()){
                            new getStatusLogin().execute();
                        }else{
                            Toast.makeText(LoginActivity.this, "Masukan Password", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(LoginActivity.this, "Masukan Nim", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Atur Alamat Server Dahulu", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_settings){
            showDialog();
        }

        return super.onOptionsItemSelected(item);

    }

    public void showDialog(){
        server = new Server();
        sessionManager = new SessionManager(getApplicationContext());
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.server_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText aturServer = (EditText)dialogView.findViewById(R.id.atur_server);
        if(sessionManager.getAlamatServer() != null){
            aturServer.setText(sessionManager.getAlamatServer());
        }

        dialogBuilder.setTitle("Atur Server");
        dialogBuilder.setMessage("Masukan Alamat Server");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (aturServer.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Alamat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    sessionManager.deleteSession();
                    sessionManager.saveUrlServer(aturServer.getText().toString());
                }

            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private class getStatusLogin extends AsyncTask<String,Void,String>{
        ProgressDialog pDialog;
        JSONObject json;
        JSONArray data;

        String tangkapError = "";
        String respon;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put(AtributName.getKODE(),AtributName.getGetStatusLogin());
            parameter.put(AtributName.getNIM(), user.getNim());
            parameter.put(AtributName.getPASSWORD(), user.getPassword());

            JSONParser jsonParser = new JSONParser();

            try{
                json = jsonParser.getJSONFromUrl(sessionManager.getAlamatServer() + ConverterParameter.getQuery(parameter));
                respon = json.getString(AtributName.getRESPON());
                Log.d("Respon",respon);
                if(!respon.equals(AtributName.getNOL())){
                    parameter.remove(AtributName.getKODE());
                    parameter.put(AtributName.getKODE(),AtributName.getGetNama());
                    json = jsonParser.getJSONFromUrl(sessionManager.getAlamatServer() + ConverterParameter.getQuery(parameter));
                    data = json.getJSONArray(AtributName.getRESPON());
                    for(int i = 0;i<data.length();i++){
                        JSONObject c = data.getJSONObject(i);

                        user.setNama(c.getString(AtributName.getNAMA()));
                    }
                }

            }catch (Exception e){
                tangkapError = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (tangkapError == ("")) {
                if(respon.equals("1")){
                    sessionManager.createSessionLogin(user.getNim(), user.getNama());
//                    Toast.makeText(LoginActivity.this, user.getNama(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MenuUtamaActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Username dan Password Salah", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(LoginActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();

        }
    }
}
