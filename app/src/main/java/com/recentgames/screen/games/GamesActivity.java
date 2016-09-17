package com.recentgames.screen.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.recentgames.R;
import com.recentgames.screen.search.SearchActivity;

public class GamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_games, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_search:
                //mPresenter.onMenuSearchClick();
                openSearchActivity();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void openSearchActivity() {
        startActivity(new Intent(this, SearchActivity.class));
    }

}
