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

/**
 * Fragment pour lister les differents operations de conversion deja effectué
 */
public class ListOperationFragment extends Fragment {
    /**
     * Variable FireBase
     */
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Init
     */
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

        /**
         * Je recupere les datas
         */
        lectureBDD(view);

        return view;
    }

    /**
     * adapter de la listView
     * @param view
     */
    void adapt(View view){
        adapter = new ArrayAdapter<String>(getContext(), R.layout.list_operation, R.id.textOperation) {
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

        /**
         * Je push les datas recupere dans l'adapteur
         */
        for(int i = 0; i < arrayDate.length; i++){
            adapter.add(arrayInfo[i] + "\n"+arrayDate[i] );
        }

        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    /**
     * Connection à FireBase pour recuperer les données
     * @param view
     */
    void lectureBDD(View view){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.i("DBB","Recuperation data");
                        Map<String, Object> data; // Format de recuperation
                        String inf;
                        String date;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data = document.getData(); // Je recupere les données
                                Log.d(TAG, document.getId() + " => " + data);

                                inf = (String) data.get("info"); // Je recupere avec la cle info
                                date = (String) data.get("date"); // Je recupere avecla cle date
                                Log.i("Info",inf);
                                Log.i("Date",date);

                                /**
                                 * J'ajoute " # " pour pouvoir séparer les données plus tard dans un tableau
                                 */
                                recupInfo.append(inf + "#");
                                recupData.append(date + "#");
                            }

                            /**
                             * Je converti les données pour mieux les manipuler
                             */
                            String infoAll = String.valueOf(recupInfo);
                            String dateAll = String.valueOf(recupData);

                            /**
                             * Je transforme les données pour les mettre dans un tableau grace au séparateur " # "
                             */
                            arrayInfo = infoAll.split("#");
                            arrayDate = dateAll.split("#");

                            Log.i("BDD", "Fin BDD");
                            adapt(view);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }
}