package com.supreme.smartclusters;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyUniAdapter extends RecyclerView.Adapter<MyUniAdapter.ViewHolder> {

    private ArrayList<MyUniData> uniData;
    static Context cnt;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;
    public double f;

    public MyUniAdapter(ArrayList<MyUniData> uniData, Context cnt) {
        this.uniData = uniData;
        this.cnt = cnt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_unis, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MyUniData myUniData = uniData.get(position);

        try {


                holder.unicode.setText("Uni Code: " + myUniData.getUniCode());
                holder.coursename.setText(myUniData.getCourseName());





        }
        catch (Exception e){
          //  Toast.makeText(cnt.getApplicationContext(), "Holder error", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public int getItemCount() {
        return uniData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        RecyclerView recycler;
        TextView unicode,coursename;
        
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            unicode = itemView.findViewById(R.id.uni_code);

            coursename = itemView.findViewById(R.id.course_name);



        }
    }

}