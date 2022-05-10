package com.supreme.smartclusters;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
    TextView coursename,clusters,Grade,total,recomedation,progcode,unicode,cut20,cut21,mycluster;
    ImageView confirm3;
    Button apply;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        coursename=findViewById(R.id.coursename);
        confirm3=findViewById(R.id.confirm);
        clusters=findViewById(R.id.clusters);
        recomedation=findViewById(R.id.recomedation);
        progcode=findViewById(R.id.progcode);
        unicode=findViewById(R.id.unicode);
        cut20=findViewById(R.id.cutoff_2020);
        cut21=findViewById(R.id.cutoff_2021);
        mycluster=findViewById(R.id.myclusters);
        Grade=findViewById(R.id.Grade);
        total=findViewById(R.id.total);
        apply=findViewById(R.id.apply);

        db.collection("marksdata").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Intent bund =new Intent();
                        bund = getIntent();
                        String coursenames=bund.getStringExtra("course");
                        String cutoffones=bund.getStringExtra("cutoff_20");
                        String cutofftwos=bund.getStringExtra("cutoff_21");
                        String unicodes=bund.getStringExtra("unicode");
                        String progcodes=bund.getStringExtra("progcode");
                        String cluster = task.getResult().getString("cluster");
                        String totals = task.getResult().getString("total");
                        String grades = task.getResult().getString("grade");
                        int myclus=Integer.parseInt(cluster);
                        int courseclus=Integer.parseInt(cutofftwos);
                        coursename.setText(coursenames);
                        cut20.setText(cutoffones);
                        cut21.setText(cutofftwos);
                        unicode.setText(unicodes);
                        progcode.setText(progcodes);
                       mycluster.setText(cluster);
                       total.setText(totals);
                       Grade.setText(grades);
                        if(myclus>courseclus){
                            recomedation.setText("Recomedable.Go ahead");
                            confirm3.setImageResource(R.drawable.correct_won);
                        }
                        else{
                            recomedation.setText("Not Recomedable.");
                            confirm3.setImageResource(R.drawable.false_lost);
                        }
                         }
                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(Details.this, "Firestore Load Error: " + errorMessage, Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}