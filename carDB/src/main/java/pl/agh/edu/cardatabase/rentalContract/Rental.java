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
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600060015534801561005557600080fd5b5061123d806100656000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806317c392861461005c5780634cf2010e1461007a5780637e26b8161461009657806385a32bc4146100c6578063b31c0a9a146100e2575b600080fd5b610064610112565b6040516100719190610b39565b60405180910390f35b610094600480360381019061008f9190610b8c565b610172565b005b6100b060048036038101906100ab9190610b8c565b6101ef565b6040516100bd9190610cab565b60405180910390f35b6100e060048036038101906100db9190610cc6565b6103d0565b005b6100fc60048036038101906100f79190610d06565b610624565b6040516101099190610d68565b60405180910390f35b606061011c61080f565b600280548060200260200160405190810160405280929190818152602001828054801561016857602002820191906000526020600020905b815481526020019060010190808311610154575b5050505050905090565b61017a61080f565b6101838161089f565b156101c3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101ba90610de0565b60405180910390fd5b600281908060018154018082558091505060019003906000526020600020016000909190919091505550565b6101f7610a29565b6000600160058054905061020b9190610e2f565b90505b6000811061038f57826005828154811061022b5761022a610e63565b5b9060005260206000209060060201600201540361037c5760018081111561025557610254610bb9565b5b6005828154811061026957610268610e63565b5b906000526020600020906006020160050160009054906101000a900460ff16600181111561029a57610299610bb9565b5b036102da576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102d190610ede565b60405180910390fd5b600581815481106102ee576102ed610e63565b5b90600052602060002090600602016040518060c001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff16600181111561035e5761035d610bb9565b5b60018111156103705761036f610bb9565b5b815250509150506103cb565b808061038790610efe565b91505061020e565b506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103c290610ede565b60405180910390fd5b919050565b6103d982610901565b610418576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161040f90610f73565b60405180910390fd5b61042182610921565b15610461576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161045890611005565b60405180910390fd5b60006005600160036000868152602001908152602001600020546104859190610e2f565b8154811061049657610495610e63565b5b90600052602060002090600602016040518060c001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820160009054906101000a900460ff16600181111561050657610505610bb9565b5b600181111561051857610517610bb9565b5b81525050905060006040518060c0016040528084815260200183602001518152602001836040015181526020014281526020018360800151815260200160018081111561056857610567610bb9565b5b81525090506005819080600181540180825580915050600190039060005260206000209060060201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff021916908360018111156105f6576105f5610bb9565b5b0217905550505060058054905060046000846080015181526020019081526020016000208190555050505050565b600061062f8361089f565b61066e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161066590611071565b60405180910390fd5b61067782610941565b156106b7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106ae906110dd565b60405180910390fd5b60006040518060c001604052808681526020018581526020018481526020014281526020016001548152602001600060018111156106f8576106f7610bb9565b5b81525090506005819080600181540180825580915050600190039060005260206000209060060201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff0219169083600181111561078657610785610bb9565b5b021790555050506005805490506003600060015481526020019081526020016000208190555060018060008282546107be91906110fd565b925050819055507f3002c20c0259d29efbca701677fc4e3a5bc849a584f8e73a3e1b3ab46c8f9d3481608001516040516107f89190610d68565b60405180910390a180608001519150509392505050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461089d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108949061119f565b60405180910390fd5b565b600080600090505b6002805490508110156108f65782600282815481106108c9576108c8610e63565b5b9060005260206000200154036108e35760019150506108fc565b80806108ee906111bf565b9150506108a7565b50600090505b919050565b600080600360008481526020019081526020016000205414159050919050565b600080600460008481526020019081526020016000205414159050919050565b60008060058054905090505b6000811115610a1e578260056001836109669190610e2f565b8154811061097757610976610e63565b5b90600052602060002090600602016002015403610a0b576001808111156109a1576109a0610bb9565b5b60056001836109b09190610e2f565b815481106109c1576109c0610e63565b5b906000526020600020906006020160050160009054906101000a900460ff1660018111156109f2576109f1610bb9565b5b03610a01576000915050610a24565b6001915050610a24565b8080610a1690610efe565b91505061094d565b50600090505b919050565b6040518060c00160405280600081526020016000815260200160008152602001600081526020016000815260200160006001811115610a6b57610a6a610bb9565b5b81525090565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000819050919050565b610ab081610a9d565b82525050565b6000610ac28383610aa7565b60208301905092915050565b6000602082019050919050565b6000610ae682610a71565b610af08185610a7c565b9350610afb83610a8d565b8060005b83811015610b2c578151610b138882610ab6565b9750610b1e83610ace565b925050600181019050610aff565b5085935050505092915050565b60006020820190508181036000830152610b538184610adb565b905092915050565b600080fd5b610b6981610a9d565b8114610b7457600080fd5b50565b600081359050610b8681610b60565b92915050565b600060208284031215610ba257610ba1610b5b565b5b6000610bb084828501610b77565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b60028110610bf957610bf8610bb9565b5b50565b6000819050610c0a82610be8565b919050565b6000610c1a82610bfc565b9050919050565b610c2a81610c0f565b82525050565b60c082016000820151610c466000850182610aa7565b506020820151610c596020850182610aa7565b506040820151610c6c6040850182610aa7565b506060820151610c7f6060850182610aa7565b506080820151610c926080850182610aa7565b5060a0820151610ca560a0850182610c21565b50505050565b600060c082019050610cc06000830184610c30565b92915050565b60008060408385031215610cdd57610cdc610b5b565b5b6000610ceb85828601610b77565b9250506020610cfc85828601610b77565b9150509250929050565b600080600060608486031215610d1f57610d1e610b5b565b5b6000610d2d86828701610b77565b9350506020610d3e86828701610b77565b9250506040610d4f86828701610b77565b9150509250925092565b610d6281610a9d565b82525050565b6000602082019050610d7d6000830184610d59565b92915050565b600082825260208201905092915050565b7f43617220616c7265616479206578697374732100000000000000000000000000600082015250565b6000610dca601383610d83565b9150610dd582610d94565b602082019050919050565b60006020820190508181036000830152610df981610dbd565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610e3a82610a9d565b9150610e4583610a9d565b925082821015610e5857610e57610e00565b5b828203905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e6f206163746976652072656e74616c207265636f7264000000000000000000600082015250565b6000610ec8601783610d83565b9150610ed382610e92565b602082019050919050565b60006020820190508181036000830152610ef781610ebb565b9050919050565b6000610f0982610a9d565b915060008203610f1c57610f1b610e00565b5b600182039050919050565b7f4e6f2072656e74616c2077697468207468617420494420737461727465640000600082015250565b6000610f5d601e83610d83565b9150610f6882610f27565b602082019050919050565b60006020820190508181036000830152610f8c81610f50565b9050919050565b7f52656e74616c207769746820746861742049442068617320616c72656164792060008201527f656e646564000000000000000000000000000000000000000000000000000000602082015250565b6000610fef602583610d83565b9150610ffa82610f93565b604082019050919050565b6000602082019050818103600083015261101e81610fe2565b9050919050565b7f43617220646f6573206e6f742065786973742100000000000000000000000000600082015250565b600061105b601383610d83565b915061106682611025565b602082019050919050565b6000602082019050818103600083015261108a8161104e565b9050919050565b7f416374697665207265636f726400000000000000000000000000000000000000600082015250565b60006110c7600d83610d83565b91506110d282611091565b602082019050919050565b600060208201905081810360008301526110f6816110ba565b9050919050565b600061110882610a9d565b915061111383610a9d565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0382111561114857611147610e00565b5b828201905092915050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b6000611189601e83610d83565b915061119482611153565b602082019050919050565b600060208201905081810360008301526111b88161117c565b9050919050565b60006111ca82610a9d565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036111fc576111fb610e00565b5b60018201905091905056fea2646970667358221220de64cab650e3545f4d10374c4e1f43b539461273e20aee4ed6236fc722b8fda264736f6c634300080e0033";

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
        _addresses.put("1655672899448", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655671793978", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655672698489", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655671893721", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655672261670", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655668465448", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655674835612", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655673389710", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655632350772", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655650312339", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655648567321", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655673964452", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655672000929", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655661350758", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655671340142", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1655671629159", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
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
