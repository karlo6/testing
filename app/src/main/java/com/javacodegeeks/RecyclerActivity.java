package com.javacodegeeks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Taison_Gary on 12/20/2016.
 */

public class RecyclerActivity extends AppCompatActivity{

    private Button btnGet;
    private ProgressDialog progress;
    private ListView listview;
    public List<gotData> listdata = new ArrayList<>();
    public RecyclerView adapter;


     public class gotData {
        String name;
        String quantity;
        String price;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleractivity);


        btnGet = (Button) findViewById(R.id.btnGet);
        listview = (ListView) findViewById(R.id.listview);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = new ProgressDialog(view.getContext());
                progress.setMessage("Fetching information");
                progress.setIndeterminate(true);


                new getRequest().execute();
            }
        });


    }

    public class getRequest extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        protected String doInBackground(String... arg0) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://taisondigital.com.ph/testforyou/view-product");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                InputStream os = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(os));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                os.close();
                reader.close();
                return result.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());

            }

        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONArray listarray = new JSONArray(result);

                for (int i = 0; i < listarray.length(); i++) {
                    JSONObject j = new JSONObject(listarray.get(i).toString());
                    gotData add = new gotData();
                    add.name=j.getString("Name");
                    add.quantity=j.getString("Quantity");
                    add.price=j.getString("Price");
                    listdata.add(add);
                    Log.e("ERROR", add.toString());
                }
                adapter = (RecyclerView) findViewById(R.id.productList);
               AdapterRecycle madapter = new AdapterRecycle(RecyclerActivity.this, listdata);
                adapter.setAdapter(madapter);
                adapter.setLayoutManager(new LinearLayoutManager(RecyclerActivity.this));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("ERR", e.getMessage());
            }


            Log.e("testing", result);
            progress.dismiss();

        }
        public class AdapterRecycle extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
            private Context context;
            private LayoutInflater inflater;


            List<gotData> listdata = Collections.emptyList();
           gotData current;
            int currentPos = 0;

            public AdapterRecycle(Context context, List<gotData> listdata) {
                this.context = context;
                inflater = LayoutInflater.from(context);
                this.listdata = listdata;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.container, parent, false);
                MyHolder holder = new MyHolder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyHolder myHolder = (MyHolder) holder;
                gotData current = listdata.get(position);

                myHolder.name.setText(current.name);
                myHolder.quantity.setText(current.quantity);
                myHolder.price.setText(current.price);
                myHolder.img.setImageResource(R.drawable.seekbar_calm);
            }

            @Override
            public int getItemCount() {
                return listdata.size();
            }
            class MyHolder extends RecyclerView.ViewHolder {
                TextView name;
                TextView quantity;
                TextView price;
                ImageView img;

                public MyHolder(View ItemView) {
                    super(ItemView);
                    name = (TextView) ItemView.findViewById(R.id.name);
                    quantity = (TextView) ItemView.findViewById(R.id.quantity);
                    price = (TextView) ItemView.findViewById(R.id.price);
                    img = (ImageView) ItemView.findViewById(R.id.img);
                }

            }
        }

    }

    }


