package com.saransh.app.gitproxy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        String username = i.getStringExtra("username");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initList();



    }

    private void initList() {
        RecyclerView r_list = (RecyclerView) findViewById(R.id.list);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getApplicationContext());

        r_list.setAdapter(recyclerAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        r_list.setLayoutManager(llm);


    }
}
