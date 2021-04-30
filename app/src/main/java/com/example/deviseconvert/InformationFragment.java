package com.example.deviseconvert;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Fragment qui informe l'utilsateur sur les different usage de l'apli
 * Possibilité de recuperer les données de conversion de FireBase, pour les visualisé
 *
 * Voir : " ListOperationFragment " pour voir comment l'appli recupere les datas de firebase
 */
public class InformationFragment extends Fragment {
    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_information, container, false);

        /**
         * button menu accessible depuis ce fragment
         */
        ImageButton buttonMenu = view.findViewById(R.id.buttonMenu);
        Button buttonListe = view.findViewById(R.id.buttonInformationOperation);

        buttonMenu.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_informationFragment_to_menuFragment);
        });

        /**
         * Bouton pour recuperer les datas de conversion du FireBase
         */
        buttonListe.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_informationFragment_to_listOperationFragment);
        });

        return view;
    }
}