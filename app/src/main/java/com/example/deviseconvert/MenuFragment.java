package com.example.deviseconvert;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuFragment extends Fragment {
    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);

        Button button1 = view.findViewById(R.id.button1Menu);
        Button button2 = view.findViewById(R.id.button2Menu);
        Button button3 = view.findViewById(R.id.button3Menu);
        Button button4 = view.findViewById(R.id.button4Menu);
        ImageButton buttonInformation = view.findViewById(R.id.buttonInformationMenu);

        button1.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_menuFragment_to_tauxFragment);
        });

        button2.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_menuFragment_to_convertionFragment);
        });

        button3.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_menuFragment_to_deviceFragment);
        });

        button4.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_menuFragment_to_burreauFragment);
        });

        buttonInformation.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_menuFragment_to_informationFragment);
        });

        return view;
    }
}