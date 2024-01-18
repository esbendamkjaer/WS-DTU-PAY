package dk.dtu.grp08.dtupay.manager;

import dk.dtu.grp08.dtupay.Stub;
import dk.dtu.grp08.dtupay.api.IManagerAPI;
import dk.dtu.grp08.dtupay.api.IMerchantAPI;
import dk.dtu.grp08.dtupay.models.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ManagerFacade implements IManagerFacade {

    private final IManagerAPI managerAPI;

    public ManagerFacade() {
        this.managerAPI = Stub.get(
            IManagerAPI.class,
            Optional
                .ofNullable(System.getenv("MANAGER_API"))
                .orElse("http://localhost:8087")
        );
    }


    @Override
    public CompletableFuture<List<Payment>> getReport() {
        return this.managerAPI.getReport();
    }
}
