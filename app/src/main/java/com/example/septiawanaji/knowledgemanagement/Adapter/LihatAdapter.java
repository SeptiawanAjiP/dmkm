package com.example.septiawanaji.knowledgemanagement.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.septiawanaji.knowledgemanagement.Objek.AtributName;
import com.example.septiawanaji.knowledgemanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;

/**
 * Created by Septiawan Aji on 6/29/2016.
 */
public class LihatAdapter extends RecyclerView.Adapter<LihatAdapter.myViewHolder> {

    ArrayList<HashMap<String,String>> lihatArray = new ArrayList<>();

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView namaLihat, totalUpload, totalWord;

        public myViewHolder(View view) {
            super(view);
            namaLihat = (TextView) view.findViewById(R.id.namaLihat);
            totalUpload = (TextView) view.findViewById(R.id.totalUpload);
            totalWord = (TextView) view.findViewById(R.id.totalWord);
        }
    }

    public LihatAdapter(ArrayList<HashMap<String,String>> lihatArray){
            this.lihatArray = lihatArray;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lihat,parent,false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        HashMap<String,String> hm = lihatArray.get(position);
        holder.namaLihat.setText(hm.get(AtributName.getNAMA()));
        holder.totalWord.setText(hm.get(AtributName.getTotalWord()));
        holder.totalUpload.setText(hm.get(AtributName.getTotalUpload()));
    }

    @Override
    public int getItemCount() {
        return lihatArray.size();
    }
}

