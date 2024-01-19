package dk.dtu.grp08.dtupay.manager;

import dk.dtu.grp08.dtupay.models.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IManagerFacade {

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    List<Payment> getReport();


}
