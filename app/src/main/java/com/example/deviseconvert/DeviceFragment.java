package com.example.deviseconvert;

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

public class DeviceFragment extends Fragment {
    String apiDevises = new String();
    String key = "996cb33723dd35d455fb";

    Spinner spinnerPay;
    String[] arrayDevice;
    String[] arrayCountry;

    StringBuilder allDevise = new StringBuilder();
    StringBuilder allCountry = new StringBuilder();

    JSONObject file;

    int countryIndice;

    EditText output;

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

        boutonRecherche.setOnClickListener(v -> {
            affichage();
        });

        return view;
    }

    private void affichage() {
        output.setText(arrayDevice[countryIndice]);
    }

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

        startSpinner();
    }

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

    private String fetchDevise() {
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
                allCountry.append(devise.getString("currencyName") + "#");
            }

        } catch (Exception e) {
            Log.e("DEMO", e.toString());
            e.printStackTrace();
        }
        return null;
    }
}