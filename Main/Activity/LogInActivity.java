package com.example.athleticsessexce881final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    //Regular sign in :
    private EditText mEmailView, mPasswordView;
    private Button btnLogIn, btnReg;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


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


    }


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