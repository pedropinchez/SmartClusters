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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.supreme.smartclusters.MyListData;
import com.supreme.smartclusters.MyUniAdapter;
import com.supreme.smartclusters.MyUniData;
import com.supreme.smartclusters.R;

import java.util.ArrayList;


public class Universities extends Fragment {
    RecyclerView recyclerView;
    ArrayList<MyUniData> unidata;
    ArrayList<String> documentIds;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    Query query;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_university, container, false);
        recyclerView = view.findViewById(R.id.recycerview);

        if (container != null) {
            container.removeAllViews();
        }
        unidata = new ArrayList<>();
        documentIds = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        query = firebaseFirestore.collection("institutions").orderBy("uniCode", Query.Direction.DESCENDING);
        firebaseFirestore.collection("institutions");

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
                        unidata = new ArrayList<>();

                        assert queryDocumentSnapshots != null;
                        if (!queryDocumentSnapshots.isEmpty()) {

                            String id = "";

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                id = documentSnapshot.getId();
                                MyUniData myuniData = documentSnapshot.toObject(MyUniData.class);
                                unidata.add(myuniData);
                                documentIds.add(id);

                            }

                            if (IsConnected()) {


                                recyclerView.setAdapter(null);
                                MyUniAdapter adapters = new MyUniAdapter(unidata, getActivity());
                               // recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapters);

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

















