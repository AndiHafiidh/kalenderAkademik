package com.example.e5471.akademikub;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Jadwal> jadwalAkademik = new ArrayList<Jadwal>();
    private ArrayList<Jadwal> eventAkademik = new ArrayList<Jadwal>();

    private ProgressDialog progressDialog;
    private String isiJSON;
    private ListView listEvent;
    //private ListView listTodayEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listEvent = (ListView) findViewById(R.id.listEvent);
        //listTodayEvent = (ListView) findViewById(R.id.todayEvent);

        //Calendar c = Calendar.getInstance();

        /*SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
        String formattedDate = df.format(c.getTime());*/


        if (isNetworkAvailable() == true){
            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
            readStream("http://192.168.1.103");
            //readStreamToday("http://192.168.1.103?tgl="+formattedDate);

        }else{
            Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
        }


    }

    private void readStream(String urlStr) {
        progressDialog = ProgressDialog.show(this,"","Loading...");
        final String url = urlStr;

        new Thread(){
            public void run(){
                InputStream in = null;

                Message msg = Message.obtain();
                msg.what = 1;

                try{
                    StringBuilder sb = new StringBuilder();
                    in = openHttpConnection(url);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    String nextLine = "";
                    while((nextLine = reader.readLine()) != null){
                        sb.append(nextLine);
                        isiJSON = sb.toString();
                        in.close();
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }

                messageHandler.sendMessage(msg);
            }
        }.start();
    }

   /* private void readStreamToday(String urlStr) {
        progressDialog = ProgressDialog.show(this,"","Loading...");
        final String url = urlStr;

        new Thread(){
            public void run(){
                InputStream in = null;

                Message msg = Message.obtain();
                msg.what = 1;

                try{
                    StringBuilder sb = new StringBuilder();
                    in = openHttpConnection(url);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    String nextLine = "";
                    while((nextLine = reader.readLine()) != null){
                        sb.append(nextLine);
                        isiJSON = sb.toString();
                        in.close();
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }

                messageHandler2.sendMessage(msg);
            }
        }.start();
    }
*/

    private Handler messageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (!isiJSON.equals("null")){
                try {
                    JSONArray semuaData = new JSONArray(isiJSON);
                    for (int i = 0; i < semuaData.length(); i++ ){
                        JSONObject object = semuaData.getJSONObject(i);
                        jadwalAkademik.add(new Jadwal(object.getString("tanggal"), object.getString("judul"), object.getString("keterangan")));

                    }

                    CustomAdapter myadapter = new CustomAdapter(getApplicationContext(),jadwalAkademik);
                    listEvent.setAdapter(myadapter);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            /*semuaData = new JSONArray(isiJSON);*/
            progressDialog.dismiss();
        }
    };

    /*private Handler messageHandler2 = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (!isiJSON.equals("null")){
                try {
                    JSONArray semuaData = new JSONArray(isiJSON);
                    for (int i = 0; i < semuaData.length(); i++ ){
                        JSONObject object = semuaData.getJSONObject(i);
                        eventAkademik.add(new Jadwal(object.getString("tanggal"), object.getString("judul"), object.getString("keterangan")));
                    }

                    CustomAdapter myadapter = new CustomAdapter(getApplicationContext(),eventAkademik);
                    listTodayEvent.setAdapter(myadapter);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            *//*semuaData = new JSONArray(isiJSON);*//*
            progressDialog.dismiss();
        }
    };*/

    public boolean isNetworkAvailable(){
        ConnectivityManager connect = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connect.getActiveNetworkInfo();

        if (info != null && info.isConnected()){
            /*Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_LONG).show();*/
            return true;
        }else {
            /*Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_LONG).show();*/
            return false;
        }
    }

    private InputStream openHttpConnection(String urlStr){
        InputStream in = null;
        int resCode = -1;

        try{
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)){
                throw new IOException("URL is not an Http URL");
            }

            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK){
                in = httpConn.getInputStream();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return in;
    }


}
