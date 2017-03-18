package com.example.dario.euro2016;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityFragment.Communication, PartiteFragment.Communication {

    private final String TAG = MainActivity.class.getSimpleName();
    private boolean isTabletLayout = false;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState==null) {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Intent intent = new Intent(this, EuroIntentService.class);
                intent.putExtra(EuroIntentService.REQUEST_TYPE, EuroIntentService.TYPE_TORNEO);
                startService(intent);
                intent.putExtra(EuroIntentService.REQUEST_TYPE, EuroIntentService.TYPE_PARTITE);
                startService(intent);
                intent.putExtra(EuroIntentService.REQUEST_TYPE, EuroIntentService.TYPE_CLASSIFICHE);
                startService(intent);
            }
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getSupportFragmentManager();

        //Il layout con id detailRoot c'è solo sul layout per tablet
        if(findViewById(R.id.detailRoot)!=null)
            isTabletLayout=true;

        //se ho creato l'activity per la prima volta e non sono su tablet, uso addToBackStack per far gestire al
        //fragmentmanager la pressione di back e il ripristino del fragment alla rotazione
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        if(savedInstanceState==null) {
            if(!isTabletLayout)
                fm.beginTransaction().replace(R.id.fragment, mainActivityFragment).addToBackStack(mainActivityFragment.TAG).commit();
            else {
                //sono su tablet, quindi non aggiungo il fragment al backstack
                fm.beginTransaction().replace(R.id.fragment, mainActivityFragment).commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("fragment", 1);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onItemChoosed(long id) {

        DetailPartiteActivityFragment partiteDetailFragment = DetailPartiteActivityFragment.newInstance(id);

        if(isTabletLayout) {
            fm.beginTransaction().replace(R.id.detailRoot, partiteDetailFragment).commit();
        }
        else {
            Intent intent = new Intent(this, DetailPartiteActivity.class);

            intent.putExtra(Contract.Partite.COLUMN_ID_API_PARTITA,id);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if(fm.getBackStackEntryCount()==0)
            finish();
        else {
            getFragmentManager().popBackStack();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        Class fragmentClass = null;

        int id = item.getItemId();

        Bundle bundle = new Bundle();

        if (id == R.id.home) {
            fragmentClass = MainActivityFragment.class;
        } else if (id == R.id.partite) {
            fragmentClass = PartiteFragment.class;
        } else if (id == R.id.classifiche) {
            fragmentClass = ClassificheFragment.class;
        } else if (id == R.id.info_torneo) {
            bundle.putLong(Contract.Torneo.COLUMN_ID_API, 424);
            fragmentClass = TorneoFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).addToBackStack(fragmentClass.getSimpleName()).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);

        // Set action bar title

        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
