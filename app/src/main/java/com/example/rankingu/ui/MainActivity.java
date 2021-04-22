package com.example.rankingu.ui;
//CLASE NECESARIA
import com.example.rankingu.R;
// MAYORMENTE INTERFAZ OSEA CLIENTE SUPONGO
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.example.rankingu.ui.Search.SearchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
// FIREBASE - SERVIDOR
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// FACEBOOK - SERVIDOR
import com.facebook.login.LoginManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity {

    private View hView;
    private AppBarConfiguration mAppBarConfiguration;
    private FloatingActionButton btnBusqueda;
    private ArrayList<ArrayList<TextView>> matrizHorario;
    private TextView t;
    private TableLayout horario;
    private TableRow f7;
    private ImageView fotoUser;

    private String imgDef = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.floatBtnSearch);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
               /* Snackbar.make(view, "PROGRAMAR ALGO", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        hView = navigationView.getHeaderView(0);
        TextView emailUser = hView.findViewById(R.id.Buscarpor);
        TextView nombreUser = hView.findViewById(R.id.nombreView);
        fotoUser = hView.findViewById(R.id.fotoView);
        nombreUser.setText(user.getDisplayName());
        emailUser.setText(user.getEmail());
        btnBusqueda = findViewById(R.id.floatBtnSearch);
        matrizHorario = new ArrayList<>(12);
        for(ArrayList<TextView> i:matrizHorario)
        {
            matrizHorario.add(new ArrayList<TextView>(8));
        }

        consultaImg(this.getApplicationContext());
        //Glide.with(this.getApplicationContext()).load(imgDef).fitCenter().into(fotoUser);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send , R.id.nav_error)
                .setDrawerLayout(drawer)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings).setTitle("Cerrar Sesion");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Consulta
    public void consultaImg(final Context x){
        db.collection("Usuarios").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Glide.with(x).load(task.getResult().getData().get("foto").toString()).fitCenter().into(fotoUser);
                } else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void updateUi(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
