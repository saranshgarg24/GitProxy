package com.saransh.app.gitproxy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    String AccessToken;
    RecyclerView r_list;
    AVLoadingIndicatorView loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        AccessToken = i.getStringExtra("accesstoken");

        loader = (AVLoadingIndicatorView)findViewById(R.id.avi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Repositories");

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();

        if(ni == null)
        {
            Toast.makeText(getApplicationContext(), "Can't Connect to the Internet", Toast.LENGTH_LONG).show();
        }
        else {


            new getRepositries().execute();
        }

        initList();



    }

    private void initList() {
        r_list = (RecyclerView) findViewById(R.id.list);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        r_list.setLayoutManager(llm);
    }

    class getRepositries extends AsyncTask<Object, Object, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loader.smoothToShow(); //showing the loader


        }


        @Override
        protected JSONArray doInBackground(Object... args) {

            JSONParser jsonParser = new JSONParser();

            String url = "https://api.github.com/user/repos";
            url = url + "?access_token=" + AccessToken;

            JSONArray response = jsonParser.getJSONFromUrl(url);

            if (response != null)
            {
                return response;
            }
            return null;
        }
        @Override
        protected void onPostExecute(JSONArray k) {

            loader.hide();
            if (k != null){

                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getApplicationContext(),k);
                r_list.setAdapter(recyclerAdapter);

            }else {
                Toast.makeText(getApplicationContext(), "no internet"
                        , Toast.LENGTH_SHORT).show();
            }


        }

    }
}
