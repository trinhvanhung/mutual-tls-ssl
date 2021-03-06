package nl.altindag.client.service;

import static nl.altindag.client.ClientType.JERSEY_CLIENT;
import static nl.altindag.client.Constants.HEADER_KEY_CLIENT_TYPE;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.altindag.client.ClientType;
import nl.altindag.client.model.ClientResponse;

@Service
public class JerseyClientService implements RequestService {

    private final Client client;

    @Autowired
    public JerseyClientService(Client client) {
        this.client = client;
    }

    @Override
    public ClientResponse executeRequest(String url) {
        Response response = client.target(url)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header(HEADER_KEY_CLIENT_TYPE, getClientType().getValue())
                .get();

        return new ClientResponse(response.readEntity(String.class), response.getStatus());
    }

    @Override
    public ClientType getClientType() {
        return JERSEY_CLIENT;
    }

}
