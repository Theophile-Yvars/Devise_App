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

/**
 *  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄
 * ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌
 *  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀
 */


/**
 *  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄
 * ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌
 * ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀
 * ▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌
 * ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░▌
 * ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌
 * ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀      ▐░▌
 * ▐░▌       ▐░▌▐░▌               ▐░▌
 * ▐░▌       ▐░▌▐░▌           ▄▄▄▄█░█▄▄▄▄
 * ▐░▌       ▐░▌▐░▌          ▐░░░░░░░░░░░▌
 */

/**
 * API : https://www.currencyconverterapi.com/docs
 */

/**
 *  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄
 * ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌
 *  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀
 */

/**
 * Ce fragment gére la conversion d'un montant d'une devise, à une autre.
 */
public class ConvertionFragment extends Fragment implements Serializable {
    /**
     * Ma CLE pour me servir de l'API.
     * Génération de cle gratuite
     */
    String key = "996cb33723dd35d455fb";

    /**
     * Variables
     */
    String apiDevises = new String(); // futur url
    StringBuilder allDevise = new StringBuilder(); // recupere les valeurs

    JSONObject file; // Le fichier json de la reponse de la requete à l'API

    /**
     * Spinner pour faire voir les differents choix
     */
    Spinner spinnerSource;
    Spinner spinnerDestination;

    /**
     * Recupere l'inforation dans le choix du spinner et regarde la valeur associé dans un tableau
     * Recupere le coeff du la conversion aussi
     */
    int sourceIndice;
    int destinationIndice;
    float coeffFloat;

    /**
     * EditText
     */
    EditText input;
    EditText output;

    /**
     * Stock le resultat de la conversion
     */
    float resultat;

    /**
     * Base de bonnée : FireBase
     */
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Variables pour sauvegarder avec sharedPreferences
     */
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

        /**
         * Initialisation
         */

        Button buttonConvert = view.findViewById(R.id.buttonTaux);

        input = view.findViewById(R.id.inputMontant);
        output = view.findViewById(R.id.outputTaux);

        /**
         * Si donnée stocké en intern, j'affiche
         */
        loadData();

        /**
         * Je recupere les devises disponible sur l'API, pour que l'utilisateur puisse choisir ces devises de conversion
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


        /**
         * Je laisse 2 secondes pour que la recherche asynchrone des devises se finisse.
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                /**
                 * Une fois les 2 secondes passé, je charge mes listViews pour que l'utilisateur vois les devises
                 */
                Log.i("Devise", String.valueOf(allDevise));
                adapterFunc(view);
            }
        }, 2000);   //2 seconds

        /**
         * J'écoute le bouton conversion. Lorsqu'il est solicité, je converti.
         * La conversion est une requette sur l'API pour recuperer le coefficiant de conversion entre les 2 devises.
         */
        buttonConvert.setOnClickListener(v -> {
            AsyncTask<Void,Void,String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    return fetchAffichage(); // Recherche est affichage du coeff.
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

    /**
     * Fonction d'écoute des spinners.
     */
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

    /**
     * Fonction de requette sur l'API pour avoir le coeff entre deux devises.
     * @return
     */
    private String fetchAffichage() {
        String d = String.valueOf(allDevise);
        String[] array = d.split(" ");

        String deviseSource = array[sourceIndice];
        String deviseDestination = array[destinationIndice];
        String coeff;

        /**
         * url de requette pour avoir le coeff
         */
        apiDevises = "https://free.currconv.com/api/v7/convert?apiKey=" + key +"&q="+deviseSource+"_"+deviseDestination+"&compact=y";
        //https://free.currconv.com/api/v7/convert?apiKey=do-not-use-this-key&q=USD_PHP&compact=y
        Log.i("Resquet",apiDevises);
        /**
         * J'essaie de me connecter
         */
        try{
            URL url = new URL(apiDevises); // url
            InputStream inputStream = url.openConnection().getInputStream(); // ouverture d'un flux
            StringBuilder responseContent = new StringBuilder(); // recuperer plusieur objet de type String
            /**
             * J'essaie de lire les informations recu
             */
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                line = reader.readLine(); // Je recupere un bloc String correspondant au JSON de l'API
                file = new JSONObject(line); // Je le convertis en JSON pour pouvoir le manipuler
            }
            Log.i("Conversion", "Messages = \n" + file);

            /**
             * Je formate dans un type Json. Ca sera plus pratique à manipuler
             */
            JSONObject test = file.getJSONObject(deviseSource+"_"+deviseDestination); // Je recupere le Json dans la cle results. Ce qui correspond à toutes les valeurs.
            coeff = test.getString("val"); // je cherche la valeur dont l'ID est val

            Log.i("val",coeff);
            coeffFloat = Float.parseFloat(coeff); // J'ai mon coeff !
            Log.i("Float", String.valueOf(coeffFloat));

            /**
             * calcul grace au coeff obtenu
             */
            convert(deviseDestination,deviseSource,coeffFloat);

        } catch (Exception e) {
            Log.e("DEMO", e.toString());
            e.printStackTrace();
        }


        return null;
    }

    /**
     * initialisation de l'adapter pour les spinners
     * @param v
     */
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

        /**
         * Fonction pour être à l'écoute des spinners.
         */
        startSpinner();
    }

    /**
     * Fonction de conversion en tre deux devises, et avec le coefficiant
     * @param affichageDestination
     * @param afiichageSource
     * @param coeffFloat
     */
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

            /**
             * Calcul
             */
            resultat = this.coeffFloat * inputValue;

            /**
             * Affichage
             */
            output.setText(String.valueOf(resultat) + " " + affichageDestination);

            /**
             * J'enregistre ma convertion dans FireBase
             */
            bdd(affichageDestination,afiichageSource,resultat,inputValue);

        } catch(Exception e){
            output.setText("Invalid input");
        }
    }

    /**
     * Sauvegarde des données de conversion dans FireBase
     * @param affichageDestination
     * @param afiichageSource
     * @param resultat
     * @param inputValue
     */
    private void bdd(String affichageDestination, String afiichageSource, float resultat,float inputValue) {
        // Create a new user with a first and last name

        /**
         * Je recupere la date pour la sauvegarder aussi
         */
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        String resultDate = formatter.format(now);

        /**
         * Format de sauvegarde des données de conversion
         */
        String stockage = String.valueOf(inputValue) + " " + afiichageSource + " -> " + affichageDestination + " : " + String.valueOf(resultat);

        /**
         * HashMap, format dans lequel sera sauvegarder les données dans FireBase
         */
        HashMap<String, Object> user = new HashMap<>();
        //user.put("info", stockage + " : " + resultDate);
        user.put("info", stockage);
        user.put("date", resultDate);

        String TAG = "BDD";
        Log.i(TAG,"Enregistremenent");

        /**
         * Connection a FireBase, et sauvegarde
         */
        // Add a new document with a generated ID
        db.collection("users")
                .add(user)// ajout les data de users
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
     * Fonction pour obtenir toutes les devises de l'API disponibles
     * @return
     */
    String fetchDevise() {
        try {
            /**
             * Format de la requette https à l'API, pour obtenir la liste des devises.
             */
            apiDevises = "https://free.currconv.com/api/v7/currencies?apiKey="+key;
            URL url = new URL(apiDevises);

            /**
             * Ouvre un flux de donné avec l'API
             */
            InputStream inputStream = url.openConnection().getInputStream();
            StringBuilder responseContent = new StringBuilder();
            /**
             * J'essaie de lire
             */
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

    /**
     * Fonction qui cherche dans le stockage interne si l'utilisation a deja fait une conversion.
     * Si oui, il affiche le resultat.
     */
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

    /**
     * Lorsque le fragment est fini, il enregistre le montant de la conversion, si une conversion à bien était faite.
     */
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