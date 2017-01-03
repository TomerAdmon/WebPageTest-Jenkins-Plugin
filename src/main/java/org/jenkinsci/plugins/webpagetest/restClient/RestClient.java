package org.jenkinsci.plugins.webpagetest.restClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

public class RestClient {

    public static RestResponse Get(String url, MediaType mediaType) {
            Client client = Client.create();

            WebResource webResource = client
                    .resource(url);

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            int status = response.getStatus();
            if (status != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + status);
            }

        String data = response.getEntity(String.class);
        return new RestResponse(status,
                    new JSONObject(data));
    }

    public static ClientResponse Post(String url, MediaType mediaType) {
        return null;
    }

}
