package com.step.currencyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    final String CURRENCY_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    TextView tvContent;
    private JSONArray currencyArray;
    private ArrayList<Rate> rates = new ArrayList<>();
    private String contentBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tvContent);

        Button initButton = findViewById(R.id.btn_init);
        initButton.setOnClickListener((v) -> {
            new Thread(openUrl).start();
        });
    }

    private final Runnable displayRates = () -> {
        tvContent.setText("Rates count: " + rates.size());
    };

    private final Runnable displayContent = () -> {
        tvContent.setText(contentBuffer);
    };

    private final Runnable parseContent = () -> {
        try {
            currencyArray = new JSONArray(contentBuffer);
            rates.clear();
            for(int i = 0; i < currencyArray.length(); ++i){
                JSONObject obj = currencyArray.getJSONObject(i);

                Rate rate = new Rate(
                        obj.getInt("r030"),
                        obj.getString("txt"),
                        obj.getDouble("rate"),
                        obj.getString("cc"),
                        obj.getString("exchangedate")
                );
                rates.add(rate);
            }

            runOnUiThread(displayRates);

        } catch (JSONException e) {
            Log.e("parseContent: ", e.getMessage());
            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "Failed to parse JSON", Toast.LENGTH_LONG);
            });
        }
    };

    private final Runnable openUrl = () -> {
        try{
            InputStream stream = new URL(CURRENCY_URL).openStream();
            StringBuilder sb = new StringBuilder();
            int sym;
            while((sym = stream.read()) != -1)
                sb.append((char) sym);

            contentBuffer = new String(sb.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            runOnUiThread(parseContent);
        }
        catch (Exception ex){
            Log.e("loadCurrency()", ex.getMessage());
            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "Failed to load currency, try again later", Toast.LENGTH_SHORT).show();
            });
        }
    };

}