package com.example.rankingu.ui.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditPasswActivity extends AppCompatActivity {
    Button b1;
    EditText c1,c2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiarcontrase);
        c1=(EditText)findViewById(R.id.editTextTextPassword);
        c1=(EditText)findViewById(R.id.editTextTextPassword2);


    }
    public void confirmeditcorreo(View view )
    {String correo1= c1.getText().toString();
        String Confirmacioncorreo2=c2.getText().toString();
        if(correo1.equals(Confirmacioncorreo2))
        {
            user.updateEmail(correo1);

        }
        else{
            Toast.makeText(this,"Los correos no coinciden ", Toast.LENGTH_SHORT).show();
        }






    }
}
