package com.app.car;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Timer timer;
    private Handler handler;
    private TextView trendingTextView;
    private LinearLayout SellCar;
    private LinearLayout BrowseCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        if (username != null && !username.isEmpty()) {
            TextView textUserName = findViewById(R.id.textUserName);
            textUserName.setText(username);
        }

        trendingTextView = findViewById(R.id.Quotes);
        handler = new Handler();
        startTimer();

        SellCar = findViewById(R.id.layoutSell);
        SellCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SellCar.class);
                startActivity(intent);
            }
        });

        BrowseCar = findViewById(R.id.layoutBuy);
        BrowseCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BrowseCar.class);
                startActivity(intent);
            }
        });

        ImageView Profile = findViewById(R.id.ImageMenu);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileSetting.class);
                startActivity(intent);
            }
        });


    }

    private void startTimer() {
        timer = new Timer();

        // Schedule a task to run every 5 seconds
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Generate a random quote about cars
                String[] carQuotes = {
                        "The best car safety device is a rear-view mirror with a cop in it. - Dudley Moore",
                        "A dream without ambition is like a car without gas... you're not going anywhere. - Sean Hampton",
                        "Take care of your car in the garage, and the car will take care of you on the road. - Amit Kalantri",
                        "Cars bring me sheer joy. - Evan Spiegel",
                        "The car has become the carapace, the protective and aggressive shell, of urban and suburban man. - Marshall McLuhan",
                        "I have always been into cars. Cars are part of our genetic makeup. It's unavoidable. - Matthew Fox",
                        "To attract men, I wear a perfume called 'New Car Interior.' - Rita Rudner",
                        "Cars are the closest thing we will ever create to something that is alive. - Sir William Lyons",
                        "Cars are the sculptures of our everyday lives. - Chris Bangle",
                        "The cars we drive say a lot about us. - Alexandra Paul",
                        "Driving a fast car can make you feel like a superhero. - Unknown",
                        "A car is like a blank canvas; the possibilities are endless. - Unknown",
                    "The cars we drive say a lot about us. - Alexandra Paul",
                    "It's not just a car; it's someone's dream. - Unknown"
                        // Add more quotes here
                };
                final String randomQuote = getRandomQuote(carQuotes);

                // Update the quote on the UI thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        trendingTextView.setText(randomQuote);
                    }
                });
            }
        }, 0, 5000); // Change quote every 5 seconds (adjust the interval as needed)
    }

    private String getRandomQuote(String[] quotes) {
        Random random = new Random();
        int index = random.nextInt(quotes.length);
        return quotes[index];
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the timer when the activity is destroyed
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
