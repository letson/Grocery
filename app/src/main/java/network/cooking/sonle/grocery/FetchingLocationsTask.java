package network.cooking.sonle.grocery;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sonle on 4/5/16.
 */
// get suggestion place from Google place web api
public class FetchingLocationsTask extends AsyncTask<String, Void, String> {
    private final Context context;
    private String locationName;
    public AsyncResponse delegate = null;
    private ProgressDialog mDialog;
    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
    }
    public FetchingLocationsTask(Context c, AsyncResponse delegate){
        this.context = c;
        this.delegate = delegate;
        mDialog = new ProgressDialog(context);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog.setMessage("Loading...");
        mDialog.show();
    }
    @Override
    protected String doInBackground(String... params) {
        try{
            locationName = params[0];
            String queryURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=33.8842914,-117.3207917&radius=2500&name="+locationName+"&key=AIzaSyAoB_Q0nRCZEkzRV7atDztbnDkSTNTdZMY";
            URL url = new URL(queryURL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            StringBuilder stringBuilder = new StringBuilder();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                // bridge than convert(read and decode to bytes) bytes to characters using charset
                InputStreamReader streamReader = new InputStreamReader(conn.getInputStream());
                // read text from character input stream
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                // Closes the stream and releases any system resources associated with it.
                bufferedReader.close();
                Log.d("Grocery", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("Grocery", conn.getResponseMessage());
            }
        } catch (IOException x) {
            Log.e("Grocery", "Error sending param", x);
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}