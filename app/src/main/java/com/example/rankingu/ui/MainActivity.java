package com.example.rankingu.ui;
//CLASE NECESARIA

import com.example.rankingu.Classes.Horario;
import com.example.rankingu.Classes.MateriaDelMain;
import com.example.rankingu.R;
// MAYORMENTE INTERFAZ OSEA CLIENTE SUPONGO
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.rankingu.ui.Enroll.ConflictActivity;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.example.rankingu.ui.Materia.MateriaActivity;
import com.example.rankingu.ui.Search.SearchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavController.OnDestinationChangedListener;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private TextView nombreUser;

    private String imgDef = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        hView = navigationView.getHeaderView(0);
        TextView emailUser = hView.findViewById(R.id.Buscarpor);
        nombreUser = hView.findViewById(R.id.nombreView);
        fotoUser = hView.findViewById(R.id.fotoView);
        emailUser.setText(user.getEmail());
        final ArrayList<String> imgs = new ArrayList<>();
        matrizHorario = new ArrayList<>(12);
        for (ArrayList<TextView> i : matrizHorario) {
            matrizHorario.add(new ArrayList<TextView>(8));
        }
        if (user.getPhotoUrl() != null && user.getDisplayName() != null) {
            Glide.with(this.getApplicationContext()).load(user.getPhotoUrl()).fitCenter().into(fotoUser);
            nombreUser.setText(user.getDisplayName());
        } else {
            consultaLogin(this.getApplicationContext());
        }

        consultaImgs(MainActivity.this, imgs);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_error)
                .setDrawerLayout(drawer)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        fotoUser.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_cambio_img);

                final int[] i = {0};
                final ImageView img = dialog.findViewById(R.id.goProDialogImage);
                //img.setImageURI(Uri.parse(imgs.get(i[0])));
                Glide.with(MainActivity.this).load(Uri.parse(imgs.get(i[0]))).fitCenter().into(img);

                Button sig = dialog.findViewById(R.id.button8);
                sig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(i[0]+1<imgs.size())
                            i[0] = i[0]+1;
                        else
                            i[0] = 0;
                        Glide.with(MainActivity.this).load(Uri.parse(imgs.get(i[0]))).fitCenter().into(img);
                        //img.setImageURI(Uri.parse(imgs.get(i[0])));
                    }
                });

                Button guardar = dialog.findViewById(R.id.button7);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        guardarImg(MainActivity.this, imgs.get(i[0]));
                        consultaLogin(MainActivity.this);
                    }
                });

                dialog.show();
                return true;
            }
        });

        navigationView.getMenu().findItem(R.id.nav_exit).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Consulta IMGS
    public void consultaImgs(final Context x, final ArrayList<String> imgs) {
        db.collection("Imagenes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("dir").toString());
                                imgs.add(document.getData().get("dir").toString());
                            }
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }

    //Consulta
    public void consultaLogin(final Context x) {
        db.collection("Usuarios").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Glide.with(x).load(task.getResult().getData().get("foto").toString()).fitCenter().into(fotoUser);
                    nombreUser.setText(task.getResult().getData().get("usuario").toString());
                } else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }
            }
        });
    }

    //Guardar BD
    public void guardarImg(final Context x, String img) {
        //Añadir a la BD
        db.collection("Usuarios").document(user.getEmail()).update("foto", img)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(x, "Imagen guardada.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(x, "Vuelva a intentarlo.", Toast.LENGTH_LONG).show();
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
