package io.jadu.agrichain.decentralized.contracts.deploy.javawrappers.io.jadu.agrichain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple8;
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
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class SupplyChain_tsol_SupplyChainContract extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b50600280546001600160a01b031916331790556117558061002f5f395ff3fe608060405234801561000f575f80fd5b50600436106100e5575f3560e01c80638ca88fe611610088578063d6d0be9f11610063578063d6d0be9f1461027f578063d70efee3146102a6578063d810ff0f146102b9578063f5802053146102dc575f80fd5b80638ca88fe61461022e5780638da5cb5b14610241578063ab772e831461026c575f80fd5b80635ed53b8f116100c35780635ed53b8f1461012457806370712939146101df57806376dd12f7146101f25780637acc0b2014610205575f80fd5b806326df645b146100e95780632e8651e1146100fe57806342f1181e14610111575b5f80fd5b6100fc6100f73660046111e7565b6102ef565b005b6100fc61010c3660046111e7565b61037b565b6100fc61011f36600461124f565b6103f4565b6101a061013236600461126f565b5f9081526020818152604091829020825160a081018452600a8201546001600160401b03808216808452600160401b83048216958401869052600160801b83048216968401879052600160c01b909204811660608401819052600b9094015416608090920182905294929392565b604080516001600160401b03968716815294861660208601529285169284019290925283166060830152909116608082015260a0015b60405180910390f35b6100fc6101ed36600461124f565b6104a3565b6100fc6102003660046111e7565b610585565b61021861021336600461126f565b610641565b6040516101d69a999897969594939291906112c9565b6100fc61023c3660046113db565b610a48565b600254610254906001600160a01b031681565b6040516001600160a01b0390911681526020016101d6565b6100fc61027a36600461126f565b610aee565b61029261028d36600461126f565b610b3b565b6040516101d6989796959493929190611475565b6100fc6102b43660046111e7565b610e6d565b6102cc6102c736600461126f565b610eeb565b6040516101d69493929190611503565b6100fc6102ea366004611531565b610fe1565b6002546001600160a01b0316331461032f5761030a336110d2565b61032f5760405162461bcd60e51b81526004016103269061155b565b60405180910390fd5b5f8381526020819052604090206001810161034a848261162c565b50600a0180546001600160401b03909216600160801b0267ffffffffffffffff60801b199092169190911790555050565b6002546001600160a01b031633146103b257610396336110d2565b6103b25760405162461bcd60e51b81526004016103269061155b565b5f838152602081905260409020600381016103cd848261162c565b50600b01805467ffffffffffffffff19166001600160401b03929092169190911790555050565b6002546001600160a01b031633146104625760405162461bcd60e51b815260206004820152602b60248201527f4f6e6c7920746865206f776e65722063616e2061646420617574686f72697a6560448201526a642061646472657373657360a81b6064820152608401610326565b6001805480820182555f919091526001600160a01b03919091167fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf690910155565b6002546001600160a01b031633146105145760405162461bcd60e51b815260206004820152602e60248201527f4f6e6c7920746865206f776e65722063616e2072656d6f766520617574686f7260448201526d697a65642061646472657373657360901b6064820152608401610326565b6001600160a01b0381165f5b60015481101561058057816001828154811061053e5761053e6116e7565b905f5260205f2001540361056e576001818154811061055f5761055f6116e7565b5f918252602082200155505050565b80610578816116fb565b915050610520565b505050565b42816001600160401b031611156105ec5760405162461bcd60e51b815260206004820152602560248201527f506c616e74696e6720646174652063616e6e6f7420626520696e207468652066604482015264757475726560d81b6064820152608401610326565b5f83815260208190526040902080610604848261162c565b50600a810180546001600160401b0390931667ffffffffffffffff1990931692909217909155600d0180546001600160a01b031916331790555050565b5f6020819052908152604090208054819061065b906115a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610687906115a7565b80156106d25780601f106106a9576101008083540402835291602001916106d2565b820191905f5260205f20905b8154815290600101906020018083116106b557829003601f168201915b5050505050908060010180546106e7906115a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610713906115a7565b801561075e5780601f106107355761010080835404028352916020019161075e565b820191905f5260205f20905b81548152906001019060200180831161074157829003601f168201915b505050505090806002018054610773906115a7565b80601f016020809104026020016040519081016040528092919081815260200182805461079f906115a7565b80156107ea5780601f106107c1576101008083540402835291602001916107ea565b820191905f5260205f20905b8154815290600101906020018083116107cd57829003601f168201915b5050505050908060030180546107ff906115a7565b80601f016020809104026020016040519081016040528092919081815260200182805461082b906115a7565b80156108765780601f1061084d57610100808354040283529160200191610876565b820191905f5260205f20905b81548152906001019060200180831161085957829003601f168201915b50505050509080600401805461088b906115a7565b80601f01602080910402602001604051908101604052809291908181526020018280546108b7906115a7565b80156109025780601f106108d957610100808354040283529160200191610902565b820191905f5260205f20905b8154815290600101906020018083116108e557829003601f168201915b505050505090806005015490806006016040518060800160405290815f8201805461092c906115a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610958906115a7565b80156109a35780601f1061097a576101008083540402835291602001916109a3565b820191905f5260205f20905b81548152906001019060200180831161098657829003601f168201915b505050918352505060018201546020808301919091526002830154604080840191909152600390930154606092830152825160a081018452600a8601546001600160401b038082168352600160401b8204811693830193909352600160801b8104831694820194909452600160c01b909304811691830191909152600b840154166080820152600c830154600d90930154919290916001600160a01b0391821691168a565b6002546001600160a01b03163314610a7f57610a63336110d2565b610a7f5760405162461bcd60e51b81526004016103269061155b565b5f87815260208190526040902060048101610a9a888261162c565b506008810185905560098101849055600c810180546001600160a01b0319166001600160a01b0385161790556001600160401b038616600782015560068101610ae3838261162c565b505050505050505050565b6002546001600160a01b03163314610b2557610b09336110d2565b610b255760405162461bcd60e51b81526004016103269061155b565b5f90815260208190526040902042600590910155565b60608060608060605f805f805f808b81526020019081526020015f209050805f0181600101826002018360030184600401856005015486600c015f9054906101000a90046001600160a01b031687600d015f9054906101000a90046001600160a01b0316878054610bab906115a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610bd7906115a7565b8015610c225780601f10610bf957610100808354040283529160200191610c22565b820191905f5260205f20905b815481529060010190602001808311610c0557829003601f168201915b50505050509750868054610c35906115a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610c61906115a7565b8015610cac5780601f10610c8357610100808354040283529160200191610cac565b820191905f5260205f20905b815481529060010190602001808311610c8f57829003601f168201915b50505050509650858054610cbf906115a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610ceb906115a7565b8015610d365780601f10610d0d57610100808354040283529160200191610d36565b820191905f5260205f20905b815481529060010190602001808311610d1957829003601f168201915b50505050509550848054610d49906115a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610d75906115a7565b8015610dc05780601f10610d9757610100808354040283529160200191610dc0565b820191905f5260205f20905b815481529060010190602001808311610da357829003601f168201915b50505050509450838054610dd3906115a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610dff906115a7565b8015610e4a5780601f10610e2157610100808354040283529160200191610e4a565b820191905f5260205f20905b815481529060010190602001808311610e2d57829003601f168201915b505050505093509850985098509850985098509850985050919395975091939597565b6002546001600160a01b03163314610ea457610e88336110d2565b610ea45760405162461bcd60e51b81526004016103269061155b565b5f83815260208190526040902060028101610ebf848261162c565b50600a0180546001600160401b03909216600160c01b026001600160c01b039092169190911790555050565b60605f805f805f808781526020019081526020015f206006016040518060800160405290815f82018054610f1e906115a7565b80601f0160208091040260200160405190810160405280929190818152602001828054610f4a906115a7565b8015610f955780601f10610f6c57610100808354040283529160200191610f95565b820191905f5260205f20905b815481529060010190602001808311610f7857829003601f168201915b5050505050815260200160018201548152602001600282015481526020016003820154815250509050805f01518160200151826040015183606001519450945094509450509193509193565b6002546001600160a01b0316331461101857610ffc336110d2565b6110185760405162461bcd60e51b81526004016103269061155b565b5f828152602081905260409020600a8101546001600160401b03600160401b90910481169083161161109e5760405162461bcd60e51b815260206004820152602960248201527f506c616e74696e672064617465206d757374206265206265666f72652068617260448201526876657374206461746560b81b6064820152608401610326565b600a0180546001600160401b03909216600160401b026fffffffffffffffff00000000000000001990921691909117905550565b5f6001600160a01b038216815b6001548110156111275781600182815481106110fd576110fd6116e7565b905f5260205f20015403611115575060019392505050565b8061111f816116fb565b9150506110df565b505f9392505050565b634e487b7160e01b5f52604160045260245ffd5b5f82601f830112611153575f80fd5b81356001600160401b038082111561116d5761116d611130565b604051601f8301601f19908116603f0116810190828211818310171561119557611195611130565b816040528381528660208588010111156111ad575f80fd5b836020870160208301375f602085830101528094505050505092915050565b80356001600160401b03811681146111e2575f80fd5b919050565b5f805f606084860312156111f9575f80fd5b8335925060208401356001600160401b03811115611215575f80fd5b61122186828701611144565b925050611230604085016111cc565b90509250925092565b80356001600160a01b03811681146111e2575f80fd5b5f6020828403121561125f575f80fd5b61126882611239565b9392505050565b5f6020828403121561127f575f80fd5b5035919050565b5f81518084525f5b818110156112aa5760208185018101518683018201520161128e565b505f602082860101526020601f19601f83011685010191505092915050565b5f6101c08083526112dc8184018e611286565b905082810360208401526112f0818d611286565b90508281036040840152611304818c611286565b90508281036060840152611318818b611286565b9050828103608084015261132c818a611286565b90508760a084015282810360c084015286516080825261134f6080830182611286565b602089810151848201526040808b01518186015260609a8b0151948b019490945288516001600160401b0390811660e0880152908901518116610100870152928801518316610120860152978701518216610140850152506080909501519094166101608201526001600160a01b0392831661018082015291166101a090910152509695505050505050565b5f805f805f805f60e0888a0312156113f1575f80fd5b8735965060208801356001600160401b038082111561140e575f80fd5b61141a8b838c01611144565b975061142860408b016111cc565b965060608a0135955060808a0135945061144460a08b01611239565b935060c08a0135915080821115611459575f80fd5b506114668a828b01611144565b91505092959891949750929550565b5f6101008083526114888184018c611286565b9050828103602084015261149c818b611286565b905082810360408401526114b0818a611286565b905082810360608401526114c48189611286565b905082810360808401526114d88188611286565b60a084019690965250506001600160a01b0392831660c0820152911660e09091015295945050505050565b608081525f6115156080830187611286565b6020830195909552506040810192909252606090910152919050565b5f8060408385031215611542575f80fd5b82359150611552602084016111cc565b90509250929050565b6020808252602c908201527f53656e646572206e6f7420617574686f72697a656420746f207570646174652060408201526b68617276657374206461746560a01b606082015260800190565b600181811c908216806115bb57607f821691505b6020821081036115d957634e487b7160e01b5f52602260045260245ffd5b50919050565b601f821115610580575f81815260208120601f850160051c810160208610156116055750805b601f850160051c820191505b8181101561162457828155600101611611565b505050505050565b81516001600160401b0381111561164557611645611130565b6116598161165384546115a7565b846115df565b602080601f83116001811461168c575f84156116755750858301515b5f19600386901b1c1916600185901b178555611624565b5f85815260208120601f198616915b828110156116ba5788860151825594840194600190910190840161169b565b50858210156116d757878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b634e487b7160e01b5f52603260045260245ffd5b5f6001820161171857634e487b7160e01b5f52601160045260245ffd5b506001019056fea2646970667358221220de4d433f4ce4e17ed6f1a79ec784c1737b952a0f3d03d88e25cc3352adf0780364736f6c63430008140033";

    public static final String FUNC_ADDAUTHORIZEDADDRESS = "addAuthorizedAddress";

    public static final String FUNC_ADDPRODUCT = "addProduct";

    public static final String FUNC_GETPAYMENTINFO = "getPaymentInfo";

    public static final String FUNC_GETPRODUCTDATA = "getProductData";

    public static final String FUNC_GETPRODUCTDATES = "getProductDates";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PRODUCTDELIVERED = "productDelivered";

    public static final String FUNC_PRODUCTS = "products";

    public static final String FUNC_REMOVEAUTHORIZEDADDRESS = "removeAuthorizedAddress";

    public static final String FUNC_UPDATECONSUMERDETAILS = "updateConsumerDetails";

    public static final String FUNC_UPDATEHARVESTDATE = "updateHarvestDate";

    public static final String FUNC_UPDATEPACKAGINGFACILITY = "updatePackagingFacility";

    public static final String FUNC_UPDATEPROCESSINGFACILITY = "updateProcessingFacility";

    public static final String FUNC_UPDATESHIPMENTLOCATION = "updateShipmentLocation";

    @Deprecated
    protected SupplyChain_tsol_SupplyChainContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SupplyChain_tsol_SupplyChainContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SupplyChain_tsol_SupplyChainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SupplyChain_tsol_SupplyChainContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addAuthorizedAddress(String newAddress) {
        final Function function = new Function(
                FUNC_ADDAUTHORIZEDADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addProduct(BigInteger productId, String farmLocation, BigInteger plantingDate) {
        final Function function = new Function(
                FUNC_ADDPRODUCT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId), 
                new org.web3j.abi.datatypes.Utf8String(farmLocation), 
                new org.web3j.abi.datatypes.generated.Uint64(plantingDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<String, BigInteger, BigInteger, BigInteger>> getPaymentInfo(BigInteger productId) {
        final Function function = new Function(FUNC_GETPAYMENTINFO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<String, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple4<String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple8<String, String, String, String, String, BigInteger, String, String>> getProductData(BigInteger productId) {
        final Function function = new Function(FUNC_GETPRODUCTDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple8<String, String, String, String, String, BigInteger, String, String>>(function,
                new Callable<Tuple8<String, String, String, String, String, BigInteger, String, String>>() {
                    @Override
                    public Tuple8<String, String, String, String, String, BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, String, String, String, String, BigInteger, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (String) results.get(7).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> getProductDates(BigInteger productId) {
        final Function function = new Function(FUNC_GETPRODUCTDATES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> productDelivered(BigInteger productId) {
        final Function function = new Function(
                FUNC_PRODUCTDELIVERED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple10<String, String, String, String, String, BigInteger, PaymentInfo, ProductDates, String, String>> products(BigInteger param0) {
        final Function function = new Function(FUNC_PRODUCTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<PaymentInfo>() {}, new TypeReference<ProductDates>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple10<String, String, String, String, String, BigInteger, PaymentInfo, ProductDates, String, String>>(function,
                new Callable<Tuple10<String, String, String, String, String, BigInteger, PaymentInfo, ProductDates, String, String>>() {
                    @Override
                    public Tuple10<String, String, String, String, String, BigInteger, PaymentInfo, ProductDates, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<String, String, String, String, String, BigInteger, PaymentInfo, ProductDates, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (PaymentInfo) results.get(6), 
                                (ProductDates) results.get(7), 
                                (String) results.get(8).getValue(), 
                                (String) results.get(9).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> removeAuthorizedAddress(String addressToRemove) {
        final Function function = new Function(
                FUNC_REMOVEAUTHORIZEDADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addressToRemove)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateConsumerDetails(BigInteger productId, String deliveryLocation, BigInteger paymentDate, BigInteger paymentAmount, BigInteger paymentId, String consumerId, String paymentStatus) {
        final Function function = new Function(
                FUNC_UPDATECONSUMERDETAILS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId), 
                new org.web3j.abi.datatypes.Utf8String(deliveryLocation), 
                new org.web3j.abi.datatypes.generated.Uint64(paymentDate), 
                new org.web3j.abi.datatypes.generated.Uint256(paymentAmount), 
                new org.web3j.abi.datatypes.generated.Uint256(paymentId), 
                new org.web3j.abi.datatypes.Address(160, consumerId), 
                new org.web3j.abi.datatypes.Utf8String(paymentStatus)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateHarvestDate(BigInteger productId, BigInteger harvestDate) {
        final Function function = new Function(
                FUNC_UPDATEHARVESTDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId), 
                new org.web3j.abi.datatypes.generated.Uint64(harvestDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updatePackagingFacility(BigInteger productId, String packagingFacility, BigInteger packagingDate) {
        final Function function = new Function(
                FUNC_UPDATEPACKAGINGFACILITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId), 
                new org.web3j.abi.datatypes.Utf8String(packagingFacility), 
                new org.web3j.abi.datatypes.generated.Uint64(packagingDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateProcessingFacility(BigInteger productId, String processingFacility, BigInteger processingDate) {
        final Function function = new Function(
                FUNC_UPDATEPROCESSINGFACILITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId), 
                new org.web3j.abi.datatypes.Utf8String(processingFacility), 
                new org.web3j.abi.datatypes.generated.Uint64(processingDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateShipmentLocation(BigInteger productId, String shipmentLocation, BigInteger shipmentDate) {
        final Function function = new Function(
                FUNC_UPDATESHIPMENTLOCATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productId), 
                new org.web3j.abi.datatypes.Utf8String(shipmentLocation), 
                new org.web3j.abi.datatypes.generated.Uint64(shipmentDate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SupplyChain_tsol_SupplyChainContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SupplyChain_tsol_SupplyChainContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SupplyChain_tsol_SupplyChainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SupplyChain_tsol_SupplyChainContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SupplyChain_tsol_SupplyChainContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SupplyChain_tsol_SupplyChainContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SupplyChain_tsol_SupplyChainContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SupplyChain_tsol_SupplyChainContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SupplyChain_tsol_SupplyChainContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SupplyChain_tsol_SupplyChainContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SupplyChain_tsol_SupplyChainContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SupplyChain_tsol_SupplyChainContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SupplyChain_tsol_SupplyChainContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SupplyChain_tsol_SupplyChainContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SupplyChain_tsol_SupplyChainContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SupplyChain_tsol_SupplyChainContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class PaymentInfo extends DynamicStruct {
        public String paymentStatus;

        public BigInteger paymentDate;

        public BigInteger paymentAmount;

        public BigInteger paymentId;

        public PaymentInfo(String paymentStatus, BigInteger paymentDate, BigInteger paymentAmount, BigInteger paymentId) {
            super(new org.web3j.abi.datatypes.Utf8String(paymentStatus), 
                    new org.web3j.abi.datatypes.generated.Uint256(paymentDate), 
                    new org.web3j.abi.datatypes.generated.Uint256(paymentAmount), 
                    new org.web3j.abi.datatypes.generated.Uint256(paymentId));
            this.paymentStatus = paymentStatus;
            this.paymentDate = paymentDate;
            this.paymentAmount = paymentAmount;
            this.paymentId = paymentId;
        }

        public PaymentInfo(Utf8String paymentStatus, Uint256 paymentDate, Uint256 paymentAmount, Uint256 paymentId) {
            super(paymentStatus, paymentDate, paymentAmount, paymentId);
            this.paymentStatus = paymentStatus.getValue();
            this.paymentDate = paymentDate.getValue();
            this.paymentAmount = paymentAmount.getValue();
            this.paymentId = paymentId.getValue();
        }
    }

    public static class ProductDates extends StaticStruct {
        public BigInteger plantingDate;

        public BigInteger harvestDate;

        public BigInteger processingDate;

        public BigInteger packagingDate;

        public BigInteger shipmentDate;

        public ProductDates(BigInteger plantingDate, BigInteger harvestDate, BigInteger processingDate, BigInteger packagingDate, BigInteger shipmentDate) {
            super(new org.web3j.abi.datatypes.generated.Uint64(plantingDate), 
                    new org.web3j.abi.datatypes.generated.Uint64(harvestDate), 
                    new org.web3j.abi.datatypes.generated.Uint64(processingDate), 
                    new org.web3j.abi.datatypes.generated.Uint64(packagingDate), 
                    new org.web3j.abi.datatypes.generated.Uint64(shipmentDate));
            this.plantingDate = plantingDate;
            this.harvestDate = harvestDate;
            this.processingDate = processingDate;
            this.packagingDate = packagingDate;
            this.shipmentDate = shipmentDate;
        }

        public ProductDates(Uint64 plantingDate, Uint64 harvestDate, Uint64 processingDate, Uint64 packagingDate, Uint64 shipmentDate) {
            super(plantingDate, harvestDate, processingDate, packagingDate, shipmentDate);
            this.plantingDate = plantingDate.getValue();
            this.harvestDate = harvestDate.getValue();
            this.processingDate = processingDate.getValue();
            this.packagingDate = packagingDate.getValue();
            this.shipmentDate = shipmentDate.getValue();
        }
    }
}
