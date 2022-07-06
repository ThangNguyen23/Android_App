package com.example.exchange_money;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.R.layout.simple_spinner_dropdown_item;

public class MainActivity extends AppCompatActivity {
    Button button;
    ProgressBar progressBar;
    Spinner spinner1, spinner2;
    EditText editText1, editText2;
    ListView listView;
    ArrayList<Convert> listDescription;
    ListViewAdapter listViewAdapter;
    ConstraintLayout constraintLayout;
    AnimationDrawable animationDrawable;

    String url = "https://vnd.fxexchangerate.com/rss.xml";

    List<String> currency_type_list, description_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = (ConstraintLayout) findViewById(R.id.root);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        init();
        
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setIndeterminateDrawable(new Wave());

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position1 = spinner1.getSelectedItemPosition();
                int position2 = spinner2.getSelectedItemPosition();
                spinner2.setSelection(position1);
                spinner1.setSelection(position2);
            }
        });

        if (isNetworkAvailable(this))
            new AsyncTaskGetXML().execute(url);
        else
            Toast.makeText(this, "No Internet Available !!!", Toast.LENGTH_LONG).show();
    }

    private void init() {
        button = findViewById(R.id.button);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        progressBar = findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listView);
    }
    
    public float getValue(String s) {
        String arr[] = s.split("\\=");
        String arr_1[] = arr[1].trim().split("\\s");
        float result = Float.parseFloat(arr_1[0]);
        return result;
    }

    public String getNameCountry(String s) {
        if (s.contains("(") && s.contains(")")) {
            s = s.replace("(", " ");
            s = s.replace(")", "");
        }
        String[] arr = s.split("\\s");
        return arr[0];
    }

    public String getCodeCurrency(String s) {
        String[] arr = s.split("\\s");
        String s1 = arr[arr.length - 1];
        if (s1.contains("(") && s1.contains(")")) {
            s1 = s1.replace("(", " ");
            s1 = s1.replace(")", "");
        }
        String[] s2 = s1.split("\\s");
        return s2[s2.length - 1];
    }
    
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    
    public class AsyncTaskGetXML extends AsyncTask<String, Void, String> {
        String xml = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null)
                    stringBuilder.append(line + "\n");
                xml = stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                Log.d("a", e.toString() + "sa");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("a", e.toString() + "2");
                e.printStackTrace();
            }
            return xml;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.VISIBLE);

            XMLParser xmlDomParser = new XMLParser();
            Document document = xmlDomParser.getDocument(s);
            
            NodeList nodeList = document.getElementsByTagName("item");
            
            String x = "";
            String time = null;
            
            currency_type_list = new ArrayList<>();
            description_list = new ArrayList<>();
            listDescription = new ArrayList<>();
            
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element = (Element) nodeList.item(i);
                String title = xmlDomParser.getValue(element, "title");
                String main = title.replace("Vietnam Dong(VND)/", "");

                time = xmlDomParser.getValue(element, "pubDate");

                String decription = xmlDomParser.getValue(element, "description");
                
                description_list.add(decription);
                currency_type_list.add(main);
                listDescription.add(new Convert(decription));
                listViewAdapter = new ListViewAdapter(listDescription);
                listView.setAdapter(listViewAdapter);
            }

            if (spinner1.getSelectedItem() == null)
                getDataFromSpinner(currency_type_list);
            
            Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();
            
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String edit = editText1.getText().toString();
                    int temp = Integer.parseInt(edit);
                    String code2 = (String) spinner2.getSelectedItem();
                    
                    for (int i = 0; i < description_list.size(); ++i) {
                        if (description_list.get(i).contains(getNameCountry(code2))) {
                            float value = getValue(description_list.get(i));
                            float result = temp * value;
                            editText2.setText(result + " ");
                            break;
                        }
                    }
                    if (spinner1.getSelectedItem().toString() == code2)
                        editText2.setText(1 + "");
                }
            });
        }
    }

    private void getDataFromSpinner(List<String> data_list) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), simple_spinner_dropdown_item, data_list);
        spinner1.setAdapter(arrayAdapter);
        spinner2.setAdapter(arrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = getCodeCurrency(currency_type_list.get(position));
                url = "https://" + code.toLowerCase() + ".fxexchangerate.com/rss.xml";

                if(isNetworkAvailable(getApplicationContext()))
                    new AsyncTaskGetXML().execute(url);
                else
                    Toast.makeText(MainActivity.this, "No Internet Access!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}


