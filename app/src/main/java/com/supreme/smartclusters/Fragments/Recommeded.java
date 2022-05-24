package com.supreme.smartclusters.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.supreme.smartclusters.MyAppliedAdapter;
import com.supreme.smartclusters.MyAppliedData;
import com.supreme.smartclusters.MyRecAdapter;
import com.supreme.smartclusters.MyRecData;
import com.supreme.smartclusters.R;

import java.util.ArrayList;


public class Recommeded extends Fragment {
    RecyclerView recyclerView;
    ArrayList<MyRecData> recData;
    ArrayList<String> documentIds;
    FirebaseFirestore firebaseFirestore;
    Query query;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_recommeded, container, false);
        recyclerView = view.findViewById(R.id.recycerview);

        if (container != null) {
            container.removeAllViews();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        recData= new ArrayList<>();
        documentIds = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        query = firebaseFirestore.collection("favourites").orderBy("mycluster", Query.Direction.DESCENDING).limit(3);
        firebaseFirestore.collection("favourites");

        return view;


    }


    public void loadData() {

        if (isAdded() && getActivity() != null) {

            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Toast.makeText(getActivity(), "Error occured : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    else {
                        recData = new ArrayList<>();

                        assert queryDocumentSnapshots != null;
                        if (!queryDocumentSnapshots.isEmpty()) {

                            String id = "";

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                id = documentSnapshot.getId();
                                MyRecData myRecData = documentSnapshot.toObject(MyRecData.class);
                                recData.add(myRecData);
                                documentIds.add(id);

                            }

                            if (IsConnected()) {


                                recyclerView.setAdapter(null);
                                MyRecAdapter adapter = new MyRecAdapter(recData, getActivity());
                                // recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);

                            } else {
//                                recyclerView.setAdapter(null);
//                                MyListAdapter adapter = new MyListAdapter(listdata, getActivity());
//                                recyclerView.setHasFixedSize(true);
//                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                                recyclerView.setAdapter(adapter);
                                Toast.makeText(getActivity(), "Network unavailable", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();

    }

    private boolean IsConnected() {

        try {


            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        } catch (Exception ignored) {
            return true;
        }
    }
}

















