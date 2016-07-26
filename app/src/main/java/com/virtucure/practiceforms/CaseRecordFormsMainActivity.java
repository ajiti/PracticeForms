package com.virtucure.practiceforms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

/**
 * Created by AJITI on 20-Jul-16.
 */
public class CaseRecordFormsMainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentTransaction transaction;
    private static final String TAG = CaseRecordFormsMainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.case_record_forms_frame_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Existing Case Records");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setUpNavDrawerBody(navigationView);

        transaction = getSupportFragmentManager().beginTransaction();
        if(savedInstanceState == null){
            selectDrawerItem(navigationView.getMenu().findItem(R.id.nav_case_record));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpNavDrawerBody(NavigationView navView) {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Class className = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
//                className = RecyclerViewHomeFragment.class;
                break;

            case R.id.nav_case_record:
                className = "".getClass();

            case R.id.inbox:
//                className = RecyclerViewHomeFragment.class;
                break;
            default:
//                className = RecyclerViewHomeFragment.class;
        }
        try {
            if(!item.isChecked()) {
                fragment = (Fragment) className.newInstance();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, fragment).commit();
                item.setChecked(true);
                setTitle(item.getTitle());
            }
            drawerLayout.closeDrawers();
        } catch (Exception e) {
            Log.e(TAG, "Drawer Item Menu", e);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
