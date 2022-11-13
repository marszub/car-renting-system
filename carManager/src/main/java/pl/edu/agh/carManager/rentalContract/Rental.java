package pl.edu.agh.carManager.rentalContract;

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
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
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
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005057600080fd5b50611ece806100606000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c8063ba65f78711610066578063ba65f78714610145578063bb997dc414610175578063e8b1e6ce146101a5578063f2645dbe146101c4578063fba692d3146101e35761009e565b806314221131146100a357806317c39286146100bf5780634cf2010e146100dd5780637e26b816146100f957806385a32bc414610129575b600080fd5b6100bd60048036038101906100b89190611358565b6101ff565b005b6100c76103e6565b6040516100d49190611456565b60405180910390f35b6100f760048036038101906100f29190611478565b610446565b005b610113600480360381019061010e9190611478565b610552565b60405161012091906115e0565b60405180910390f35b610143600480360381019061013e9190611358565b6107a6565b005b61015f600480360381019061015a9190611478565b610865565b60405161016c9190611679565b60405180910390f35b61018f600480360381019061018a91906116f2565b6108a1565b60405161019c919061177c565b60405180910390f35b6101ad610b88565b6040516101bb929190611855565b60405180910390f35b6101cc610d24565b6040516101da929190611956565b60405180910390f35b6101fd60048036038101906101f89190611358565b610e7a565b005b61020882611062565b610247576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161023e906119ea565b60405180910390fd5b61025082611073565b15610290576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161028790611a7c565b60405180910390fd5b600260038111156102a4576102a3611602565b5b6102d2600384815481106102bb576102ba611a9c565b5b906000526020600020906009020160020154610865565b60038111156102e4576102e3611602565b5b14610324576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161031b90611b17565b60405180910390fd5b6001806003848154811061033b5761033a611a9c565b5b9060005260206000209060090201600201548154811061035e5761035d611a9c565b5b906000526020600020906002020160010160006101000a81548160ff0219169083600381111561039157610390611602565b5b0217905550600382815481106103aa576103a9611a9c565b5b90600052602060002090600902016008018190806001815401808255809150506001900390600052602060002001600090919091909150555050565b60606103f06110b6565b600280548060200260200160405190810160405280929190818152602001828054801561043c57602002820191906000526020600020905b815481526020019060010190808311610428575b5050505050905090565b61044e6110b6565b61045781611146565b15610497576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161048e90611b83565b60405180910390fd5b60016040518060400160405280838152602001600060038111156104be576104bd611602565b5b81525090806001815401808255809150506001900390600052602060002090600202016000909190919091506000820151816000015560208201518160010160006101000a81548160ff0219169083600381111561051f5761051e611602565b5b02179055505050600281908060018154018082558091505060019003906000526020600020016000909190919091505550565b61055a611267565b600060038054905090505b60008111156107655782600360018361057e9190611bd2565b8154811061058f5761058e611a9c565b5b9060005260206000209060090201600301540361075257600060036001836105b79190611bd2565b815481106105c8576105c7611a9c565b5b9060005260206000209060090201600101541461061a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161061190611c52565b60405180910390fd5b60036001826106299190611bd2565b8154811061063a57610639611a9c565b5b90600052602060002090600902016040518061012001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820154815260200160068201548152602001600782018054806020026020016040519081016040528092919081815260200182805480156106e957602002820191906000526020600020905b8154815260200190600101908083116106d5575b505050505081526020016008820180548060200260200160405190810160405280929190818152602001828054801561074157602002820191906000526020600020905b81548152602001906001019080831161072d575b5050505050815250509150506107a1565b808061075d90611c72565b915050610565565b506040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161079890611c52565b60405180910390fd5b919050565b6107af82611062565b6107ee576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107e5906119ea565b60405180910390fd5b6107f782611073565b15610837576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161082e90611a7c565b60405180910390fd5b806003838154811061084c5761084b611a9c565b5b9060005260206000209060090201600101819055505050565b60006001828154811061087b5761087a611a9c565b5b906000526020600020906002020160010160009054906101000a900460ff169050919050565b60006108ac85611146565b6108eb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108e290611ce7565b60405180910390fd5b60006040518061012001604052808881526020016000815260200187815260200186815260200142815260200160038054905081526020018573ffffffffffffffffffffffffffffffffffffffff1663b79eaecc866040518263ffffffff1660e01b815260040161095c919061177c565b602060405180830381865afa158015610979573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061099d9190611d1c565b8152602001600067ffffffffffffffff8111156109bd576109bc611d49565b5b6040519080825280602002602001820160405280156109eb5781602001602082028036833780820191505090505b508152602001600067ffffffffffffffff811115610a0c57610a0b611d49565b5b604051908082528060200260200182016040528015610a3a5781602001602082028036833780820191505090505b5081525090506003819080600181540180825580915050600190039060005260206000209060090201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005015560c0820151816006015560e0820151816007019080519060200190610ace9291906112b3565b50610100820151816008019080519060200190610aec9291906112b3565b5050506001808781548110610b0457610b03611a9c565b5b906000526020600020906002020160010160006101000a81548160ff02191690836003811115610b3757610b36611602565b5b02179055507f3002c20c0259d29efbca701677fc4e3a5bc849a584f8e73a3e1b3ab46c8f9d348160a00151604051610b6f919061177c565b60405180910390a18060a0015191505095945050505050565b606080610b936110b6565b600060018054905067ffffffffffffffff811115610bb457610bb3611d49565b5b604051908082528060200260200182016040528015610be25781602001602082028036833780820191505090505b509050600060018054905067ffffffffffffffff811115610c0657610c05611d49565b5b604051908082528060200260200182016040528015610c345781602001602082028036833780820191505090505b50905060005b600180549050811015610d175760018181548110610c5b57610c5a611a9c565b5b906000526020600020906002020160000154838281518110610c8057610c7f611a9c565b5b60200260200101818152505060018181548110610ca057610c9f611a9c565b5b906000526020600020906002020160010160009054906101000a900460ff16828281518110610cd257610cd1611a9c565b5b60200260200101906003811115610cec57610ceb611602565b5b90816003811115610d0057610cff611602565b5b815250508080610d0f90611d78565b915050610c3a565b5081819350935050509091565b606080600060028054905067ffffffffffffffff811115610d4857610d47611d49565b5b604051908082528060200260200182016040528015610d765781602001602082028036833780820191505090505b50905060005b600280549050811015610e1b57610db060028281548110610da057610d9f611a9c565b5b90600052602060002001546111af565b610de0576000828281518110610dc957610dc8611a9c565b5b602002602001019015159081151581525050610e08565b6001828281518110610df557610df4611a9c565b5b6020026020010190151590811515815250505b8080610e1390611d78565b915050610d7c565b5060028181805480602002602001604051908101604052809291908181526020018280548015610e6a57602002820191906000526020600020905b815481526020019060010190808311610e56575b5050505050915092509250509091565b610e8382611062565b610ec2576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610eb9906119ea565b60405180910390fd5b610ecb82611073565b15610f0b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f0290611a7c565b60405180910390fd5b60016003811115610f1f57610f1e611602565b5b610f4d60038481548110610f3657610f35611a9c565b5b906000526020600020906009020160020154610865565b6003811115610f5f57610f5e611602565b5b14610f9f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f9690611e0c565b60405180910390fd5b6002600160038481548110610fb757610fb6611a9c565b5b90600052602060002090600902016002015481548110610fda57610fd9611a9c565b5b906000526020600020906002020160010160006101000a81548160ff0219169083600381111561100d5761100c611602565b5b02179055506003828154811061102657611025611a9c565b5b90600052602060002090600902016007018190806001815401808255809150506001900390600052602060002001600090919091909150555050565b600060038054905082109050919050565b6000600380549050821080156110af575060006003838154811061109a57611099611a9c565b5b90600052602060002090600902016001015414155b9050919050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611144576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161113b90611e78565b60405180910390fd5b565b600080600090505b6001805490508110156111a45782600182815481106111705761116f611a9c565b5b906000526020600020906002020160000154036111915760019150506111aa565b808061119c90611d78565b91505061114e565b50600090505b919050565b60008060038054905090505b600081111561125c578260036001836111d49190611bd2565b815481106111e5576111e4611a9c565b5b90600052602060002090600902016002015403611249576000600360018361120d9190611bd2565b8154811061121e5761121d611a9c565b5b9060005260206000209060090201600101541461123f576000915050611262565b6001915050611262565b808061125490611c72565b9150506111bb565b50600090505b919050565b6040518061012001604052806000815260200160008152602001600081526020016000815260200160008152602001600081526020016000815260200160608152602001606081525090565b8280548282559060005260206000209081019282156112ef579160200282015b828111156112ee5782518255916020019190600101906112d3565b5b5090506112fc9190611300565b5090565b5b80821115611319576000816000905550600101611301565b5090565b600080fd5b6000819050919050565b61133581611322565b811461134057600080fd5b50565b6000813590506113528161132c565b92915050565b6000806040838503121561136f5761136e61131d565b5b600061137d85828601611343565b925050602061138e85828601611343565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6113cd81611322565b82525050565b60006113df83836113c4565b60208301905092915050565b6000602082019050919050565b600061140382611398565b61140d81856113a3565b9350611418836113b4565b8060005b8381101561144957815161143088826113d3565b975061143b836113eb565b92505060018101905061141c565b5085935050505092915050565b6000602082019050818103600083015261147081846113f8565b905092915050565b60006020828403121561148e5761148d61131d565b5b600061149c84828501611343565b91505092915050565b600082825260208201905092915050565b60006114c182611398565b6114cb81856114a5565b93506114d6836113b4565b8060005b838110156115075781516114ee88826113d3565b97506114f9836113eb565b9250506001810190506114da565b5085935050505092915050565b60006101208301600083015161152d60008601826113c4565b50602083015161154060208601826113c4565b50604083015161155360408601826113c4565b50606083015161156660608601826113c4565b50608083015161157960808601826113c4565b5060a083015161158c60a08601826113c4565b5060c083015161159f60c08601826113c4565b5060e083015184820360e08601526115b782826114b6565b9150506101008301518482036101008601526115d382826114b6565b9150508091505092915050565b600060208201905081810360008301526115fa8184611514565b905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b6004811061164257611641611602565b5b50565b600081905061165382611631565b919050565b600061166382611645565b9050919050565b61167381611658565b82525050565b600060208201905061168e600083018461166a565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006116bf82611694565b9050919050565b6116cf816116b4565b81146116da57600080fd5b50565b6000813590506116ec816116c6565b92915050565b600080600080600060a0868803121561170e5761170d61131d565b5b600061171c88828901611343565b955050602061172d88828901611343565b945050604061173e88828901611343565b935050606061174f888289016116dd565b925050608061176088828901611343565b9150509295509295909350565b61177681611322565b82525050565b6000602082019050611791600083018461176d565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6117cc81611658565b82525050565b60006117de83836117c3565b60208301905092915050565b6000602082019050919050565b600061180282611797565b61180c81856117a2565b9350611817836117b3565b8060005b8381101561184857815161182f88826117d2565b975061183a836117ea565b92505060018101905061181b565b5085935050505092915050565b6000604082019050818103600083015261186f81856113f8565b9050818103602083015261188381846117f7565b90509392505050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b60008115159050919050565b6118cd816118b8565b82525050565b60006118df83836118c4565b60208301905092915050565b6000602082019050919050565b60006119038261188c565b61190d8185611897565b9350611918836118a8565b8060005b8381101561194957815161193088826118d3565b975061193b836118eb565b92505060018101905061191c565b5085935050505092915050565b6000604082019050818103600083015261197081856113f8565b9050818103602083015261198481846118f8565b90509392505050565b600082825260208201905092915050565b7f4e6f2072656e74616c2077697468207468617420494420737461727465640000600082015250565b60006119d4601e8361198d565b91506119df8261199e565b602082019050919050565b60006020820190508181036000830152611a03816119c7565b9050919050565b7f52656e74616c207769746820746861742049442068617320616c72656164792060008201527f656e646564000000000000000000000000000000000000000000000000000000602082015250565b6000611a6660258361198d565b9150611a7182611a0a565b604082019050919050565b60006020820190508181036000830152611a9581611a59565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f436172206973206e6f74207061726b6564000000000000000000000000000000600082015250565b6000611b0160118361198d565b9150611b0c82611acb565b602082019050919050565b60006020820190508181036000830152611b3081611af4565b9050919050565b7f43617220616c7265616479206578697374732100000000000000000000000000600082015250565b6000611b6d60138361198d565b9150611b7882611b37565b602082019050919050565b60006020820190508181036000830152611b9c81611b60565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611bdd82611322565b9150611be883611322565b925082821015611bfb57611bfa611ba3565b5b828203905092915050565b7f4e6f206163746976652072656e74616c207265636f7264000000000000000000600082015250565b6000611c3c60178361198d565b9150611c4782611c06565b602082019050919050565b60006020820190508181036000830152611c6b81611c2f565b9050919050565b6000611c7d82611322565b915060008203611c9057611c8f611ba3565b5b600182039050919050565b7f43617220646f6573206e6f742065786973742100000000000000000000000000600082015250565b6000611cd160138361198d565b9150611cdc82611c9b565b602082019050919050565b60006020820190508181036000830152611d0081611cc4565b9050919050565b600081519050611d168161132c565b92915050565b600060208284031215611d3257611d3161131d565b5b6000611d4084828501611d07565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000611d8382611322565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611db557611db4611ba3565b5b600182019050919050565b7f4361722063616e206e6f74207061726b00000000000000000000000000000000600082015250565b6000611df660108361198d565b9150611e0182611dc0565b602082019050919050565b60006020820190508181036000830152611e2581611de9565b9050919050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b6000611e62601e8361198d565b9150611e6d82611e2c565b602082019050919050565b60006020820190508181036000830152611e9181611e55565b905091905056fea26469706673582212204886450468cf8788a5908c796f5b39c6ae76c368b2eca7d483501fb6ae47d41264736f6c634300080e0033";

    public static final String FUNC_ADDCAR = "addCar";

    public static final String FUNC_GETCARS = "getCars";

    public static final String FUNC_NEWGETCARS = "newGetCars";

    public static final String FUNC_STARTRENTAL = "startRental";

    public static final String FUNC_STARTPARKING = "startParking";

    public static final String FUNC_ENDPARKING = "endParking";

    public static final String FUNC_ENDRENTAL = "endRental";

    public static final String FUNC_GETACTIVERENTAL = "getActiveRental";

    public static final String FUNC_GETALLAVAILABLECARS = "getAllAvailableCars";

    public static final String FUNC_CHECKCARSTATUS = "checkCarStatus";

    public static final Event ADDEDNEWRENTALID_EVENT = new Event("addedNewRentalID", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1664741985186", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1665345199468", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1663101172886", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1665940546566", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1663095688909", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1665521917020", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1659879647307", "0x70C8CD0A3Ef83543CF6973776FC69f79e37BcA95");
        _addresses.put("5777", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1665335024369", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1663100352252", "0xb691b9B370D898a7891192d68EcF7C1EDcFF5Dbb");
        _addresses.put("1664399515121", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
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

    public RemoteFunctionCall<Tuple2<List<BigInteger>, List<BigInteger>>> newGetCars() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NEWGETCARS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint8>>() {}));
        return new RemoteFunctionCall<Tuple2<List<BigInteger>, List<BigInteger>>>(function,
                new Callable<Tuple2<List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple2<List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Uint8>) results.get(1).getValue()));
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

    public RemoteFunctionCall<TransactionReceipt> startParking(BigInteger rentalID, BigInteger _startParkingTime) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STARTPARKING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(rentalID), 
                new org.web3j.abi.datatypes.generated.Uint256(_startParkingTime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> endParking(BigInteger rentalID, BigInteger _endParkingTime) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ENDPARKING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(rentalID), 
                new org.web3j.abi.datatypes.generated.Uint256(_endParkingTime)), 
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

    public RemoteFunctionCall<BigInteger> checkCarStatus(BigInteger carID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CHECKCARSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(carID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public static class RentalRecord extends DynamicStruct {
        public BigInteger startRentTime;

        public BigInteger endRentTime;

        public BigInteger carID;

        public BigInteger userID;

        public BigInteger blockchainTime;

        public BigInteger rentalID;

        public BigInteger rentalPricing;

        public List<BigInteger> parkingHistoryStarts;

        public List<BigInteger> parkingHistoryEnds;

        public RentalRecord(BigInteger startRentTime, BigInteger endRentTime, BigInteger carID, BigInteger userID, BigInteger blockchainTime, BigInteger rentalID, BigInteger rentalPricing, List<BigInteger> parkingHistoryStarts, List<BigInteger> parkingHistoryEnds) {
            super(new org.web3j.abi.datatypes.generated.Uint256(startRentTime),new org.web3j.abi.datatypes.generated.Uint256(endRentTime),new org.web3j.abi.datatypes.generated.Uint256(carID),new org.web3j.abi.datatypes.generated.Uint256(userID),new org.web3j.abi.datatypes.generated.Uint256(blockchainTime),new org.web3j.abi.datatypes.generated.Uint256(rentalID),new org.web3j.abi.datatypes.generated.Uint256(rentalPricing),new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>((Uint256) parkingHistoryStarts),new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>((Uint256) parkingHistoryEnds));
            this.startRentTime = startRentTime;
            this.endRentTime = endRentTime;
            this.carID = carID;
            this.userID = userID;
            this.blockchainTime = blockchainTime;
            this.rentalID = rentalID;
            this.rentalPricing = rentalPricing;
            this.parkingHistoryStarts = parkingHistoryStarts;
            this.parkingHistoryEnds = parkingHistoryEnds;
        }
    }

    public static class AddedNewRentalIDEventResponse extends BaseEventResponse {
        public BigInteger reservationID;
    }
}
