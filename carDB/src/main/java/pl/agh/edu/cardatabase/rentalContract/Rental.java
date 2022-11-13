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
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005057600080fd5b50611c8c806100606000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c806391f255881161006657806391f255881461011e578063ba65f7871461014e578063bb997dc41461017e578063e8b1e6ce146101ae578063f2645dbe146101cd57610093565b806317c39286146100985780634cf2010e146100b65780637e26b816146100d257806385a32bc414610102575b600080fd5b6100a06101ec565b6040516100ad91906110e7565b60405180910390f35b6100d060048036038101906100cb919061113a565b61024c565b005b6100ec60048036038101906100e7919061113a565b610358565b6040516100f991906111f5565b60405180910390f35b61011c60048036038101906101179190611210565b6104fb565b005b6101386004803603810190610133919061113a565b6105ea565b60405161014591906111f5565b60405180910390f35b6101686004803603810190610163919061113a565b610724565b60405161017591906112c7565b60405180910390f35b61019860048036038101906101939190611340565b6107f4565b6040516101a591906113ca565b60405180910390f35b6101b6610a08565b6040516101c49291906114a3565b60405180910390f35b6101d5610ba4565b6040516101e39291906115a4565b60405180910390f35b60606101f6610cfb565b600280548060200260200160405190810160405280929190818152602001828054801561024257602002820191906000526020600020905b81548152602001906001019080831161022e575b5050505050905090565b610254610cfb565b61025d81610d8b565b1561029d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161029490611638565b60405180910390fd5b60016040518060400160405280838152602001600060038111156102c4576102c3611250565b5b81525090806001815401808255809150506001900390600052602060002090600202016000909190919091506000820151816000015560208201518160010160006101000a81548160ff0219169083600381111561032557610324611250565b5b02179055505050600281908060018154018082558091505060019003906000526020600020016000909190919091505550565b610360610fe2565b600060038054905090505b60008111156104ba578260036001836103849190611687565b81548110610395576103946116bb565b5b906000526020600020906007020160030154036104a757600060036001836103bd9190611687565b815481106103ce576103cd6116bb565b5b90600052602060002090600702016001015414610420576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104179061175c565b60405180910390fd5b600360018261042f9190611687565b815481106104405761043f6116bb565b5b90600052602060002090600702016040518060e00160405290816000820154815260200160018201548152602001600282015481526020016003820154815260200160048201548152602001600582015481526020016006820154815250509150506104f6565b80806104b29061177c565b91505061036b565b506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104ed90611817565b60405180910390fd5b919050565b61050482610df4565b610543576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161053a90611883565b60405180910390fd5b61054c82610e05565b1561058c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161058390611915565b60405180910390fd5b6105bc600383815481106105a3576105a26116bb565b5b9060005260206000209060070201600201546000610e48565b80600383815481106105d1576105d06116bb565b5b9060005260206000209060070201600101819055505050565b6105f2610fe2565b6105fa610cfb565b600380549050821115610642576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610639906119a7565b60405180910390fd5b600060038381548110610658576106576116bb565b5b906000526020600020906007020160010154036106aa576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106a190611a39565b60405180910390fd5b600382815481106106be576106bd6116bb565b5b90600052602060002090600702016040518060e00160405290816000820154815260200160018201548152602001600282015481526020016003820154815260200160048201548152602001600582015481526020016006820154815250509050919050565b600080600090505b6001805490508110156107b357826001828154811061074e5761074d6116bb565b5b906000526020600020906002020160000154036107a05760018181548110610779576107786116bb565b5b906000526020600020906002020160010160009054906101000a900460ff169150506107ef565b80806107ab90611a59565b91505061072c565b506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107e690611aed565b60405180910390fd5b919050565b60006107ff85610d8b565b61083e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161083590611aed565b60405180910390fd5b61084785610f2a565b15610887576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161087e90611b59565b60405180910390fd5b60006040518060e001604052808881526020016000815260200187815260200186815260200142815260200160038054905081526020018573ffffffffffffffffffffffffffffffffffffffff1663b79eaecc866040518263ffffffff1660e01b81526004016108f791906113ca565b602060405180830381865afa158015610914573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906109389190611b8e565b81525090506003819080600181540180825580915050600190039060005260206000209060070201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005015560c0820151816006015550506109bc866001610e48565b7f3002c20c0259d29efbca701677fc4e3a5bc849a584f8e73a3e1b3ab46c8f9d348160a001516040516109ef91906113ca565b60405180910390a18060a0015191505095945050505050565b606080610a13610cfb565b600060018054905067ffffffffffffffff811115610a3457610a33611bbb565b5b604051908082528060200260200182016040528015610a625781602001602082028036833780820191505090505b509050600060018054905067ffffffffffffffff811115610a8657610a85611bbb565b5b604051908082528060200260200182016040528015610ab45781602001602082028036833780820191505090505b50905060005b600180549050811015610b975760018181548110610adb57610ada6116bb565b5b906000526020600020906002020160000154838281518110610b0057610aff6116bb565b5b60200260200101818152505060018181548110610b2057610b1f6116bb565b5b906000526020600020906002020160010160009054906101000a900460ff16828281518110610b5257610b516116bb565b5b60200260200101906003811115610b6c57610b6b611250565b5b90816003811115610b8057610b7f611250565b5b815250508080610b8f90611a59565b915050610aba565b5081819350935050509091565b606080600060028054905067ffffffffffffffff811115610bc857610bc7611bbb565b5b604051908082528060200260200182016040528015610bf65781602001602082028036833780820191505090505b50905060005b600280549050811015610c9c57610c3060028281548110610c2057610c1f6116bb565b5b9060005260206000200154610f2a565b15610c61576000828281518110610c4a57610c496116bb565b5b602002602001019015159081151581525050610c89565b6001828281518110610c7657610c756116bb565b5b6020026020010190151590811515815250505b8080610c9490611a59565b915050610bfc565b5060028181805480602002602001604051908101604052809291908181526020018280548015610ceb57602002820191906000526020600020905b815481526020019060010190808311610cd7575b5050505050915092509250509091565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610d89576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d8090611c36565b60405180910390fd5b565b600080600090505b600180549050811015610de9578260018281548110610db557610db46116bb565b5b90600052602060002090600202016000015403610dd6576001915050610def565b8080610de190611a59565b915050610d93565b50600090505b919050565b600060038054905082109050919050565b600060038054905082108015610e415750600060038381548110610e2c57610e2b6116bb565b5b90600052602060002090600702016001015414155b9050919050565b60005b600180549050811015610eea578260018281548110610e6d57610e6c6116bb565b5b90600052602060002090600202016000015403610ed7578160018281548110610e9957610e986116bb565b5b906000526020600020906002020160010160006101000a81548160ff02191690836003811115610ecc57610ecb611250565b5b021790555050610f26565b8080610ee290611a59565b915050610e4b565b506040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f1d90611aed565b60405180910390fd5b5050565b60008060038054905090505b6000811115610fd757826003600183610f4f9190611687565b81548110610f6057610f5f6116bb565b5b90600052602060002090600702016002015403610fc45760006003600183610f889190611687565b81548110610f9957610f986116bb565b5b90600052602060002090600702016001015414610fba576000915050610fdd565b6001915050610fdd565b8080610fcf9061177c565b915050610f36565b50600090505b919050565b6040518060e00160405280600081526020016000815260200160008152602001600081526020016000815260200160008152602001600081525090565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6000819050919050565b61105e8161104b565b82525050565b60006110708383611055565b60208301905092915050565b6000602082019050919050565b60006110948261101f565b61109e818561102a565b93506110a98361103b565b8060005b838110156110da5781516110c18882611064565b97506110cc8361107c565b9250506001810190506110ad565b5085935050505092915050565b600060208201905081810360008301526111018184611089565b905092915050565b600080fd5b6111178161104b565b811461112257600080fd5b50565b6000813590506111348161110e565b92915050565b6000602082840312156111505761114f611109565b5b600061115e84828501611125565b91505092915050565b60e08201600082015161117d6000850182611055565b5060208201516111906020850182611055565b5060408201516111a36040850182611055565b5060608201516111b66060850182611055565b5060808201516111c96080850182611055565b5060a08201516111dc60a0850182611055565b5060c08201516111ef60c0850182611055565b50505050565b600060e08201905061120a6000830184611167565b92915050565b6000806040838503121561122757611226611109565b5b600061123585828601611125565b925050602061124685828601611125565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b600481106112905761128f611250565b5b50565b60008190506112a18261127f565b919050565b60006112b182611293565b9050919050565b6112c1816112a6565b82525050565b60006020820190506112dc60008301846112b8565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061130d826112e2565b9050919050565b61131d81611302565b811461132857600080fd5b50565b60008135905061133a81611314565b92915050565b600080600080600060a0868803121561135c5761135b611109565b5b600061136a88828901611125565b955050602061137b88828901611125565b945050604061138c88828901611125565b935050606061139d8882890161132b565b92505060806113ae88828901611125565b9150509295509295909350565b6113c48161104b565b82525050565b60006020820190506113df60008301846113bb565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b61141a816112a6565b82525050565b600061142c8383611411565b60208301905092915050565b6000602082019050919050565b6000611450826113e5565b61145a81856113f0565b935061146583611401565b8060005b8381101561149657815161147d8882611420565b975061148883611438565b925050600181019050611469565b5085935050505092915050565b600060408201905081810360008301526114bd8185611089565b905081810360208301526114d18184611445565b90509392505050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b60008115159050919050565b61151b81611506565b82525050565b600061152d8383611512565b60208301905092915050565b6000602082019050919050565b6000611551826114da565b61155b81856114e5565b9350611566836114f6565b8060005b8381101561159757815161157e8882611521565b975061158983611539565b92505060018101905061156a565b5085935050505092915050565b600060408201905081810360008301526115be8185611089565b905081810360208301526115d28184611546565b90509392505050565b600082825260208201905092915050565b7f43617220616c7265616479206578697374732100000000000000000000000000600082015250565b60006116226013836115db565b915061162d826115ec565b602082019050919050565b6000602082019050818103600083015261165181611615565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006116928261104b565b915061169d8361104b565b9250828210156116b0576116af611658565b5b828203905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e6f206163746976652072656e74616c207265636f7264202d20616c7265616460008201527f7920656e64656400000000000000000000000000000000000000000000000000602082015250565b60006117466027836115db565b9150611751826116ea565b604082019050919050565b6000602082019050818103600083015261177581611739565b9050919050565b60006117878261104b565b91506000820361179a57611799611658565b5b600182039050919050565b7f4e6f206163746976652072656e74616c207265636f7264202d206e6f7420696e60008201527f2074686520686973746f72790000000000000000000000000000000000000000602082015250565b6000611801602c836115db565b915061180c826117a5565b604082019050919050565b60006020820190508181036000830152611830816117f4565b9050919050565b7f4e6f2072656e74616c2077697468207468617420494420737461727465640000600082015250565b600061186d601e836115db565b915061187882611837565b602082019050919050565b6000602082019050818103600083015261189c81611860565b9050919050565b7f52656e74616c207769746820746861742049442068617320616c72656164792060008201527f656e646564000000000000000000000000000000000000000000000000000000602082015250565b60006118ff6025836115db565b915061190a826118a3565b604082019050919050565b6000602082019050818103600083015261192e816118f2565b9050919050565b7f5468652072656e74616c2077697468207468697320494420646f6573206e6f7460008201527f2065786973740000000000000000000000000000000000000000000000000000602082015250565b60006119916026836115db565b915061199c82611935565b604082019050919050565b600060208201905081810360008301526119c081611984565b9050919050565b7f5468652072656e74616c2077697468207468697320494420686173206e6f742060008201527f79657420656e6465640000000000000000000000000000000000000000000000602082015250565b6000611a236029836115db565b9150611a2e826119c7565b604082019050919050565b60006020820190508181036000830152611a5281611a16565b9050919050565b6000611a648261104b565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611a9657611a95611658565b5b600182019050919050565b7f43617220646f6573206e6f742065786973742100000000000000000000000000600082015250565b6000611ad76013836115db565b9150611ae282611aa1565b602082019050919050565b60006020820190508181036000830152611b0681611aca565b9050919050565b7f4361722068617320616c7265616479206265656e2072656e7465642100000000600082015250565b6000611b43601c836115db565b9150611b4e82611b0d565b602082019050919050565b60006020820190508181036000830152611b7281611b36565b9050919050565b600081519050611b888161110e565b92915050565b600060208284031215611ba457611ba3611109565b5b6000611bb284828501611b79565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b6000611c20601e836115db565b9150611c2b82611bea565b602082019050919050565b60006020820190508181036000830152611c4f81611c13565b905091905056fea26469706673582212206d141e20c2683a15fe2001374e75fa9f455bbc31220ff3dc039c97afe15fe02a64736f6c634300080e0033";

    public static final String FUNC_ADDCAR = "addCar";

    public static final String FUNC_GETCARS = "getCars";

    public static final String FUNC_NEWGETCARS = "newGetCars";

    public static final String FUNC_GETRECORDHISTORY = "getRecordHistory";

    public static final String FUNC_STARTRENTAL = "startRental";

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
        _addresses.put("1667515278127", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667575543445", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667753854536", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667764587675", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667670114838", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666473966870", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667164986128", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666516197291", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1665850328675", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667257819837", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667339524962", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666551153486", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667757111170", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667668565032", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667596901560", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667339803463", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667514815150", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667325939470", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1668091411191", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666474286183", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667512679520", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667257617733", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1665847779318", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1668094744217", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667683335259", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1668197799783", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1668091273907", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667591718444", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667586958096", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1668028817547", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667516313212", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666519826886", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667573158458", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667428881426", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667245156373", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667572595745", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666472115220", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666449322535", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667339672283", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667514330339", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1668199473570", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667680301825", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667665888509", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
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

    public RemoteFunctionCall<RentalRecord> getRecordHistory(BigInteger _rentalID) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRECORDHISTORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rentalID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<RentalRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, RentalRecord.class);
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

    public static class RentalRecord extends StaticStruct {
        public BigInteger startRentTime;

        public BigInteger endRentTime;

        public BigInteger carID;

        public BigInteger userID;

        public BigInteger blockchainTime;

        public BigInteger rentalID;

        public BigInteger rentalPricing;

        public RentalRecord(BigInteger startRentTime, BigInteger endRentTime, BigInteger carID, BigInteger userID, BigInteger blockchainTime, BigInteger rentalID, BigInteger rentalPricing) {
            super(new org.web3j.abi.datatypes.generated.Uint256(startRentTime),new org.web3j.abi.datatypes.generated.Uint256(endRentTime),new org.web3j.abi.datatypes.generated.Uint256(carID),new org.web3j.abi.datatypes.generated.Uint256(userID),new org.web3j.abi.datatypes.generated.Uint256(blockchainTime),new org.web3j.abi.datatypes.generated.Uint256(rentalID),new org.web3j.abi.datatypes.generated.Uint256(rentalPricing));
            this.startRentTime = startRentTime;
            this.endRentTime = endRentTime;
            this.carID = carID;
            this.userID = userID;
            this.blockchainTime = blockchainTime;
            this.rentalID = rentalID;
            this.rentalPricing = rentalPricing;
        }

        public RentalRecord(Uint256 startRentTime, Uint256 endRentTime, Uint256 carID, Uint256 userID, Uint256 blockchainTime, Uint256 rentalID, Uint256 rentalPricing) {
            super(startRentTime,endRentTime,carID,userID,blockchainTime,rentalID,rentalPricing);
            this.startRentTime = startRentTime.getValue();
            this.endRentTime = endRentTime.getValue();
            this.carID = carID.getValue();
            this.userID = userID.getValue();
            this.blockchainTime = blockchainTime.getValue();
            this.rentalID = rentalID.getValue();
            this.rentalPricing = rentalPricing.getValue();
        }
    }

    public static class AddedNewRentalIDEventResponse extends BaseEventResponse {
        public BigInteger reservationID;
    }
}
