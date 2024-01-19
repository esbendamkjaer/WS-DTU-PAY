package dk.dtu.grp08.dtupay;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class Stub {

    /**
     * @author Esben
     */
    public static <T> T get(Class<T> clazz, String baseUrl) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl);
        ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
        return rtarget.proxy(clazz);
    }

}
