package pl.edu.agh.rental.rentalContract;

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
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005057600080fd5b50610b95806100606000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80635eae513414610051578063b79eaecc14610071578063ee9b2940146100a1578063ffdab909146100bd575b600080fd5b6100596100d9565b60405161006893929190610801565b60405180910390f35b61008b6004803603810190610086919061087e565b610337565b60405161009891906108ba565b60405180910390f35b6100bb60048036038101906100b691906108d5565b61047e565b005b6100d760048036038101906100d2919061087e565b61053f565b005b6060806060600060018054905067ffffffffffffffff8111156100ff576100fe610915565b5b60405190808252806020026020018201604052801561012d5781602001602082028036833780820191505090505b509050600060018054905067ffffffffffffffff81111561015157610150610915565b5b60405190808252806020026020018201604052801561017f5781602001602082028036833780820191505090505b509050600060018054905067ffffffffffffffff8111156101a3576101a2610915565b5b6040519080825280602002602001820160405280156101d15781602001602082028036833780820191505090505b50905060005b60018054905081101561032557600060018111156101f8576101f7610944565b5b6001828154811061020c5761020b610973565b5b906000526020600020906004020160030160009054906101000a900460ff16600181111561023d5761023c610944565b5b03610312576001818154811061025657610255610973565b5b90600052602060002090600402016000015484828151811061027b5761027a610973565b5b6020026020010181815250506001818154811061029b5761029a610973565b5b9060005260206000209060040201600101548382815181106102c0576102bf610973565b5b602002602001018181525050600181815481106102e0576102df610973565b5b90600052602060002090600402016002015482828151811061030557610304610973565b5b6020026020010181815250505b808061031d906109d1565b9150506101d7565b50828282955095509550505050909192565b60008060018054905090505b600081111561043d57826001808361035b9190610a19565b8154811061036c5761036b610973565b5b9060005260206000209060040201600001541480156103ec57506000600181111561039a57610399610944565b5b600180836103a89190610a19565b815481106103b9576103b8610973565b5b906000526020600020906004020160030160009054906101000a900460ff1660018111156103ea576103e9610944565b5b145b1561042a57600180826103ff9190610a19565b815481106104105761040f610973565b5b906000526020600020906004020160010154915050610479565b808061043590610a4d565b915050610343565b506040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161047090610ad3565b60405180910390fd5b919050565b6104866106a9565b60006040518060800160405280848152602001838152602001428152602001600060018111156104b9576104b8610944565b5b8152509050600181908060018154018082558091505060019003906000526020600020906004020160009091909190915060008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a81548160ff0219169083600181111561053357610532610944565b5b02179055505050505050565b6105476106a9565b600060018054905090505b600081111561066d57816001808361056a9190610a19565b8154811061057b5761057a610973565b5b9060005260206000209060040201600001541480156105fb5750600060018111156105a9576105a8610944565b5b600180836105b79190610a19565b815481106105c8576105c7610973565b5b906000526020600020906004020160030160009054906101000a900460ff1660018111156105f9576105f8610944565b5b145b1561065a576001806001836106109190610a19565b8154811061062157610620610973565b5b906000526020600020906004020160030160006101000a81548160ff0219169083600181111561065457610653610944565b5b02179055505b808061066590610a4d565b915050610552565b506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106a090610ad3565b60405180910390fd5b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610737576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161072e90610b3f565b60405180910390fd5b565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000819050919050565b61077881610765565b82525050565b600061078a838361076f565b60208301905092915050565b6000602082019050919050565b60006107ae82610739565b6107b88185610744565b93506107c383610755565b8060005b838110156107f45781516107db888261077e565b97506107e683610796565b9250506001810190506107c7565b5085935050505092915050565b6000606082019050818103600083015261081b81866107a3565b9050818103602083015261082f81856107a3565b9050818103604083015261084381846107a3565b9050949350505050565b600080fd5b61085b81610765565b811461086657600080fd5b50565b60008135905061087881610852565b92915050565b6000602082840312156108945761089361084d565b5b60006108a284828501610869565b91505092915050565b6108b481610765565b82525050565b60006020820190506108cf60008301846108ab565b92915050565b600080604083850312156108ec576108eb61084d565b5b60006108fa85828601610869565b925050602061090b85828601610869565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006109dc82610765565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203610a0e57610a0d6109a2565b5b600182019050919050565b6000610a2482610765565b9150610a2f83610765565b925082821015610a4257610a416109a2565b5b828203905092915050565b6000610a5882610765565b915060008203610a6b57610a6a6109a2565b5b600182039050919050565b600082825260208201905092915050565b7f4e6f20656e74727920666f722074686174206361725479706549440000000000600082015250565b6000610abd601b83610a76565b9150610ac882610a87565b602082019050919050565b60006020820190508181036000830152610aec81610ab0565b9050919050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b6000610b29601e83610a76565b9150610b3482610af3565b602082019050919050565b60006020820190508181036000830152610b5881610b1c565b905091905056fea26469706673582212203ede9e2e6f299cb9acf250328eeacce43f6681aeaf26602bf3f338329d804da164736f6c634300080e0033";

    public static final String FUNC_ADDENTRY = "addEntry";

    public static final String FUNC_REMOVEENTRY = "removeEntry";

    public static final String FUNC_GETFULLPRICING = "getFullPricing";

    public static final String FUNC_GETCURRENTCARPRICING = "getCurrentCarPricing";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1666472115220", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666449322535", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666473966870", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666516197291", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1665850328675", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666519826886", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666551153486", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
        _addresses.put("1666474286183", "0x3D21EB2e5590Ee645fFB13024621Ca05728D6774");
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
