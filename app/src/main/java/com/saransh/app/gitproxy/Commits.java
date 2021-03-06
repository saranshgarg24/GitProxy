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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;

import static com.saransh.app.gitproxy.Login.accessToken;

public class Commits extends AppCompatActivity {

    String owners;
    String repo;
    String AccessToken;
    RecyclerView r_list;
    AVLoadingIndicatorView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        Intent i = getIntent();
        owners = i.getStringExtra("owner");
        repo = i.getStringExtra("repo");
        loader = (AVLoadingIndicatorView)findViewById(R.id.avi);

        initToolbar();

        TextView repotitle = (TextView) findViewById(R.id.repo);

        repotitle.setText(repo);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();

        if(ni == null)
        {
            Toast.makeText(getApplicationContext(), "Can't Connect to the Internet", Toast.LENGTH_LONG).show();

        }
        else {

            new getCommits().execute();
        }

        initList();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initList() {
        r_list = (RecyclerView) findViewById(R.id.list);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        r_list.setLayoutManager(llm);
    }
    class getCommits extends AsyncTask<Object, Object, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader.smoothToShow(); //showing the loader


        }
        @Override
        protected JSONArray doInBackground(Object... args) {

            JSONParser jsonParser = new JSONParser();

            String url = "https://api.github.com/repos/";
            url = url + owners + "/" + repo + "/commits"+"?access_token=" + accessToken;

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
                Log.d("response", String.valueOf(k));
                CommitAdapter recyclerAdapter = new CommitAdapter(getApplicationContext(),k);
                r_list.setAdapter(recyclerAdapter);
            }else {
                Toast.makeText(getApplicationContext(), "no internet"
                        , Toast.LENGTH_SHORT).show();
            }


        }

    }
}
