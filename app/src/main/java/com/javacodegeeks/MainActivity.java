package com.javacodegeeks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Created by Taison_Gary on 12/14/2016.
 */

public class MainActivity extends Activity {

    private Button btnSpec, btnAdd, btnDelete, btnView, btnRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSpec = (Button) findViewById(R.id.btnSpec);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnView = (Button) findViewById(R.id.btnView);
        btnRec = (Button) findViewById(R.id.btnRec);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(MainActivity.this, AddActivity.class);
                startActivity(add);
            }
        });

        btnSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent spec = new Intent(MainActivity.this, SubActivity.class);
                startActivity(spec);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent del = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(del);
            }
        });


        btnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vel = new Intent(MainActivity.this, RecyclerActivity.class);
                startActivity(vel);
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vel = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(vel);
            }
        });

    }
}
