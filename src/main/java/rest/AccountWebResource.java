package rest;

import com.google.gson.Gson;
import common.ParameterValidationUtil;
import exception.DuplicateAccountIdException;
import exception.NoSuchAccountException;
import model.Account;
import service.AccountService;
import service.AccountServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountWebResource {

    private AccountService accountService = AccountServiceImpl.getInstance();
    private Gson gson = new Gson();

    @POST
    @Path("/create")
    public Response create(@QueryParam("id") String id,
                           @QueryParam("amount") String initialAmount) {
        long numericId;
        double numericAmount;

        try {
            numericId = ParameterValidationUtil.validateId(id);
            numericAmount = ParameterValidationUtil.validateInitialAmount(initialAmount);
            accountService.createAccount(numericId,numericAmount);
            return Response.status(Response.Status.OK).build();
        }catch (BadRequestException | DuplicateAccountIdException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getAccount(@PathParam("id") String id){
        long numericId;
        Account result;

        try {
            numericId = ParameterValidationUtil.validateId(id);
            result = accountService.getAccount(numericId);
            return Response.status(Response.Status.OK).entity(gson.toJson(result)).build();
        }catch (BadRequestException | NoSuchAccountException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
