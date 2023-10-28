package io.jadu.agrichain.presentation.registrationui

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.jadu.agrichain.MainActivity
import io.jadu.agrichain.R
import io.jadu.agrichain.databinding.FragmentSelectLanguageBinding
import io.jadu.agrichain.utils.kvStorage.KvStorage
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class SelectLanguageFragment : Fragment() {
    private lateinit var binding: FragmentSelectLanguageBinding
    @Inject
    lateinit var kvStorage: KvStorage
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectLanguageBinding.inflate(inflater,container,false)
        navigateViaHindi()
        navigateViaEnglish()
        changeAppLanguage()
        auth = FirebaseAuth.getInstance()
        checkUserExist()
        (activity as MainActivity).hideActionBar()
        return binding.root
    }

    private fun navigateViaHindi(){
        binding.hindiCardView.setOnClickListener {
            findNavController().navigate(R.id.action_selectLanguageFragment_to_infoScreen)
            kvStorage.storageSetString("AppLanguage","hindi")
            changeAppLanguage()
        }
    }

    private fun navigateViaEnglish(){
        binding.englishCardView.setOnClickListener {
            findNavController().navigate(R.id.action_selectLanguageFragment_to_infoScreen)
            kvStorage.storageSetString("AppLanguage","english")
            changeAppLanguage()
        }
    }

    private fun changeAppLanguage(){
        val language = kvStorage.storageGetString("AppLanguage")
        if(language == "hindi"){
            //change the app language hindi
            setLocale("hi")

        }else{
            setLocale("en-us")
        }
    }

    private fun setLocale(language: String) {
        val myLocale = Locale(language)
        Locale.setDefault(myLocale)
        val configuration: Configuration = resources.configuration
        configuration.setLocale(myLocale)
        requireActivity().baseContext.resources.updateConfiguration(configuration,requireActivity().baseContext.resources.displayMetrics)
    }

    private fun checkUserExist(){
        val currentUser = auth.currentUser
        if(currentUser != null){
            findNavController().navigate(R.id.homeFragment)
        }
    }

}