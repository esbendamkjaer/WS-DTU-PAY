package dk.dtu.grp08.dtupay.manager;

import dk.dtu.grp08.dtupay.Stub;
import dk.dtu.grp08.dtupay.api.IManagerAPI;
import dk.dtu.grp08.dtupay.models.Payment;

import java.util.List;
import java.util.Optional;

public class ManagerFacade implements IManagerFacade {

    private final IManagerAPI managerAPI;

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    public ManagerFacade() {
        this.managerAPI = Stub.get(
            IManagerAPI.class,
            Optional
                .ofNullable(System.getenv("MANAGER_API"))
                .orElse("http://localhost:8087")
        );
    }


    /**
     * @author Fuad Hassan Jama (s233468)
     */
    @Override
    public List<Payment> getReport() {
        return this.managerAPI.getReport();
    }
}
