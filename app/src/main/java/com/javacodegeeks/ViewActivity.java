package com.javacodegeeks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Taison_Gary on 12/14/2016.
 */

public class ViewActivity extends Activity {

    private EditText areaTXT;
    private Button btnGet;
    private ProgressDialog progress;
    private ListView listview;
    ArrayList<HashMap<String, String>> listdata;
    ListAdapter adapter;
    String[] from;
    int[] to;
    public JSONArray listarray;
    public String[] mKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_layout);


        btnGet = (Button) findViewById(R.id.btnGet);
        listview = (ListView) findViewById(R.id.listview);
        listdata = new ArrayList<>();
        adapter = new HashMapAdapter(this, listdata, R.layout.listview, mKeys);



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

         /*  try {
                JSONArray listarray = new JSONArray(result);
               from = new String[]{"Name", "Quantity", "Price"};
               HashMap<String, String> value = new HashMap<>();

                to = new int[]{R.id.name, R.id.quantity, R.id.price};

                for (int i = 0; i < listarray.length(); i++) {
                    JSONObject j = listarray.getJSONObject(i);
                    String name = j.getString("Name");
                    String quan = j.getString("Quantity");
                    String pri = j.getString("Price");
                    value.put("Name", name);
                    value.put("Quantity", quan);
                    value.put("Price", pri);
                    listdata.add(value);
                    Log.e("ERROR", value.toString());
                }
                adapter = new SimpleAdapter(ViewActivity.this, listdata, R.layout.listview, from, to);
                listview.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("ERR", e.getMessage());
            }*/
            adapter = new HashMapAdapter(mKeys, listdata, R.layout.listview);
            listview.setAdapter(adapter);
            Log.e("test", result);
            progress.dismiss();

        }


    }
            public class HashMapAdapter extends BaseAdapter {
                private HashMap<String, String> mValue = new HashMap<String, String>();
                JSONArray listarray = new JSONArray();


                public HashMapAdapter(HashMap<String, String> value) {
                    mValue = value;
                    mKeys = mValue.keySet().toArray(new String[value.size()]);
                }

                    @Override
                    public int getCount () {
                        return mValue.size();
                    }

                    @Override
                    public Object getItem ( int position){
                        return mValue.get(mKeys[position]);
                    }
                    @Override
                    public long getItemId ( int arg0){
                        return arg0;
                    }
                    @Override
                    public View getView ( int pos, View convertView, ViewGroup parent){
                        String key = mKeys[pos];
                        String value = getItem(pos).toString();

                        TextView nam = (TextView) convertView.findViewById(R.id.name);
                        TextView quan = (TextView) convertView.findViewById(R.id.quantity);
                        TextView pri = (TextView) convertView.findViewById(R.id.price);

                        mValue.put("Name", value);
                        mValue.put("Quantity", value);
                        mValue.put("Price", value);
                        listdata.add(mValue);
                        return convertView;
                    }

 }


}
