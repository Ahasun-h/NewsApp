package com.example.newsapp.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.newsapp.Fragment.All;
import com.example.newsapp.Fragment.Business;
import com.example.newsapp.Fragment.Popular;
import com.example.newsapp.Fragment.TechCrunch;
import com.example.newsapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    FrameLayout viewPager;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    //FrameLayout fragmentContiner;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    All nAll;
    Business Business;
    Popular Popular;
    TechCrunch nTechCrunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);



        ((AppCompatActivity) MainActivity.this).getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, new All()).commit();

        //          Test Code  For  Tab Lay Out
        TabLayout.Tab All = tabLayout.newTab();
        All.setText("All");
        All.setIcon(R.drawable.ic_blog);
        tabLayout.addTab(All);

        TabLayout.Tab business = tabLayout.newTab();
        business.setText("Business");
        business.setIcon(R.drawable.ic_broadcast);
        tabLayout.addTab(business);

        TabLayout.Tab popular = tabLayout.newTab();
        popular.setText("Popular");
        popular.setIcon(R.drawable.ic_magazine);
        tabLayout.addTab(popular);

        TabLayout.Tab TechCrunch = tabLayout.newTab();
        TechCrunch.setText("TechCrunch");
        TechCrunch.setIcon(R.drawable.ic_tablet);
        tabLayout.addTab(TechCrunch);


        // perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
       // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new All();
                        break;
                    case 1:
                        fragment = new Business();
                        break;

                    case 2:
                        fragment = new Popular();
                        break;

                    case 3:
                        fragment = new TechCrunch();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.viewPager, fragment);
                ft.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });

        //Navigation Drawer

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        nAll = new All();
        Business = new Business();
        Popular = new Popular();
        nTechCrunch = new TechCrunch();


       // NavController navController = Navigation.findNavController( this, R.id.viewPager );
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.all:
                InitialFragment(nAll);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.business:
                InitialFragment(Business);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.popular:
                InitialFragment(Popular);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.techCrunch:
                InitialFragment(nTechCrunch);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            default:
                break;
        }
        return false;
    }

    public void  InitialFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.viewPager,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

}
