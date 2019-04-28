package com.shivanshvij.kubectrl;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.JsonReader;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import io.swagger.client.*;
import io.swagger.client.api.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.AdmissionregistrationApi;

import java.util.HashMap;

import java.io.File;
import java.sql.Array;
import java.util.*;

import javax.net.ssl.SSLContext;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shivanshvij.kubectrl.SslUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Kubernetes kubernetes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        this.kubernetes = new Kubernetes("https://cbeaf424-708d-49b6-a9b6-d88a7e6a3292.k8s.ondigitalocean.com", "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLXhreDQ4Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI0MGQyNDFjYi02OTJhLTExZTktODE4Mi0zZWRhYzIwOTgxYjciLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06YWRtaW4tdXNlciJ9.YINWjiLcEsw4nDK-snv1ujJnOEGWzdGaBpBinIfdWVItegJScElGRoBEiAlPyYdz-hvi1JnO-2QAc9BolEL8DoOgBullH3yViVeBBhwGOyzdr3_QEOzMkTPXlvPUV3VNrRo5aq9AGgq7J3RgMGnQDX9M1qpAJu2RZqqdpLjtthUALjVy0TKwdl2JeH0f4i67I4swllOW4syqZ7pvvy6wqWCwxORCzXZV5Tfx_B-jaXczjlUwoZoNKJuVTwcnm4mDUXQgzVW1tetdlKWMDUpiZNUAybwncVpkopK9E1dMPb-t6Q4V0pavRR0zi8VVfuFvkiAFYYmRvizM7DSdBJN8kQ", false);

        HashMap<String, String> Nodes = new HashMap<String, String>();
        ArrayList<String> NodeString = new ArrayList<String>();

        IoK8sApiCoreV1NodeList _NodeList = this.kubernetes.getNodes(true,null,null, null, null, 56, null, 56, false);

        Gson GSON = new Gson();
        String __NodeList = GSON.toJson(_NodeList);

        try {
            JSONObject NodeList = new JSONObject(__NodeList);
            JSONArray items = NodeList.getJSONArray("items");
            for(Integer i = 0; i < items.length(); i = i+1){
                Nodes.put(items.getJSONObject(i).getJSONObject("status").getJSONArray("addresses").getJSONObject(0).getString("address"), items.getJSONObject(i).getJSONObject("status").getJSONArray("addresses").getJSONObject(1).getString("address"));
                NodeString.add(items.getJSONObject(i).getJSONObject("status").getJSONArray("addresses").getJSONObject(0).getString("address") + " : " + items.getJSONObject(i).getJSONObject("status").getJSONArray("addresses").getJSONObject(1).getString("address"));
                System.out.println(Nodes.toString());
            }
        } catch (Exception e) {

        }

        ListView simpleList = (ListView)findViewById(R.id.nodelist);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, NodeString);
        simpleList.setAdapter(arrayAdapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
