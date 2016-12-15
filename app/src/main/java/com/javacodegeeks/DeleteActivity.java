package com.javacodegeeks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Taison_Gary on 12/14/2016.
 */

public class DeleteActivity extends Activity{

    private EditText idTXT;
    private Button btnGet;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_layout);


        idTXT = (EditText) findViewById(R.id.idTXT);
        btnGet = (Button) findViewById(R.id.btnGet);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = new ProgressDialog(view.getContext());
                progress.setMessage("Deleting id:"+idTXT.getText());
                progress.setIndeterminate(true);

                new getRequest().execute(idTXT.getText().toString());
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
                URL url = new URL("http://taisondigital.com.ph/testforyou/delete-product/"+ arg0[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                InputStream os = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(os));

                String line;
                while((line = reader.readLine()) != null){
                    result.append(line);
                }
                os.close();
                reader.close();
                return result.toString();
            }
            catch (Exception e) {
                return new String("Exception: " + e.getMessage());

            }

        }

        @Override
        protected void  onPostExecute(String result) {
            progress.dismiss();
            Toast.makeText(getApplicationContext(), "Successfully deleted id :" + idTXT.getText(), Toast.LENGTH_LONG).show();
            Log.e("test", result);
            idTXT.setText("");

        }


    }
}
