package com.saransh.app.gitproxy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import static com.saransh.app.gitproxy.Login.accessToken;

public class Commits extends AppCompatActivity {

    String owners;
    String repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        Intent i = getIntent();
        owners = i.getStringExtra("owner");
        repo = i.getStringExtra("repo");

        TextView repotitle = (TextView) findViewById(R.id.repo);

        repotitle.setText(repo);

        new getCommits().execute();
    }
    class getCommits extends AsyncTask<Object, Object, JSONArray> {

        @Override
        protected JSONArray doInBackground(Object... args) {

            JSONParser jsonParser = new JSONParser();

            String url = "https://api.github.com/repos/";
            url = url + owners + "/" + repo + "?access_token=" + accessToken;

            JSONArray response = jsonParser.getJSONFromUrl(url);

            if (response != null)
            {
                return response;
            }
            return null;
        }
        @Override
        protected void onPostExecute(JSONArray k) {
            if (k != null){
                Log.d("response", String.valueOf(k));
            }else {
                Toast.makeText(getApplicationContext(), "no internet"
                        , Toast.LENGTH_SHORT).show();
            }


        }

    }
}
