package com.tempconverter.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SharedPreferences preferences;
    private RadioButton CToF, FToC;
    private TextView history;
    private EditText inputEditText, outputEditText;
    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        CToF = findViewById(R.id.CToF);
        FToC = findViewById(R.id.FToC);
        
        preferences = getSharedPreferences("SELECTIONS", MODE_PRIVATE);
        String pref = preferences.getString("OPTION", "CELSIUSTOFAHR");
        
        if (pref.equals("CELSIUSTOFAHR"))
            CToF.setChecked(true);
        else
            FToC.setChecked(true);
        
        history = findViewById(R.id.history);
        history.setMovementMethod(new ScrollingMovementMethod());
        inputEditText = findViewById(R.id.inputEditText);
        outputEditText = findViewById(R.id.outputEditText);
        outputEditText.setFocusable(false);

        constraintLayout = (ConstraintLayout) findViewById(R.id.root);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
    }
    
    public void convertTemp(View v) {
        RadioGroup choices = findViewById(R.id.choices);
        int selected = choices.getCheckedRadioButtonId();
        RadioButton btn = findViewById(selected);
        history = findViewById(R.id.history);
        String historyString = history.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        
        switch (btn.getId()) {
            case R.id.CToF:
                String test = convertCtoF();
                history.setText(test + historyString);
                editor.putString("OPTION", "CELSIUSTOFAHR");
                break;
            case R.id.FToC:
                String test_1 = convertFtoC();
                history.setText(test_1 + historyString);
                editor.putString("OPTION", "FAHRTOCELSIUS");
                break;
            default:
                Log.d(TAG, "convertTemp: UNKNOWN");
                break;
        }
        editor.apply();
    }

    public String convertCtoF() {
        String CString = inputEditText.getText().toString();
        if (CString.trim().isEmpty())
            return "Empty\n";
        else {
            double Cels = Double.parseDouble(CString);
            double Fahr = (Cels * 9 / 5) + 32;
            String result = String.format("%.1f", Fahr);
            outputEditText.setText(result);
            String output = String.format("C to F: %.1f -> %.1f%n", Cels, Fahr);
            return output;
        }
    }

    public String convertFtoC() {
        String FString = inputEditText.getText().toString();
        if (FString.trim().isEmpty())
            return "Empty\n";
        else {
            double Fahr = Double.parseDouble(FString);
            double Cels = (Fahr - 32) * 5 / 9;
            String result = String.format("%.1f", Cels);
            outputEditText.setText(result);
            String output = String.format("F to C: %.1f -> %.1f%n", Fahr, Cels);
            return output;
        }
    }

    public void clearHistory(View v) {
        history.setText("");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("HISTORY", history.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        history.setText(savedInstanceState.getString("HISTORY"));
    }
}