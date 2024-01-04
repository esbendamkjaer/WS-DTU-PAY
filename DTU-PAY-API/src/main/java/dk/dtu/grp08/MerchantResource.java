package dk.dtu.grp08;

import dk.dtu.grp08.contracts.IMerchantResource;
import dk.dtu.grp08.models.Merchant;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/merchants")
public class MerchantResource implements IMerchantResource {
    private final Map<String, Merchant> merchants = new HashMap<>();

    @Override
    public void createMerchant(Merchant merchant) {
        this.merchants.put(merchant.getId(), merchant);
    }

    @Override
    public List<Merchant> listMerchants() {
        return this.merchants.values().stream().toList();
    }

    @Override
    public Merchant getMerchant(String id) {
        if (!merchants.containsKey(id)) {
            throw new NotFoundException();
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
