package com.shivanshvij.kubectrl;

import android.os.StrictMode;

import io.swagger.client.*;
import io.swagger.client.api.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.AdmissionregistrationApi;

import javax.net.ssl.SSLContext;

import java.util.*;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import com.shivanshvij.kubectrl.SslUtils;

public class Kubernetes {
    private Boolean DEBUG;

    private ApiClient Client;
    private ApiKeyAuth BearerToken;

    Kubernetes(String HostPath, String APIKey, Boolean DEBUG){
        this.DEBUG = DEBUG;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(this.DEBUG){
            System.out.println("Setting thread policy to strict");
        }

        this.Client = Configuration.getDefaultApiClient();
        this.Client.setVerifyingSsl(false);

        this.setHostPath(HostPath);

        this.BearerToken = (ApiKeyAuth) this.Client.getAuthentication("BearerToken");
        this.BearerToken.setApiKeyPrefix("Bearer");
        this.setAPIKey(APIKey);
    }

    public void setHostPath(String HostPath){
        this.Client.setBasePath(HostPath);
        if(this.DEBUG){
            System.out.println("Host Path set to: " + this.getHostPath());
        }
    }

    public String getHostPath(){
        return this.Client.getBasePath();
    }

    public void setAPIKey(String APIKey){
        this.BearerToken.setApiKey(APIKey);
        if(this.DEBUG){
            System.out.println("APIKey set to: " + this.getAPIKey());
        }
    }

    public String getAPIKey(){
        return this.BearerToken.getApiKey();
    }

    public IoK8sApimachineryPkgApisMetaV1APIGroup getAdmissionregistrationAPIGroup(){
        AdmissionregistrationApi apiInstance = new AdmissionregistrationApi(this.Client);
        try {
            if(this.DEBUG){
                System.out.println("Trying Kubernetes.getAdmissionregistrationAPIGroup()");
        }
            IoK8sApimachineryPkgApisMetaV1APIGroup result = apiInstance.getAdmissionregistrationAPIGroup();
            if(this.DEBUG){
                System.out.println(result);
            }
            return result;
        } catch (ApiException e) {
            System.err.println("Exception when calling Kubernetes.AdmissionregistrationApi.getAdmissionregistrationAPIGroup");
            e.printStackTrace();
        }
        return new IoK8sApimachineryPkgApisMetaV1APIGroup();
    }

    public IoK8sApiCoreV1NodeList getNodes(Boolean includeUninitialized, String pretty, String _continue, String fieldSelector, String labelSelector, Integer limit, String resourceVersion, Integer timeoutSeconds, Boolean watch){
        CoreV1Api apiInstance = new CoreV1Api(this.Client);

        try{
            if(this.DEBUG){
                System.out.println("Trying Kubernetes.getNodes()");
            }
            IoK8sApiCoreV1NodeList result = apiInstance.listCoreV1Node(includeUninitialized, pretty, _continue, fieldSelector, labelSelector, limit, resourceVersion, timeoutSeconds, watch);
            if(this.DEBUG){
                System.out.println(result);
            }
            return result;

        } catch (ApiException e){
            System.err.println("Exception when calling Kubernetes.CoreV1Api.listCoreV1Node");
            e.printStackTrace();
        }

        return new IoK8sApiCoreV1NodeList();
    }

}
