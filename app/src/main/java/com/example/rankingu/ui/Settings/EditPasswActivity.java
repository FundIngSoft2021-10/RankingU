package com.example.rankingu.ui.Settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Error;
import com.example.rankingu.R;
import com.example.rankingu.ui.Error.ErrorFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditPasswActivity extends AppCompatActivity {

    private Button confirmar;
    private EditText newPasw;
    private EditText confPasw;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiarcontrase);

        newPasw = findViewById(R.id.editTextTextPassword);
        confPasw = findViewById(R.id.editTextTextPassword2);
        confirmar = findViewById(R.id.Botonconfirmarcontrasen);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = validarContraseña(newPasw.getText().toString(),confPasw.getText().toString());
                if (result) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(EditPasswActivity.this);
                    alerta.setMessage("Contraseña modificada")
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
                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(EditPasswActivity.this);
                    alerta.setMessage("Error, intente de nuevo.")
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
    }

    public boolean validarContraseña(String p, String p2) {
        if (p.equals(p2)) {
            user.updatePassword(p);
            return true;
        }
        return false;
    }
}
