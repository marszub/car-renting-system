package pl.agh.edu.cardatabase.carCategory.ice;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.ServantLocator;
import com.zeroc.Ice.UserException;

public class CarLocator implements ServantLocator {
    @Override
    public LocateResult locate(Current curr) throws UserException {
        System.out.println("Locating: " + curr.id.category + " " + curr.id.name);
        return new LocateResult(new CarServant(), new Object());
    }

    @Override
    public void deactivate(String category) {
    }

    @Override
    public void finished(Current curr, com.zeroc.Ice.Object servant, java.lang.Object cookie) throws UserException {
    }
}
