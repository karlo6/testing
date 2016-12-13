package com.javacodegeeks;

/**
 * Created by Taison_Gary on 12/13/2016.
 */
import java.io.IOException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.javacodegeeks.R;

import  okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AndroidHttpPostGetActivity extends Activity{
    OkHttpClient client;
    MediaType JSON;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (TextView)findViewById(R.id.TextView1);
        text.setOnClickListener((View.OnClickListener) this);

        client = new OkHttpClient();
        JSON = MediaType.parse("application/json; charset=utf-8");
    }
    public void makeGetRequest(View v) throws IOException {
        GetTask task = new GetTask();
        task.execute();

    }


    public class GetTask extends AsyncTask {
        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                String getResponse = get("http://taisondigital.com.ph/testforyou/view-product");
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(String getResponse) {
            System.out.println(getResponse);
        }

        public String get(String url) throws IOException {
            Request request = new Request.Builder()
                    .url("taisondigital.com.ph/testforyou/view-product")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }


        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }
    public void onClick(View v) throws IOException {
         makePostRequest(v);
    }



    public void  makePostRequest (View v)throws IOException {
            PostTask task = new PostTask();
            task.execute();
            Toast.makeText(getApplicationContext(), "nakapasa na", Toast.LENGTH_LONG).show();

        }



    public class PostTask extends AsyncTask {
        private Exception exception;
        protected String doInBackground(String... urls) {

            try {
                String getResponse = post("http://taisondigital.com.ph/testforyou/add-product", passJson("Karlson", "Capt.price", "samquantity" ));
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }
        protected void onPostExecute(String getResponse) {
            System.out.println(getResponse);
        }
        private String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            return response.body().string();

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }
    public String passJson(String name, String price, String quantity) {

        return "["
                + "{'name':" + name + "},"
                + "{'price':" + price + "},"
                + "{'quantity':" +quantity+"}"
                + "]}";
    }
}
