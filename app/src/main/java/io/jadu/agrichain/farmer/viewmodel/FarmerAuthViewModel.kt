package io.jadu.agrichain.farmer.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jadu.agrichain.farmer.models.dtos.AddProduct
import io.jadu.agrichain.farmer.models.dtos.Shop
import io.jadu.agrichain.farmer.models.dtos.productDetails
import io.jadu.agrichain.farmer.models.repository.FarmerRepository
import io.jadu.agrichain.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.bouncycastle.jce.provider.BouncyCastleProvider
import retrofit2.HttpException
import java.io.File
import java.security.Provider
import java.security.Security
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FarmerAuthViewModel @Inject constructor(
    private val farmerRepository: FarmerRepository,
    private val application: Application,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
):ViewModel(){

    private val mainEventChannel = Channel<MainEvent>()
    val mainEvent = mainEventChannel.receiveAsFlow()
    private val _getAllProducts = MutableLiveData<productDetails>()

    val getAllProduct: LiveData<productDetails>
        get() = _getAllProducts

    private val _getItemList = MutableLiveData<List<productDetails>>()
    val getItemList: LiveData<List<productDetails>>
        get() = _getItemList

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var _index = 0
    private var _imageData: MultipartBody.Part? = null
    private var _uri: Uri? = null
    var locality = ""

    init {
        setupBouncyCastle()
        getProducts(Constants.farmerId)
        getProductDetails(Constants.productId)
    }

    private fun setupBouncyCastle() {
        val provider: Provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
            ?: return
        if (provider.javaClass == BouncyCastleProvider::class.java) {
            return
        }
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

    fun setPhone(role:String, phoneNo: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            farmerRepository.farmerAuth(role,phoneNo)
        } catch (e: HttpException) {
            e.printStackTrace()
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.message()))
            }
        }
    }


    fun addProduct(farmerId: String,name:String,description: String,price:Int,quantity:Int,availability:String, image_url:String, sow_date:String, harvest_date:String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            farmerRepository.addProduct(Constants.farmerId,name, description, price, quantity, availability, image_url, sow_date, harvest_date)
        }catch (e:HttpException){
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.ProductAddedSuccess(e.message()))
            }
        }
    }

    fun getProductDetails(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = farmerRepository.getProduct(productId)
            if (response.isSuccessful){
                response.body()?.let {
                    _getAllProducts.postValue(it)
                }
            }
        }catch (e:HttpException){
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.message()))
            }
        }
    }

    fun getProducts(userId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = farmerRepository.getProducts(userId)
            if (response.isSuccessful){
                response.body()?.let {
                    Log.d("FarmerAuthViewModel", "getProducts: $it")
                    _getItemList.postValue(it)
                }
            }
        }catch (e:HttpException){
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.message()))
            }
        }
    }


    fun farmerProductList(addProduct: AddProduct) = viewModelScope.launch(Dispatchers.IO) {
        try {
            farmerRepository.getFarmerProductList(
                AddProduct(
                Constants.farmerId,
                addProduct.name,
                addProduct.description,
                addProduct.price,
                addProduct.quantity,
                addProduct.availability,
                addProduct.image_url,
                addProduct.sow_date,
                addProduct.harvest_date,
            )
            )
        }catch (e:HttpException){
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.message()))
            }
        }
    }

    fun checkLocationPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(application, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(application, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

    fun getLastLocation(){
        if(checkLocationPermission()){
            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener {
                if (it != null) {
                    getFarmLocationFromCoordinates(it.latitude, it.longitude)
                }
            }
        }
    }

    private fun getFarmLocationFromCoordinates(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(application, Locale.getDefault())
        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
            locality = addresses[0].getAddressLine(0)
        }catch (e:Exception){
            viewModelScope.launch {
                mainEventChannel.send(MainEvent.Error(e.toString()))
            }
        }

    }

    fun setIndex(index: Int) {
        _index = index
    }

    fun getIndex(): Int {
        return _index
    }

    fun setUri(uri: Uri) {
        _uri = uri
    }

    fun getUri(): Uri? {
        return _uri
    }

    fun getImagePart(selectedImageUri: Uri, context: Context): MultipartBody.Part {
        val file = File(getRealPathFromUri(selectedImageUri, context))
        val contentResolver = context.contentResolver
        val mimeType = contentResolver.getType(selectedImageUri)
        val requestFile = RequestBody.create(mimeType?.toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("productImageUrl", file.name, requestFile)
    }

    private fun getRealPathFromUri(uri: Uri, context: Context): String {
        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.moveToFirst()
        val nameIndex = cursor?.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
        val fileName = cursor?.getString(nameIndex!!)
        cursor?.close()

        // Construct the actual file path
        val path = Environment.getExternalStorageDirectory().absolutePath + "/Download/" + fileName
        Log.d("FarmerListItemViewModel", "getRealPathFromUri: $path")

        return path
    }

    fun isManageStoragePermissionGranted(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            return result == PackageManager.PERMISSION_GRANTED
        }
    }

    sealed class MainEvent {
        data class Error(val error: String) : MainEvent()
        data class Success(val message: String) : MainEvent()
        data class shopAddedSuccess(val message: String):MainEvent()

        data class ProductAddedSuccess(val message: String):MainEvent()
    }

}