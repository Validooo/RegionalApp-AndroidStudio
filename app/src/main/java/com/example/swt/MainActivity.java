package com.example.swt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.swt.model.Company;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button getstarted;
    Singletonclass singletonobject;
    ArrayList<Company> alldata = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getstarted= findViewById(R.id.getstarted);
        singletonobject= Singletonclass.get();
        singletonobject.readCompanies();
        alldata = singletonobject.getCompanies();


        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                intent.putExtra("alldata",alldata);
                startActivity(intent);

            }
        });





    }
}