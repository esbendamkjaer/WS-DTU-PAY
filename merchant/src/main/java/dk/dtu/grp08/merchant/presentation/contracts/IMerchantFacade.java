package dk.dtu.grp08.merchant.presentation.contracts;

public interface IMerchantFacade {

    void register();

    void deregister();

    void pay();

    void getReports();
}
