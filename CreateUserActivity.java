package com.example.athleticsessexce881final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.auth.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateUserActivity extends AppCompatActivity {

    private EditText Username, Age , country;
    private Button createButton;
    private CircleImageView profileimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);


        Username = findViewById(R.id.username_setup);
        Age = findViewById(R.id.age_setup);
        country = findViewById(R.id.Country_setup);
        createButton = findViewById(R.id.create_btn);
        profileimg = findViewById(R.id.user_header_setup);




    }










}
