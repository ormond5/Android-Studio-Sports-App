package com.example.athleticsessexce881final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class MainActivity extends AppCompatActivity {

    Button searchBtn;
    GridLayout mainGrid;
    CardView Mlax, Wlax, Wball, AF, WSoccer, MVball;

    private NavigationView navigationview;
    private DrawerLayout drawerlayout;
    private ProgressDialog progressDialog;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageButton newimageButton;
    private Toolbar mToolbar;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Need to make activities for all of the Classes*/

        Mlax = findViewById(R.id.MensLax);
        Wlax = findViewById(R.id.WomensLax);
        Wball = findViewById(R.id.WomensBBall);
        AF = findViewById(R.id.AmericanFootball);
        WSoccer = findViewById(R.id.WomensSoccer);
        MVball = findViewById(R.id.MensVball);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());
        mainGrid = findViewById(R.id.MainGrid);

        searchBtn = findViewById(R.id.searchBtn);
        setSingleEvent(mainGrid);


        navigationview = findViewById(R.id.navigation_view);
        //View navView = navigationview.inflateHeaderView(R.layout.navigation_header);
        //Getting an error here idk why
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                usermenuselector(menuItem);
                return false;
            }
        });


        mToolbar = findViewById(R.id.main_page_tbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Menu");


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//THIS IS CRASHING
                new SimpleSearchDialogCompat(MainActivity.this, "Search....", "What are you looking for?",
                        null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        Toast.makeText(MainActivity.this, "" + searchable.getTitle(), Toast.LENGTH_SHORT).show();
                        baseSearchDialogCompat.dismiss();

                        if (searchable.getTitle() == "Men's Lacrosse") {
                            startActivity(new Intent(MainActivity.this, MLaxActivity.class));
                        } else if (searchable.getTitle() == "Women's Lacrosse") {
                            startActivity(new Intent(MainActivity.this, WLaxActivity.class));
                        } else if (searchable.getTitle() == "Men's Volleyball") {
                            startActivity(new Intent(MainActivity.this, MenVballActivity.class));
                        } else if (searchable.getTitle() == "Women's Soccer" || searchable.getTitle() == "Women's Football") {
                            startActivity(new Intent(MainActivity.this, WomensSoccerActivity.class));
                        } else if (searchable.getTitle() == "Women's Basketball") {
                            startActivity(new Intent(MainActivity.this, WomensBBallActivity.class));
                        } else if (searchable.getTitle() == "American Football") {
                            startActivity(new Intent(MainActivity.this, AFActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Sorry That sport isn't registered yet", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }

    private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            CardView cardView = (CardView) mainGrid.getChildAt(i);

            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (finalI == 0) {

                        Toast.makeText(MainActivity.this, "Men's Lacrosse", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, MLaxActivity.class));
                    } else if (finalI == 1) {
                        Toast.makeText(MainActivity.this, "Women's Basketball", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, WomensBBallActivity.class));
                    } else if (finalI == 2) {
                        Toast.makeText(MainActivity.this, "Women's Lacrosse", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, WLaxActivity.class));
                    } else if (finalI == 3) {
                        Toast.makeText(MainActivity.this, "American Football", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, AFActivity.class));
                    } else if (finalI == 4) {
                        Toast.makeText(MainActivity.this, "Women's Football", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, WomensSoccerActivity.class));
                    } else if (finalI == 5) {
                        Toast.makeText(MainActivity.this, "Men's Volleyball", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, MenVballActivity.class));
                    }
                }
            });

        }

    }


    private ArrayList<SearchModel> initData() {
        ArrayList<SearchModel> items = new ArrayList<>();
        items.add(new SearchModel("Women's Basketball"));
        items.add(new SearchModel("Men's Lacrosse"));
        items.add(new SearchModel("Women's Lacrosse"));
        items.add(new SearchModel("Men's Volleyball"));
        items.add(new SearchModel("Women's Football"));
        items.add(new SearchModel("American Football"));

        return items;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (actionBarDrawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void usermenuselector(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            //Options for each nav item. create an activity to send to another page.
            case R.id.nav_home:
                Toast.makeText(this, "Lets go home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_Developer:
                Toast.makeText(this, "Hi, my name is DeShaun. I hope you enjoy!", Toast.LENGTH_SHORT).show();
                break;
            //break;
            case R.id.nav_logOut:
                mAuth.signOut();
                Toast.makeText(this, "Bye Buddy, I hope you find your dad", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, LogInActivity.class));
                break;
        }
    }


   /* @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LogInActivity.class));
        } else {

            progressDialog.setTitle("Login");
            progressDialog.setMessage("Please wait, you will be logged in shortly");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            //UserCreatedCheck();


        }
    }*/
}

   /* private void UserCreatedCheck() {


        final String current_user_id = mAuth.getCurrentUser().getUid();
        //Fix this line right here.
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(current_user_id)) {
                    progressDialog.dismiss();
                    startActivity(new Intent(MainActivity.this, CreateUserActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    }
*/