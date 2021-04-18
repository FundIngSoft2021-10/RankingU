package com.example.rankingu.ui.Settings;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;

public class EditCorreoActivity extends AppCompatActivity {
    Button b1;
    EditText c1,c2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarcorreo);
        b1=(Button)findViewById(R.id.Botonconfirmareditcorreo);
        c1=(EditText)findViewById(R.id.editTextTextEmailAddress);
        c1=(EditText)findViewById(R.id.editTextTextEmailAddress2);

    }

}
