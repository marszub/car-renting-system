package pl.edu.agh.tarrif.tarrif.service;

import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecord;
import pl.edu.agh.tarrif.blockchain.TarrifBlockchainProxy;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecordsList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TarrifService {

    private final TarrifBlockchainProxy tarrifBlockchainProxy;

    public TarrifService(final TarrifBlockchainProxy tarrifBlockchainProxy) {
        this.tarrifBlockchainProxy = tarrifBlockchainProxy;
    }

    public boolean addPricing(PricingRecord record) {
        try {
            TransactionReceipt result = tarrifBlockchainProxy.addEntry(record);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public PricingRecordsList getPricing(){
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
        return new PricingRecordsList(pricing);
    }
    //TODO add to rental microservice counting, how much it costs
}
