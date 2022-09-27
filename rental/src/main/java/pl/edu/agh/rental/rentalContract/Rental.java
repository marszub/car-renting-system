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
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001805534801561005457600080fd5b506119e9806100646000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c806385a32bc41161005b57806385a32bc4146100ec57806391f2558814610108578063bb997dc414610138578063f2645dbe146101685761007d565b806317c39286146100825780634cf2010e146100a05780637e26b816146100bc575b600080fd5b61008a610187565b6040516100979190610fda565b60405180910390f35b6100ba60048036038101906100b5919061102d565b6101e7565b005b6100d660048036038101906100d1919061102d565b610264565b6040516100e3919061115f565b60405180910390f35b6101066004803603810190610101919061117a565b610468565b005b610122600480360381019061011d919061102d565b6106da565b60405161012f9190611278565b60405180910390f35b610152600480360381019061014d91906112f2565b610900565b60405161015f919061137c565b60405180910390f35b610170610b2d565b60405161017e929190611461565b60405180910390f35b6060610191610c83565b60028054806020026020016040519081016040528092919081815260200182805480156101dd57602002820191906000526020600020905b8154815260200190600101908083116101c9575b5050505050905090565b6101ef610c83565b6101f881610d13565b15610238576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161022f906114f5565b60405180910390fd5b600281908060018154018082558091505060019003906000526020600020016000909190919091505550565b61026c610e9d565b600060058054905090505b6000811115610427578260056001836102909190611544565b815481106102a1576102a0611578565b5b90600052602060002090600702016002015403610414576001808111156102cb576102ca61105a565b5b60056001836102da9190611544565b815481106102eb576102ea611578565b5b906000526020600020906007020160060160009054906101000a900460ff16600181111561031c5761031b61105a565b5b0361035c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610353906115f3565b60405180910390fd5b600560018261036b9190611544565b8154811061037c5761037b611578565b5b90600052602060002090600702016040518060e00160405290816000820154815260200160018201548152602001600282015481526020016003820154815260200160048201548152602001600582015481526020016006820160009054906101000a900460ff1660018111156103f6576103f561105a565b5b60018111156104085761040761105a565b5b81525050915050610463565b808061041f90611613565b915050610277565b506040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161045a906115f3565b60405180910390fd5b919050565b61047182610d75565b6104b0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104a790611688565b60405180910390fd5b6104b982610d95565b156104f9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104f09061171a565b60405180910390fd5b600060056001600360008681526020019081526020016000205461051d9190611544565b8154811061052e5761052d611578565b5b90600052602060002090600702016040518060e00160405290816000820154815260200160018201548152602001600282015481526020016003820154815260200160048201548152602001600582015481526020016006820160009054906101000a900460ff1660018111156105a8576105a761105a565b5b60018111156105ba576105b961105a565b5b81525050905060006040518060e001604052808481526020018360200151815260200183604001518152602001428152602001836080015181526020018360a0015181526020016001808111156106145761061361105a565b5b81525090506005819080600181540180825580915050600190039060005260206000209060070201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005015560c08201518160060160006101000a81548160ff021916908360018111156106ac576106ab61105a565b5b0217905550505060058054905060046000846080015181526020019081526020016000208190555050505050565b6106e2610eec565b6106eb82610d75565b61072a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161072190611688565b60405180910390fd5b61073382610d95565b610772576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610769906117ac565b60405180910390fd5b6000604051806040016040528060056003600087815260200190815260200160002054815481106107a6576107a5611578565b5b90600052602060002090600702016040518060e00160405290816000820154815260200160018201548152602001600282015481526020016003820154815260200160048201548152602001600582015481526020016006820160009054906101000a900460ff1660018111156108205761081f61105a565b5b60018111156108325761083161105a565b5b815250508152602001600560046000878152602001908152602001600020548154811061086257610861611578565b5b90600052602060002090600702016040518060e00160405290816000820154815260200160018201548152602001600282015481526020016003820154815260200160048201548152602001600582015481526020016006820160009054906101000a900460ff1660018111156108dc576108db61105a565b5b60018111156108ee576108ed61105a565b5b81525050815250905080915050919050565b600061090b85610d13565b61094a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161094190611818565b60405180910390fd5b60006040518060e0016040528088815260200187815260200186815260200142815260200160015481526020018573ffffffffffffffffffffffffffffffffffffffff1663b79eaecc866040518263ffffffff1660e01b81526004016109b0919061137c565b602060405180830381865afa1580156109cd573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906109f1919061184d565b815260200160006001811115610a0a57610a0961105a565b5b81525090506005819080600181540180825580915050600190039060005260206000209060070201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005015560c08201518160060160006101000a81548160ff02191690836001811115610aa257610aa161105a565b5b02179055505050600580549050600360006001548152602001908152602001600020819055506001806000828254610ada919061187a565b925050819055507f3002c20c0259d29efbca701677fc4e3a5bc849a584f8e73a3e1b3ab46c8f9d348160800151604051610b14919061137c565b60405180910390a1806080015191505095945050505050565b606080600060028054905067ffffffffffffffff811115610b5157610b506118d0565b5b604051908082528060200260200182016040528015610b7f5781602001602082028036833780820191505090505b50905060005b600280549050811015610c2457610bb960028281548110610ba957610ba8611578565b5b9060005260206000200154610db5565b610be9576000828281518110610bd257610bd1611578565b5b602002602001019015159081151581525050610c11565b6001828281518110610bfe57610bfd611578565b5b6020026020010190151590811515815250505b8080610c1c906118ff565b915050610b85565b5060028181805480602002602001604051908101604052809291908181526020018280548015610c7357602002820191906000526020600020905b815481526020019060010190808311610c5f575b5050505050915092509250509091565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610d11576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d0890611993565b60405180910390fd5b565b600080600090505b600280549050811015610d6a578260028281548110610d3d57610d3c611578565b5b906000526020600020015403610d57576001915050610d70565b8080610d62906118ff565b915050610d1b565b50600090505b919050565b600080600360008481526020019081526020016000205414159050919050565b600080600460008481526020019081526020016000205414159050919050565b60008060058054905090505b6000811115610e9257826005600183610dda9190611544565b81548110610deb57610dea611578565b5b90600052602060002090600702016001015403610e7f57600180811115610e1557610e1461105a565b5b6005600183610e249190611544565b81548110610e3557610e34611578565b5b906000526020600020906007020160060160009054906101000a900460ff166001811115610e6657610e6561105a565b5b03610e75576000915050610e98565b6001915050610e98565b8080610e8a90611613565b915050610dc1565b50600090505b919050565b6040518060e0016040528060008152602001600081526020016000815260200160008152602001600081526020016000815260200160006001811115610ee657610ee561105a565b5b81525090565b6040518060400160405280610eff610e9d565b8152602001610f0c610e9d565b81525090565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000819050919050565b610f5181610f3e565b82525050565b6000610f638383610f48565b60208301905092915050565b6000602082019050919050565b6000610f8782610f12565b610f918185610f1d565b9350610f9c83610f2e565b8060005b83811015610fcd578151610fb48882610f57565b9750610fbf83610f6f565b925050600181019050610fa0565b5085935050505092915050565b60006020820190508181036000830152610ff48184610f7c565b905092915050565b600080fd5b61100a81610f3e565b811461101557600080fd5b50565b60008135905061102781611001565b92915050565b60006020828403121561104357611042610ffc565b5b600061105184828501611018565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b6002811061109a5761109961105a565b5b50565b60008190506110ab82611089565b919050565b60006110bb8261109d565b9050919050565b6110cb816110b0565b82525050565b60e0820160008201516110e76000850182610f48565b5060208201516110fa6020850182610f48565b50604082015161110d6040850182610f48565b5060608201516111206060850182610f48565b5060808201516111336080850182610f48565b5060a082015161114660a0850182610f48565b5060c082015161115960c08501826110c2565b50505050565b600060e08201905061117460008301846110d1565b92915050565b6000806040838503121561119157611190610ffc565b5b600061119f85828601611018565b92505060206111b085828601611018565b9150509250929050565b60e0820160008201516111d06000850182610f48565b5060208201516111e36020850182610f48565b5060408201516111f66040850182610f48565b5060608201516112096060850182610f48565b50608082015161121c6080850182610f48565b5060a082015161122f60a0850182610f48565b5060c082015161124260c08501826110c2565b50505050565b6101c08201600082015161125f60008501826111ba565b50602082015161127260e08501826111ba565b50505050565b60006101c08201905061128e6000830184611248565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006112bf82611294565b9050919050565b6112cf816112b4565b81146112da57600080fd5b50565b6000813590506112ec816112c6565b92915050565b600080600080600060a0868803121561130e5761130d610ffc565b5b600061131c88828901611018565b955050602061132d88828901611018565b945050604061133e88828901611018565b935050606061134f888289016112dd565b925050608061136088828901611018565b9150509295509295909350565b61137681610f3e565b82525050565b6000602082019050611391600083018461136d565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b60008115159050919050565b6113d8816113c3565b82525050565b60006113ea83836113cf565b60208301905092915050565b6000602082019050919050565b600061140e82611397565b61141881856113a2565b9350611423836113b3565b8060005b8381101561145457815161143b88826113de565b9750611446836113f6565b925050600181019050611427565b5085935050505092915050565b6000604082019050818103600083015261147b8185610f7c565b9050818103602083015261148f8184611403565b90509392505050565b600082825260208201905092915050565b7f43617220616c7265616479206578697374732100000000000000000000000000600082015250565b60006114df601383611498565b91506114ea826114a9565b602082019050919050565b6000602082019050818103600083015261150e816114d2565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061154f82610f3e565b915061155a83610f3e565b92508282101561156d5761156c611515565b5b828203905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e6f206163746976652072656e74616c207265636f7264000000000000000000600082015250565b60006115dd601783611498565b91506115e8826115a7565b602082019050919050565b6000602082019050818103600083015261160c816115d0565b9050919050565b600061161e82610f3e565b91506000820361163157611630611515565b5b600182039050919050565b7f4e6f2072656e74616c2077697468207468617420494420737461727465640000600082015250565b6000611672601e83611498565b915061167d8261163c565b602082019050919050565b600060208201905081810360008301526116a181611665565b9050919050565b7f52656e74616c207769746820746861742049442068617320616c72656164792060008201527f656e646564000000000000000000000000000000000000000000000000000000602082015250565b6000611704602583611498565b915061170f826116a8565b604082019050919050565b60006020820190508181036000830152611733816116f7565b9050919050565b7f52656e74616c20776974682074686174204944206861736e277420656e64656460008201527f2079657400000000000000000000000000000000000000000000000000000000602082015250565b6000611796602483611498565b91506117a18261173a565b604082019050919050565b600060208201905081810360008301526117c581611789565b9050919050565b7f43617220646f6573206e6f742065786973742100000000000000000000000000600082015250565b6000611802601383611498565b915061180d826117cc565b602082019050919050565b60006020820190508181036000830152611831816117f5565b9050919050565b60008151905061184781611001565b92915050565b60006020828403121561186357611862610ffc565b5b600061187184828501611838565b91505092915050565b600061188582610f3e565b915061189083610f3e565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff038211156118c5576118c4611515565b5b828201905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b600061190a82610f3e565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff820361193c5761193b611515565b5b600182019050919050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b600061197d601e83611498565b915061198882611947565b602082019050919050565b600060208201905081810360008301526119ac81611970565b905091905056fea2646970667358221220f9c6df7e0b39e120865186bb98481316a07cc3ca22c7c2a9e28400c7119e711e64736f6c634300080e0033";

    public static final String FUNC_ADDCAR = "addCar";

    public static final String FUNC_GETCARS = "getCars";

    public static final String FUNC_STARTRENTAL = "startRental";

    public static final String FUNC_ENDRENTAL = "endRental";

    public static final String FUNC_GETACTIVERENTAL = "getActiveRental";

    public static final String FUNC_GETRECORDHISTORY = "getRecordHistory";

    public static final String FUNC_GETALLAVAILABLECARS = "getAllAvailableCars";

    public static final Event ADDEDNEWRENTALID_EVENT = new Event("addedNewRentalID", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1663101172886", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1663095688909", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1659879647307", "0x70C8CD0A3Ef83543CF6973776FC69f79e37BcA95");
        _addresses.put("5777", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1663100352252", "0xb691b9B370D898a7891192d68EcF7C1EDcFF5Dbb");
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

    public RemoteFunctionCall<TransactionReceipt> startRental(BigInteger _rentTime, BigInteger _carID, BigInteger _userID, String _tarrifAddress, BigInteger _carTypeID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STARTRENTAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rentTime), 
                new org.web3j.abi.datatypes.generated.Uint256(_carID), 
                new org.web3j.abi.datatypes.generated.Uint256(_userID), 
                new org.web3j.abi.datatypes.Address(_tarrifAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_carTypeID)), 
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

    public RemoteFunctionCall<FullRecord> getRecordHistory(BigInteger rentalID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRECORDHISTORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(rentalID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<FullRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, FullRecord.class);
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

        public BigInteger rentalPricing;

        public BigInteger rentalState;

        public RentalRecord(BigInteger rentTime, BigInteger carID, BigInteger userID, BigInteger blockchainTime, BigInteger rentalID, BigInteger rentalPricing, BigInteger rentalState) {
            super(new org.web3j.abi.datatypes.generated.Uint256(rentTime),new org.web3j.abi.datatypes.generated.Uint256(carID),new org.web3j.abi.datatypes.generated.Uint256(userID),new org.web3j.abi.datatypes.generated.Uint256(blockchainTime),new org.web3j.abi.datatypes.generated.Uint256(rentalID),new org.web3j.abi.datatypes.generated.Uint256(rentalPricing),new org.web3j.abi.datatypes.generated.Uint8(rentalState));
            this.rentTime = rentTime;
            this.carID = carID;
            this.userID = userID;
            this.blockchainTime = blockchainTime;
            this.rentalID = rentalID;
            this.rentalPricing = rentalPricing;
            this.rentalState = rentalState;
        }

        public RentalRecord(Uint256 rentTime, Uint256 carID, Uint256 userID, Uint256 blockchainTime, Uint256 rentalID, Uint256 rentalPricing, Uint8 rentalState) {
            super(rentTime,carID,userID,blockchainTime,rentalID,rentalPricing,rentalState);
            this.rentTime = rentTime.getValue();
            this.carID = carID.getValue();
            this.userID = userID.getValue();
            this.blockchainTime = blockchainTime.getValue();
            this.rentalID = rentalID.getValue();
            this.rentalPricing = rentalPricing.getValue();
            this.rentalState = rentalState.getValue();
        }
    }

    public static class FullRecord extends StaticStruct {
        public RentalRecord start;

        public RentalRecord end;

        public FullRecord(RentalRecord start, RentalRecord end) {
            super(start,end);
            this.start = start;
            this.end = end;
        }
    }

    public static class AddedNewRentalIDEventResponse extends BaseEventResponse {
        public BigInteger reservationID;
    }
}
