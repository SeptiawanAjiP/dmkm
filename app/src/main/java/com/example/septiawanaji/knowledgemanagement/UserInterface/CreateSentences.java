package com.example.septiawanaji.knowledgemanagement.UserInterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.septiawanaji.knowledgemanagement.Koneksi.ConverterParameter;
import com.example.septiawanaji.knowledgemanagement.Koneksi.JSONParser;
import com.example.septiawanaji.knowledgemanagement.Objek.AtributName;
import com.example.septiawanaji.knowledgemanagement.Objek.Sentences;
import com.example.septiawanaji.knowledgemanagement.Objek.User;
import com.example.septiawanaji.knowledgemanagement.R;
import com.example.septiawanaji.knowledgemanagement.SessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Septiawan Aji on 6/22/2016.
 */
public class CreateSentences extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Button button;
    SessionManager sessionManager;
    HashMap<String,String> hm;
    User user;
    Sentences sentences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentences);
        Log.d("Create Sentences", "On");

        textView = (TextView)findViewById(R.id.nama);
        editText = (EditText)findViewById(R.id.kalimat);
        button = (Button)findViewById(R.id.submit);

        user = new User();
        sentences = new Sentences();
        sessionManager = new SessionManager(getApplicationContext());
        hm = sessionManager.getUserSession();
        user.setNim(hm.get(AtributName.getNIM()));
        textView.setText(hm.get(AtributName.getNAMA()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentences.setKalimat(editText.getText().toString().replace(".","").replace(",",""));
                new uploadSentences().execute();
//                editText.setText("");
            }
        });


    }

    private int totalWord(String sentences){
        int count = 1;
        for(int i=0;i<=sentences.length()-1;i++){
            if(sentences.charAt(i) == ' ' && sentences.charAt(i+1)!= ' '){
                count++;
            }
        }
        //Toast.makeText(CreateSentences.this,"Jumlah Kata : "+Integer.toString(count) , Toast.LENGTH_SHORT).show();
        return count;

    }



    private class uploadSentences extends AsyncTask<String,Void,String>{
        ProgressDialog pDialog;
        JSONObject json;
        JSONArray data;

        String tangkapError = "";
        String respon;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateSentences.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HashMap<String,String> parameter = new HashMap<>();
            parameter.put(AtributName.getNIM(),user.getNim());
            parameter.put(AtributName.getKODE(),AtributName.getInsertSentences());
            parameter.put(AtributName.getSENTENCES(), sentences.getKalimat());
            parameter.put(AtributName.getTotalWord(), Integer.toString(totalWord(sentences.getKalimat())));

            JSONParser jsonParser = new JSONParser();

            try{
                json = jsonParser.getJSONFromUrl(sessionManager.getAlamatServer() + ConverterParameter.getQuery(parameter));
                respon = json.getString(AtributName.getRESPON());
                Log.d("Respon Insert",respon);


            }catch (Exception e){
                tangkapError = e.getMessage();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(tangkapError == ("")){
                if(!respon.equals(AtributName.getNOL())){
                    Intent intent = new Intent(getApplicationContext(),MenuUtamaActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(CreateSentences.this, "Input Berhasil", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(CreateSentences.this, "Input Gagal", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(CreateSentences.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }
}
