package com.example.rankingu.ui.Horario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.R;
import com.example.rankingu.ui.MainActivity;
import com.example.rankingu.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class eliminarMateria extends AppCompatActivity {

    private TextView txtNombreProfe;
    private  TextView txtNombreMateria;
    private TextView txtHorario;
    private Button btnEliminar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar_materia_horario);
        txtNombreMateria = (TextView) findViewById(R.id.borrar_horario_materianombre);
        txtNombreProfe = (TextView) findViewById(R.id.borrar_horario_docente);
        txtHorario = (TextView) findViewById(R.id.borrar_horario_Horario);
        btnEliminar = (Button) findViewById(R.id.boton_eliminar_horario);
        Intent intent = getIntent();
        Materia m =  (Materia) intent.getExtras().getSerializable("materia");

        txtNombreProfe.setText(m.getProfesores());
        txtNombreMateria.setText(m.getNombre());
        String horario= "";
        for(SesionClase s :m.getSesiones_clase())
        {
            horario+=s.toString()+"\n";
        }
        txtHorario.setText(horario);

        btnEliminar.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(final View view) {

                db.collection("Usuarios").document(user.getEmail()).collection("materias").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document:task.getResult())
                        {
                            if(document.getData().get("nombre").toString().equalsIgnoreCase(txtNombreMateria.getText().toString()))
                            {
                                updateUi("entro if");
                                db.collection("Usuarios").document(user.getEmail()).collection("materias").document(document.getId()).delete();


                            }
                        }
                    }
                });
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }


    private void updateUi(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
