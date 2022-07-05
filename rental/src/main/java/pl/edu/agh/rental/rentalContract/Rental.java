package pl.edu.agh.rental.rentalContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
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
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600060015534801561005557600080fd5b50611450806100656000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806317c39286146100675780634cf2010e146100855780637e26b816146100a157806385a32bc4146100d1578063b31c0a9a146100ed578063f2645dbe1461011d575b600080fd5b61006f61013c565b60405161007c9190610c88565b60405180910390f35b61009f600480360381019061009a9190610cdb565b61019c565b005b6100bb60048036038101906100b69190610cdb565b610219565b6040516100c89190610dfa565b60405180910390f35b6100eb60048036038101906100e69190610e15565b610413565b005b61010760048036038101906101029190610e55565b610666565b6040516101149190610eb7565b60405180910390f35b610125610808565b604051610133929190610f9c565b60405180910390f35b606061014661095e565b600280548060200260200160405190810160405280929190818152602001828054801561019257602002820191906000526020600020905b81548152602001906001019080831161017e575b5050505050905090565b6101a461095e565b6101ad816109ee565b156101ed576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101e490611030565b60405180910390fd5b600281908060018154018082558091505060019003906000526020600020016000909190919091505550565b610221610b78565b600060058054905090505b60008111156103d257826005600183610245919061107f565b81548110610256576102556110b3565b5b906000526020600020906006020160020154036103bf576001808111156102805761027f610d08565b5b600560018361028f919061107f565b815481106102a05761029f6110b3565b5b906000526020600020906006020160050160009054906101000a900460ff1660018111156102d1576102d0610d08565b5b03610311576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103089061112e565b60405180910390fd5b6005600182610320919061107f565b81548110610331576103306110b3565b5b90600052602060002090600602016040518060c001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff1660018111156103a1576103a0610d08565b5b60018111156103b3576103b2610d08565b5b8152505091505061040e565b80806103ca9061114e565b91505061022c565b506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104059061112e565b60405180910390fd5b919050565b61041c82610a50565b61045b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610452906111c3565b60405180910390fd5b61046482610a70565b6104a3576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161049a90611255565b60405180910390fd5b60006005600160036000868152602001908152602001600020546104c7919061107f565b815481106104d8576104d76110b3565b5b90600052602060002090600602016040518060c001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff16600181111561054857610547610d08565b5b600181111561055a57610559610d08565b5b81525050905060006040518060c001604052808481526020018360200151815260200183604001518152602001428152602001836080015181526020016001808111156105aa576105a9610d08565b5b81525090506005819080600181540180825580915050600190039060005260206000209060060201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff0219169083600181111561063857610637610d08565b5b0217905550505060058054905060046000846080015181526020019081526020016000208190555050505050565b6000610671836109ee565b6106b0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106a7906112c1565b60405180910390fd5b60006040518060c001604052808681526020018581526020018481526020014281526020016001548152602001600060018111156106f1576106f0610d08565b5b81525090506005819080600181540180825580915050600190039060005260206000209060060201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff0219169083600181111561077f5761077e610d08565b5b021790555050506005805490506003600060015481526020019081526020016000208190555060018060008282546107b791906112e1565b925050819055507f3002c20c0259d29efbca701677fc4e3a5bc849a584f8e73a3e1b3ab46c8f9d3481608001516040516107f19190610eb7565b60405180910390a180608001519150509392505050565b606080600060028054905067ffffffffffffffff81111561082c5761082b611337565b5b60405190808252806020026020018201604052801561085a5781602001602082028036833780820191505090505b50905060005b6002805490508110156108ff5761089460028281548110610884576108836110b3565b5b9060005260206000200154610a90565b6108c45760008282815181106108ad576108ac6110b3565b5b6020026020010190151590811515815250506108ec565b60018282815181106108d9576108d86110b3565b5b6020026020010190151590811515815250505b80806108f790611366565b915050610860565b506002818180548060200260200160405190810160405280929190818152602001828054801561094e57602002820191906000526020600020905b81548152602001906001019080831161093a575b5050505050915092509250509091565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146109ec576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109e3906113fa565b60405180910390fd5b565b600080600090505b600280549050811015610a45578260028281548110610a1857610a176110b3565b5b906000526020600020015403610a32576001915050610a4b565b8080610a3d90611366565b9150506109f6565b50600090505b919050565b600080600360008481526020019081526020016000205414159050919050565b600080600460008481526020019081526020016000205414159050919050565b60008060058054905090505b6000811115610b6d57826005600183610ab5919061107f565b81548110610ac657610ac56110b3565b5b90600052602060002090600602016001015403610b5a57600180811115610af057610aef610d08565b5b6005600183610aff919061107f565b81548110610b1057610b0f6110b3565b5b906000526020600020906006020160050160009054906101000a900460ff166001811115610b4157610b40610d08565b5b03610b50576000915050610b73565b6001915050610b73565b8080610b659061114e565b915050610a9c565b50600090505b919050565b6040518060c00160405280600081526020016000815260200160008152602001600081526020016000815260200160006001811115610bba57610bb9610d08565b5b81525090565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000819050919050565b610bff81610bec565b82525050565b6000610c118383610bf6565b60208301905092915050565b6000602082019050919050565b6000610c3582610bc0565b610c3f8185610bcb565b9350610c4a83610bdc565b8060005b83811015610c7b578151610c628882610c05565b9750610c6d83610c1d565b925050600181019050610c4e565b5085935050505092915050565b60006020820190508181036000830152610ca28184610c2a565b905092915050565b600080fd5b610cb881610bec565b8114610cc357600080fd5b50565b600081359050610cd581610caf565b92915050565b600060208284031215610cf157610cf0610caa565b5b6000610cff84828501610cc6565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b60028110610d4857610d47610d08565b5b50565b6000819050610d5982610d37565b919050565b6000610d6982610d4b565b9050919050565b610d7981610d5e565b82525050565b60c082016000820151610d956000850182610bf6565b506020820151610da86020850182610bf6565b506040820151610dbb6040850182610bf6565b506060820151610dce6060850182610bf6565b506080820151610de16080850182610bf6565b5060a0820151610df460a0850182610d70565b50505050565b600060c082019050610e0f6000830184610d7f565b92915050565b60008060408385031215610e2c57610e2b610caa565b5b6000610e3a85828601610cc6565b9250506020610e4b85828601610cc6565b9150509250929050565b600080600060608486031215610e6e57610e6d610caa565b5b6000610e7c86828701610cc6565b9350506020610e8d86828701610cc6565b9250506040610e9e86828701610cc6565b9150509250925092565b610eb181610bec565b82525050565b6000602082019050610ecc6000830184610ea8565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b60008115159050919050565b610f1381610efe565b82525050565b6000610f258383610f0a565b60208301905092915050565b6000602082019050919050565b6000610f4982610ed2565b610f538185610edd565b9350610f5e83610eee565b8060005b83811015610f8f578151610f768882610f19565b9750610f8183610f31565b925050600181019050610f62565b5085935050505092915050565b60006040820190508181036000830152610fb68185610c2a565b90508181036020830152610fca8184610f3e565b90509392505050565b600082825260208201905092915050565b7f43617220616c7265616479206578697374732100000000000000000000000000600082015250565b600061101a601383610fd3565b915061102582610fe4565b602082019050919050565b600060208201905081810360008301526110498161100d565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061108a82610bec565b915061109583610bec565b9250828210156110a8576110a7611050565b5b828203905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e6f206163746976652072656e74616c207265636f7264000000000000000000600082015250565b6000611118601783610fd3565b9150611123826110e2565b602082019050919050565b600060208201905081810360008301526111478161110b565b9050919050565b600061115982610bec565b91506000820361116c5761116b611050565b5b600182039050919050565b7f4e6f2072656e74616c2077697468207468617420494420737461727465640000600082015250565b60006111ad601e83610fd3565b91506111b882611177565b602082019050919050565b600060208201905081810360008301526111dc816111a0565b9050919050565b7f52656e74616c207769746820746861742049442068617320616c72656164792060008201527f656e646564000000000000000000000000000000000000000000000000000000602082015250565b600061123f602583610fd3565b915061124a826111e3565b604082019050919050565b6000602082019050818103600083015261126e81611232565b9050919050565b7f43617220646f6573206e6f742065786973742100000000000000000000000000600082015250565b60006112ab601383610fd3565b91506112b682611275565b602082019050919050565b600060208201905081810360008301526112da8161129e565b9050919050565b60006112ec82610bec565b91506112f783610bec565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0382111561132c5761132b611050565b5b828201905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600061137182610bec565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036113a3576113a2611050565b5b600182019050919050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b60006113e4601e83610fd3565b91506113ef826113ae565b602082019050919050565b60006020820190508181036000830152611413816113d7565b905091905056fea264697066735822122013f3347c846167e1359a2742ac8ac842815887c0ef63ff772fc37f2ad5bfc5e864736f6c634300080e0033";

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
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDEDNEWRENTALID_EVENT, transactionReceipt);
        ArrayList<AddedNewRentalIDEventResponse> responses = new ArrayList<AddedNewRentalIDEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
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
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDEDNEWRENTALID_EVENT, log);
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_carID)), 
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rentTime), 
                new org.web3j.abi.datatypes.generated.Uint256(_carID), 
                new org.web3j.abi.datatypes.generated.Uint256(_userID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> endRental(BigInteger rentalID, BigInteger _endRentTime) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ENDRENTAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(rentalID), 
                new org.web3j.abi.datatypes.generated.Uint256(_endRentTime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<RentalRecord> getActiveRental(BigInteger _userID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETACTIVERENTAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_userID)), 
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
            super(new org.web3j.abi.datatypes.generated.Uint256(rentTime),new org.web3j.abi.datatypes.generated.Uint256(carID),new org.web3j.abi.datatypes.generated.Uint256(userID),new org.web3j.abi.datatypes.generated.Uint256(blockchainTime),new org.web3j.abi.datatypes.generated.Uint256(rentalID),new org.web3j.abi.datatypes.generated.Uint8(rentalState));
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
