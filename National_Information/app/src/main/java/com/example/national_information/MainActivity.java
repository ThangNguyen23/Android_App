package com.example.national_information;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    public static List<ItemList> list;
    final List<ItemList> list_country = getListCountry();
    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = (ConstraintLayout) findViewById(R.id.root);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


        list = list_country;
        final ListView listView = (ListView) findViewById(R.id.listCountry);

        CustomListAdapter adapter = new CustomListAdapter(this, list_country);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0)
                {
                    ItemList country = list_country.get(position);

                    Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
                    detailIntent.putExtra("id", String.valueOf(position));

                    startActivity(detailIntent);
                }
            }
        });

//        Change color of statusbar
//        if (Build.VERSION.SDK_INT >= 21) {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.colorCustom));
//        }
    }

    public List<ItemList> getListCountry() {

        final List<ItemList> items = new ArrayList<ItemList>();

        ItemList item = new ItemList("Country Code", "Country Name", "Capital", 0, 0);
        items.add(item);

        AsyncHttpClient client = new AsyncHttpClient();
        GeoNameClient.get("?formatted=true&lang=en&username=boykunis&style=full",null ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject response = new JSONObject(new String(responseBody));

                    String countryCode = "", countryName = "", capital = "", currencyCode = "";
                    int population = 0;
                    double areaInSqKm = 0;

                    JSONArray result = response.getJSONArray("geonames");

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject object = result.getJSONObject(i);
                        countryCode = object.getString("countryCode");
                        countryName = object.getString("countryName");
                        capital = object.getString("capital");
                        population = object.getInt("population");
                        areaInSqKm = object.getDouble("areaInSqKm");

                        ItemList item = new ItemList(countryCode, countryName, capital, population, areaInSqKm);
                        items.add(item);
                    }

                    final ListView listView = (ListView) findViewById(R.id.listCountry);

                    CustomListAdapter adapter = new CustomListAdapter(MainActivity.this, list_country);

                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }
        });

        return items;
    }
}

class GeoNameClient {
    private static final String BASE_URL = "http://api.geonames.org/countryInfoJSON";
//    private static final String BASE_URL = "http://api.geonames.org/countryInfoJSON?username=jandragon113";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}

