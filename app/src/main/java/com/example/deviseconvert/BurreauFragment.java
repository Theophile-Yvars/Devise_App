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

/**
 * Ici, le fragment gere la recherche des bureaux de change dans la ville désiré.
 * Un page google map s'ouvre lorsque la ville est marqué, et que l'utilisatueur clique sur "rechercher"
 */
public class BurreauFragment extends Fragment {

    /**
     * 2 variable globale pour récuperer l'information de la ville
     */
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

        /**
         * Création de variable pour interagir avec l'utilisateur
         */
        input = (EditText) view.findViewById(R.id.inputVille1);
        Button buttonBurreau = view.findViewById(R.id.buttonBurreau);
        ImageButton buttonMenu = view.findViewById(R.id.buttonMenu);
        ImageButton buttonInformation = view.findViewById(R.id.buttonInformation);

        /**
         * bouton "rechercher" lance l'algorithme de recherche.
         */
        buttonBurreau.setOnClickListener(this::onClick);

        return view;
    }

    /**
     * Lorsque j'ai cliqué sur "rechercher"
     * @param v
     */
    private void onClick(View v) {
        ville = new String();
        ville = null;

        /**
         * Essaie d'afficher les bureaux de change dans google maps
         */
        try {
            /**
             * Recupere le nom de la ville saisie
             */
            ville = input.getText().toString();
            Log.i("Nom de ville", ville);
            if(ville.length() == 0){ // vérifie si il y a bien un nom de ville saisie
                Snackbar mySnackbar = Snackbar.make(v, R.string.burreau_erreur, Snackbar.LENGTH_LONG);
                mySnackbar.show(); // sinon, apparition d'un Snackbar
            }else { // Si il y a bien un nom de ville saisie

                // Adresse cible
                String url="https://www.google.com/maps/search/bureau+de+change+" + ville; // recherche dans google maps -> bureau de change "nom de ville"
                Log.i("Requette : ", url); // information sur la requette https

                // Intent implicite. Va à l'adresse cible
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}