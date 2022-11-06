package pl.edu.agh.rental.rental.service;

import Auth.AccessData;
import Auth.AccountPrx;
import Auth.Role;
import Auth.TokenVerificationStatus;
import com.zeroc.Ice.ObjectPrx;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import pl.edu.agh.rental.auth.User;
import pl.edu.agh.rental.errors.ActiveRentalError;
import pl.edu.agh.rental.errors.NoCarError;
import pl.edu.agh.rental.errors.NoRentalError;
import pl.edu.agh.rental.errors.UserUnauthorizedError;
import pl.edu.agh.rental.rental.dto.RentalData;

import java.sql.Timestamp;

//smart contracts
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;
import java.time.Instant;
import pl.edu.agh.rental.rentalContract.Rental;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
public class RentalService {

    private Web3j web3client;//connection with blockchain
    private ContractGasProvider gasProvider;//data about the costs in blockchain
    private Credentials credentials;//admin Credentials in blockchain
    private Rental adminRentalService;

    private CarDb carDb;

    private static final String CONTRACT_ADDRESS =
            "0xee3e92973010664a804bf96188ac4766fb84a3b9";//TODO - change to config
    private static final String ADMIN_PRIVATE_KEY =
            "0x23901d28534eda9518308ce5cfea39b04b91a0518ceea6f3406b6c1ed8201e6a";//TODO - change to config
    private static final String TARRIF_CONTRACT_ADDRESS =
            "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774";

    public RentalService(@Value("${blockchain.address}") final String blockchainAddress) {
        web3client =
                Web3j.build(new HttpService(blockchainAddress));

        gasProvider =
                new StaticGasProvider(BigInteger.valueOf(2000000000), BigInteger.valueOf(6721975));//constant values

        credentials =
                Credentials.create(ADMIN_PRIVATE_KEY);//choosing the account by its private key

        adminRentalService =
                Rental.load(CONTRACT_ADDRESS, web3client, credentials, gasProvider);//object used to call contracts

        carDb = new CarDb();
    }

    public RentalData createRental(final int carId, final User user) throws NoCarError, ActiveRentalError {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int reservationID;
        try {
            //send request synchronously, it throws error if it reverts
            System.out.println(carId);
            int categoryId = 1;//carDb.getCarCategory(carId);
            //IMO the carDb.getCategory does not work
            System.out.println(categoryId);
            
            TransactionReceipt reservationReceipt = adminRentalService.
                    startRental(BigInteger.valueOf(timestamp.getTime()),
                            BigInteger.valueOf(carId),
                            BigInteger.valueOf(user.id()), TARRIF_CONTRACT_ADDRESS,BigInteger.valueOf(categoryId)).send();

            //get event from transaction ("emit" in solidity)
            reservationID = adminRentalService.getAddedNewRentalIDEvents(reservationReceipt).get(0).reservationID.intValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("Car already exists!")){
                throw new NoCarError();
            }
            if(e.getMessage().contains("Active record")){
                throw new ActiveRentalError();
            }

            throw new RuntimeException(e);
            //TODO - problem with revert() - reason as a part of message string
            //possibly solved with string.contains()
        }

        return new RentalData(reservationID, carId, timestamp.getTime());
    }

    public void endRental(final Integer rentalId, final User user) throws UserUnauthorizedError {
        //TODO - change the name of the error
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try{
            //send request synchronously, it throws error if it reverts
            adminRentalService.endRental(BigInteger.valueOf(rentalId),BigInteger.valueOf(timestamp.getTime())).send();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("No rental with that ID started")){
                throw new UserUnauthorizedError();
            }
            if(e.getMessage().contains("Rental with that ID has already ended")){
                throw new UserUnauthorizedError();
            }
            throw new RuntimeException(e);
            //TODO - problem with revert() - reason as a part of message string
            //possibly solved with string.contains()
        }
    }

    public RentalData getRental(final User user) throws NoRentalError {
        Rental.RentalRecord result;
        System.out.println(user.id());
        try{
            //no need for emit, this a "view" function
            result = adminRentalService.getActiveRental(BigInteger.valueOf(user.id())).send();

        }catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("No active rental record")){
                throw new NoRentalError();
            }
            else{
                throw new RuntimeException(e);
            }

        }
        //TODO - problem with revert() - reason as a part of message string
        //possibly solved with string.contains()

        return new RentalData(result.rentalID.intValue(), result.carID.intValue(), result.startRentTime.longValue());
    }

    public long getCurrentRentalTime(final User user) throws NoRentalError {
        Rental.RentalRecord result;
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try{
            //no need for emit, this a "view" function
            result = adminRentalService.getActiveRental(BigInteger.valueOf(user.id())).send();
            return timestamp.getTime() - result.startRentTime.longValue();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("No active rental record")){
                throw new NoRentalError();
            }
            else{
                throw new RuntimeException(e);
            }
        }
    }

    public long getRentalTime(final int rentalID) throws UserUnauthorizedError{
        Rental.RentalRecord result;
        try {
            result = adminRentalService.getRecordHistory(BigInteger.valueOf(rentalID)).send();
            return result.endRentTime.longValue() - result.startRentTime.longValue();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("No rental with that ID started")){
                throw new UserUnauthorizedError();
            }
            if(e.getMessage().contains("Rental with that ID hasn't ended yet")){
                throw new UserUnauthorizedError();
            }
            throw new RuntimeException(e);
        }
    }

    public long getCurrentRentalCost(final User user) throws NoRentalError{
        Rental.RentalRecord result;
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try{
            //no need for emit, this a "view" function
            result = adminRentalService.getActiveRental(BigInteger.valueOf(user.id())).send();
            return (timestamp.getTime() - result.startRentTime.longValue()) * result.rentalPricing.longValue();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("No active rental record")){
                throw new NoRentalError();
            }
            else{
                throw new RuntimeException(e);
            }
        }
    }
    public long getRentalCost(final int rentalID) throws UserUnauthorizedError{
        Rental.RentalRecord result;
        try {
            result = adminRentalService.getRecordHistory(BigInteger.valueOf(rentalID)).send();
            long time = result.endRentTime.longValue() - result.startRentTime.longValue();
            return time * result.rentalPricing.longValue();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("No rental with that ID started")){
                throw new UserUnauthorizedError();
            }
            if(e.getMessage().contains("Rental with that ID hasn't ended yet")){
                throw new UserUnauthorizedError();
            }
            throw new RuntimeException(e);
        }
    }
}
