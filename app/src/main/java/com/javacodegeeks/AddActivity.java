package com.javacodegeeks;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Taison_Gary on 12/14/2016.
 */

public class AddActivity extends AppCompatActivity {
    private EditText nameTXT, quantityTXT, priceTXT;
    private Button btnPost;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        nameTXT = (EditText) findViewById(R.id.nameTXT);
        quantityTXT = (EditText) findViewById(R.id.quantityTXT);
        priceTXT = (EditText) findViewById(R.id.priceTXT);
        btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = new ProgressDialog(view.getContext());
                progress.setMessage("Adding items");
                progress.setIndeterminate(true);


             String test = "^[a-zA-Z]+";

                if(nameTXT.getText().toString().trim().matches(test)){
                    new postRequest().execute(nameTXT.getText().toString(), quantityTXT.getText().toString(), priceTXT.getText().toString());
                }
                else{
                    nameTXT.setError("Alphabets only");
                }
            }
        });

    }

    public class postRequest extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        protected String doInBackground(String... arg0) {
            try {
                URL url = new URL("http://taisondigital.com.ph/testforyou/add-product");

                JSONObject postData = new JSONObject();

                postData.put("name", arg0[0]);
                postData.put("quantity", arg0[1]);
                postData.put("price", arg0[2]);
                Log.e("params", postData.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(postDataString(postData));

                writer.flush();
                writer.close();
                os.close(  );

                int responseCode = conn.getResponseCode();

                if(responseCode == HttpsURLConnection.HTTP_OK){
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = " ";

                    while ((line = in.readLine()) != null){
                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                }
                else {
                    return new String("false: " +responseCode);

                }

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void  onPostExecute(String result) {
            progress.dismiss();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            nameTXT.setText("");
            quantityTXT.setText("");
            priceTXT.setText("");
        }
    }

    public String postDataString(JSONObject params) throws Exception{
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()){
            String key =  itr.next();
            Object value = params.get(key);
            if(first)
                first=false;
            else
                result.append("&");


            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}
