package io.jadu.agrichain.presentation.registrationui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.jadu.agrichain.R
import io.jadu.agrichain.databinding.FragmentInfoScreenBinding


class InfoScreen : Fragment() {
    private lateinit var binding: FragmentInfoScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoScreenBinding.inflate(inflater,container,false)
        binding.btnlogin.setOnClickListener {
            findNavController().navigate(R.id.action_infoScreen_to_phoneVerificationFragment)
        }

        return binding.root
    }


}