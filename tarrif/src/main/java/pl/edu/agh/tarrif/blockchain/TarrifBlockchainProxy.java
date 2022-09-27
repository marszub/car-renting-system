package pl.edu.agh.tarrif.blockchain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import pl.edu.agh.tarrif.tarrif.dto.PricingRecord;
import pl.edu.agh.tarrif.tarrifContract.Tarrif;

import java.math.BigInteger;
import java.util.List;

@Component
public class TarrifBlockchainProxy {
    private static final String CONTRACT_ADDRESS =
            "0xee3e92973010664a804bf96188ac4766fb84a3b9";//TODO - change to config
    private static final String ADMIN_PRIVATE_KEY =
            "0x23901d28534eda9518308ce5cfea39b04b91a0518ceea6f3406b6c1ed8201e6a";//TODO - change to config

    private Web3j web3client;//connection with blockchain
    private ContractGasProvider gasProvider;//data about the costs in blockchain
    private Credentials credentials;//admin Credentials in blockchain
    private Tarrif adminTarrifService;

    public TarrifBlockchainProxy(@Value("${blockchain.address}") final String blockchainAddress) {
        this.web3client =
                Web3j.build(new HttpService(blockchainAddress));//where blockchain is

        this.gasProvider =
                new StaticGasProvider(BigInteger.valueOf(2000000000), BigInteger.valueOf(6721975));//constant values

        this.credentials =
                Credentials.create(ADMIN_PRIVATE_KEY);//choosing the account by its private key

        this.adminTarrifService =
                Tarrif.load(CONTRACT_ADDRESS, web3client, credentials, gasProvider);//object used to call contracts
    }

    public TransactionReceipt addEntry(PricingRecord record) throws Exception{
        return adminTarrifService.addEntry(BigInteger.valueOf(record.carType()), BigInteger.valueOf(record.price())).send();
    }

    public Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>> getPricing() throws Exception{
        return adminTarrifService.getPricing().send();
    }


}
