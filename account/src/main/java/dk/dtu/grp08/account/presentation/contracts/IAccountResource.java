package dk.dtu.grp08.account.presentation.contracts;

import dk.dtu.grp08.account.domain.models.UserAccount;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/accounts")
public interface IAccountResource {


    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @POST
    UserAccount createUserAccount(UserAccount userAccount);

}
