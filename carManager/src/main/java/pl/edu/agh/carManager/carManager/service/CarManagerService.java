package pl.edu.agh.carManager.carManager.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import pl.edu.agh.carManager.carManager.dto.PricingRecord;
import pl.edu.agh.carManager.blockchain.TarrifBlockchainProxy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarManagerService {

    private final TarrifBlockchainProxy tarrifBlockchainProxy;

    public CarManagerService(final TarrifBlockchainProxy tarrifBlockchainProxy) {
        this.tarrifBlockchainProxy = tarrifBlockchainProxy;
    }
    @Transactional
    public boolean addPricing(PricingRecord record) {
        try {
            TransactionReceipt result = tarrifBlockchainProxy.addEntry(record);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<PricingRecord> getPricing(){
        ArrayList<PricingRecord> pricing = new ArrayList<>();
        try {
            Tuple3<List<BigInteger>, List<BigInteger>,List<BigInteger>> res = tarrifBlockchainProxy.getPricing();
            for(int i = 0; i<res.component1().size(); i++){
                pricing.add(new PricingRecord(res.component1().get(i).intValue(), res.component2().get(i).intValue()));
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return pricing;
        //TODO add to car DB the car types
    }
    //TODO add to rental microservice counting, how much it costs
}
