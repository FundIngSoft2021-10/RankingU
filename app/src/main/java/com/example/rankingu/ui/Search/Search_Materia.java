package com.example.rankingu.ui.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.example.rankingu.ui.MainActivity;

public class Search_Materia extends AppCompatActivity {

    private Button op1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_materia);

        op1 = findViewById(R.id.materiaB1);

        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search_Materia.this, EnrollActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
               /* Snackbar.make(view, "PROGRAMAR ALGO", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }


}
