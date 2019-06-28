package com.example.athleticsessexce881final;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

public class MLaxActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlax);





        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Menu");




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_favorite){
            Toast.makeText(this,"Favorited",Toast.LENGTH_SHORT).show();

            return true;
        }else if (id == R.id.action_settings){
            Toast.makeText(this,"No Settings just yet", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.action_Home){
            startActivity(new Intent(this,MainActivity.class));
            return true;
        }else if (id == R.id.action_Help){
            Toast.makeText(this,"This is a sports page that displays all of the match details, hit the home button for more sport pages.",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.action_exit){
            startActivity(new Intent(this,LogInActivity.class));
            return true;

        }else if (id == R.id.action_Search){
            Toast.makeText(this,"Search for a sport",Toast.LENGTH_SHORT).show();

            //searchFunction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
