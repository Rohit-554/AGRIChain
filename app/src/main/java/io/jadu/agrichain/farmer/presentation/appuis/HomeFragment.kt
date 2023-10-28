package io.jadu.agrichain.presentation.appuis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.jadu.agrichain.MainActivity
import io.jadu.agrichain.R
import io.jadu.agrichain.databinding.FragmentHomeBinding
import io.jadu.agrichain.farmer.viewmodel.FarmerAuthViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private val farmerAuthViewModel: FarmerAuthViewModel by viewModels()
    private var backPressedTime: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        setupMenu()
        exitOnBackPress()
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            requireActivity().finish()
        }
        farmerAuthViewModel.setPhone("farmer",auth.currentUser?.phoneNumber!!.substring(3))
        (activity as MainActivity).showBottomNavigation()
        (activity as MainActivity).showActionBar()

        return binding.root
    }

    private fun exitOnBackPress(){
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            if (System.currentTimeMillis() - backPressedTime < 2000) { // if back pressed twice within 2 seconds
                activity?.finish()
            } else {
                Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
                backPressedTime = System.currentTimeMillis() // update the last back pressed time
            }
        }
    }
    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menuitems, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.user_profile){
                    //implement nav controller and set the destination ...
                }
                return true
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}