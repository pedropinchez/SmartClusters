package com.supreme.smartclusters;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private ArrayList<MyListData> listData;
    static Context cnt;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;
    public double f;

    public MyListAdapter(ArrayList<MyListData> listData, Context cnt) {
        this.listData = listData;
        this.cnt = cnt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_items, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MyListData myListData = listData.get(position);

        try {


                holder.unicode.setText("Uni Code: " + myListData.getUniCode());
                holder.coursename.setText(myListData.getCourseName());
                holder.cutoff_21.setText("2021 clusters: " + myListData.getCutoffTwo());
                holder.progcode.setText("ProgCode: " + myListData.getProgCode());
                holder.cutoff_20.setText("2020 cluster: " + myListData.getCutoffOne());



        }
        catch (Exception e){
          //  Toast.makeText(cnt.getApplicationContext(), "Holder error", Toast.LENGTH_SHORT).show();
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go = new Intent(cnt, Details.class);
                go.putExtra("course",myListData.getCourseName());
                go.putExtra("unicode",myListData.getUniCode());
                go.putExtra("cutoff_20",myListData.getCutoffOne());
                go.putExtra("cutoff_21", myListData.getCutoffTwo())  ;
                go.putExtra("progcode",myListData.getProgCode());
                cnt.startActivity(new Intent(go));
            }
        });
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        RecyclerView recycler;
        TextView unicode,progcode,coursename,cutoff_20,cutoff_21,clusters;
        
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            unicode = itemView.findViewById(R.id.uni_code);
            progcode= itemView.findViewById(R.id.progcode);
            coursename = itemView.findViewById(R.id.course_name);
            cutoff_20=itemView.findViewById(R.id.cutoff_2020);
            cutoff_21= itemView.findViewById(R.id.cutoff_2021);
            recycler=itemView.findViewById(R.id.recycerview);


        }
    }

}