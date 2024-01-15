package dk.dtu.grp08.stubs;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class Stub {

    public static <T> T get(Class<T> clazz) {
        return get(clazz, "http://localhost:8080");
    }

    public static <T> T get(Class<T> clazz, String baseUrl) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl);
        ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
        return rtarget.proxy(clazz);
    }

}
