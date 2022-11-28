package pl.edu.agh.rental.rental.ice;

import Rental.RentalData;
import Rental.RentalDoesNotExistException;
import Rental.RentalSummaryData;
import Rental.UnauthorizedRequestException;
import com.zeroc.Ice.Current;
import org.springframework.core.env.Environment;
import pl.edu.agh.rental.auth.User;
import pl.edu.agh.rental.errors.UserUnauthorizedError;
import pl.edu.agh.rental.rental.service.RentalService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RentalServant implements Rental.RentalData {
    private final RentalService rentalService;
    private final String secret;

    public RentalServant(final RentalService service, final Environment environment) {
        this.rentalService = service;
        this.secret = environment.getProperty("ice.rental.secret");
    }

    @Override
    public RentalSummaryData endRental(int userId, String token, Current current) throws UnauthorizedRequestException, RentalDoesNotExistException {
        int rentalId = Integer.parseInt(current.id.name);
        if(!validateRequest(userId, rentalId, token)) {
            throw new UnauthorizedRequestException("Invalid token");
        }
        long cost = 0;
        try {
            rentalService.endRental(rentalId, new User(userId, "USER"));
            cost = rentalService.getRentalCost(rentalId);
        } catch (UserUnauthorizedError e) {
            throw new RentalDoesNotExistException("Rental with that id does not exist");
        }
        return new RentalSummaryData(cost);
    }

    @Override
    public void ice_ping(Current current) {
        RentalData.super.ice_ping(current);
    }

    private boolean validateRequest(int userId, int rentalId, String token) {
        String data = "UserId: " + userId + " rentalId: " + rentalId;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(md == null) {
            return false;
        }
        md.update((data + this.secret).getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02X", b));
        }
        System.out.println("-------MD5-----" + sb + "\n\n");

        return sb.toString().equalsIgnoreCase(token);
    }
}
