package com.shivanshvij.kubectrl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private KubeController kubecontroller;
    private SharedPreferences ClusterPreferences;
    private SharedPreferences.Editor ClusterPreferencesEditor;

    private NavigationView navigationView;

    private Boolean FirstRun = false;
    private ArrayList<String> Namespaces;

    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    FrameLayout progressBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        this.navigationView.setNavigationItemSelectedListener(this);

        this.ClusterPreferences = getSharedPreferences("ClusterPreferences", Context.MODE_PRIVATE);
        this.ClusterPreferencesEditor = ClusterPreferences.edit();

        String HOSTPATH = this.ClusterPreferences.getString("HOSTPATH", "");
        String BEARER = this.ClusterPreferences.getString("BEARER", "");

        if(!HOSTPATH.equalsIgnoreCase("") && !BEARER.equalsIgnoreCase("")){
            System.out.println(HOSTPATH);
            System.out.println(BEARER);
            this.kubecontroller = new KubeController(HOSTPATH, BEARER, false);

            this.navigationView.setCheckedItem(R.id.nav_nodes);

            Toast.makeText(getApplicationContext(),"Getting Nodes",Toast.LENGTH_SHORT).show();
            this.clearVisibility();

            this.nodeView();
            Toast.makeText(getApplicationContext(),"Nodes successfully retrieved",Toast.LENGTH_LONG).show();
        }
        else{
            this.navigationView.setCheckedItem(R.id.nav_cluster_settings);

            this.FirstRun = true;
            Toast.makeText(getApplicationContext(),"Please set host path and bearer token for cluster",Toast.LENGTH_LONG).show();
            this.clearVisibility();
            this.settingsClusterView();
        }

//        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);

        this.Namespaces = this.kubecontroller.getNamespaces_STRING();
        Spinner namespaces_spinner = findViewById(R.id.namespaces_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Namespaces);
        namespaces_spinner.setAdapter(adapter);

        namespaces_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearVisibility();
                podsView();

                Toast.makeText(getApplicationContext(),"Pods successfully retrieved",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void clearVisibility(){
        FrameLayout progressOverlay = findViewById(R.id.progress_overlay);
        progressOverlay.setVisibility(View.VISIBLE);

        ListView item0 = findViewById(R.id.nodelist);
        LinearLayout item1 = findViewById(R.id.podslist_layout);
        LinearLayout item2 = findViewById(R.id.cluster_settings_layout);

        item0.setVisibility(View.GONE);
        item1.setVisibility(View.GONE);
        item2.setVisibility(View.GONE);
    }

    private void nodeView(){
        ArrayList<String> NodeString = this.kubecontroller.getNodes_STRING();

        ListView simpleList = findViewById(R.id.nodelist);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, NodeString);
        simpleList.setAdapter(arrayAdapter);
        simpleList.setVisibility(View.VISIBLE);

        FrameLayout progressOverlay = findViewById(R.id.progress_overlay);
        progressOverlay.setVisibility(View.GONE);
    }

    private void podsView(){
        Spinner namespaces_spinner = findViewById(R.id.namespaces_spinner);
        ArrayList<String> PodString = this.kubecontroller.getPodsNamespaces_STRING(namespaces_spinner.getSelectedItem().toString());

        ListView simpleList = findViewById(R.id.podslist);
        LinearLayout linearLayout = findViewById(R.id.podslist_layout);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, PodString);
        simpleList.setAdapter(arrayAdapter);
        linearLayout.setVisibility(View.VISIBLE);

        FrameLayout progressOverlay = findViewById(R.id.progress_overlay);
        progressOverlay.setVisibility(View.GONE);
    }

    private void settingsClusterView(){
        LinearLayout linearLayout = findViewById(R.id.cluster_settings_layout);

        EditText hostname = findViewById(R.id.hostpath_entry);
        EditText bearer_token = findViewById(R.id.bearer_token_entry);

        String HOSTPATH = this.ClusterPreferences.getString("HOSTPATH", "");
        String BEARER = this.ClusterPreferences.getString("BEARER", "");

        if(!HOSTPATH.equalsIgnoreCase("") && !BEARER.equalsIgnoreCase("")){
            hostname.setText(HOSTPATH);
            bearer_token.setText(BEARER);
        }
        else{
            hostname.setText("https://");
            bearer_token.setText("");
        }

        linearLayout.setVisibility(View.VISIBLE);

        FrameLayout progressOverlay = findViewById(R.id.progress_overlay);
        progressOverlay.setVisibility(View.GONE);
    }

    public void saveSettingsClusterView(View view){
        EditText hostname = findViewById(R.id.hostpath_entry);
        EditText bearer_token = findViewById(R.id.bearer_token_entry);

        this.ClusterPreferencesEditor.clear();


        this.ClusterPreferencesEditor.putString("HOSTPATH", hostname.getText().toString());
        this.ClusterPreferencesEditor.putString("BEARER", bearer_token.getText().toString());
        this.ClusterPreferencesEditor.commit();

        Toast.makeText(getApplicationContext(),"New Credentials successfully saved",Toast.LENGTH_LONG).show();

        if(this.FirstRun){
            this.FirstRun = false;
            this.kubecontroller = new KubeController(hostname.toString(), bearer_token.toString(), false);
            this.navigationView.setCheckedItem(R.id.nav_nodes);
        }

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.nav_nodes) {
            this.clearVisibility();
            this.nodeView();
            Toast.makeText(getApplicationContext(),"Nodes successfully retrieved",Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_pods) {
            this.clearVisibility();
            this.podsView();
            Toast.makeText(getApplicationContext(),"Pods successfully retrieved",Toast.LENGTH_LONG).show();

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
            this.settingsClusterView();

        }

        return true;
    }
}
