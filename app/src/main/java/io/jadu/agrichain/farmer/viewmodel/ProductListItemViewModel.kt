package io.jadu.agrichain.farmer.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListItemViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application
):ViewModel() {
    private var _index = 0
    private var _uri:Uri? = null
    fun setIndex(index: Int) {
        _index = index
    }

    fun getIndex(): Int {
        return _index
    }
    fun getUri(): Uri? {
        return _uri
    }
}