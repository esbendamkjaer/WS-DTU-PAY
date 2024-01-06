package dk.dtu.grp08;

import dk.dtu.grp08.contracts.IMerchantResource;
import dk.dtu.grp08.dtupay.bank.BankService;
import dk.dtu.grp08.dtupay.bank.BankServiceService;
import dk.dtu.grp08.models.Merchant;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/merchants")
public class MerchantResource implements IMerchantResource {
    private final Map<String, Merchant> merchants = new HashMap<>();

    @Override
    public void createMerchant(Merchant merchant) {

        try {
            BankService bank = new BankServiceService().getBankServicePort();

            if(bank.getAccount(merchant.getAccountId()) == null){
                return;

            }
            this.merchants.put(merchant.getId(), merchant);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Merchant> listMerchants() {
        return this.merchants.values().stream().toList();
    }

    @Override
    public Merchant getMerchant(String id) {
        if (!merchants.containsKey(id)) {
           throw new NotFoundException(
                Response.status(404).entity("merchant with id " + id + " is unknown").build()
            );
        }

        return merchants.get(id);
    }

    @Override
    public void updateMerchant(String id, Merchant merchant) {
        if (!merchants.containsKey(id)) {
            throw new NotFoundException();
        }

        this.merchants.put(id, merchant);
    }

    @Override
    public void deleteMerchant(String id) {
        if (!merchants.containsKey(id)) {
            throw new NotFoundException();
        }

        this.merchants.remove(id);
    }

}
