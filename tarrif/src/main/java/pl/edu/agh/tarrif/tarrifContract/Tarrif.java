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
public class Tarrif extends Contract {
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005057600080fd5b5061068b806100606000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c806338b4e3381461003b578063ee9b29401461005b575b600080fd5b610043610077565b60405161005293929190610446565b60405180910390f35b610075600480360381019061007091906104c3565b610276565b005b6060806060600060018054905067ffffffffffffffff81111561009d5761009c610503565b5b6040519080825280602002602001820160405280156100cb5781602001602082028036833780820191505090505b509050600060018054905067ffffffffffffffff8111156100ef576100ee610503565b5b60405190808252806020026020018201604052801561011d5781602001602082028036833780820191505090505b509050600060018054905067ffffffffffffffff81111561014157610140610503565b5b60405190808252806020026020018201604052801561016f5781602001602082028036833780820191505090505b50905060005b600180549050811015610264576001818154811061019657610195610532565b5b9060005260206000209060030201600001548482815181106101bb576101ba610532565b5b602002602001018181525050600181815481106101db576101da610532565b5b906000526020600020906003020160010154838281518110610200576101ff610532565b5b602002602001018181525050600181815481106102205761021f610532565b5b90600052602060002090600302016002015482828151811061024557610244610532565b5b602002602001018181525050808061025c90610590565b915050610175565b50828282955095509550505050909192565b61027e6102ee565b6000604051806060016040528084815260200183815260200142815250905060018190806001815401808255809150506001900390600052602060002090600302016000909190919091506000820151816000015560208201518160010155604082015181600201555050505050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461037c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161037390610635565b60405180910390fd5b565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000819050919050565b6103bd816103aa565b82525050565b60006103cf83836103b4565b60208301905092915050565b6000602082019050919050565b60006103f38261037e565b6103fd8185610389565b93506104088361039a565b8060005b8381101561043957815161042088826103c3565b975061042b836103db565b92505060018101905061040c565b5085935050505092915050565b6000606082019050818103600083015261046081866103e8565b9050818103602083015261047481856103e8565b9050818103604083015261048881846103e8565b9050949350505050565b600080fd5b6104a0816103aa565b81146104ab57600080fd5b50565b6000813590506104bd81610497565b92915050565b600080604083850312156104da576104d9610492565b5b60006104e8858286016104ae565b92505060206104f9858286016104ae565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061059b826103aa565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036105cd576105cc610561565b5b600182019050919050565b600082825260208201905092915050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b600061061f601e836105d8565b915061062a826105e9565b602082019050919050565b6000602082019050818103600083015261064e81610612565b905091905056fea26469706673582212208ed516d543c0d66529540893814aeb5c132688baa2f96352bfc4a4195b2a1b0164736f6c634300080e0033";

    public static final String FUNC_ADDENTRY = "addEntry";

    public static final String FUNC_GETPRICING = "getPricing";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected Tarrif(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Tarrif(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Tarrif(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Tarrif(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<Tuple3<List<BigInteger>, List<BigInteger>, List<BigInteger>>> getPricing() {
        final Function function = new Function(FUNC_GETPRICING, 
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

    @Deprecated
    public static Tarrif load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Tarrif(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Tarrif load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Tarrif(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Tarrif load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Tarrif(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Tarrif load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Tarrif(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Tarrif> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Tarrif.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Tarrif> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Tarrif.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Tarrif> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Tarrif.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Tarrif> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Tarrif.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
