package com.javacodegeeks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * Created by Taison_Gary on 12/14/2016.
 */

public class ViewActivity extends Activity {

    private EditText areaTXT;
    private Button btnGet;
    private ProgressDialog progress;
    private ListView listview;
    public ArrayList<Data> listdata = new ArrayList<>();
    public ListAdapter adapter;
   public class Data {
       String name;
       String quantity;
       String price;
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);


        btnGet = (Button) findViewById(R.id.btnGet);
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ListAdapter(this);
        listview.setAdapter(adapter);
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
                    Data Add = new Data();
                    Add.name=j.getString("Name");
                    Add.quantity=j.getString("Quantity");
                    Add.price=j.getString("Price");
                    listdata.add(Add);
                    Log.e("ERROR", Add.toString());
                }
            adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("ERR", e.getMessage());
            }


            Log.e("test", result);
            progress.dismiss();

        }


    }

          public class ListAdapter extends BaseAdapter {
              ViewActivity main;

              ListAdapter(ViewActivity main){
                  this.main = main;
              }

              @Override
              public int getCount(){
                  return main.listdata.size();
              }
              @Override
              public Object getItem(int position){
                  return null;
              }
              @Override
              public long getItemId(int position){
                  return 0;
              }
               class ViewHolderItem{
                  TextView  name;
                  TextView  quantity;
                  TextView  price;
              }
              @Override
              public View getView(int position, View convertView, ViewGroup parent){
                  ViewHolderItem holder = new ViewHolderItem();
                  if(convertView==null){
                      LayoutInflater inflater = (LayoutInflater)main.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                      convertView = inflater.inflate(R.layout.listview, null);

                      holder.name = (TextView) convertView.findViewById(R.id.name);
                      holder.quantity = (TextView) convertView.findViewById(R.id.quantity);
                      holder.price = (TextView) convertView.findViewById(R.id.price);
                      convertView.setTag(holder);
                  }
                  else {
                      holder = (ViewHolderItem) convertView.getTag();
                  }
                  holder.name.setText(this.main.listdata.get(position).name);
                  holder.quantity.setText(this.main.listdata.get(position).quantity);
                  holder.price.setText(this.main.listdata.get(position).price);
                  return convertView;
              }
          }

}
