package com.travismosley.armadafleetadmiral.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;

import com.travismosley.android.ui.Gravity;
import com.travismosley.armadafleetadmiral.R;
import com.travismosley.armadafleetadmiral.fragment.MainActivityFragment;
import com.travismosley.armadafleetadmiral.fragment.selector.FleetSelectorFragment;
import com.travismosley.armadafleetadmiral.game.Fleet;

public class MainActivity extends AppCompatActivity
    implements MainActivityFragment.OnFleetSelectorRequestedListener,
        FleetSelectorFragment.OnFleetBuilderRequestedListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment mainFrag = new MainActivityFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_root, mainFrag)
                    .commit();
        }
    }

    private void launchFleetBuilder(Fleet fleet){
        Intent intent = new Intent(this, FleetBuilderActivity.class);
        intent.putExtra(getString(R.string.key_fleet), fleet);
        startActivity(intent);
    }

    public void onFleetBuilderRequested(Fleet fleet){
        launchFleetBuilder(fleet);
    }

    public void onFleetListRequested(int factionId){
        transitionToFragment(FleetSelectorFragment.newInstance(factionId));
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void transitionToFragment(Fragment fragment){
        transitionToFragment(fragment, new Slide(Gravity.start()));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void transitionToFragment(Fragment fragment, Transition transition){

        // Replace the current fragment
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_root, fragment)
                .addToBackStack(null)
                .commit();

        fragment.setEnterTransition(transition);
        fragment.setReturnTransition(transition);
        fragment.setExitTransition(transition);
        fragment.setReenterTransition(transition);
        fragment.setAllowEnterTransitionOverlap(false);
        fragment.setAllowReturnTransitionOverlap(false);
    }
}
