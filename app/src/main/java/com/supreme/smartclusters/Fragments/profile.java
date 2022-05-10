package com.supreme.smartclusters.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.supreme.smartclusters.MainActivity;
import com.supreme.smartclusters.R;


import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;

    TextView coursename,clusters,Grade,total,Eng,kiswa,maths,sciA,sciB,hum,applied;


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_profile, container, false);
            if(container!=null){
                container.removeAllViews();
            }
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            userId = mAuth.getCurrentUser().getUid();
            clusters=view.findViewById(R.id.clusters);
            Grade=view.findViewById(R.id.Grade);
            total=view.findViewById(R.id.total);
            Eng=view.findViewById(R.id.Eng);
            kiswa=view.findViewById(R.id.kiswa);
            maths=view.findViewById(R.id.maths);
            sciA=view.findViewById(R.id.SciA);
            sciB=view.findViewById(R.id.SciB);
            hum=view.findViewById(R.id.hum);
            applied=view.findViewById(R.id.applied);


            db.collection("marksdata").document(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                        String eng = task.getResult().getString("eng");
                        String kisw = task.getResult().getString("kisw");
                        String math = task.getResult().getString("math");
                        String SciA = task.getResult().getString("sciA");
                        String SciB = task.getResult().getString("sciB");
                        String human = task.getResult().getString("hum");
                        String app = task.getResult().getString("applied");
                        String grade = task.getResult().getString("grade");
                        String Total = task.getResult().getString("total");
                        String clust = task.getResult().getString("cluster");
                       // coursename.setText("frfrf");
                        clusters.setText(clust);
                        Grade.setText(grade);
                        total.setText(Total);
                        Eng.setText(eng);
                        kiswa.setText(kisw);
                        maths.setText(math);
                        sciA.setText(SciA);
                        sciB.setText(SciB);
                        hum.setText(human);
                        applied.setText(app);
                    Toast.makeText(getActivity(), "abracadabra", Toast.LENGTH_SHORT).show();

                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(getActivity(), "Firestore Load Error: " + errorMessage, Toast.LENGTH_LONG).show();
                }

            });
                    return view;
        }
    }



