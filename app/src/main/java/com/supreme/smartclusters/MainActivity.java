package com.supreme.smartclusters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.supreme.smartclusters.Fragments.Home;
import com.supreme.smartclusters.Fragments.Universities;
import com.supreme.smartclusters.Fragments.profile;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle mActionBarDrawerToggle;
    private AppBarConfiguration mAppBarConfiguration;
    private Uri mainImageURI = null;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(R.color.colorPrimary);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.username);
        final TextView email = (TextView) headerView.findViewById(R.id.email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String profemail = user.getEmail();
        email.setText(profemail);


        db.collection("marksdata").document(userId).get().addOnCompleteListener(task -> {

                if (task.getResult().exists()) {
                    Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
                    String eng = task.getResult().getString("eng");
                    String kisw = task.getResult().getString("kisw");
                    String math = task.getResult().getString("math");
                    String sciA = task.getResult().getString("sciA");
                    String sciB = task.getResult().getString("sciB");
                    String hum = task.getResult().getString("hum");
                    String applied = task.getResult().getString("applied");
                    String grade = task.getResult().getString("grade");

                        int english=Integer.parseInt(eng);
                        int kiswa=Integer.parseInt(kisw);
                        int maths=Integer.parseInt(math);
                        int scia=Integer.parseInt(sciA);
                        int scib=Integer.parseInt(sciB);
                        int human=Integer.parseInt(hum);
                        int app=Integer.parseInt(applied);
                        int t=english+kiswa+maths+scia+scib+human+app;
                        int T=84;
                        int a=maths;
                        int b=Math.max(kiswa,english);
                        int c=Math.max(scia,scib);
                        int d=Math.max(human,app);
                        int r=a+b+c+d;
                        int R=48;
                        double f=Math.sqrt((r/R)*(t/T));
                        f= (int) (f*48);

                        Map<String, Object> post = new HashMap<>();


                        post.put("cluster", f);
                        post.put("total", t);
                        FirebaseFirestore.getInstance().collection("marksdata").document()
                                .set(post)
                                .addOnSuccessListener(aVoid -> {
                                    // pdialog.dismiss();
                                    Toast.makeText(MainActivity.this, "upload success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    finish();

                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                                    finish();


                                });


                      }


        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mActionBarDrawerToggle.syncState();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mActionBarDrawerToggle.syncState();

            }
        };

        mActionBarDrawerToggle.syncState();
        drawer.addDrawerListener(mActionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Home home = new Home();
                        goToFragment(home);
                        drawer.closeDrawers();

                        break;
                    case R.id.nav_unis:
                        Universities uni = new Universities();
                        goToFragment(uni);
                        drawer.closeDrawers();

                        break;

                    case R.id.nav_profile:
                        profile profile = new profile();
                        goToFragment(profile);
                        drawer.closeDrawers();
                        break;



                }

                return false;
            }
        });


    }
    private void goToFragment(Fragment selectedFragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, selectedFragment)
                .commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

           

            case R.id.action_logout:



                break;



            case R.id.action_profile:

                  startActivity(new Intent(getApplicationContext(),  profile.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
