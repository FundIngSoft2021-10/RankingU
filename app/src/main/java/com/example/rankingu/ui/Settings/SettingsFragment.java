package com.example.rankingu.ui.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.rankingu.R;
import com.example.rankingu.ui.LoginActivity;
import com.example.rankingu.ui.MainActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    EditText t1, t2, t3, t4;
    Button r1, r2, r3;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        t3 = root.findViewById(R.id.editTextTextPersonName);
        t4 = root.findViewById(R.id.editTextTextPersonName2);

        consultaLogin(this.getContext());
        t1.setText(user.getEmail());

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!r1.getText().equals("ACTUALIZAR DATOS"))
                {
                    t2.setEnabled(true);
                    t3.setEnabled(true);
                    t4.setEnabled(true);
                    r1.setText("ACTUALIZAR DATOS");

                }else {

                    guardarInfo(SettingsFragment.this.getContext());
                    t2.setEnabled(false);
                    t3.setEnabled(false);
                    t4.setEnabled(false);
                    r1.setText("EDITAR DATOS");
                }

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
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                dialog.cancel();
                                Intent intent = new Intent(SettingsFragment.this.getContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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

    //Consulta
    public void consultaLogin(final Context x) {
        db.collection("Usuarios").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    t4.setText(task.getResult().getData().get("carrera").toString());
                    t3.setText(task.getResult().getData().get("semestre").toString());
                    t2.setText(task.getResult().getData().get("usuario").toString());
                } else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }
            }
        });
    }

    //Guardar BD
    public void guardarInfo(final Context x) {
        //Añadir a la BD
        db.collection("Usuarios").document(user.getEmail()).update("carrera", t4.getText().toString().trim());
        db.collection("Usuarios").document(user.getEmail()).update("usuario", t2.getText().toString().trim());
        db.collection("Usuarios").document(user.getEmail()).update("semestre", t3.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(x, "Datos guardados.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(x, "Vuelva a intentarlo.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}