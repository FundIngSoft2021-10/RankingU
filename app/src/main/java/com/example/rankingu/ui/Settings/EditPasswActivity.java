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
    public void confirmpasword(View view )
    {String pasword1= c1.getText().toString();
        String Confirmacionpassword2=c2.getText().toString();
        if(pasword1.equals(Confirmacionpassword2))
        {
            user.updatePassword(pasword1);

        }
        else{
            Toast.makeText(this,"Las contraseñas no coinciden ", Toast.LENGTH_SHORT).show();
        }






    }
}
