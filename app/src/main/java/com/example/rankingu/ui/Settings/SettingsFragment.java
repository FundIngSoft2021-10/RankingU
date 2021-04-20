package com.example.rankingu.ui.Settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rankingu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    EditText t1,t2;
    Button r1, r2, r3;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);
        r1 = root.findViewById(R.id.BotonCambiarContraseña);
        r2 = root.findViewById(R.id.button4);
        r3 = root.findViewById(R.id.botoneliminarcuenta);
        t1 = root.findViewById(R.id.Settingscorreo_Electronico);
        t2 = root.findViewById(R.id.settingsnombreusuario);

        t1.setText(user.getEmail());
        t2.setText(user.getDisplayName());

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditCorreoActivity.class);
                startActivity(intent);
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditPasswActivity.class);
                startActivity(intent);
            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(SettingsFragment.this.getContext());
                alerta.setMessage("¿Eliminar Cuenta?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                user.delete();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog exito = alerta.create();
                exito.setTitle("");
                exito.show();
            }
        });
        return root;
    }
}