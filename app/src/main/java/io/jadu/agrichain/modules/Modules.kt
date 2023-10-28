package io.jadu.agrichain.modules

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.jadu.agrichain.farmer.data.FarmerApiService
import io.jadu.agrichain.presentation.registrationui.phoneverfication.PhoneVerificationViewModel
import io.jadu.agrichain.utils.kvStorage.KvStorage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun kvStorage(application: Application): KvStorage {
        return KvStorage(application.applicationContext)
    }

    @Provides
    @Singleton
    fun providePhoneVerificationViewModelFactory(): PhoneVerificationViewModel {
        return PhoneVerificationViewModel()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideFarmerApiService(): FarmerApiService = Retrofit.Builder().baseUrl(FarmerApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FarmerApiService::class.java)

}