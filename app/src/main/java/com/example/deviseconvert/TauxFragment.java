package com.example.deviseconvert;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;

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
 * Fragment pour trouver le taux entre deux devises
 * Ce fragment est tres similaire au fragment convertion
 */
public class TauxFragment extends Fragment {
    String apiDevises = new String();
    /**
     * Cle de connection à l'API
     */
    String key = "996cb33723dd35d455fb";

    /**
     * Variables
     */

    StringBuilder allDevise = new StringBuilder();
    /**
     * JSON
     */
    String json;
    JSONObject file;

    /**
     * Spinner
     */
    Spinner spinnerSource;
    Spinner spinnerDestination;

    /**
     * Varaible de recuperation
     */
    int sourceIndice;
    int destinationIndice;
    float coeffFloat = -1;

    EditText output;

    Context mContext;

    public static final String SHARED_PREFS = "sharedPrefs";

    public TauxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_taux, container, false);

        Button buttonConvert = view.findViewById(R.id.buttonTaux);
        output = view.findViewById(R.id.outputTaux);

        /**
         * Recherche si l'utilisateur à deja utiliser ce fragment
         * Si oui, cette fonction cherche la donnée enregistré en mémoire interne dans l'application.
         */
        loadData();

        /**
         * Je recupere les devises sur l'API
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
                Log.i("Devise", String.valueOf(allDevise));
                adapterFunc(view);
            }
        }, 2000);   //2 seconds

        /**
         * J'écoute le bouton conversion. Lorsqu'il est solicité, je cherche le taux de conversion.
         * La conversion est une requette sur l'API pour recuperer le coefficiant de conversion entre les 2 devises.
         */
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

            // Affichage
            output.setText("Coeff : " + String.valueOf(coeffFloat));

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

        spinnerSource = (Spinner) v.findViewById(R.id.spinnerSourceTaux);
        spinnerDestination = (Spinner) v.findViewById(R.id.spinnerDestinationTaux);

        this.spinnerSource.setAdapter(adapter);
        this.spinnerDestination.setAdapter(adapter);

        /**
         * Fonction pour être à l'écoute des spinners.
         */
        startSpinner();
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
        Log.i("DATA","load");
        try {
            SharedPreferences settings = mContext.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            coeffFloat = settings.getFloat("ta",coeffFloat);
            Log.i("DATA", String.valueOf(coeffFloat));
            output.setText(String.valueOf(coeffFloat));
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

        if(coeffFloat != -1){
            editor.putFloat("ta", coeffFloat);
            Log.i("SAVE", String.valueOf(coeffFloat));
        }
        // Commit the edits!
        editor.commit();
    }
}