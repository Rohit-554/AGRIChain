package io.jadu.agrichain.presentation.appuis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.jadu.agrichain.R
import io.jadu.agrichain.databinding.FragmentProductPreviewBinding


class ProductPreviewFragment : Fragment() {
    private lateinit var binding: FragmentProductPreviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductPreviewBinding.inflate(inflater,container,false)
        return binding.root
    }
}