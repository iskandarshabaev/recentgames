package com.recentgames.screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.recentgames.GamesType;
import com.recentgames.R;
import com.recentgames.router.impl.GamesRouterImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GamesRouterImpl(getSupportFragmentManager()).navigateToGames(GamesType.WEEK);
    }

}
