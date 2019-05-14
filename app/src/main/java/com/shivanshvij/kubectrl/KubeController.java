package com.shivanshvij.kubectrl;

import android.os.StrictMode;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.swagger.client.model.IoK8sApiCoreV1NamespaceList;
import io.swagger.client.model.IoK8sApiCoreV1NodeList;
import io.swagger.client.model.IoK8sApiCoreV1PodList;

public class KubeController {
    private Boolean DEBUG; // Class-global DEBUG variable to enable debug output

    private Kubernetes kubernetes; // Class-global Kubernetes Control instance for the wrapper

    private Integer timeout = 200;

    KubeController(String HostPath, String APIKey, Boolean DEBUG){
        this.DEBUG = DEBUG;

        this.setThreadPolicy();

        this.kubernetes = new Kubernetes(HostPath, APIKey, DEBUG);
    }

    private void setThreadPolicy(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(this.DEBUG){
            System.out.println("[KubeController] Setting thread policy to strict");
        }
    }

    public ArrayList<String> getNodes_STRING(){
        ArrayList<String> Nodes = new ArrayList<String>();

        IoK8sApiCoreV1NodeList ApiNodes = this.kubernetes.getNodes(true,null,null, null, null, 56, null, this.timeout, false);

        Gson GSONReader = new Gson();

        try {
            JSONObject JSONNodes = new JSONObject(GSONReader.toJson(ApiNodes));
            JSONArray items = JSONNodes.getJSONArray("items");
            for(Integer i = 0; i < items.length(); i = i+1){
                Nodes.add(items.getJSONObject(i).getJSONObject("status").getJSONArray("addresses").getJSONObject(0).getString("address") + " : " + items.getJSONObject(i).getJSONObject("status").getJSONArray("addresses").getJSONObject(1).getString("address"));
            }
        } catch (Exception e) {

        }

        return Nodes;
    }
    public HashMap<String, String> getNodes_HASH(){
        HashMap<String, String> Nodes = new HashMap<String, String>();

        IoK8sApiCoreV1NodeList ApiNodes = this.kubernetes.getNodes(true,null,null, null, null, 56, null, 56, false);

        Gson GSONReader = new Gson();

        try {
            JSONObject JSONNodes = new JSONObject(GSONReader.toJson(ApiNodes));
            JSONArray items = JSONNodes.getJSONArray("items");
            for(Integer i = 0; i < items.length(); i = i+1){
                Nodes.put(items.getJSONObject(i).getJSONObject("status").getJSONArray("addresses").getJSONObject(0).getString("address"), items.getJSONObject(i).getJSONObject("status").getJSONArray("addresses").getJSONObject(1).getString("address"));
            }
        } catch (Exception e) {

        }

        return Nodes;
    }


    public ArrayList<String> getPodsAll_STRING(){
        ArrayList<String> Pods = new ArrayList<String>();

        IoK8sApiCoreV1PodList ApiPods = this.kubernetes.getPodsAll(true,null,null, null, null, 56, null, this.timeout, false);

        Gson GSONReader = new Gson();

        try {
            JSONObject JSONPods = new JSONObject(GSONReader.toJson(ApiPods));
            JSONArray items = JSONPods.getJSONArray("items");
            for(Integer i = 0; i < items.length(); i = i+1){
                Pods.add(items.getJSONObject(i).getJSONObject("metadata").getJSONObject("labels").getString("app") + " : " + items.getJSONObject(i).getJSONObject("status").getString("phase") + ", Namespace: " + items.getJSONObject(i).getJSONObject("metadata").getString("namespace"));
            }
        } catch (Exception e) {

        }

        return Pods;
    }

    public ArrayList<String> getPodsNamespaces_STRING(String namespaces){
        ArrayList<String> Pods = new ArrayList<String>();

        IoK8sApiCoreV1PodList ApiPods = this.kubernetes.getPodsNamespaced(namespaces,true,null,null, null, null, 56, null, this.timeout, false);

        Gson GSONReader = new Gson();

        try {
            JSONObject JSONPods = new JSONObject(GSONReader.toJson(ApiPods));
            JSONArray items = JSONPods.getJSONArray("items");
            for(Integer i = 0; i < items.length(); i = i+1){
                Pods.add(items.getJSONObject(i).getJSONObject("metadata").getJSONObject("labels").getString("app") + " : " + items.getJSONObject(i).getJSONObject("status").getString("phase") + ", Namespace: " + items.getJSONObject(i).getJSONObject("metadata").getString("namespace"));
            }
        } catch (Exception e) {

        }

        return Pods;
    }


    public ArrayList<String> getNamespaces_STRING(){
        ArrayList<String> Namespaces = new ArrayList<String>();

        IoK8sApiCoreV1NamespaceList ApiNamespaces = this.kubernetes.getNamespaces(true,null,null, null, null, 56, null, this.timeout, false);

        Gson GSONReader = new Gson();

        try {
            JSONObject JSONNamespaces = new JSONObject(GSONReader.toJson(ApiNamespaces));
            JSONArray items = JSONNamespaces.getJSONArray("items");
            System.out.println(items);
            for(Integer i = 0; i < items.length(); i = i+1){
                Namespaces.add(items.getJSONObject(i).getJSONObject("metadata").getString("name"));
            }
        } catch (Exception e) {

        }

        return Namespaces;
    }
}
