package com.example.rankingu.ui.Suggestions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.rankingu.Classes.Error;
import com.example.rankingu.Classes.Suggstion;
import com.example.rankingu.R;
import com.example.rankingu.ui.Error.ErrorFragment;
import com.example.rankingu.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SuggestionsFragment extends Fragment {

    private Suggestions suggestions;
    private TextView textSug;
    private Button sug;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        suggestions =
                ViewModelProviders.of(this).get(Suggestions.class);
        View root = inflater.inflate(R.layout.fragment_suggestions, container, false);

        textSug = root.findViewById(R.id.textSuggestions);
        sug = root.findViewById(R.id.send_suggestion);

        sug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = registrarInscripcion();
                if(result){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(SuggestionsFragment.this.getContext());
                    alerta.setMessage("Sugerencia enviada")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog exito = alerta.create();
                    exito.setTitle("");
                    exito.show();
                }
                else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(SuggestionsFragment.this.getContext());
                    alerta.setMessage("Error: Sugerencia no enviada.")
                            .setCancelable(false)
                            .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog exito = alerta.create();
                    exito.setTitle("");
                    exito.show();
                }
            }
        });
        return root;
    }

    public boolean validarInscripcion(){
        return true;
    }

    //Cargar sugerencia a la Base de datos
    public boolean registrarInscripcion() {
        // acabar la funcion de validar
        if (validarInscripcion()) {
            Suggstion er = new Suggstion();
            er.setSug(textSug.getText().toString());
            er.setUser(user.getEmail());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            String fechaIni = dateFormat.format(date);

            //Añadir a la BD
            db.collection("Sugerencias").document(user.getEmail()).collection(fechaIni).add(er);
            Toast.makeText(this.getContext(), "Sugerencia subida", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(this.getContext(), "Introduzca todos los datos", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}