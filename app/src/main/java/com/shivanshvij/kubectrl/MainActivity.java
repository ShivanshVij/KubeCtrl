package com.shivanshvij.kubectrl;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private KubeController kubecontroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_nodes);

        this.kubecontroller = new KubeController("https://142.93.145.99:6443", "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLXB4cXZoIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyMDIyN2I4My03NjAxLTExZTktYTdhNC0wYWE4MDliMjI0NTQiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06YWRtaW4tdXNlciJ9.LBjffq_R443JGPR8jIkehlREdtFLye8Ecw8aU3dndOxsvAUAS0YGBkcxjvBQPdKzr1-KVGd73quckAg2GPJkb1ws0A0r6ZOVijC1hqkbuc3F7nyeu43PA8OoXcW_rnmZemR_YErIc2tbKVyrMHAg6sbz5yxM7L2MbwhPSjDVlsQKmKbJ6uwjlPPHKSavXVUUGlHgNJ4athzsLV2pQhfhxergB1M1XjlwQy9rH0fU2nteehclA2k25wWwNHPyphsPtpihobViZhL8p2KuH2DbSClSpa9r-IxGHa-DKHNG0wYymAXJ8gT80LyPocAD-sY0M3VlhPTlFlIpDdMg8A_31Q", false);

        this.nodeView();
    }


    private void clearVisibility(){
        ListView item0 = findViewById(R.id.nodelist);
        ListView item1 = findViewById(R.id.podslist);

        item0.setVisibility(View.GONE);
        item1.setVisibility(View.GONE);
    }

    private void nodeView(){
        ArrayList<String> NodeString = this.kubecontroller.getNodes_STRING();

        ListView simpleList = findViewById(R.id.nodelist);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, NodeString);
        simpleList.setAdapter(arrayAdapter);
        simpleList.setVisibility(View.VISIBLE);
    }

    private void podsView(){
        ArrayList<String> PodString = this.kubecontroller.getPods_STRING();

        ListView simpleList = findViewById(R.id.podslist);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, PodString);
        simpleList.setAdapter(arrayAdapter);
        simpleList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nodes) {
            this.clearVisibility();
            this.nodeView();

        } else if (id == R.id.nav_pods) {
            this.clearVisibility();
            this.podsView();

        } else if (id == R.id.nav_services) {
            this.clearVisibility();


        } else if (id == R.id.nav_ingress) {
            this.clearVisibility();


        } else if (id == R.id.nav_pv) {
            this.clearVisibility();


        } else if (id == R.id.nav_pvc) {
            this.clearVisibility();


        }
        else if (id == R.id.nav_app_settings){
            this.clearVisibility();


        }
        else if (id == R.id.nav_cluster_settings){
            this.clearVisibility();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
