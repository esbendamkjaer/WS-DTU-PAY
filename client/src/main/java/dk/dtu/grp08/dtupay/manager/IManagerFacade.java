package dk.dtu.grp08.dtupay.manager;

import dk.dtu.grp08.dtupay.models.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IManagerFacade {



    List<Payment> getReport();


}
