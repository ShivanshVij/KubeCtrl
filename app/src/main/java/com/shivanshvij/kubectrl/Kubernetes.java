package com.shivanshvij.kubectrl;

import android.os.StrictMode;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.AdmissionregistrationApi;
import io.swagger.client.api.CoreV1Api;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.model.IoK8sApiCoreV1NamespaceList;
import io.swagger.client.model.IoK8sApiCoreV1NodeList;
import io.swagger.client.model.IoK8sApiCoreV1PodList;
import io.swagger.client.model.IoK8sApiCoreV1ServiceList;
import io.swagger.client.model.IoK8sApimachineryPkgApisMetaV1APIGroup;

public class Kubernetes {
    private Boolean DEBUG; // Class-global DEBUG variable to enable debug output

    private ApiClient Client; // Class-global ApiClient to use the same client by default for every class function
    private ApiKeyAuth BearerToken; // Class-global APIKeyAuth token for authenticating the default client 

    Kubernetes(String HostPath, String APIKey, Boolean DEBUG){
        this.DEBUG = DEBUG;

        // Set thread policy 
        this.setThreadPolicy();

        // Create default client
        this.setClient(Configuration.getDefaultApiClient());

        // Set the Host Path for the Kubernetes instance
        this.setHostPath(HostPath);

        // Get bearer token registration from the default client 
        this.setBearerToken((ApiKeyAuth) this.Client.getAuthentication("BearerToken"));

        this.setAPIKey(APIKey);
    }

    private void setThreadPolicy(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(this.DEBUG){
            System.out.println("[Kubernetes] Setting thread policy to strict");
        }
    }

    public void setHostPath(String HostPath){
        this.Client.setBasePath(HostPath);
        if(this.DEBUG){
            System.out.println("[Kubernetes] Host Path set to: " + this.getHostPath());
        }
    }

    public String getHostPath(){
        return this.Client.getBasePath();
    }

    private void setBearerToken(ApiKeyAuth BearerToken){
        this.BearerToken = BearerToken;
        if(this.DEBUG){
            System.out.println("[Kubernetes] Default Bearer Token Set");
        }
        this.BearerToken.setApiKeyPrefix("Bearer");
        if(this.DEBUG){
            System.out.println("[Kubernetes] ApiKey Prefix set for Default Bearer Token");
        }
    }

    private void setAPIKey(String APIKey){
        this.BearerToken.setApiKey(APIKey);
        if(this.DEBUG){
            System.out.println("[Kubernetes] APIKey set to: " + this.getAPIKey());
        }
    }

    public String getAPIKey(){
        return this.BearerToken.getApiKey();
    }

    public void setClient(ApiClient Client){
        this.Client = Client; 
        if(this.DEBUG){
            System.out.println("[Kubernetes] Default Client set");
        }
        this.Client.setVerifyingSsl(false);
        if(this.DEBUG){
            System.out.println("[Kubernetes] Removed SSL Verification on Default Client");
        }
    }

    public ApiClient getClient(){
        return this.Client;
    }

    public IoK8sApimachineryPkgApisMetaV1APIGroup getAdmissionregistrationAPIGroup(){
        AdmissionregistrationApi apiInstance = new AdmissionregistrationApi(this.Client);
        try {
            if(this.DEBUG){
                System.out.println("[Kubernetes] Trying Kubernetes.getAdmissionregistrationAPIGroup()");
        }
            IoK8sApimachineryPkgApisMetaV1APIGroup result = apiInstance.getAdmissionregistrationAPIGroup();
            if(this.DEBUG){
                System.out.println(result);
            }
            return result;
        } catch (ApiException e) {
            System.err.println("[Kubernetes] Exception when calling Kubernetes.AdmissionregistrationApi.getAdmissionregistrationAPIGroup");
            e.printStackTrace();
        }
        return new IoK8sApimachineryPkgApisMetaV1APIGroup();
    }

    public IoK8sApiCoreV1NodeList getNodes(Boolean includeUninitialized, String pretty, String _continue, String fieldSelector, String labelSelector, Integer limit, String resourceVersion, Integer timeoutSeconds, Boolean watch){
        CoreV1Api apiInstance = new CoreV1Api(this.Client);

        try{
            if(this.DEBUG){
                System.out.println("[Kubernetes] Trying Kubernetes.getNodes()");
            }
            IoK8sApiCoreV1NodeList result = apiInstance.listCoreV1Node(includeUninitialized, pretty, _continue, fieldSelector, labelSelector, limit, resourceVersion, timeoutSeconds, watch);
            if(this.DEBUG){
                System.out.println(result);
            }
            return result;

        } catch (ApiException e){
            System.err.println("[Kubernetes] Exception when calling Kubernetes.CoreV1Api.listCoreV1Node");
            e.printStackTrace();
        }

        return new IoK8sApiCoreV1NodeList();
    }

    public IoK8sApiCoreV1PodList getPodsAll(Boolean includeUninitialized, String pretty, String _continue, String fieldSelector, String labelSelector, Integer limit, String resourceVersion, Integer timeoutSeconds, Boolean watch){

        CoreV1Api apiInstance = new CoreV1Api(this.Client);

        try{
            if(this.DEBUG){
                System.out.println("[Kubernetes] Trying Kubernetes.getPodsAll()");
            }
            IoK8sApiCoreV1PodList result = apiInstance.listCoreV1PodForAllNamespaces(_continue, fieldSelector, includeUninitialized, labelSelector, limit, pretty, resourceVersion, timeoutSeconds, watch);
            if(this.DEBUG){
                System.out.println(result);
            }
            return result;

        } catch (ApiException e){
            System.err.println("[Kubernetes] Exception when calling Kubernetes.CoreV1Api.listCoreV1PodForAllNamespaces");
            e.printStackTrace();
        }

        return new IoK8sApiCoreV1PodList();

    }

    public IoK8sApiCoreV1PodList getPodsNamespaced(String namespace, Boolean includeUninitialized, String pretty, String _continue, String fieldSelector, String labelSelector, Integer limit, String resourceVersion, Integer timeoutSeconds, Boolean watch){

        CoreV1Api apiInstance = new CoreV1Api(this.Client);

        try{
            if(this.DEBUG){
                System.out.println("[Kubernetes] Trying Kubernetes.getPodsNamespaced()");
            }
            IoK8sApiCoreV1PodList result = apiInstance.listCoreV1NamespacedPod(namespace, includeUninitialized, pretty, _continue, fieldSelector, labelSelector, limit, resourceVersion, timeoutSeconds, watch);
            if(this.DEBUG){
                System.out.println(result);
            }
            return result;

        } catch (ApiException e){
            System.err.println("[Kubernetes] Exception when calling Kubernetes.CoreV1Api.listCoreV1NamespacedPod");
            e.printStackTrace();
        }

        return new IoK8sApiCoreV1PodList();

    }

    public IoK8sApiCoreV1ServiceList getServicesNamespaced(String namespace, Boolean includeUninitialized, String pretty, String _continue, String fieldSelector, String labelSelector, Integer limit, String resourceVersion, Integer timeoutSeconds, Boolean watch){

        CoreV1Api apiInstance = new CoreV1Api(this.Client);

        try{
            if(this.DEBUG){
                System.out.println("[Kubernetes] Trying Kubernetes.getServicesNamespaced()");
            }
            IoK8sApiCoreV1ServiceList result = apiInstance.listCoreV1NamespacedService(namespace, includeUninitialized, pretty, _continue, fieldSelector, labelSelector, limit, resourceVersion, timeoutSeconds, watch);
            if(this.DEBUG){
                System.out.println(result);
            }
            return result;

        } catch (ApiException e){
            System.err.println("[Kubernetes] Exception when calling Kubernetes.CoreV1Api.listCoreV1NamespacedService");
            e.printStackTrace();
        }

        return new IoK8sApiCoreV1ServiceList();

    }

    public IoK8sApiCoreV1NamespaceList getNamespaces(Boolean includeUninitialized, String pretty, String _continue, String fieldSelector, String labelSelector, Integer limit, String resourceVersion, Integer timeoutSeconds, Boolean watch){

        CoreV1Api apiInstance = new CoreV1Api(this.Client);

        try{
            if(this.DEBUG){
                System.out.println("[Kubernetes] Trying Kubernetes.getNamespaces()");
            }
            IoK8sApiCoreV1NamespaceList result = apiInstance.listCoreV1Namespace(includeUninitialized, pretty, _continue, fieldSelector, labelSelector, limit, resourceVersion, timeoutSeconds, watch);
            if(this.DEBUG){
                System.out.println(result);
            }
            return result;

        } catch (ApiException e){
            System.err.println("[Kubernetes] Exception when calling Kubernetes.CoreV1Api.listCoreV1Namespace");
            e.printStackTrace();
        }

        return new IoK8sApiCoreV1NamespaceList();

    }

}
