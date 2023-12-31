package io.jadu.agrichain.presentation.wallet.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.agrichain.utils.kvStorage.KvStorage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import java.security.Provider
import java.security.Security
import javax.inject.Inject

class WalletConnectViewModel @Inject constructor(): ViewModel(){
    private var file: File? = null
    private var walletName: String? = null
    private var credential: Credentials? = null
    private var web3j: Web3j? = null
    private val _walletConnectEvent = Channel<WalletConnectEvent>()
    val walletConnectEvent = _walletConnectEvent.receiveAsFlow()
    @Inject
    lateinit var KvStorage: KvStorage

    init {
        connectToWallet()
        setupBouncyCastle()
    }

    private fun connectToWallet() {
        web3j = Web3j.build(HttpService("https://eth-sepolia.g.alchemy.com/v2/Hffh4wXekIwTojB0wWaNXtEnp-fwgOO6"))
        try {
            val clientVersion = web3j!!.web3ClientVersion().sendAsync().get()
            if (!clientVersion.hasError()) {
                viewModelScope.launch {
                    _walletConnectEvent.send(WalletConnectEvent.ConnectToWalletMessage("Connected"))
                }
            } else {
                viewModelScope.launch {
                    _walletConnectEvent.send(WalletConnectEvent.ConnectToWalletError(clientVersion.error.message))
                }
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                _walletConnectEvent.send(WalletConnectEvent.ConnectToWalletError(e.message.toString()))
            }
        }
    }
    fun createWallet(context: Context, walletId: String, walletPassword: String) {
        file = File(context.filesDir, walletId)
        if (!file!!.exists()) {
            file!!.mkdir()
        }
        if(checkIfWalletAlreadyCreated(file!!,walletPassword)){
            viewModelScope.launch {
                _walletConnectEvent.send(WalletConnectEvent.WalletCreatedSuccessfully(credential!!.address))
            }
        }else{
            try {
                walletName = WalletUtils.generateLightNewWalletFile(walletPassword, file)
                credential =
                    WalletUtils.loadCredentials(walletPassword, file!!.absolutePath + "/" + walletName)
                viewModelScope.launch {
                    _walletConnectEvent.send(WalletConnectEvent.WalletCreatedSuccessfully(credential!!.address))
                }
                Log.d("WalletConnectViewModel", "Wallet Address: ${credential!!.address}")
//            txtAddress.text = credential.address  for displaying address
            } catch (e: Exception) {
                viewModelScope.launch {
                    _walletConnectEvent.send(WalletConnectEvent.WalletCreatedError(e.message.toString()))
                    Log.d("WalletConnectViewModel", "Wallet Address: ${e.message}")
                }
            }
        }

    }

    fun getPrivateKey():String {
        Log.d("getprivatekey", "getprivatekey: ${credential!!.ecKeyPair.privateKey.toString(16)}")
        val privateKey = credential!!.ecKeyPair.privateKey.toString(16)
        KvStorage.storageSetString("PrivateKey", privateKey)
        return privateKey
    }



    private fun checkIfWalletAlreadyCreated(file: File, password: String):Boolean {
        val files = file.listFiles()
        return if (files != null && files.isNotEmpty()) {
            val lastFile = files.last()
            credential = WalletUtils.loadCredentials(password, lastFile.absolutePath)
            true
        }else{
            false
        }
    }

    fun retrieveBalance() {
        try {
            val balance = web3j?.ethGetBalance(credential?.address, DefaultBlockParameterName.LATEST)?.sendAsync()?.get()
            Log.d("WalletConnectViewModel", "Balance: ${balance?.balance}")
            Log.d("getcredentialaddress", "Balance: ${credential?.address}")
            val etherBalance = Convert.fromWei(balance?.balance.toString(), Convert.Unit.ETHER)
            viewModelScope.launch {
                _walletConnectEvent.send(WalletConnectEvent.WalletBalanceSuccessfully(etherBalance.toString()))
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                _walletConnectEvent.send(WalletConnectEvent.WalletBalanceError(e.message.toString()))
            }
        }
    }

    @Throws(Exception::class)
    fun makeTransaction(requireBalance: Double, toAddress: String) {
        try {
            if (createReceipt(requireBalance, toAddress).isStatusOK) {
                viewModelScope.launch {
                    _walletConnectEvent.send(WalletConnectEvent.WalletTransactionSuccessfully("Transaction Successful"))
                }
                Log.d("WalletConnectViewModel", "Transaction Successful")
            } else {
                viewModelScope.launch {
                    _walletConnectEvent.send(WalletConnectEvent.WalletTransactionError("Transaction Failed"))
                }
                Log.d("WalletConnectViewModel", "Transaction Failed")
            }
        } catch (e: Exception) {
            Log.d("WalletConnectViewModel", "Transaction Error: ${e.message}")
            viewModelScope.launch {
                _walletConnectEvent.send(WalletConnectEvent.WalletTransactionException(e.message.toString()))
            }
        }
    }

    private fun createReceipt(value: Double, toAddress: String): TransactionReceipt {
        return Transfer.sendFundsEIP1559(
            web3j,
            credential,
            toAddress,
            BigDecimal.valueOf(value),
            Convert.Unit.ETHER,
            BigInteger.valueOf(21000L),
            DefaultGasProvider.GAS_LIMIT,
            BigInteger.valueOf(20000000000L),
        ).send()
    }

    private fun setupBouncyCastle() {
        val provider: Provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
            ?:
            return
        if (provider.javaClass == BouncyCastleProvider::class.java) {
            return
        }
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

    sealed class WalletConnectEvent {
        data class ConnectToWalletMessage(val message: String) : WalletConnectEvent()
        data class ConnectToWalletError(val error: String) : WalletConnectEvent()
        data class WalletCreatedSuccessfully(val message: String) : WalletConnectEvent()
        data class WalletCreatedError(val error: String) : WalletConnectEvent()
        data class WalletBalanceSuccessfully(val balance: String) : WalletConnectEvent()
        data class WalletBalanceError(val error: String) : WalletConnectEvent()
        data class WalletTransactionSuccessfully(val message: String) : WalletConnectEvent()
        data class WalletTransactionError(val error: String) : WalletConnectEvent()
        data class WalletTransactionException(val exception: String) : WalletConnectEvent()
    }
}