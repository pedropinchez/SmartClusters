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
import com.supreme.smartclusters.Utils.SharedPref;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
    TextView coursename,clusters,Grade,total,recomedation,progcode,unicode,cut20,cut21,mycluster;
    ImageView confirm3;
    Button apply;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;
    private double clus;
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

        final Intent[] go = {new Intent()};
        go[0] = getIntent();
        String course= go[0].getStringExtra("course");
        String cutoffones = go[0].getStringExtra("cutoff_20");
        String cutofftwos = go[0].getStringExtra("cutoff_21");
        String unicodes = go[0].getStringExtra("unicode");
        String progcodes = go[0].getStringExtra("progcode");
        coursename.setText("Course Name : "+course);
        cut20.setText("Cut OFF 2021 : "+cutoffones);
        cut21.setText("Cut OFF 2022 : "+cutofftwos);
        unicode.setText("University Code : "+unicodes);
        progcode.setText("Program Code : "+progcodes);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usersRef = db.collection("marksdata").document(userId);
        usersRef.get().addOnCompleteListener((OnCompleteListener<DocumentSnapshot>) task -> {
            if (task.isSuccessful()) {
                try{
                    //double clus;
                    //  int cluster= (int) document.get("cluster");
                    clus = (long) task.getResult().get("cluster");
                    String tota = task.getResult().get("total").toString();
                    String grad = task.getResult().getString("grade");
                    //Toast.makeText(this, cluster, Toast.LENGTH_SHORT).show();
                    mycluster.setText("Your Cluster : " + clus);
                    total.setText("Total points : " + tota);
                    Grade.setText("Your Overal Grade : " + grad);
                    SharedPref sharedPref=new SharedPref(this);
                    sharedPref.setclusters((int) clus);
                    //int in2 = new Integer(clus);
                    //int cluss = Integer.parseInt(clus);
                    int cutoff = Integer.parseInt(cutofftwos);
                   Toast.makeText(this, "Cutoff :" + cutoff + "clusters : " + clus, Toast.LENGTH_LONG).show();

                    if (clus < cutoff) {
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
                    }}
                    catch(NumberFormatException ex){

                          //  startActivity(new Intent(getApplicationContext(),MarksEntry.class));

                        Toast.makeText(Details.this, "Error "+ex, Toast.LENGTH_SHORT).show();
                    }
                }
        });
        SharedPref sharedPref=new SharedPref(this);
        int xxx=sharedPref.getclusters();
        String cl =String.valueOf(xxx);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> post = new HashMap<>();


                post.put("course", course);
                post.put("cutoffone", cutoffones);
                post.put("cutofftwo", cutofftwos);
                post.put("unicode", unicodes);
                post.put("progcode", progcodes);
                post.put("mycluster", cl);


                db.collection("favourites").document(userId).set(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Details.this, "Course added to favourites", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(Details.this, "Firestore Error: " + errorMessage, Toast.LENGTH_LONG).show();
                        }

                    }
                });
//                FirebaseFirestore.getInstance().collection("courses").document()
//                        .set(post)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                // pdialog.dismiss();
//                                Toast.makeText(Details.this, "Course added to favourites", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
//                                finish();
//
//
//                            }
//                        });
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