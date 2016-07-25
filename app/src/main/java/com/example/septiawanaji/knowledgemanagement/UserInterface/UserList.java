package com.example.septiawanaji.knowledgemanagement.UserInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.example.septiawanaji.knowledgemanagement.Adapter.AdapterListUser;
import com.example.septiawanaji.knowledgemanagement.Koneksi.ConverterParameter;
import com.example.septiawanaji.knowledgemanagement.Koneksi.JSONParser;
import com.example.septiawanaji.knowledgemanagement.Objek.AtributName;
import com.example.septiawanaji.knowledgemanagement.Objek.User;
import com.example.septiawanaji.knowledgemanagement.R;
import com.example.septiawanaji.knowledgemanagement.SessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Septiawan Aji on 6/27/2016.
 */
public class UserList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_listuser_grid);
        if(cekKoneksi()){
            new getListMaba().execute();
        }else{
            Toast.makeText(UserList.this,"Tidak Ada Koneksi", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean cekKoneksi() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private class getListMaba extends AsyncTask<String,Void,String>{
        ProgressDialog pDialog;
        JSONObject json;
        JSONArray respon;
        User user;

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        String tangkapError="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UserList.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String> parameter = new HashMap<>();
            parameter.put(AtributName.getKODE(), AtributName.getGetUserList());

            JSONParser jsonParser = new JSONParser();
            try{
                json = jsonParser.getJSONFromUrl(sessionManager.getAlamatServer()+ ConverterParameter.getQuery(parameter));
                respon = json.getJSONArray(AtributName.getRESPON());
                Log.d("Daftar User",respon.toString());
                for(int i=0;i<respon.length();i++){
                    HashMap<String,String> map = new HashMap<>();
                    user = new User();
                    JSONObject c = respon.getJSONObject(i);

                    user.setNama(c.getString(AtributName.getNAMA()));
                    user.setJumlahUpload(c.getInt(AtributName.getTotalUpload()));
                    user.setPathFoto(c.getString(AtributName.getPathFoto()));

                    map.put(AtributName.getNAMA(), user.getNama());
                    map.put(AtributName.getTotalUpload(),Integer.toString(user.getJumlahUpload()));
                    map.put(AtributName.getPathFoto(),user.getPathFoto());
                    arrayList.add(map);
                }

            }catch (Exception e){
                e.printStackTrace();
                tangkapError = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(arrayList.size()>0){
                GridView gv = (GridView)findViewById(R.id.gridView_maba);
                AdapterListUser adapterDaftarMaba = new AdapterListUser(UserList.this,R.layout.activity_daftar_listuser_grid,arrayList);
                gv.setAdapter(adapterDaftarMaba);
                pDialog.dismiss();
            }
        }
    }
}
