package com.example.lab_4;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton button;
    private TextView qty;
    private TextView price;
    private int revenue = 0;
    private int dessertsSold = 0;

    public static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate() called");

        if(savedInstanceState == null){
            Log.d(TAG, "App started");
        }

        button = findViewById(R.id.dessert_button);
        qty = findViewById(R.id.amount_sold_text);
        price = findViewById(R.id.revenue_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dessertsSold++;
                revenue += currentDessert.price;
                qty.setText("" + dessertsSold);
                price.setText("" + revenue);

                showCurrentDessert();
            }
        });

        // Make sure the correct dessert is showing
        button.setImageResource(currentDessert.getImgId());


        // get the value from the onSavedInstanceState
        // restore the values from the bundle
        if (savedInstanceState != null){
            dessertsSold = savedInstanceState.getInt("COUNTER");
            revenue = savedInstanceState.getInt("AMT");
            qty.setText(""+ dessertsSold);
            price.setText(""+ revenue);

        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called");
        outState.putInt("COUNTER", dessertsSold);
        outState.putInt("AMT", revenue);
    }


    public static class Dessert{
        public final int imgId;
        public final int price;
        public final int startProductionAmount;


        public Dessert(int imgId, int price, int startProductionAmount) {
            this.imgId = imgId;
            this.price = price;
            this.startProductionAmount = startProductionAmount;
        }


        public int getImgId() {
            return imgId;
        }

        public int getPrice() {
            return price;
        }

        public int getStartProductionAmount() {
            return startProductionAmount;
        }
    }


    // Creates a list of all Desserts
    private final List<Dessert> allDesserts = new ArrayList<Dessert>(){{
        add(new Dessert(R.drawable.cupcake, 5, 0));
        add(new Dessert(R.drawable.donut, 10, 5));
        add(new Dessert(R.drawable.eclair, 15, 20));
        add(new Dessert(R.drawable.froyo, 30, 50));
        add(new Dessert(R.drawable.gingerbread, 50, 100));
        add(new Dessert(R.drawable.honeycomb, 100, 200));
        add(new Dessert(R.drawable.icecreamsandwich, 500, 500));
        add(new Dessert(R.drawable.jellybean, 1000, 1000));
        add(new Dessert(R.drawable.kitkat, 2000, 2000));
        add(new Dessert(R.drawable.lollipop, 3000, 4000));
        add(new Dessert(R.drawable.marshmallow, 4000, 8000));
        add(new Dessert(R.drawable.nougat, 5000, 16000));
        add(new Dessert(R.drawable.oreo, 6000, 20000));
    }};

    private Dessert currentDessert = allDesserts.get(0);


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() called");

    }



    //    Determine which desserts to show
    private void showCurrentDessert() {
        Dessert newDessert = allDesserts.get(0);
        for (Dessert dessert : allDesserts) {
            if (dessertsSold >= dessert.getStartProductionAmount()) {
                newDessert = dessert;
            } else {
                break;
            }
        }
        if (!newDessert.equals(currentDessert)) {
            currentDessert = newDessert;
            button.setImageResource(newDessert.getImgId());
        }
    }



    // partially hide the activity
    private void onShare() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText(getString(R.string.share_text, dessertsSold, revenue))
                .setType("text/plain")
                .getIntent();
        try {
            startActivity(shareIntent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                onShare();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}