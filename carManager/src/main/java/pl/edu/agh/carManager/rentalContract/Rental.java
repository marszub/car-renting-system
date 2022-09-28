//BEGIN GENERATED CONTENT
package pl.edu.agh.carManager.rentalContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Callable;

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
public class Rental extends Contract {
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001805534801561005457600080fd5b50611451806100646000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806317c39286146100675780634cf2010e146100855780637e26b816146100a157806385a32bc4146100d1578063b31c0a9a146100ed578063f2645dbe1461011d575b600080fd5b61006f61013c565b60405161007c9190610c89565b60405180910390f35b61009f600480360381019061009a9190610cdc565b61019c565b005b6100bb60048036038101906100b69190610cdc565b610219565b6040516100c89190610dfb565b60405180910390f35b6100eb60048036038101906100e69190610e16565b610413565b005b61010760048036038101906101029190610e56565b610667565b6040516101149190610eb8565b60405180910390f35b610125610809565b604051610133929190610f9d565b60405180910390f35b606061014661095f565b600280548060200260200160405190810160405280929190818152602001828054801561019257602002820191906000526020600020905b81548152602001906001019080831161017e575b5050505050905090565b6101a461095f565b6101ad816109ef565b156101ed576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101e490611031565b60405180910390fd5b600281908060018154018082558091505060019003906000526020600020016000909190919091505550565b610221610b79565b600060058054905090505b60008111156103d2578260056001836102459190611080565b81548110610256576102556110b4565b5b906000526020600020906006020160020154036103bf576001808111156102805761027f610d09565b5b600560018361028f9190611080565b815481106102a05761029f6110b4565b5b906000526020600020906006020160050160009054906101000a900460ff1660018111156102d1576102d0610d09565b5b03610311576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103089061112f565b60405180910390fd5b60056001826103209190611080565b81548110610331576103306110b4565b5b90600052602060002090600602016040518060c001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff1660018111156103a1576103a0610d09565b5b60018111156103b3576103b2610d09565b5b8152505091505061040e565b80806103ca9061114f565b91505061022c565b506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104059061112f565b60405180910390fd5b919050565b61041c82610a51565b61045b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610452906111c4565b60405180910390fd5b61046482610a71565b156104a4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161049b90611256565b60405180910390fd5b60006005600160036000868152602001908152602001600020546104c89190611080565b815481106104d9576104d86110b4565b5b90600052602060002090600602016040518060c001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff16600181111561054957610548610d09565b5b600181111561055b5761055a610d09565b5b81525050905060006040518060c001604052808481526020018360200151815260200183604001518152602001428152602001836080015181526020016001808111156105ab576105aa610d09565b5b81525090506005819080600181540180825580915050600190039060005260206000209060060201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff0219169083600181111561063957610638610d09565b5b0217905550505060058054905060046000846080015181526020019081526020016000208190555050505050565b6000610672836109ef565b6106b1576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106a8906112c2565b60405180910390fd5b60006040518060c001604052808681526020018581526020018481526020014281526020016001548152602001600060018111156106f2576106f1610d09565b5b81525090506005819080600181540180825580915050600190039060005260206000209060060201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff021916908360018111156107805761077f610d09565b5b021790555050506005805490506003600060015481526020019081526020016000208190555060018060008282546107b891906112e2565b925050819055507f3002c20c0259d29efbca701677fc4e3a5bc849a584f8e73a3e1b3ab46c8f9d3481608001516040516107f29190610eb8565b60405180910390a180608001519150509392505050565b606080600060028054905067ffffffffffffffff81111561082d5761082c611338565b5b60405190808252806020026020018201604052801561085b5781602001602082028036833780820191505090505b50905060005b6002805490508110156109005761089560028281548110610885576108846110b4565b5b9060005260206000200154610a91565b6108c55760008282815181106108ae576108ad6110b4565b5b6020026020010190151590811515815250506108ed565b60018282815181106108da576108d96110b4565b5b6020026020010190151590811515815250505b80806108f890611367565b915050610861565b506002818180548060200260200160405190810160405280929190818152602001828054801561094f57602002820191906000526020600020905b81548152602001906001019080831161093b575b5050505050915092509250509091565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146109ed576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109e4906113fb565b60405180910390fd5b565b600080600090505b600280549050811015610a46578260028281548110610a1957610a186110b4565b5b906000526020600020015403610a33576001915050610a4c565b8080610a3e90611367565b9150506109f7565b50600090505b919050565b600080600360008481526020019081526020016000205414159050919050565b600080600460008481526020019081526020016000205414159050919050565b60008060058054905090505b6000811115610b6e57826005600183610ab69190611080565b81548110610ac757610ac66110b4565b5b90600052602060002090600602016001015403610b5b57600180811115610af157610af0610d09565b5b6005600183610b009190611080565b81548110610b1157610b106110b4565b5b906000526020600020906006020160050160009054906101000a900460ff166001811115610b4257610b41610d09565b5b03610b51576000915050610b74565b6001915050610b74565b8080610b669061114f565b915050610a9d565b50600090505b919050565b6040518060c00160405280600081526020016000815260200160008152602001600081526020016000815260200160006001811115610bbb57610bba610d09565b5b81525090565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000819050919050565b610c0081610bed565b82525050565b6000610c128383610bf7565b60208301905092915050565b6000602082019050919050565b6000610c3682610bc1565b610c408185610bcc565b9350610c4b83610bdd565b8060005b83811015610c7c578151610c638882610c06565b9750610c6e83610c1e565b925050600181019050610c4f565b5085935050505092915050565b60006020820190508181036000830152610ca38184610c2b565b905092915050565b600080fd5b610cb981610bed565b8114610cc457600080fd5b50565b600081359050610cd681610cb0565b92915050565b600060208284031215610cf257610cf1610cab565b5b6000610d0084828501610cc7565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b60028110610d4957610d48610d09565b5b50565b6000819050610d5a82610d38565b919050565b6000610d6a82610d4c565b9050919050565b610d7a81610d5f565b82525050565b60c082016000820151610d966000850182610bf7565b506020820151610da96020850182610bf7565b506040820151610dbc6040850182610bf7565b506060820151610dcf6060850182610bf7565b506080820151610de26080850182610bf7565b5060a0820151610df560a0850182610d71565b50505050565b600060c082019050610e106000830184610d80565b92915050565b60008060408385031215610e2d57610e2c610cab565b5b6000610e3b85828601610cc7565b9250506020610e4c85828601610cc7565b9150509250929050565b600080600060608486031215610e6f57610e6e610cab565b5b6000610e7d86828701610cc7565b9350506020610e8e86828701610cc7565b9250506040610e9f86828701610cc7565b9150509250925092565b610eb281610bed565b82525050565b6000602082019050610ecd6000830184610ea9565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b60008115159050919050565b610f1481610eff565b82525050565b6000610f268383610f0b565b60208301905092915050565b6000602082019050919050565b6000610f4a82610ed3565b610f548185610ede565b9350610f5f83610eef565b8060005b83811015610f90578151610f778882610f1a565b9750610f8283610f32565b925050600181019050610f63565b5085935050505092915050565b60006040820190508181036000830152610fb78185610c2b565b90508181036020830152610fcb8184610f3f565b90509392505050565b600082825260208201905092915050565b7f43617220616c7265616479206578697374732100000000000000000000000000600082015250565b600061101b601383610fd4565b915061102682610fe5565b602082019050919050565b6000602082019050818103600083015261104a8161100e565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061108b82610bed565b915061109683610bed565b9250828210156110a9576110a8611051565b5b828203905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e6f206163746976652072656e74616c207265636f7264000000000000000000600082015250565b6000611119601783610fd4565b9150611124826110e3565b602082019050919050565b600060208201905081810360008301526111488161110c565b9050919050565b600061115a82610bed565b91506000820361116d5761116c611051565b5b600182039050919050565b7f4e6f2072656e74616c2077697468207468617420494420737461727465640000600082015250565b60006111ae601e83610fd4565b91506111b982611178565b602082019050919050565b600060208201905081810360008301526111dd816111a1565b9050919050565b7f52656e74616c207769746820746861742049442068617320616c72656164792060008201527f656e646564000000000000000000000000000000000000000000000000000000602082015250565b6000611240602583610fd4565b915061124b826111e4565b604082019050919050565b6000602082019050818103600083015261126f81611233565b9050919050565b7f43617220646f6573206e6f742065786973742100000000000000000000000000600082015250565b60006112ac601383610fd4565b91506112b782611276565b602082019050919050565b600060208201905081810360008301526112db8161129f565b9050919050565b60006112ed82610bed565b91506112f883610bed565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0382111561132d5761132c611051565b5b828201905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600061137282610bed565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036113a4576113a3611051565b5b600182019050919050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b60006113e5601e83610fd4565b91506113f0826113af565b602082019050919050565b60006020820190508181036000830152611414816113d8565b905091905056fea264697066735822122075f33c78ee2490a9ca55f071846368ff725f3493f9085532df3ef55fe0eb01e364736f6c634300080e0033";

    public static final String FUNC_ADDCAR = "addCar";

    public static final String FUNC_GETCARS = "getCars";

    public static final String FUNC_STARTRENTAL = "startRental";

    public static final String FUNC_ENDRENTAL = "endRental";

    public static final String FUNC_GETACTIVERENTAL = "getActiveRental";

    public static final String FUNC_GETALLAVAILABLECARS = "getAllAvailableCars";

    public static final Event ADDEDNEWRENTALID_EVENT = new Event("addedNewRentalID", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
    }

    @Deprecated
    protected Rental(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Rental(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Rental(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Rental(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AddedNewRentalIDEventResponse> getAddedNewRentalIDEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ADDEDNEWRENTALID_EVENT, transactionReceipt);
        ArrayList<AddedNewRentalIDEventResponse> responses = new ArrayList<AddedNewRentalIDEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AddedNewRentalIDEventResponse typedResponse = new AddedNewRentalIDEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reservationID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddedNewRentalIDEventResponse> addedNewRentalIDEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AddedNewRentalIDEventResponse>() {
            @Override
            public AddedNewRentalIDEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ADDEDNEWRENTALID_EVENT, log);
                AddedNewRentalIDEventResponse typedResponse = new AddedNewRentalIDEventResponse();
                typedResponse.log = log;
                typedResponse.reservationID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddedNewRentalIDEventResponse> addedNewRentalIDEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDEDNEWRENTALID_EVENT));
        return addedNewRentalIDEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addCar(BigInteger _carID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDCAR, 
                Arrays.<Type>asList(new Uint256(_carID)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getCars() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCARS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> startRental(BigInteger _rentTime, BigInteger _carID, BigInteger _userID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STARTRENTAL, 
                Arrays.<Type>asList(new Uint256(_rentTime),
                new Uint256(_carID),
                new Uint256(_userID)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> endRental(BigInteger rentalID, BigInteger _endRentTime) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ENDRENTAL, 
                Arrays.<Type>asList(new Uint256(rentalID),
                new Uint256(_endRentTime)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<RentalRecord> getActiveRental(BigInteger _userID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETACTIVERENTAL, 
                Arrays.<Type>asList(new Uint256(_userID)),
                Arrays.<TypeReference<?>>asList(new TypeReference<RentalRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, RentalRecord.class);
    }

    public RemoteFunctionCall<Tuple2<List<BigInteger>, List<Boolean>>> getAllAvailableCars() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETALLAVAILABLECARS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Bool>>() {}));
        return new RemoteFunctionCall<Tuple2<List<BigInteger>, List<Boolean>>>(function,
                new Callable<Tuple2<List<BigInteger>, List<Boolean>>>() {
                    @Override
                    public Tuple2<List<BigInteger>, List<Boolean>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<List<BigInteger>, List<Boolean>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Bool>) results.get(1).getValue()));
                    }
                });
    }

    @Deprecated
    public static Rental load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Rental(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Rental load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Rental(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Rental load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Rental(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Rental load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Rental(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Rental> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Rental.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Rental> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Rental.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Rental> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Rental.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Rental> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Rental.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class RentalRecord extends StaticStruct {
        public BigInteger rentTime;

        public BigInteger carID;

        public BigInteger userID;

        public BigInteger blockchainTime;

        public BigInteger rentalID;

        public BigInteger rentalState;

        public RentalRecord(BigInteger rentTime, BigInteger carID, BigInteger userID, BigInteger blockchainTime, BigInteger rentalID, BigInteger rentalState) {
            super(new Uint256(rentTime),new Uint256(carID),new Uint256(userID),new Uint256(blockchainTime),new Uint256(rentalID),new Uint8(rentalState));
            this.rentTime = rentTime;
            this.carID = carID;
            this.userID = userID;
            this.blockchainTime = blockchainTime;
            this.rentalID = rentalID;
            this.rentalState = rentalState;
        }

        public RentalRecord(Uint256 rentTime, Uint256 carID, Uint256 userID, Uint256 blockchainTime, Uint256 rentalID, Uint8 rentalState) {
            super(rentTime,carID,userID,blockchainTime,rentalID,rentalState);
            this.rentTime = rentTime.getValue();
            this.carID = carID.getValue();
            this.userID = userID.getValue();
            this.blockchainTime = blockchainTime.getValue();
            this.rentalID = rentalID.getValue();
            this.rentalState = rentalState.getValue();
        }
    }

    public static class AddedNewRentalIDEventResponse extends BaseEventResponse {
        public BigInteger reservationID;
    }
}
//END GENERATED CONTENT
