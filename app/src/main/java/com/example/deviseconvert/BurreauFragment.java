package com.example.deviseconvert;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

public class BurreauFragment extends Fragment {
    public String ville;
    EditText input; // ville

    public BurreauFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_burreau, container, false);

        input = (EditText) view.findViewById(R.id.inputVille1);

        Button buttonBurreau = view.findViewById(R.id.buttonBurreau);
        ImageButton buttonMenu = view.findViewById(R.id.buttonMenu);
        ImageButton buttonInformation = view.findViewById(R.id.buttonInformation);

        buttonBurreau.setOnClickListener(this::onClick);

        buttonMenu.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_burreauFragment_to_menuFragment);
        });

        buttonInformation.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_burreauFragment_to_informationFragment);
        });

        return view;
    }

    private void onClick(View v) {
        ville = new String();
        ville = null;

        // Recuperation du nom de la ville
        try {
            ville = input.getText().toString();
            Log.i("Nom de ville", ville);
            if(ville.length() == 0){
                Snackbar mySnackbar = Snackbar.make(v, R.string.burreau_erreur, Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }else {

                // Adresse cible
                String url="https://www.google.com/maps/search/bureau+de+change+" + ville;
                Log.i("Requette : ", url);

                // Intent implicite. Va à l'adresse cible
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        }catch (Exception e){
            /*Snackbar mySnackbar = Snackbar.make(v, R.string.burreau_erreur, Snackbar.LENGTH_LONG);
            mySnackbar.show();*/
        }
    }
}