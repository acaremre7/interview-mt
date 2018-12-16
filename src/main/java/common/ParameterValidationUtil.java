package common;

import javax.ws.rs.BadRequestException;

public class ParameterValidationUtil {
    public static long validateId(String id) throws BadRequestException{
        long parsedId;
        if(id == null || id.isEmpty()){
            throw new BadRequestException(ApplicationConstants.INVALID_ID + ApplicationConstants.NULL_OR_EMPTY_ID);
        }else {
            try {
                parsedId = Long.parseLong(id);
            }catch (Exception e){
                throw new BadRequestException(ApplicationConstants.INVALID_ID + id);
            }
        }
        if(parsedId < 0){
            throw new BadRequestException(ApplicationConstants.INVALID_ID + id);
        }
        return parsedId;
    }

    public static double validateInitialAmount(String initialAmount) throws BadRequestException{
        double parsedInitialAmount;
        if(initialAmount == null || initialAmount.isEmpty()){
            return 0;
        }else {
            try {
                parsedInitialAmount = Double.parseDouble(initialAmount);
            }catch (Exception e){
                throw new BadRequestException(ApplicationConstants.INVALID_INITIAL_BALANCE + initialAmount);
            }
        }
        if(parsedInitialAmount < 0){
            throw new BadRequestException(ApplicationConstants.NEGATIVE_INITIAL_BALANCE);
        }
        return parsedInitialAmount;
    }

    public static double validateTransferAmount(String transferAmount) throws BadRequestException{
        double parsedAmount;

        if(transferAmount == null || transferAmount.isEmpty()){
            throw new BadRequestException(ApplicationConstants.INVALID_TRANSFER_AMOUNT + transferAmount);
        }else {
            try {
                parsedAmount = Double.parseDouble(transferAmount);
            }catch (Exception e){
                throw new BadRequestException(ApplicationConstants.INVALID_TRANSFER_AMOUNT + transferAmount);
            }
        }
        if(parsedAmount < 0){
            throw new BadRequestException(ApplicationConstants.NEGATIVE_TRANSFER_AMOUNT);
        }
        return parsedAmount;
    }
}
