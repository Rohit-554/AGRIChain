package io.jadu.agrichain.presentation.wallet.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jadu.agrichain.decentralized.contracts.deploy.javawrappers.io.jadu.agrichain.SupplyChain_tsol_SupplyChainContract
import io.jadu.agrichain.utils.kvStorage.KvStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class ContractOperationViewModel @Inject constructor(private val application:Application):ViewModel() {
    private  var web3: Web3j = Web3j.build(HttpService("https://eth-sepolia.g.alchemy.com/v2/90tQ5woHcI8ey8xPwOe-apkcJV1PLXoy"))
    private lateinit var contractAddress: String
    private lateinit var credentials: org.web3j.crypto.Credentials
    private lateinit var kvStorage: KvStorage
    private val _contractOperationEventChannel = Channel<ContractOperationEvent>()
    private val gasLimit = BigInteger.valueOf(6721975)
    private val gasPrice = BigInteger.valueOf(2200000000)
    val contractOperationEvent = _contractOperationEventChannel.receiveAsFlow()

    private val _contractInstance = MutableLiveData<SupplyChain_tsol_SupplyChainContract?>()
    val contractInstance: LiveData<SupplyChain_tsol_SupplyChainContract?> = _contractInstance

    fun deployContract(privateKey: String, context: Context) {
        credentials = org.web3j.crypto.Credentials.create(privateKey)
        kvStorage = KvStorage(context)
        Log.d("contractAddress", "${kvStorage.storageGetString("contractAddress")}")
        viewModelScope.launch(Dispatchers.IO) {
            if (kvStorage.storageGetString("contractAddress")?.let { isContractDeployed(it) } == true) {
                contractAddress = kvStorage.storageGetString("contractAddress")!!
                loadContract(contractAddress)
                Log.d("retrieveContract", "Contract Retrieved Successfully")
                _contractOperationEventChannel.send( ContractOperationEvent.ContractOperationMessage("Contract Retrieved Successfully"))
            } else {
                try {
                    val deployContract: SupplyChain_tsol_SupplyChainContract =
                        SupplyChain_tsol_SupplyChainContract.deploy(
                            web3, credentials,
                            gasPrice,
                            gasLimit
                        ).sendAsync().get()
                    contractAddress = deployContract.contractAddress
                    Log.d("contractAddressx", contractAddress)
                    kvStorage.storageSetString("contractAddress", contractAddress)
                    _contractOperationEventChannel.send(ContractOperationEvent.ContractOperationMessage("Contract Deployed Successfully"))
                    loadContract(contractAddress)
                }catch (e: Exception){
                    Log.d("deployContract", e.message.toString())
                    _contractOperationEventChannel.send(ContractOperationEvent.ContractOperationError(e.message.toString()))
                }
            }
        }
    }

    private fun loadContract(contractAddress: String) {
        if(contractAddress.isEmpty()) return
        else{
            val contract: SupplyChain_tsol_SupplyChainContract =
                SupplyChain_tsol_SupplyChainContract.load(
                    contractAddress, web3, credentials,
                    gasPrice,
                    gasLimit
                )
            _contractInstance.postValue(contract)
            contractInstance()
            viewModelScope.launch(Dispatchers.IO) {
                Log.d("isValid", contract.toString())
            }
        }
    }

    fun contractInstance(): SupplyChain_tsol_SupplyChainContract? {
        Log.d("contractInstance", contractInstance.toString())
        return contractInstance.value
    }

    private fun isContractDeployed(contractAddress: String): Boolean {
        return if (contractAddress.isEmpty()) false
        else{
            val isDeployed: Boolean
            val ethGetCode = web3.ethGetCode(contractAddress, DefaultBlockParameterName.LATEST).send()
            isDeployed = !ethGetCode.hasError() && ethGetCode.code != "0x"
            isDeployed
        }

    }

    sealed class ContractOperationEvent {
        data class ContractOperationMessage(val message: String) : ContractOperationEvent()
        data class ContractOperationError(val message: String) : ContractOperationEvent()
    }
}