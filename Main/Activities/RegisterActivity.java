package com.example.athleticsessexce881final;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText regEmail, regPass, regConfirmPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        regEmail = findViewById(R.id.regemail);
        regPass = findViewById(R.id.regpass);
        regConfirmPass = findViewById(R.id.regconfirmpass);
        Button regBtn = findViewById(R.id.RegBtn);
        mAuth = FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });



    }

    private void registerUser() {

        String Email = regEmail.getText().toString().trim(); //Trim removes spaces
        String password = regPass.getText().toString().trim();
        String password_cnfrm = regConfirmPass.getText().toString();

        if (TextUtils.isEmpty(password)|| TextUtils.isEmpty(password_cnfrm)|| TextUtils.isEmpty(Email))
        {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();


        }else if (!password.equals(password_cnfrm)){
            Toast.makeText(RegisterActivity.this, "Your Passwords do not match", Toast.LENGTH_SHORT).show();
        }else
        {

            Toast.makeText(RegisterActivity.this, "Creating your account now", Toast.LENGTH_SHORT).show();
            //create account and change to log in.
            mAuth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterActivity.this, LogInActivity.class));


                    } else {
                        Toast.makeText(RegisterActivity.this, "Make sure you add the @blah.com to the end of the email and your password has min length of 8 characters. ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }
}
