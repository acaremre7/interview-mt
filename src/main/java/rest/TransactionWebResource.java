package rest;

import common.ParameterValidationUtil;
import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Transaction;
import service.TransactionService;
import service.TransactionServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionWebResource {

    private TransactionService transactionService = TransactionServiceImpl.getInstance();

    @POST
    public Response transfer(@QueryParam("from") String senderId,
                             @QueryParam("to") String recipientId,
                             @QueryParam("amount") String amount){

        long senderNumericId;
        long recipientNumericId;
        double numericAmount;

        try {
            senderNumericId = ParameterValidationUtil.validateId(senderId);
            recipientNumericId = ParameterValidationUtil.validateId(recipientId);
            numericAmount = ParameterValidationUtil.validateTransferAmount(amount);
            Transaction transaction = new Transaction(senderNumericId,recipientNumericId,numericAmount);
            transactionService.processTransaction(transaction);
            return Response.ok().build();
        }catch (BadRequestException | NoSuchAccountException | InsufficientBalanceException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
