package com.example.national_information;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_detail);

        String id = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             id = bundle.getString("id");
        }

        final List<ItemList> list_country = MainActivity.list;
        Log.i("res", String.valueOf(list_country));
        assert id != null;
        ItemList country = list_country.get(Integer.parseInt(id));

        TextView countryName = (TextView) findViewById(R.id.countryName);
        ImageView countryFlag = (ImageView) findViewById(R.id.countryFlag);
        TextView population = (TextView) findViewById(R.id.population);
        TextView areaKm = (TextView) findViewById(R.id.areaKm);
        ImageView countryMap = (ImageView) findViewById(R.id.countryMap);

        countryName.setText(country.getCountryName());
        new DownloadImageTask((ImageView) findViewById(R.id.countryFlag)).execute(country.getCountryFlag());
        population.setText(Double.toString(country.getPopulation()));
        areaKm.setText(Integer.toString((int) country.getAreaKm()));
        new DownloadImageTask((ImageView) findViewById(R.id.countryMap)).execute(country.getCountryMap());
    }
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
