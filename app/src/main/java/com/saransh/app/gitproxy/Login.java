package com.saransh.app.gitproxy;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    public static String OAUTH_URL = "http://github.com/login/oauth/authorize";
    public static String OAUTH_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    public static String CLIENT_ID = "27df83cfb3490de690de";
    public static String CLIENT_SECRET = "bd23029ed0c6126a57fa185cf6999b73e37e4acb";
    public static String CALLBACK_URL = "https://gitproxy-aaf55.firebaseapp.com/__/auth/handler";

    String accessCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);


        String url = OAUTH_URL + "?client_id=" + CLIENT_ID;

        WebView webview = (WebView)findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String accessTokenFragment = "access_token=";
                String accessCodeFragment = "code=";

                // We hijack the GET request to extract the OAuth parameters

                Log.d("url1",url);

                if (url.contains(accessTokenFragment)) {
                    // the GET request contains directly the token
                    String accessToken = url.substring(url.indexOf(accessTokenFragment));
                  //  TokenStorer.setAccessToken(accessToken);
                    Log.d("accessToken",accessToken);

                } else if(url.contains(accessCodeFragment)) {
                    // the GET request contains an authorization code
                    accessCode = url.substring(url.indexOf(accessCodeFragment));
                    //TokenStorer.setAccessCode(accessCode);
                    accessCode = accessCode.replaceAll("code=","").replaceAll("&state=","");
                    Log.d("accessCode",accessCode);

                    String query = "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + accessCode + "&state=";
                    view.postUrl(OAUTH_ACCESS_TOKEN_URL, query.getBytes());

                    new SendPostRequest().execute();

                }
            }
        });
        Log.d("url2",url);
        webview.loadUrl(url);
    }

    class SendPostRequest extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("client_id", CLIENT_ID));
            params.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
            params.add(new BasicNameValuePair("code", accessCode));

            String response = jsonParser.getJSONFromUrl(OAUTH_ACCESS_TOKEN_URL, params);

            if (response != null)
            {
                Log.d("response", String.valueOf(response));
                return "ok";
            }
            return null;
        }
        @Override
        protected void onPostExecute(String k) {
                Toast.makeText(getApplicationContext(), "no internet"
                        , Toast.LENGTH_SHORT).show();

        }

    }



}
