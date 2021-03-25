package com.cheekupeeku.testapicall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cheekupeeku.testapicall.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Post> al;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("please wait while loading data...");
        pd.show();
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";
        StringRequest request  = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try{
                    al = new ArrayList<>();
                    JSONArray arr = new JSONArray(response);
                    for(int i=0; i<arr.length(); i++){
                        JSONObject obj = arr.getJSONObject(i);
                        int userId = obj.getInt("userId");
                        int id = obj.getInt("id");
                        String title = obj.getString("title");
                        String body = obj.getString("body");
                        Post p = new Post(userId,id,title,body);
                        al.add(p);
                    }
                    PostAdapter adapter = new PostAdapter(MainActivity.this,al);
                    binding.rv.setAdapter(adapter);
                    binding.rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);
        /*
        un comment to test AsyncTask
        new PostTask().execute(apiUrl);

         */
    }
    /*
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
            try{
                al = new ArrayList<>();
                JSONArray arr = new JSONArray(s);
                for(int i=0; i<arr.length(); i++){
                     JSONObject obj = arr.getJSONObject(i);
                     int userId = obj.getInt("userId");
                     int id = obj.getInt("id");
                     String title = obj.getString("title");
                     String body = obj.getString("body");
                     Post p = new Post(userId,id,title,body);
                     al.add(p);
                }
                PostAdapter adapter = new PostAdapter(MainActivity.this,al);
                binding.rv.setAdapter(adapter);
                binding.rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
            catch (Exception e){
                Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}



