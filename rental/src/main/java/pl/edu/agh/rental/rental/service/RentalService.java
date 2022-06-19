package pl.edu.agh.rental.rental.service;

import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import pl.edu.agh.rental.auth.User;
import pl.edu.agh.rental.errors.ActiveRentalError;
import pl.edu.agh.rental.errors.NoCarError;
import pl.edu.agh.rental.errors.NoRentalError;
import pl.edu.agh.rental.errors.UserUnauthorizedError;
import pl.edu.agh.rental.rental.dto.RentalCreateInput;
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
import java.util.Objects;

@Service
public class RentalService {

    private Web3j web3client;//connection with blockchain
    private ContractGasProvider gasProvider;//data about the costs in blockchain
    private Credentials credentials;//admin Credentials in blockchain
    private Rental adminRentalService;

    private static final String CONTRACT_ADDRESS =
            "0xee3e92973010664a804bf96188ac4766fb84a3b9";//TODO - change to config
    private static final String ADMIN_PRIVATE_KEY =
            "0x23901d28534eda9518308ce5cfea39b04b91a0518ceea6f3406b6c1ed8201e6a";//TODO - change to config

    private static final String BLOCKCHAIN_ADDRESS = "HTTP://0.0.0.0:5031/";//TODO - change to config
    public RentalService() {
        this.web3client =
                Web3j.build(new HttpService(BLOCKCHAIN_ADDRESS));//where blockchain is TODO - change to config

        this.gasProvider =
                new StaticGasProvider(BigInteger.valueOf(2000000000), BigInteger.valueOf(6721975));//constant values

        this.credentials =
                Credentials.create(ADMIN_PRIVATE_KEY);//choosing the account by its private key

        this.adminRentalService =
                Rental.load(CONTRACT_ADDRESS, web3client, credentials, gasProvider);//object used to call contracts
    }

    public RentalData createRental(final RentalCreateInput input, final User user) throws NoCarError, ActiveRentalError {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int reservationID;
        try {
            //send request synchronously, it throws error if it reverts
            System.out.println(input.carId());
            TransactionReceipt reservationReceipt = adminRentalService.
                    startRental(BigInteger.valueOf(timestamp.getTime()),
                            BigInteger.valueOf(input.carId()),
                            BigInteger.valueOf(user.id())).send();

            //get event from transaction ("emit" in solidity)
            reservationID = adminRentalService.getAddedNewRentalIDEvents(reservationReceipt).get(0).reservationID.intValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(Objects.equals(e.getMessage(),
                    "Error processing transaction request: VM Exception while processing transaction:" +
                            " revert Car does not exist!")){
                throw new NoCarError();
            }
            if(Objects.equals(e.getMessage(),
                    "Error processing transaction request: VM Exception while processing transaction:" +
                            " revert Active record")){
                throw new ActiveRentalError();
            }

            throw new RuntimeException(e);
            //TODO - problem with revert() - reason as a part of message string
        }

        return new RentalData(reservationID, input.carId(), timestamp.getTime());
    }

    public void endRental(final Integer rentalId, final User user) throws UserUnauthorizedError {
        //TODO - change the name of the error
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try{
            //send request synchronously, it throws error if it reverts
            adminRentalService.endRental(BigInteger.valueOf(rentalId),BigInteger.valueOf(timestamp.getTime())).send();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            if(Objects.equals(e.getMessage(),
                    "Error processing transaction request: VM Exception while processing transaction:" +
                            " revert No rental with that ID started")){
                throw new UserUnauthorizedError();
            }
            if(Objects.equals(e.getMessage(),
                    "Error processing transaction request: VM Exception while processing transaction:" +
                            " revert Rental with that ID has already ended")){
                throw new UserUnauthorizedError();
            }
            throw new RuntimeException(e);
            //TODO - problem with revert() - reason as a part of message string
        }
    }

    public RentalData getRental(final User user) throws NoRentalError {
        Rental.RentalRecord result;
        try{
            //no need for emit, this a "view" function
            result = adminRentalService.getActiveRental(BigInteger.valueOf(user.id())).send();

        }catch (Exception e) {
            System.out.println(e.getMessage());
            if(Objects.equals(e.getMessage(),
                    "Error processing transaction request: VM Exception while processing transaction:" +
                            " revert No active rental record")){
                throw new NoRentalError();
            }
            else{
                throw new RuntimeException(e);
            }

        }
        //TODO - problem with revert() - reason as a part of message string

        return new RentalData(result.rentalID.intValue(), result.carID.intValue(), result.rentTime.longValue());
    }
}
