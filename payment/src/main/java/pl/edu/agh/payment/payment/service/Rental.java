package pl.edu.agh.payment.payment.service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import Rental.RentalSummaryData;
import Rental.RentalDataPrx;
import Rental.RentalDoesNotExistException;
import Rental.UnauthorizedRequestException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Rental {
    private final Communicator communicator;
    private final String secret;

    public Rental(Communicator communicator, Environment environment) {
        this.communicator = communicator;
        this.secret = environment.getProperty("ice.rental.secret");
    }

    public RentalSummaryData endRental(int rentalId, int userId) throws UnauthorizedRequestException, RentalDoesNotExistException {
        ObjectPrx baseAccount = communicator.stringToProxy("rental/" + rentalId + ":" + communicator.getProperties().getProperty("Rental.Proxy"));
        RentalDataPrx rental = RentalDataPrx.checkedCast(baseAccount);
        if (rental == null) throw new Error("Invalid proxy");

        return rental.endRental(userId, token(rentalId, userId));
    }

    private String token(int rentalId, int userId) {
        String data = "UserId: " + userId + " rentalId: " + rentalId;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(md == null) {
            return "";
        }
        md.update((data + this.secret).getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02X", b));
        }
        System.out.println("-------MD5Rental-------" + sb + "\n\n");

        return sb.toString().toUpperCase();
    }
}
