package rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionWebResource {

    @POST
    public Response transfer(@QueryParam("from") String senderId,
                             @QueryParam("to") String recipientId,
                             @QueryParam("amount") String amount){
        return null;
    }
}
