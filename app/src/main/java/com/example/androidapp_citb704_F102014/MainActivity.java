package com.example.androidapp_citb704_F102014;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.androidapp_citb704_F102014.Data.CategoryData;
import com.example.androidapp_citb704_F102014.Helpers.DataBaseHelper;
import com.example.androidapp_citb704_F102014.Helpers.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final String TAG = MainActivity.class.getSimpleName();
    private final String DATA_URL = "https://gist.githubusercontent.com/sarabadjieva/7fdddf1fc6245394a711778ca3416267/raw/fa3661718cb788427dbbfe89c37375dd193361e2/ItemsList.js";
    public static final String CATEGORIES_DATA = "CATEGORIES_DATA";

    //tasks
    private ArrayList<CategoryData> categoriesData;
    private final long delay = 2000;
    private long startTime;
    private long endTime;
    private int tasksCounter = 0;

    private class GetItems extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            incrementTasks();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(DATA_URL);

            categoriesData = new ArrayList<>();
            //Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray categories = jsonObj.getJSONArray("items");

                    for (int i = 0; i < categories.length(); i++){
                        JSONObject category = categories.getJSONObject(i);
                        categoriesData.add(new CategoryData(category));
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            decrementTasks();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTime = System.currentTimeMillis();

        new GetItems().execute();

        Thread thread = new Thread(
                new Runnable() {

            @Override
            public void run() {
                DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
                dbHelper.readFavoritesDB();
                dbHelper.readCartDB();
                dbHelper.close();
                decrementTasks();
            }
        });
        thread.start();
        incrementTasks();
    }

    private void incrementTasks() {
        tasksCounter++;
    }

    private void decrementTasks() {
        tasksCounter--;
        if (tasksCounter == 0)
        {
            endTime = System.currentTimeMillis();

            if (endTime - startTime < delay)
            {
                new Handler().postDelayed(this::startHomeActivity, delay - (endTime - startTime));
            }
            else
            {
                startHomeActivity();
            }
        }
    }

    private void startHomeActivity()
    {
        //explicit intent
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putParcelableArrayListExtra(CATEGORIES_DATA, categoriesData);
        startActivity(intent);
        finish();
    }
}