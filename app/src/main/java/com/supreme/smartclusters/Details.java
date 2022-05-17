package com.supreme.smartclusters;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
    TextView coursename,clusters,Grade,total,recomedation,progcode,unicode,cut20,cut21,mycluster;
    ImageView confirm3;
    Button apply;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;
    String clus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        coursename = findViewById(R.id.coursename);
        confirm3 = findViewById(R.id.confirm);
        clusters = findViewById(R.id.clusters);
        recomedation = findViewById(R.id.recomedation);
        progcode = findViewById(R.id.progcode);
        unicode = findViewById(R.id.unicode);
        cut20 = findViewById(R.id.cutoff_2020);
        cut21 = findViewById(R.id.cutoff_2021);
        mycluster = findViewById(R.id.myclusters);
        Grade = findViewById(R.id.grade);
        total = findViewById(R.id.total);
        apply = findViewById(R.id.apply);

        Intent go = new Intent();
        go = getIntent();
        String course=go.getStringExtra("course");
        String cutoffones = go.getStringExtra("cutoff_20");
        String cutofftwos = go.getStringExtra("cutoff_21");
        String unicodes = go.getStringExtra("unicode");
        String progcodes = go.getStringExtra("progcode");
        coursename.setText("Course Name : "+course);
        cut20.setText("Cut OFF 2021 : "+cutoffones);
        cut21.setText("Cut OFF 2022 : "+cutofftwos);
        unicode.setText("University Code : "+unicodes);
        progcode.setText("Program Code : "+progcodes);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("marksdata");
        usersRef.get().addOnCompleteListener((OnCompleteListener<QuerySnapshot>) task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //  int cluster= (int) document.get("cluster");
                    clus = document.get("cluster").toString();
                    String tota = document.get("total").toString();
                    String grad = document.getString("grade");
                    //Toast.makeText(this, cluster, Toast.LENGTH_SHORT).show();
                    mycluster.setText("Your Cluster : " + clus);
                    total.setText("Total points : " + tota);
                    Grade.setText("Your Overal Grade : " + grad);
                    int cluss = Integer.parseInt(clus);
                    int cutoff = Integer.parseInt(cutofftwos);
                 //   Toast.makeText(this, "Cutoff :" + cutoff + "clusters : " + cluss, Toast.LENGTH_LONG).show();

                    if (cluss < cutoff) {
                        recomedation.setText("Not Recomedable. Cluster Point way above");
                       recomedation.setVisibility(View.VISIBLE);
                        confirm3.setVisibility(View.VISIBLE);
                        confirm3.setImageResource(R.drawable.false_lost);


                    } else {

                       // recomedation.setText("Course is  Recomedable for you.");
                        apply.setVisibility(View.VISIBLE);
                        recomedation.setVisibility(View.VISIBLE);
                        confirm3.setVisibility(View.VISIBLE);
                        confirm3.setImageResource(R.drawable.correct_won);
                    }
                }}
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> post = new HashMap<>();


                post.put("course", course);
                post.put("cutoffone", cutoffones);
                post.put("cutofftwo", cutofftwos);
                post.put("unicode", unicodes);
                post.put("progcode", progcodes);
                post.put("mycluster", clus);
                FirebaseFirestore.getInstance().collection("courses").document()
                        .set(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // pdialog.dismiss();
                                Toast.makeText(Details.this, "You have succesfully registered for this course", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                                finish();


                            }
                        });
            }
        });
//        db.collection("marksdata").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    if (task.getResult().exists()) {
//
//                        String cluster = task.getResult().getString("cluster");
//                        String totals = task.getResult().getString("total");
//                        String grades = task.getResult().getString("grade");
//                        int myclus = Integer.parseInt(cluster);
//                        int courseclus = Integer.parseInt(cutofftwos);
//                        //  coursename.setText(coursenames);
//
//
//                    }
//                } else {
//                    String errorMessage = task.getException().getMessage();
//                    Toast.makeText(Details.this, "Firestore Load Error: " + errorMessage, Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });


    }}