package pl.agh.edu.cardatabase.rentalContract;

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
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001805534801561005457600080fd5b5061123b806100646000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806317c392861461005c5780634cf2010e1461007a5780637e26b8161461009657806385a32bc4146100c6578063b31c0a9a146100e2575b600080fd5b610064610112565b6040516100719190610b37565b60405180910390f35b610094600480360381019061008f9190610b8a565b610172565b005b6100b060048036038101906100ab9190610b8a565b6101ef565b6040516100bd9190610ca9565b60405180910390f35b6100e060048036038101906100db9190610cc4565b6103d0565b005b6100fc60048036038101906100f79190610d04565b610623565b6040516101099190610d66565b60405180910390f35b606061011c61081a565b600280548060200260200160405190810160405280929190818152602001828054801561016857602002820191906000526020600020905b815481526020019060010190808311610154575b5050505050905090565b61017a61081a565b610183816108aa565b156101c3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101ba90610dde565b60405180910390fd5b600281908060018154018082558091505060019003906000526020600020016000909190919091505550565b6101f7610a27565b6000600160058054905061020b9190610e2d565b90505b6000811061038f57826005828154811061022b5761022a610e61565b5b9060005260206000209060060201600201540361037c5760018081111561025557610254610bb7565b5b6005828154811061026957610268610e61565b5b906000526020600020906006020160050160009054906101000a900460ff16600181111561029a57610299610bb7565b5b036102da576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102d190610edc565b60405180910390fd5b600581815481106102ee576102ed610e61565b5b90600052602060002090600602016040518060c001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff16600181111561035e5761035d610bb7565b5b60018111156103705761036f610bb7565b5b815250509150506103cb565b808061038790610efc565b91505061020e565b506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103c290610edc565b60405180910390fd5b919050565b6103d98261090c565b610418576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161040f90610f71565b60405180910390fd5b6104218261092c565b610460576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161045790611003565b60405180910390fd5b6000600560036000858152602001908152602001600020548154811061048957610488610e61565b5b90600052602060002090600602016040518060c001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff1660018111156104f9576104f8610bb7565b5b600181111561050b5761050a610bb7565b5b81525050905060006040518060c0016040528084815260200183602001518152602001836040015181526020014281526020018360800151815260200160018081111561055b5761055a610bb7565b5b81525090506005819080600181540180825580915050600190039060005260206000209060060201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff021916908360018111156105e9576105e8610bb7565b5b0217905550505060016005805490506106029190610e2d565b60046000846080015181526020019081526020016000208190555050505050565b600061062e836108aa565b61066d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106649061106f565b60405180910390fd5b6106768261094c565b156106b6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106ad906110db565b60405180910390fd5b60006040518060c001604052808681526020018581526020018481526020014281526020016001548152602001600060018111156106f7576106f6610bb7565b5b81525090506005819080600181540180825580915050600190039060005260206000209060060201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff0219169083600181111561078557610784610bb7565b5b02179055505050600160058054905061079e9190610e2d565b6003600060015481526020019081526020016000208190555060018060008282546107c991906110fb565b925050819055507f3002c20c0259d29efbca701677fc4e3a5bc849a584f8e73a3e1b3ab46c8f9d3481608001516040516108039190610d66565b60405180910390a180608001519150509392505050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146108a8576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161089f9061119d565b60405180910390fd5b565b600080600090505b6002805490508110156109015782600282815481106108d4576108d3610e61565b5b9060005260206000200154036108ee576001915050610907565b80806108f9906111bd565b9150506108b2565b50600090505b919050565b600080600360008481526020019081526020016000205414159050919050565b600080600460008481526020019081526020016000205414159050919050565b60008060016005805490506109619190610e2d565b90505b60008110610a1c57826005828154811061098157610980610e61565b5b90600052602060002090600602016002015403610a09576001808111156109ab576109aa610bb7565b5b600582815481106109bf576109be610e61565b5b906000526020600020906006020160050160009054906101000a900460ff1660018111156109f0576109ef610bb7565b5b036109ff576000915050610a22565b6001915050610a22565b8080610a1490610efc565b915050610964565b50600090505b919050565b6040518060c00160405280600081526020016000815260200160008152602001600081526020016000815260200160006001811115610a6957610a68610bb7565b5b81525090565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000819050919050565b610aae81610a9b565b82525050565b6000610ac08383610aa5565b60208301905092915050565b6000602082019050919050565b6000610ae482610a6f565b610aee8185610a7a565b9350610af983610a8b565b8060005b83811015610b2a578151610b118882610ab4565b9750610b1c83610acc565b925050600181019050610afd565b5085935050505092915050565b60006020820190508181036000830152610b518184610ad9565b905092915050565b600080fd5b610b6781610a9b565b8114610b7257600080fd5b50565b600081359050610b8481610b5e565b92915050565b600060208284031215610ba057610b9f610b59565b5b6000610bae84828501610b75565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b60028110610bf757610bf6610bb7565b5b50565b6000819050610c0882610be6565b919050565b6000610c1882610bfa565b9050919050565b610c2881610c0d565b82525050565b60c082016000820151610c446000850182610aa5565b506020820151610c576020850182610aa5565b506040820151610c6a6040850182610aa5565b506060820151610c7d6060850182610aa5565b506080820151610c906080850182610aa5565b5060a0820151610ca360a0850182610c1f565b50505050565b600060c082019050610cbe6000830184610c2e565b92915050565b60008060408385031215610cdb57610cda610b59565b5b6000610ce985828601610b75565b9250506020610cfa85828601610b75565b9150509250929050565b600080600060608486031215610d1d57610d1c610b59565b5b6000610d2b86828701610b75565b9350506020610d3c86828701610b75565b9250506040610d4d86828701610b75565b9150509250925092565b610d6081610a9b565b82525050565b6000602082019050610d7b6000830184610d57565b92915050565b600082825260208201905092915050565b7f43617220616c7265616479206578697374732100000000000000000000000000600082015250565b6000610dc8601383610d81565b9150610dd382610d92565b602082019050919050565b60006020820190508181036000830152610df781610dbb565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610e3882610a9b565b9150610e4383610a9b565b925082821015610e5657610e55610dfe565b5b828203905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e6f206163746976652072656e74616c207265636f7264000000000000000000600082015250565b6000610ec6601783610d81565b9150610ed182610e90565b602082019050919050565b60006020820190508181036000830152610ef581610eb9565b9050919050565b6000610f0782610a9b565b915060008203610f1a57610f19610dfe565b5b600182039050919050565b7f4e6f2072656e74616c2077697468207468617420494420737461727465640000600082015250565b6000610f5b601e83610d81565b9150610f6682610f25565b602082019050919050565b60006020820190508181036000830152610f8a81610f4e565b9050919050565b7f52656e74616c207769746820746861742049442068617320616c72656164792060008201527f656e646564000000000000000000000000000000000000000000000000000000602082015250565b6000610fed602583610d81565b9150610ff882610f91565b604082019050919050565b6000602082019050818103600083015261101c81610fe0565b9050919050565b7f43617220646f6573206e6f742065786973742100000000000000000000000000600082015250565b6000611059601383610d81565b915061106482611023565b602082019050919050565b600060208201905081810360008301526110888161104c565b9050919050565b7f416374697665207265636f726400000000000000000000000000000000000000600082015250565b60006110c5600d83610d81565b91506110d08261108f565b602082019050919050565b600060208201905081810360008301526110f4816110b8565b9050919050565b600061110682610a9b565b915061111183610a9b565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0382111561114657611145610dfe565b5b828201905092915050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b6000611187601e83610d81565b915061119282611151565b602082019050919050565b600060208201905081810360008301526111b68161117a565b9050919050565b60006111c882610a9b565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036111fa576111f9610dfe565b5b60018201905091905056fea2646970667358221220dd417c34d77255b6a6f5b6cf8effaa08275e03b899630d9d636840f9c2c97b2364736f6c634300080e0033";

    public static final String FUNC_ADDCAR = "addCar";

    public static final String FUNC_GETCARS = "getCars";

    public static final String FUNC_STARTRENTAL = "startRental";

    public static final String FUNC_ENDRENTAL = "endRental";

    public static final String FUNC_GETACTIVERENTAL = "getActiveRental";

    public static final Event ADDEDNEWRENTALID_EVENT = new Event("addedNewRentalID", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1655632350772", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655650312339", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655648567321", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
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
