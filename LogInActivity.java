package com.example.athleticsessexce881final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.TimeUnit;

public class LogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

    private ProgressDialog progressDialog;
    //Regular sign in :
    private EditText mEmailView, mPasswordView;
    private Button btnLogIn, btnReg;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Google Sign in
    private GoogleApiClient mGoogleApiClient;
    private SignInButton GsignInButton;
    Button GsignoutButton;


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = findViewById(R.id.LogInEmail);
        mPasswordView = findViewById(R.id.LoginPassword);
        btnLogIn = findViewById(R.id.LogInbtn);
        btnReg = findViewById(R.id.LogInreg);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LogInActivity.this,"WHAAT", Toast.LENGTH_SHORT).show();
                startSignIn();
                //startActivity(new Intent(LogInActivity.this, MainActivity.class));
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
                Toast.makeText(LogInActivity.this, "Lets get you registered", Toast.LENGTH_SHORT).show();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        GsignInButton = findViewById(R.id.GoogleSignIn);
        GsignoutButton = findViewById(R.id.GSignOut);
        GsignInButton.setOnClickListener(this);
        GsignoutButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.GoogleSignIn:
                signIn();
                startActivity(new Intent(LogInActivity.this,MainActivity.class));
                break;
        }

    }

    //GOOGLE SIGN IN AND THEN START THE WRITE UP.
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handlesigninresult(result);

        }
    }

    private void handlesigninresult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult" + result.isSuccess());

        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            Toast.makeText(LogInActivity.this, "Welcome " + account.getDisplayName(), Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(LogInActivity.this,MainActivity.class));
        } else {

        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {


        Log.d(TAG, "onConnectionFailed" + connectionResult);
    }

    /////regular sign in

    private void startSignIn() {
        final String email = mEmailView.getText().toString().trim();//get rid of spaces if any
        String password = mPasswordView.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LogInActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

        } else {
            //Present Loading Bar
            progressDialog.setTitle("Login");
            progressDialog.setMessage("Please wait, you will be logged in shortly");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "Welcome " + email, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                        progressDialog.dismiss();

                    } else {
                        String messageError = task.getException().getMessage();
                        Toast.makeText(LogInActivity.this, "An error happened: " + messageError, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //mAuth.addAuthStateListener(mAuthListener);
        if (currentUser != null) {
            //startActivity(new Intent(LogInActivity.this, MainActivity.class));

            Toast.makeText(LogInActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();
        }

    }
}