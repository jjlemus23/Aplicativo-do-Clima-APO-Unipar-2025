package com.example.apoclima;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SobreFragment extends DialogFragment {

    public SobreFragment() {
        // Construtor público vazio é necessário
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate o layout para este fragment
        View view = inflater.inflate(R.layout.fragment_sobre, container, false);

        // Obtenha referências para os TextViews do layout
        TextView nameTextView = view.findViewById(R.id.text_name);
        TextView raTextView = view.findViewById(R.id.text_ra);
        TextView courseTextView = view.findViewById(R.id.text_course);

        // Defina o texto para cada TextView com suas informações
        nameTextView.setText("Jose Jesus Lemus Barreto");
        raTextView.setText("RA: 09048050");
        courseTextView.setText("Curso: Análise e Desenvolvimento de Sistemas");

        return view;
    }
}
