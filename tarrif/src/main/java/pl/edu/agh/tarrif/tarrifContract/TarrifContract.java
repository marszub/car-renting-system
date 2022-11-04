package pl.edu.agh.tarrif.tarrifContract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class TarrifContract extends Contract {
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005057600080fd5b50610d13806100606000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806332f970a91461005c5780635eae513414610078578063b79eaecc14610098578063ee9b2940146100c8578063ffdab909146100e4575b600080fd5b610076600480360381019061007191906108f2565b610100565b005b61008061024f565b60405161008f939291906109f0565b60405180910390f35b6100b260048036038101906100ad9190610a3c565b6104ad565b6040516100bf9190610a78565b60405180910390f35b6100e260048036038101906100dd91906108f2565b6105f4565b005b6100fe60048036038101906100f99190610a3c565b6106b5565b005b610108610827565b600060018054905090505b600081111561020f57826001808361012b9190610ac2565b8154811061013c5761013b610af6565b5b9060005260206000209060040201600001541480156101bc57506000600181111561016a57610169610b25565b5b600180836101789190610ac2565b8154811061018957610188610af6565b5b906000526020600020906004020160030160009054906101000a900460ff1660018111156101ba576101b9610b25565b5b145b156101fc5781600180836101d09190610ac2565b815481106101e1576101e0610af6565b5b9060005260206000209060040201600101819055505061024b565b808061020790610b54565b915050610113565b506040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161024290610bda565b60405180910390fd5b5050565b6060806060600060018054905067ffffffffffffffff81111561027557610274610bfa565b5b6040519080825280602002602001820160405280156102a35781602001602082028036833780820191505090505b509050600060018054905067ffffffffffffffff8111156102c7576102c6610bfa565b5b6040519080825280602002602001820160405280156102f55781602001602082028036833780820191505090505b509050600060018054905067ffffffffffffffff81111561031957610318610bfa565b5b6040519080825280602002602001820160405280156103475781602001602082028036833780820191505090505b50905060005b60018054905081101561049b576000600181111561036e5761036d610b25565b5b6001828154811061038257610381610af6565b5b906000526020600020906004020160030160009054906101000a900460ff1660018111156103b3576103b2610b25565b5b0361048857600181815481106103cc576103cb610af6565b5b9060005260206000209060040201600001548482815181106103f1576103f0610af6565b5b6020026020010181815250506001818154811061041157610410610af6565b5b90600052602060002090600402016001015483828151811061043657610435610af6565b5b6020026020010181815250506001818154811061045657610455610af6565b5b90600052602060002090600402016002015482828151811061047b5761047a610af6565b5b6020026020010181815250505b808061049390610c29565b91505061034d565b50828282955095509550505050909192565b60008060018054905090505b60008111156105b35782600180836104d19190610ac2565b815481106104e2576104e1610af6565b5b9060005260206000209060040201600001541480156105625750600060018111156105105761050f610b25565b5b6001808361051e9190610ac2565b8154811061052f5761052e610af6565b5b906000526020600020906004020160030160009054906101000a900460ff1660018111156105605761055f610b25565b5b145b156105a057600180826105759190610ac2565b8154811061058657610585610af6565b5b9060005260206000209060040201600101549150506105ef565b80806105ab90610b54565b9150506104b9565b506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105e690610bda565b60405180910390fd5b919050565b6105fc610827565b600060405180608001604052808481526020018381526020014281526020016000600181111561062f5761062e610b25565b5b8152509050600181908060018154018082558091505060019003906000526020600020906004020160009091909190915060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff021916908360018111156106a9576106a8610b25565b5b02179055505050505050565b6106bd610827565b600060018054905090505b60008111156107e85781600180836106e09190610ac2565b815481106106f1576106f0610af6565b5b90600052602060002090600402016000015414801561077157506000600181111561071f5761071e610b25565b5b6001808361072d9190610ac2565b8154811061073e5761073d610af6565b5b906000526020600020906004020160030160009054906101000a900460ff16600181111561076f5761076e610b25565b5b145b156107d5576001806001836107869190610ac2565b8154811061079757610796610af6565b5b906000526020600020906004020160030160006101000a81548160ff021916908360018111156107ca576107c9610b25565b5b021790555050610824565b80806107e090610b54565b9150506106c8565b506040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161081b90610bda565b60405180910390fd5b50565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146108b5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108ac90610cbd565b60405180910390fd5b565b600080fd5b6000819050919050565b6108cf816108bc565b81146108da57600080fd5b50565b6000813590506108ec816108c6565b92915050565b60008060408385031215610909576109086108b7565b5b6000610917858286016108dd565b9250506020610928858286016108dd565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b610967816108bc565b82525050565b6000610979838361095e565b60208301905092915050565b6000602082019050919050565b600061099d82610932565b6109a7818561093d565b93506109b28361094e565b8060005b838110156109e35781516109ca888261096d565b97506109d583610985565b9250506001810190506109b6565b5085935050505092915050565b60006060820190508181036000830152610a0a8186610992565b90508181036020830152610a1e8185610992565b90508181036040830152610a328184610992565b9050949350505050565b600060208284031215610a5257610a516108b7565b5b6000610a60848285016108dd565b91505092915050565b610a72816108bc565b82525050565b6000602082019050610a8d6000830184610a69565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610acd826108bc565b9150610ad8836108bc565b925082821015610aeb57610aea610a93565b5b828203905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b6000610b5f826108bc565b915060008203610b7257610b71610a93565b5b600182039050919050565b600082825260208201905092915050565b7f4e6f20656e74727920666f722074686174206361725479706549440000000000600082015250565b6000610bc4601b83610b7d565b9150610bcf82610b8e565b602082019050919050565b60006020820190508181036000830152610bf381610bb7565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000610c34826108bc565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203610c6657610c65610a93565b5b600182019050919050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b6000610ca7601e83610b7d565b9150610cb282610c71565b602082019050919050565b60006020820190508181036000830152610cd681610c9a565b905091905056fea264697066735822122015bbe1b27bf4bcda09d3009258f0a16d50d68f7bbca1890cce77dcdbe2bc649e64736f6c634300080e0033";

    public static final String FUNC_ADDENTRY = "addEntry";

    public static final String FUNC_REMOVEENTRY = "removeEntry";

    public static final String FUNC_EDITENTRY = "editEntry";

    public static final String FUNC_GETFULLPRICING = "getFullPricing";

    public static final String FUNC_GETCURRENTCARPRICING = "getCurrentCarPricing";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1666473966870", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1667164986128", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666516197291", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1665850328675", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1667257819837", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666519826886", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1667339524962", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666551153486", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1667245156373", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666472115220", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1667339803463", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666449322535", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1667339672283", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1667325939470", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666474286183", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1667257617733", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1665847779318", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
    }

    @Deprecated
    protected TarrifContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TarrifContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TarrifContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TarrifContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addEntry(BigInteger _carTypeID, BigInteger _pricePerMinute) {
        final Function function = new Function(
                FUNC_ADDENTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_carTypeID), 
                new org.web3j.abi.datatypes.generated.Uint256(_pricePerMinute)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeEntry(BigInteger _carTypeID) {
        final Function function = new Function(
                FUNC_REMOVEENTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_carTypeID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> editEntry(BigInteger _carTypeID, BigInteger _pricePerMinute) {
        final Function function = new Function(
                FUNC_EDITENTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_carTypeID), 
                new org.web3j.abi.datatypes.generated.Uint256(_pricePerMinute)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>> getFullPricing() {
        final Function function = new Function(FUNC_GETFULLPRICING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>>(function,
                new Callable<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Uint256>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getCurrentCarPricing(BigInteger _carTypeID) {
        final Function function = new Function(FUNC_GETCURRENTCARPRICING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_carTypeID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static TarrifContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TarrifContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TarrifContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TarrifContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TarrifContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TarrifContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TarrifContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TarrifContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TarrifContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TarrifContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TarrifContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TarrifContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TarrifContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TarrifContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TarrifContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TarrifContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
