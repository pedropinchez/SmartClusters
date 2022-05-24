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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.supreme.smartclusters.MyListAdapter;
import com.supreme.smartclusters.MyListData;
import com.supreme.smartclusters.R;


import java.util.ArrayList;


public class Applied extends Fragment {
    RecyclerView recyclerView;
    ArrayList<MyAppliedData> appliedData;
    ArrayList<String> documentIds;
    FirebaseFirestore firebaseFirestore;
    Query query;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_applied, container, false);
        recyclerView = view.findViewById(R.id.recycerview);

        if (container != null) {
            container.removeAllViews();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        appliedData = new ArrayList<>();
        documentIds = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        query = firebaseFirestore.collection("favourites").orderBy("unicode", Query.Direction.DESCENDING);
        firebaseFirestore.collection("favourites");
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                    firebaseFirestore.collection("favourites")
                            .document(documentIds.get(viewHolder.getAdapterPosition()))
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    Toast.makeText(getActivity(), "deleted" + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }).attachToRecyclerView(recyclerView);
        } else {


        }
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
                        appliedData = new ArrayList<>();

                        assert queryDocumentSnapshots != null;
                        if (!queryDocumentSnapshots.isEmpty()) {

                            String id = "";

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                id = documentSnapshot.getId();
                                MyAppliedData myAppliedData = documentSnapshot.toObject(MyAppliedData.class);
                                appliedData.add(myAppliedData);
                                documentIds.add(id);

                            }

                            if (IsConnected()) {


                                recyclerView.setAdapter(null);
                                MyAppliedAdapter adapter = new MyAppliedAdapter(appliedData, getActivity());
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

















