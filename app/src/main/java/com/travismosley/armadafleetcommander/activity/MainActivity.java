package com.travismosley.armadafleetcommander.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.travismosley.armadafleetcommander.R;
import com.travismosley.armadafleetcommander.fragment.MainActivityFragment;

public class MainActivity extends AppCompatActivity
    implements MainActivityFragment.OnFleetBuilderRequestedCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void launchFleetBuilder(int factionId){
        Intent intent = new Intent(this, FleetBuilderActivity.class);
        intent.putExtra("KEY_FACTION_ID", factionId);
        this.startActivity(intent);
    }

    public void onFleetBuilderRequested(int factionId){
        launchFleetBuilder(factionId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
