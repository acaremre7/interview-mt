package rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountWebResource {

    @POST
    @Path("/create")
    public Response create(@QueryParam("id") String id,
                           @QueryParam("amount") String initialAmount) {
        return null;
    }

    @GET
    @Path("/{id}")
    public Response getAccount(@PathParam("id") String id){
        return null;
    }
}
