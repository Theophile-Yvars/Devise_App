package com.example.deviseconvert;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.util.Map;

public class ListOperationFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String TAG = "BDD";
    StringBuilder recupData = new StringBuilder();
    StringBuilder recupInfo = new StringBuilder();
    String[] arrayDate;
    String[] arrayInfo;

    public ArrayAdapter<String> adapter;

    public ListOperationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_operation, container, false);

        lectureBDD();
  /*      adapt();

        adapter.addAll("Abricot", "Brugnon", "Cerise", "Datte", "Figue", "Fraise", "Groseille", "Kiwi", "Mandarine", "Melon", "Myrtille", "Noix", "Olive", "Orange", "Pamplemousse", "Pêche", "Poire", "Pomme", "Prune", "Raisin");

        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
*/
        return view;
    }
/*
    void adapt(){
        adapter = new ArrayAdapter<String>(this, R.layout.list_operation, R.id.textOperation) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                final String devicedata = getItem(position);

                TextView data = view.findViewById(R.id.textOperation);

                data.setText(devicedata);


                return view;
            }
        };
    }
*/
    void lectureBDD(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.i("DBB","Recuperation data");
                        Map<String, Object> data;
                        String inf;
                        String date;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data = document.getData();
                                Log.d(TAG, document.getId() + " => " + data);

                                inf = (String) data.get("info");
                                date = (String) data.get("date");
                                Log.i("Info",inf);
                                Log.i("Date",date);

                                recupInfo.append(inf + "#");
                                recupData.append(date + "#");
                            }

                            String infoAll = String.valueOf(recupInfo);
                            String dateAll = String.valueOf(recupData);

                            arrayInfo = infoAll.split("#");
                            arrayDate = dateAll.split("#");
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}