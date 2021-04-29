package com.example.deviseconvert;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;


/*
API : https://www.currencyconverterapi.com/docs
 */

public class ConvertionFragment extends Fragment implements Serializable {
    String apiDevises = new String();
    String key = "996cb33723dd35d455fb";

    StringBuilder allDevise = new StringBuilder();

    String json;
    JSONObject file;

    Spinner spinnerSource;
    Spinner spinnerDestination;

    int sourceIndice;
    int destinationIndice;
    float coeffFloat;

    EditText input;
    EditText output;

    float resultat;

    String convertSerialise = "ficConvert";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String SHARED_PREFS = "sharedPrefs";

    private Context mContext;

    public ConvertionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_convertion, container, false);

        Button buttonConvert = view.findViewById(R.id.buttonTaux);

        input = view.findViewById(R.id.inputMontant);
        output = view.findViewById(R.id.outputTaux);

        loadData();

        /*
        if(Serializer.deserialize(convertSerialise,getContext()) != null){
            resultat = (float)Serializer.deserialize(convertSerialise,getContext());
            output.setText(String.valueOf(resultat));
        }
        */

         /*
        déclaration du tableau d'item pour dans les spinner
         */

        AsyncTask<Void,Void,String> taskDevise = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return fetchDevise();
            }
            @Override
            protected void onPostExecute(String responseContent) {
                if (responseContent == null) {
                    Log.e("DEMO", "Failed to fetched all messages");
                } else {
                    Log.i("DEMO", "All messages = \n" + responseContent);
                }
            }
        };
        taskDevise.execute();



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.i("Devise", String.valueOf(allDevise));
                adapterFunc(view);
            }
        }, 2000);   //2 seconds

        buttonConvert.setOnClickListener(v -> {
            AsyncTask<Void,Void,String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    return fetchAffichage();
                }
                @Override
                protected void onPostExecute(String responseContent) {
                    if (responseContent == null) {
                        Log.e("DEMO", "Failed to fetched all messages");
                    } else {
                        Log.i("DEMO", "All messages = \n" + responseContent);
                    }
                }
            };
            task.execute();
        });

        return view;
    }

    private void startSpinner() {

        // When user select a List-Item.
        this.spinnerSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //onItemSelected(parent, view, position, id);
                sourceIndice = position;
                Log.i("Source", String.valueOf(sourceIndice));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // When user select a List-Item.
        this.spinnerDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //onItemSelected(parent, view, position, id);
                destinationIndice = position;
                Log.i("Destination", String.valueOf(destinationIndice));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String fetchAffichage() {
        String d = String.valueOf(allDevise);
        String[] array = d.split(" ");

        String deviseSource = array[sourceIndice];
        String deviseDestination = array[destinationIndice];
        String coeff;

        apiDevises = "https://free.currconv.com/api/v7/convert?apiKey=" + key +"&q="+deviseSource+"_"+deviseDestination+"&compact=y";
        //https://free.currconv.com/api/v7/convert?apiKey=do-not-use-this-key&q=USD_PHP&compact=y
        Log.i("Resquet",apiDevises);
        try{
            URL url = new URL(apiDevises);
            InputStream inputStream = url.openConnection().getInputStream();
            StringBuilder responseContent = new StringBuilder();
            //StringBuilder test = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                line = reader.readLine(); // Je recupere un bloc String correspondant au JSON de l'API
                file = new JSONObject(line); // Je le convertis en JSON pour pouvoir le manipuler
            }
            Log.i("Conversion", "Messages = \n" + file);

            JSONObject test = file.getJSONObject(deviseSource+"_"+deviseDestination); // Je recupere le Json dans la cle results. Ce qui correspond à toutes les valeurs.
            coeff = test.getString("val");

            Log.i("val",coeff);
            coeffFloat = Float.parseFloat(coeff);
            Log.i("Float", String.valueOf(coeffFloat));

            convert(deviseDestination,deviseSource,coeffFloat);

        } catch (Exception e) {
            Log.e("DEMO", e.toString());
            e.printStackTrace();
        }


        return null;
    }

    void adapterFunc(View v){
        String d = String.valueOf(allDevise);
        String[] array = d.split(" ");

        Log.i("Array", String.valueOf(array.length));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, array);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSource = (Spinner) v.findViewById(R.id.spinnerSource);
        spinnerDestination = (Spinner) v.findViewById(R.id.spinnerDestination);

        this.spinnerSource.setAdapter(adapter);
        this.spinnerDestination.setAdapter(adapter);

        startSpinner();
    }

    void convert(String affichageDestination, String afiichageSource, float coeffFloat){
        try{
             /*
            Recuperation de la valeur saisie, en String
             */
            final String inputStr = input.getText().toString();
            /*
            Convertion de ce string en float
             */
            final float inputValue = Float.parseFloat(inputStr);

            resultat = this.coeffFloat * inputValue;

            output.setText(String.valueOf(resultat) + " " + affichageDestination);

            bdd(affichageDestination,afiichageSource,resultat,inputValue);

        } catch(Exception e){
            output.setText("Invalid input");
        }
    }

    private void bdd(String affichageDestination, String afiichageSource, float resultat,float inputValue) {
        // Create a new user with a first and last name

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        String resultDate = formatter.format(now);

        String stockage = String.valueOf(inputValue) + " " + afiichageSource + " -> " + affichageDestination + " : " + String.valueOf(resultat);

        HashMap<String, Object> user = new HashMap<>();
        //user.put("info", stockage + " : " + resultDate);
        user.put("info", stockage);
        user.put("date", resultDate);


        String TAG = "BDD";

        Log.i(TAG,"Enregistremenent");

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /**
     *
     * @return
     */
    String fetchDevise() {
        try {
            //target = "EUR";
            //format = "THB";
            //quantity = "12";

            apiDevises = "https://free.currconv.com/api/v7/currencies?apiKey="+key;

            URL url = new URL(apiDevises);
            InputStream inputStream = url.openConnection().getInputStream();
            StringBuilder responseContent = new StringBuilder();
            //StringBuilder test = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                line = reader.readLine(); // Je recupere un bloc String correspondant au JSON de l'API
                file = new JSONObject(line); // Je le convertis en JSON pour pouvoir le manipuler
            }
            Log.i("DEMO", "All messages = \n" + file);

            JSONObject test = file.getJSONObject("results"); // Je recupere le Json dans la cle results. Ce qui correspond à toutes les valeurs.
            Iterator<String> it = test.keys(); // Pour l'indice de lecture

            while(it.hasNext()){
            //for(int i = 0; i < array.length(); i++){
                String KEY = it.next(); // Je recupere le nom de la clef
                JSONObject devise = test.getJSONObject(KEY); // Je recupere le json du la cle. La valeur de la cle correspond à toutes les infos d'une devise
                //Log.i("Devise",devise.getString("id")); // Je recupere le valeur dont la cle est "id"
                //Log.i("Conversion",test.getString(KEY));
                allDevise.append(devise.getString("id") + " ");
            }

        } catch (Exception e) {
            Log.e("DEMO", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    public void loadData(){
        mContext = getContext();
        try {
            SharedPreferences settings = mContext.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            resultat = settings.getFloat("Res",resultat);
            output.setText(String.valueOf(resultat));
        }catch (Exception e){
            Log.i("loadData","Rien en mémoire");
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mContext = getContext();
        SharedPreferences settings = mContext.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("Res", resultat);

        // Commit the edits!
        editor.commit();
    }
}