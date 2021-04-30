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
 * Fragment pour obtenir une devise d'un pay.
 */
public class DeviceFragment extends Fragment {
    String apiDevises = new String();
    /**
     * Cle de connection à l'API
     */
    String key = "996cb33723dd35d455fb";

    /**
     * Variables
     */
    Spinner spinnerPay;
    String[] arrayDevice;
    String[] arrayCountry;

    StringBuilder allDevise = new StringBuilder();
    StringBuilder allCountry = new StringBuilder();

    /**
     * JSON
     */
    JSONObject file;
    String memoData = null;

    /**
     * Indice de selection de l'utilisateur dans le spinner
     */
    int countryIndice;

    /**
     * affichage de la devise
     */
    EditText output;

    /**
     * Variables pour sauvegarder avec sharedPreferences
     */
    Context mContext;
    public static final String SHARED_PREFS = "sharedPrefs";

    public DeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device, container, false);
        Button boutonRecherche = view.findViewById(R.id.buttonRecherche);
        output = view.findViewById(R.id.outputDevise);

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
                /**
                 * Une fois les 2 secondes passé, je charge mes listViews pour que l'utilisateur vois les devises
                 */
                Log.i("Devise", String.valueOf(allDevise));
                adapterFunc(view);
            }
        }, 2000);   //2 seconds

        boutonRecherche.setOnClickListener(v -> {
            affichage();
        });

        return view;
    }

    /**
     * Fonction d'affichage de la devise sur l'interface graphique
     */
    private void affichage() {
        output.setText(arrayDevice[countryIndice]);
        memoData = arrayDevice[countryIndice]; // memorise la valeur pour la sauvegarder en interne à la fin de l'utilisation
    }

    /**
     * Fonction d'initialisation du spinner
     * @param v
     */
    public void adapterFunc(View v) {
        String d = String.valueOf(allDevise);
        String c = String.valueOf(allCountry);
        arrayDevice = d.split(" ");
        arrayCountry = c.split("#");

        Log.i("Array Device", String.valueOf(arrayDevice.length));
        Log.i("Array Country", String.valueOf(arrayCountry.length));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayCountry);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPay = (Spinner) v.findViewById(R.id.spinnerCountry);

        this.spinnerPay.setAdapter(adapter);

        /**
         * function d'écoute du spinner
         */
        startSpinner();
        /**
         * Si une donnée est deja sauvegarder en mémoire interne, je la charge et je l'affiche
         */
        loadData();
    }

    /**
     * Fonction d'écoute des spinners
     */
    public void startSpinner() {
        // When user select a List-Item.
        this.spinnerPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //onItemSelected(parent, view, position, id);
                countryIndice = position;
                Log.i("Source", String.valueOf(countryIndice));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Recupere les devises
     * @return
     */
    private String fetchDevise() {
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
                allCountry.append(devise.getString("currencyName") + "#");
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
        String val = null;
        Log.i("DATA","load");
        try {
            SharedPreferences settings = mContext.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            val = settings.getString("dev",val);
            Log.i("DATA",val);
            output.setText(val);
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

        if(memoData != null){
            editor.putString("dev", memoData);
            Log.i("SAVE",memoData);
        }
        // Commit the edits!
        editor.commit();
    }
}