package com.example.rankingu.ui;
// mayormente inferfaz entonces cliente?
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
// facebook - interfaz y persistencia - cliente y servidor?
import com.example.rankingu.ui.SignUp.SignUp;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
// google - interfaz y persistencia - cliente y servidor?
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
// firebase - persistencia - cliente?
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
// imports necesarios
import com.example.rankingu.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private TextView crearCuentaText;
    private TextView olvidoContraText;
    private FirebaseAuth miAuth;
    private ProgressDialog CargaProc;
    private CallbackManager callbackManager;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    public static final int SIGN_IN_CODE = 777;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        miAuth = FirebaseAuth.getInstance();
        olvidoContraText = findViewById(R.id.textOlvido);
        crearCuentaText = findViewById(R.id.textCrear);

        crearCuentaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ccc = new Intent(LoginActivity.this, SignUp.class);
                ccc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ccc);
            }
        });

        // LOGIN PROPIO
        usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton2 = findViewById(R.id.login);
        CargaProc = new ProgressDialog(this);

        // FACEBOOK
        LoginButton loginButton = findViewById(R.id.BtonFacebook);

        // GOOGLE
        SignInButton signInButton = findViewById(R.id.BtonGoogle);
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);

        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUi("PROXIMAMENTE...");
                //Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                //startActivityForResult(signInIntent, SIGN_IN_CODE);
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                updateUi("PROXIMAMENTE...");
                //handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                updateUi("CANCEL");
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onError(FacebookException error) {
                updateUi("ERROR");
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_LONG).show();
                    GoMainScreen();
                }
            }
        };

        //LOGIN EMAIL PROPIO
        loginButton2.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               CargaProc.setMessage("Verificando con la Base de datos...");
                                               CargaProc.show();
                                               if(!usernameEditText.getText().toString().trim().isEmpty() && !passwordEditText.getText().toString().trim().isEmpty()) {
                                                   InicioUser(usernameEditText.getText().toString().trim(),
                                                           passwordEditText.getText().toString().trim());
                                               }else{
                                                   CargaProc.setMessage("Datos incompletos");
                                                   CargaProc.show();
                                               }
                                           }
                                       }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else {
            //callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            updateUi("GOOGLE");
            firebaseAuthWithGoogle(result.getSignInAccount().getIdToken());
        }else{
            updateUi("NO GOOGLE");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        miAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuthListener!=null)
            miAuth.removeAuthStateListener(firebaseAuthListener);
    }

    // IR A PANTALLA PRINCIPAL
    private void GoMainScreen(){
        Intent ccc = new Intent(LoginActivity.this, MainActivity.class);
        ccc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ccc);
    }

    // LOGIN CON EMAIL Y PASSWORD
    private void InicioUser(String email, String password){
        miAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = miAuth.getCurrentUser();
                            if(user != null) {
                                GoMainScreen();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUi(" Falla de autentificación.");
                        }
                        CargaProc.dismiss();
                    }
                });
    }

    // LOGIN FACEBOOK
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        miAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = miAuth.getCurrentUser();
                            //updateUi(user.getDisplayName());
                        } else {
                            updateUi("NO ENTRO");
                        }
                    }
                });
    }

    // LOGIN GOOGLE
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        miAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"LLLLL",Toast.LENGTH_LONG).show();
                            FirebaseUser user = miAuth.getCurrentUser();
                        } else {
                            updateUi("NO ENTRO");
                        }
                    }
                });
    }

    private void updateUi(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
