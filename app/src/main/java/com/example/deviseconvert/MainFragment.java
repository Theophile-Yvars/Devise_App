package com.example.deviseconvert;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Fragment de démarrage.
 * L'utilsateur peut selectionner menu ou info dans la tool barre du haut
 */
public class MainFragment extends Fragment {
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Log.i("MainFragments","Initialisation");

        /**
         * Image bouton menu et information dans la tool barre
         */
        ImageButton buttonMenu = view.findViewById(R.id.buttonMenu);
        ImageButton buttonInformation = view.findViewById(R.id.buttonInformation);

        buttonMenu.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_mainFragment_to_menuFragment);
        });

        buttonInformation.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_mainFragment_to_informationFragment);
        });

        Log.i("MainFragments","Fin Initialisation");

        return view;
    }
}