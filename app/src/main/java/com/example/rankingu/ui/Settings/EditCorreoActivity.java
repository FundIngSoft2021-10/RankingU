package com.example.rankingu.ui.Settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditCorreoActivity extends AppCompatActivity {

    private Button confirmar;
    private EditText newPasw;
    private EditText confPasw;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarcorreo);
        newPasw = findViewById(R.id.editTextTextEmailAddress);
        confPasw = findViewById(R.id.editTextTextEmailAddress2);
        confirmar = findViewById(R.id.Botonconfirmareditcorreo);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = validarEmail(newPasw.getText().toString(),confPasw.getText().toString());
                if (result) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(EditCorreoActivity.this);
                    alerta.setMessage("Email modificado.")
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
                    AlertDialog.Builder alerta = new AlertDialog.Builder(EditCorreoActivity.this);
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

    public boolean validarEmail(String p, String p2) {
        if (p.equals(p2)) {
            user.updateEmail(p);
            return true;
        }
        return false;
    }
}
