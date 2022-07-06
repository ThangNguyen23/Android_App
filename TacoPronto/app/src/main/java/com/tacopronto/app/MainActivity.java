package com.tacopronto.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.telephony.SmsManager;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//    Button button;
//    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    static String s;
    int cost = 0;
    private static final int PERMISSION_REQUEST = 1;
    private Button button1, button2;
    private RadioButton sizeLarge, sizeMedium, tortillaCorn, tortillaFlourl;
    private CheckBox beef, chicken, whiteFish, cheese, seaFood, rice, beans, picoDeGallo, guacamole, lbt;
    private CheckBox soda, cerveza, margarita, tequila;
    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

//        button = findViewById(R.id.button);
//        button.setEnabled(false);
//        if (checkPermisson(Manifest.permission.SEND_SMS)) {
//            button.setEnabled(true);
//        }
//        else {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
//        }

//        Method 1
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("5566", null, "I WANT A BIG TACO - sms message", null, null);

//        Method 2
//        String phoneNumer = "5566";
//        String message = "I WANT A BIG TACO - sms message";
//        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms: " + phoneNumer));
//        intent.putExtra("sms_body", message);
//        startActivity(intent);
    }

    public void init() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://goo.gl/maps/ZPW71dxoMERT1T4h9";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        sizeLarge = (RadioButton) findViewById(R.id.radioButton1);
        sizeMedium = (RadioButton) findViewById(R.id.radioButton2);
        tortillaCorn = (RadioButton) findViewById(R.id.radioButton3);
        tortillaFlourl = (RadioButton) findViewById(R.id.radioButton4);

        beef = (CheckBox) findViewById(R.id.checkBox1);
        chicken = (CheckBox) findViewById(R.id.checkBox2);
        whiteFish = (CheckBox) findViewById(R.id.checkBox3);
        cheese = (CheckBox) findViewById(R.id.checkBox4);
        seaFood = (CheckBox) findViewById(R.id.checkBox5);
        rice = (CheckBox) findViewById(R.id.checkBox6);
        beans = (CheckBox) findViewById(R.id.checkBox7);
        picoDeGallo = (CheckBox) findViewById(R.id.checkBox8);
        guacamole = (CheckBox) findViewById(R.id.checkBox9);
        lbt = (CheckBox) findViewById(R.id.checkBox10);

        soda = (CheckBox) findViewById(R.id.checkBox11);
        cerveza = (CheckBox) findViewById(R.id.checkBox12);
        margarita = (CheckBox) findViewById(R.id.checkBox13);
        tequila = (CheckBox) findViewById(R.id.checkBox14);

        constraintLayout = (ConstraintLayout) findViewById(R.id.root);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
    }

    private void sizeRadioCheck() {
        if (sizeLarge.isChecked()) {
            cost += 5;
            s += "# Pizza Size: \n+ Large\n";
        }
        else if (sizeMedium.isChecked()) {
            cost += 3;
            s += "# Pizza Size: \n+ Medium\n";
        }
    }

    private void tortillaRadioCheck() {
        if (tortillaCorn.isChecked()) {
            cost += 2;
            s += "# Tortilla: \n+ Corn\n";
        }
        else if (tortillaFlourl.isChecked()) {
            cost += 1;
            s += "# Tortilla: \n+ Flourl\n";
        }
    }

    private void fillingsCheck() {
        s += "# Fillings: \n";
        if (beef.isChecked()) {
            cost += 2;
            s += "+ Beef \n";
        }
        if (chicken.isChecked()) {
            cost += 1;
            s += "+ Chicken \n";
        }
        if (whiteFish.isChecked()) {
            cost += 1;
            s += "+ White Fish \n";
        }
        if (cheese.isChecked()) {
            cost += 1;
            s += "+ Cheese \n";
        }
        if (seaFood.isChecked()) {
            cost += 3;
            s += "+ Seafood \n";
        }
        if (rice.isChecked()) {
            cost += 2;
            s += "+ Rice \n";
        }
        if (beans.isChecked()) {
            cost += 2;
            s += "+ Beans \n";
        }
        if (picoDeGallo.isChecked()) {
            cost += 1;
            s += "+ Pico de Gallo \n";
        }
        if (guacamole.isChecked()) {
            cost += 1;
            s += "+ Guacamole \n";
        }
        if (lbt.isChecked()) {
            cost += 2;
            s += "+ LBT \n";
        }
    }

    private void beverageCheck() {
        s += "# Beverage: \n";
        if(soda.isChecked()) {
            cost += 0.2;
            s += "+ Soda \n";
        }
        if(cerveza.isChecked()) {
            cost += 0.1;
            s += "+ Cerveza \n";
        }
        if(margarita.isChecked()) {
            cost += 0.2;
            s += "+ Margarita \n";
        }
        if(tequila.isChecked()) {
            cost += 0.1;
            s += "+ Tequila \n";
        }
    }

    @Override
    public void onClick(View v) {
        sizeRadioCheck();
        tortillaRadioCheck();
        fillingsCheck();
        beverageCheck();

        String phoneNumber = "+1-555-521-5556";
        String total = Double.toString(cost);
        s += "# Total Cost: ";
        s += total;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", " permission to SEND_SMS denied, permission being requested");
                String[] permission = {Manifest.permission.SEND_SMS};
                requestPermissions(permission, PERMISSION_REQUEST);
            }
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, s, null, null);
            Toast.makeText(this, "Message Sent !", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permission Denied !", Toast.LENGTH_SHORT).show();
        }
        cost = 0;
        s = "";

//        Details
        Intent intent = new Intent(MainActivity.this, tacopronto.class);
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, Uri.parse("https://tacopronto.net/"));
        startActivity(intent);
    }

//    public void onSend(View v) {
//        String phoneNumber = "55665566";
//        String smsMessage = "I WANT A BIG TACO - sms message";
//
//        if (checkPermisson(Manifest.permission.SEND_SMS)) {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNumber, null, smsMessage, null, null);
//            Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public boolean checkPermisson(String permission) {
//        int check = ContextCompat.checkSelfPermission(this, permission);
//        return (check == PackageManager.PERMISSION_GRANTED);
//    }
}