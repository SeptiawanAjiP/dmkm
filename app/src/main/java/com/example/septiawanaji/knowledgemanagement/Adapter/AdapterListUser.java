package com.example.septiawanaji.knowledgemanagement.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.septiawanaji.knowledgemanagement.ImageLoader.ImageLoader;
import com.example.septiawanaji.knowledgemanagement.Objek.AtributName;
import com.example.septiawanaji.knowledgemanagement.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Septiawan Aji on 6/27/2016.
 */
public class AdapterListUser extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    ArrayList<HashMap<String,String>> daftarMaba;
    HashMap<String,String> resultp = new HashMap<>();
    private LayoutInflater inflater;

    ImageLoader imageLoader;

    public AdapterListUser(Context context, int layoutResourceId, ArrayList<HashMap<String, String>> daftarMaba){
        super(context,layoutResourceId,daftarMaba);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.daftarMaba = daftarMaba;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView namaUser,jumlahUpload;
        ImageView fotoUser;
        inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  itemView = inflater.inflate(R.layout.adapter_list_user,parent,false);

        resultp = daftarMaba.get(position);

        namaUser = (TextView)itemView.findViewById(R.id.nama_user);
        jumlahUpload = (TextView)itemView.findViewById(R.id.jumlah_upload);
        fotoUser = (ImageView)itemView.findViewById(R.id.foto_user);

        namaUser.setText(resultp.get(AtributName.getNAMA()));
        jumlahUpload.setText("Jumlah Upload : "+resultp.get(AtributName.getTotalUpload()));
//        try {
//            urutMaba.setText(Integer.toString(position+1));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        imageLoader.DisplayImage(resultp.get(AtributName.getPathFoto()),fotoUser);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultp = daftarMaba.get(position);
                Log.d("POSITION",Integer.toString(position));
                Toast.makeText(context, resultp.get(AtributName.getNAMA()), Toast.LENGTH_SHORT).show();
            }
        });
        return itemView;
    }
}
