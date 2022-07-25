package pl.edu.agh.tarrif.tarrif.service;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecord;
import pl.edu.agh.tarrif.tarrif.dto.RentalData;
import pl.edu.agh.tarrif.tarrifContract.Tarrif;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TarrifService {

    private Web3j web3client;//connection with blockchain
    private ContractGasProvider gasProvider;//data about the costs in blockchain
    private Credentials credentials;//admin Credentials in blockchain
    private Tarrif adminTarrifService;

    private static final String CONTRACT_ADDRESS =
            "0xee3e92973010664a804bf96188ac4766fb84a3b9";//TODO - change to config
    private static final String ADMIN_PRIVATE_KEY =
            "0x23901d28534eda9518308ce5cfea39b04b91a0518ceea6f3406b6c1ed8201e6a";//TODO - change to config

    private static final String BLOCKCHAIN_ADDRESS = "HTTP://0.0.0.0:5031/";//TODO - change to config

    public TarrifService() {
        this.web3client =
                Web3j.build(new HttpService(BLOCKCHAIN_ADDRESS));//where blockchain is TODO - change to config

        this.gasProvider =
                new StaticGasProvider(BigInteger.valueOf(2000000000), BigInteger.valueOf(6721975));//constant values

        this.credentials =
                Credentials.create(ADMIN_PRIVATE_KEY);//choosing the account by its private key

        this.adminTarrifService =
                Tarrif.load(CONTRACT_ADDRESS, web3client, credentials, gasProvider);//object used to call contracts
    }

    public void addPricing(PricingRecord record){
        try {
            adminTarrifService.addEntry(BigInteger.valueOf(record.carType()), BigInteger.valueOf(record.price()));
        }catch (Exception e){

        }
    }

    public ArrayList<PricingRecord> getPricing(){
        ArrayList<PricingRecord> pricing = new ArrayList<PricingRecord>();
        try {
            Tuple3<List<BigInteger>, List<BigInteger>,List<BigInteger>> res= adminTarrifService.getPricing().send();
            for(int i =0; i<res.component1().size();i++){
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
