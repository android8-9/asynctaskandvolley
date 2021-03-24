package com.cheekupeeku.testapicall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";
        new PostTask().execute(apiUrl);
    }
    class PostTask extends AsyncTask<String,Void,String>{
        ProgressDialog pd =new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please wait while loading...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String data="";
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                InputStream in = con.getInputStream();

                while(true){
                    int x = in.read();
                    if(x==-1)
                        break;
                    data = data + (char)x;
                }
            }
            catch (Exception e){
                data = e.toString();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();
        }
    }
}