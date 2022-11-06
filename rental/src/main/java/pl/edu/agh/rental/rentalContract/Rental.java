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
    public static final String BINARY = "0x6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005057600080fd5b50612034806100606000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c806391f255881161007157806391f2558814610150578063ba65f78714610180578063bb997dc4146101b0578063e8b1e6ce146101e0578063f2645dbe146101ff578063fba692d31461021e576100a9565b806314221131146100ae57806317c39286146100ca5780634cf2010e146100e85780637e26b8161461010457806385a32bc414610134575b600080fd5b6100c860048036038101906100c3919061132a565b61023a565b005b6100d2610393565b6040516100df9190611428565b60405180910390f35b61010260048036038101906100fd919061144a565b6103f3565b005b61011e6004803603810190610119919061144a565b6104ff565b60405161012b9190611505565b60405180910390f35b61014e6004803603810190610149919061132a565b6106a2565b005b61016a6004803603810190610165919061144a565b610761565b6040516101779190611505565b60405180910390f35b61019a6004803603810190610195919061144a565b61089b565b6040516101a79190611597565b60405180910390f35b6101ca60048036038101906101c59190611610565b61096b565b6040516101d7919061169a565b60405180910390f35b6101e8610b7f565b6040516101f6929190611773565b60405180910390f35b610207610d1b565b604051610215929190611874565b60405180910390f35b6102386004803603810190610233919061132a565b610e72565b005b61024382610fcb565b610282576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161027990611908565b60405180910390fd5b61028b82610fdc565b156102cb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102c29061199a565b60405180910390fd5b600260038111156102df576102de611520565b5b61030d600384815481106102f6576102f56119ba565b5b90600052602060002090600702016002015461089b565b600381111561031f5761031e611520565b5b1461035f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161035690611a35565b60405180910390fd5b61038f60038381548110610376576103756119ba565b5b906000526020600020906007020160020154600161101f565b5050565b606061039d611101565b60028054806020026020016040519081016040528092919081815260200182805480156103e957602002820191906000526020600020905b8154815260200190600101908083116103d5575b5050505050905090565b6103fb611101565b61040481611191565b15610444576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161043b90611aa1565b60405180910390fd5b600160405180604001604052808381526020016000600381111561046b5761046a611520565b5b81525090806001815401808255809150506001900390600052602060002090600202016000909190919091506000820151816000015560208201518160010160006101000a81548160ff021916908360038111156104cc576104cb611520565b5b02179055505050600281908060018154018082558091505060019003906000526020600020016000909190919091505550565b6105076112b2565b600060038054905090505b60008111156106615782600360018361052b9190611af0565b8154811061053c5761053b6119ba565b5b9060005260206000209060070201600301540361064e57600060036001836105649190611af0565b81548110610575576105746119ba565b5b906000526020600020906007020160010154146105c7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105be90611b96565b60405180910390fd5b60036001826105d69190611af0565b815481106105e7576105e66119ba565b5b90600052602060002090600702016040518060e001604052908160008201548152602001600182015481526020016002820154815260200160038201548152602001600482015481526020016005820154815260200160068201548152505091505061069d565b808061065990611bb6565b915050610512565b506040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161069490611c51565b60405180910390fd5b919050565b6106ab82610fcb565b6106ea576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106e190611908565b60405180910390fd5b6106f382610fdc565b15610733576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161072a9061199a565b60405180910390fd5b8060038381548110610748576107476119ba565b5b9060005260206000209060070201600101819055505050565b6107696112b2565b610771611101565b6003805490508211156107b9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107b090611ce3565b60405180910390fd5b6000600383815481106107cf576107ce6119ba565b5b90600052602060002090600702016001015403610821576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161081890611d75565b60405180910390fd5b60038281548110610835576108346119ba565b5b90600052602060002090600702016040518060e00160405290816000820154815260200160018201548152602001600282015481526020016003820154815260200160048201548152602001600582015481526020016006820154815250509050919050565b600080600090505b60018054905081101561092a5782600182815481106108c5576108c46119ba565b5b9060005260206000209060020201600001540361091757600181815481106108f0576108ef6119ba565b5b906000526020600020906002020160010160009054906101000a900460ff16915050610966565b808061092290611d95565b9150506108a3565b506040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161095d90611e29565b60405180910390fd5b919050565b600061097685611191565b6109b5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109ac90611e29565b60405180910390fd5b6109be856111fa565b156109fe576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109f590611e95565b60405180910390fd5b60006040518060e001604052808881526020016000815260200187815260200186815260200142815260200160038054905081526020018573ffffffffffffffffffffffffffffffffffffffff1663b79eaecc866040518263ffffffff1660e01b8152600401610a6e919061169a565b602060405180830381865afa158015610a8b573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610aaf9190611eca565b81525090506003819080600181540180825580915050600190039060005260206000209060070201600090919091909150600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005015560c082015181600601555050610b3386600161101f565b7f3002c20c0259d29efbca701677fc4e3a5bc849a584f8e73a3e1b3ab46c8f9d348160a00151604051610b66919061169a565b60405180910390a18060a0015191505095945050505050565b606080610b8a611101565b600060018054905067ffffffffffffffff811115610bab57610baa611ef7565b5b604051908082528060200260200182016040528015610bd95781602001602082028036833780820191505090505b509050600060018054905067ffffffffffffffff811115610bfd57610bfc611ef7565b5b604051908082528060200260200182016040528015610c2b5781602001602082028036833780820191505090505b50905060005b600180549050811015610d0e5760018181548110610c5257610c516119ba565b5b906000526020600020906002020160000154838281518110610c7757610c766119ba565b5b60200260200101818152505060018181548110610c9757610c966119ba565b5b906000526020600020906002020160010160009054906101000a900460ff16828281518110610cc957610cc86119ba565b5b60200260200101906003811115610ce357610ce2611520565b5b90816003811115610cf757610cf6611520565b5b815250508080610d0690611d95565b915050610c31565b5081819350935050509091565b606080600060028054905067ffffffffffffffff811115610d3f57610d3e611ef7565b5b604051908082528060200260200182016040528015610d6d5781602001602082028036833780820191505090505b50905060005b600280549050811015610e1357610da760028281548110610d9757610d966119ba565b5b90600052602060002001546111fa565b15610dd8576000828281518110610dc157610dc06119ba565b5b602002602001019015159081151581525050610e00565b6001828281518110610ded57610dec6119ba565b5b6020026020010190151590811515815250505b8080610e0b90611d95565b915050610d73565b5060028181805480602002602001604051908101604052809291908181526020018280548015610e6257602002820191906000526020600020905b815481526020019060010190808311610e4e575b5050505050915092509250509091565b610e7b82610fcb565b610eba576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610eb190611908565b60405180910390fd5b610ec382610fdc565b15610f03576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610efa9061199a565b60405180910390fd5b60016003811115610f1757610f16611520565b5b610f4560038481548110610f2e57610f2d6119ba565b5b90600052602060002090600702016002015461089b565b6003811115610f5757610f56611520565b5b14610f97576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f8e90611f72565b60405180910390fd5b610fc760038381548110610fae57610fad6119ba565b5b906000526020600020906007020160020154600261101f565b5050565b600060038054905082109050919050565b6000600380549050821080156110185750600060038381548110611003576110026119ba565b5b90600052602060002090600702016001015414155b9050919050565b60005b6001805490508110156110c1578260018281548110611044576110436119ba565b5b906000526020600020906002020160000154036110ae5781600182815481106110705761106f6119ba565b5b906000526020600020906002020160010160006101000a81548160ff021916908360038111156110a3576110a2611520565b5b0217905550506110fd565b80806110b990611d95565b915050611022565b506040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016110f490611e29565b60405180910390fd5b5050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461118f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161118690611fde565b60405180910390fd5b565b600080600090505b6001805490508110156111ef5782600182815481106111bb576111ba6119ba565b5b906000526020600020906002020160000154036111dc5760019150506111f5565b80806111e790611d95565b915050611199565b50600090505b919050565b60008060038054905090505b60008111156112a75782600360018361121f9190611af0565b815481106112305761122f6119ba565b5b9060005260206000209060070201600201540361129457600060036001836112589190611af0565b81548110611269576112686119ba565b5b9060005260206000209060070201600101541461128a5760009150506112ad565b60019150506112ad565b808061129f90611bb6565b915050611206565b50600090505b919050565b6040518060e00160405280600081526020016000815260200160008152602001600081526020016000815260200160008152602001600081525090565b600080fd5b6000819050919050565b611307816112f4565b811461131257600080fd5b50565b600081359050611324816112fe565b92915050565b60008060408385031215611341576113406112ef565b5b600061134f85828601611315565b925050602061136085828601611315565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b61139f816112f4565b82525050565b60006113b18383611396565b60208301905092915050565b6000602082019050919050565b60006113d58261136a565b6113df8185611375565b93506113ea83611386565b8060005b8381101561141b57815161140288826113a5565b975061140d836113bd565b9250506001810190506113ee565b5085935050505092915050565b6000602082019050818103600083015261144281846113ca565b905092915050565b6000602082840312156114605761145f6112ef565b5b600061146e84828501611315565b91505092915050565b60e08201600082015161148d6000850182611396565b5060208201516114a06020850182611396565b5060408201516114b36040850182611396565b5060608201516114c66060850182611396565b5060808201516114d96080850182611396565b5060a08201516114ec60a0850182611396565b5060c08201516114ff60c0850182611396565b50505050565b600060e08201905061151a6000830184611477565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602160045260246000fd5b600481106115605761155f611520565b5b50565b60008190506115718261154f565b919050565b600061158182611563565b9050919050565b61159181611576565b82525050565b60006020820190506115ac6000830184611588565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006115dd826115b2565b9050919050565b6115ed816115d2565b81146115f857600080fd5b50565b60008135905061160a816115e4565b92915050565b600080600080600060a0868803121561162c5761162b6112ef565b5b600061163a88828901611315565b955050602061164b88828901611315565b945050604061165c88828901611315565b935050606061166d888289016115fb565b925050608061167e88828901611315565b9150509295509295909350565b611694816112f4565b82525050565b60006020820190506116af600083018461168b565b92915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6116ea81611576565b82525050565b60006116fc83836116e1565b60208301905092915050565b6000602082019050919050565b6000611720826116b5565b61172a81856116c0565b9350611735836116d1565b8060005b8381101561176657815161174d88826116f0565b975061175883611708565b925050600181019050611739565b5085935050505092915050565b6000604082019050818103600083015261178d81856113ca565b905081810360208301526117a18184611715565b90509392505050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b60008115159050919050565b6117eb816117d6565b82525050565b60006117fd83836117e2565b60208301905092915050565b6000602082019050919050565b6000611821826117aa565b61182b81856117b5565b9350611836836117c6565b8060005b8381101561186757815161184e88826117f1565b975061185983611809565b92505060018101905061183a565b5085935050505092915050565b6000604082019050818103600083015261188e81856113ca565b905081810360208301526118a28184611816565b90509392505050565b600082825260208201905092915050565b7f4e6f2072656e74616c2077697468207468617420494420737461727465640000600082015250565b60006118f2601e836118ab565b91506118fd826118bc565b602082019050919050565b60006020820190508181036000830152611921816118e5565b9050919050565b7f52656e74616c207769746820746861742049442068617320616c72656164792060008201527f656e646564000000000000000000000000000000000000000000000000000000602082015250565b60006119846025836118ab565b915061198f82611928565b604082019050919050565b600060208201905081810360008301526119b381611977565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f436172206973206e6f74207061726b6564000000000000000000000000000000600082015250565b6000611a1f6011836118ab565b9150611a2a826119e9565b602082019050919050565b60006020820190508181036000830152611a4e81611a12565b9050919050565b7f43617220616c7265616479206578697374732100000000000000000000000000600082015250565b6000611a8b6013836118ab565b9150611a9682611a55565b602082019050919050565b60006020820190508181036000830152611aba81611a7e565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000611afb826112f4565b9150611b06836112f4565b925082821015611b1957611b18611ac1565b5b828203905092915050565b7f4e6f206163746976652072656e74616c207265636f7264202d20616c7265616460008201527f7920656e64656400000000000000000000000000000000000000000000000000602082015250565b6000611b806027836118ab565b9150611b8b82611b24565b604082019050919050565b60006020820190508181036000830152611baf81611b73565b9050919050565b6000611bc1826112f4565b915060008203611bd457611bd3611ac1565b5b600182039050919050565b7f4e6f206163746976652072656e74616c207265636f7264202d206e6f7420696e60008201527f2074686520686973746f72790000000000000000000000000000000000000000602082015250565b6000611c3b602c836118ab565b9150611c4682611bdf565b604082019050919050565b60006020820190508181036000830152611c6a81611c2e565b9050919050565b7f5468652072656e74616c2077697468207468697320494420646f6573206e6f7460008201527f2065786973740000000000000000000000000000000000000000000000000000602082015250565b6000611ccd6026836118ab565b9150611cd882611c71565b604082019050919050565b60006020820190508181036000830152611cfc81611cc0565b9050919050565b7f5468652072656e74616c2077697468207468697320494420686173206e6f742060008201527f79657420656e6465640000000000000000000000000000000000000000000000602082015250565b6000611d5f6029836118ab565b9150611d6a82611d03565b604082019050919050565b60006020820190508181036000830152611d8e81611d52565b9050919050565b6000611da0826112f4565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611dd257611dd1611ac1565b5b600182019050919050565b7f43617220646f6573206e6f742065786973742100000000000000000000000000600082015250565b6000611e136013836118ab565b9150611e1e82611ddd565b602082019050919050565b60006020820190508181036000830152611e4281611e06565b9050919050565b7f4361722068617320616c7265616479206265656e2072656e7465642100000000600082015250565b6000611e7f601c836118ab565b9150611e8a82611e49565b602082019050919050565b60006020820190508181036000830152611eae81611e72565b9050919050565b600081519050611ec4816112fe565b92915050565b600060208284031215611ee057611edf6112ef565b5b6000611eee84828501611eb5565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4361722063616e206e6f74207061726b00000000000000000000000000000000600082015250565b6000611f5c6010836118ab565b9150611f6782611f26565b602082019050919050565b60006020820190508181036000830152611f8b81611f4f565b9050919050565b7f41636365732064656e696564202d206e6f2061646d696e206163636573730000600082015250565b6000611fc8601e836118ab565b9150611fd382611f92565b602082019050919050565b60006020820190508181036000830152611ff781611fbb565b905091905056fea26469706673582212200d846fbe569e660afb61c3c03a9d3ebf052e895bdb08f5833d5998dc0abce4ed64736f6c634300080e0033";

    public static final String FUNC_ADDCAR = "addCar";

    public static final String FUNC_GETCARS = "getCars";

    public static final String FUNC_NEWGETCARS = "newGetCars";

    public static final String FUNC_GETRECORDHISTORY = "getRecordHistory";

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
        _addresses.put("1667515278127", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667575543445", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667670114838", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666473966870", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667164986128", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666516197291", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1665850328675", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667257819837", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667339524962", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666551153486", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667668565032", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667596901560", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667339803463", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667514815150", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667325939470", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1666474286183", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667512679520", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667257617733", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1665847779318", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667683335259", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667591718444", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
        _addresses.put("1667586958096", "0xEe3E92973010664a804BF96188ac4766fb84A3B9");
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
