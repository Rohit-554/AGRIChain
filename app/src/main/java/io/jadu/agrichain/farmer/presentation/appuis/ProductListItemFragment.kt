package io.jadu.agrichain.presentation.appuis

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.kenai.jffi.Main
import dagger.hilt.android.AndroidEntryPoint
import io.jadu.agrichain.MainActivity
import io.jadu.agrichain.R
import io.jadu.agrichain.databinding.FragmentProductListItemBinding
import io.jadu.agrichain.farmer.viewmodel.FarmerAuthViewModel
import io.jadu.agrichain.farmer.viewmodel.ProductListItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListItemFragment : Fragment() {
    private lateinit var binding: FragmentProductListItemBinding
    private lateinit var itemList: List<CardView>
    private lateinit var auth: FirebaseAuth
    private var dateInMillis:Long = 0L
    private var bundle = Bundle()
    private var _isImageImported = false
    private val productListViewModel: ProductListItemViewModel by viewModels()
    private val farmerAuthViewModel:FarmerAuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListItemBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        selectProductType()
        binding.harvestedDate.setOnClickListener {
            getSeedingDate(binding.harvestedDate)
        }
        binding.expiryDate.setOnClickListener {
            getExpiryDate(binding.expiryDate)
        }

        (activity as MainActivity)?.hideFabandBootomNav()


        val pickMedia = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                farmerAuthViewModel.setUri(uri)
                binding.ivCustomimageselect.setImageURI(uri)
                binding.tvClicktoupload.text = getString(R.string.image_uploaded)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        binding.cvCustomimage.setOnClickListener {
            if(!farmerAuthViewModel.isManageStoragePermissionGranted(requireContext())){
                requestPermission()
            }else{
                _isImageImported = true
                pickMedia.launch("image/*")
            }
        }

        binding.detectLocation.setOnClickListener {
            if(!farmerAuthViewModel.checkLocationPermission()){
                requestPermissionLauncherForLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }else{
                farmerAuthViewModel.getLastLocation()
                binding.farmLocation.setText(farmerAuthViewModel.locality)
            }
        }










        binding.PreviewAddedProduct.setOnClickListener {
            productInfo()
            findNavController().navigate(
                R.id.action_productListItemFragment_to_productPreviewFragment, bundle
            )
//            if (isFieldsEmpty()) {
//                Toast.makeText(
//                    requireContext(), getString(R.string.Fields_not_empty), Toast.LENGTH_SHORT
//                ).show()
////                isDescriptionEmpty()
////                isProductNameEmpty()
////                isHarvestedDateEmpty()
////                isExpiryDateEmpty()
////                isProductPriceEmpty()
////                isFarmLocationEmpty()
////                isImageNotSelected()
//                binding.productName.error = "Enter Product Name"
//                binding.productDescription.error = "Enter Product Description"
//                binding.harvestedDate.error = "Enter Harvested Date"
//                binding.expiryDate.error = "Enter Expiry Date"
//                binding.productPrice.error = "Enter Product Price"
//                binding.farmLocation.error = "Enter Farm Location"
//                binding.warningImgNotSelected.visibility = View.VISIBLE
//            } else {
//
//            }
        }


        return binding.root
    }


    private fun getSeedingDate(view: TextInputEditText) {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val constraintsBuilder = CalendarConstraints.Builder()
        constraintsBuilder.setValidator(DateValidatorPointBackward.before(today))

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraintsBuilder.build())
            .setTitleText(R.string.select_planting_date)
            .build()

        datePicker.show(parentFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener { selection ->
            if (selection != null) {
                dateInMillis = selection
                view.setText(datePicker.headerText)
            }
        }
    }

    private fun getExpiryDate(view: TextInputEditText) {
        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val constraintsBuilder = CalendarConstraints.Builder()
        constraintsBuilder.setValidator(DateValidatorPointForward.from(today))

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraintsBuilder.build())
            .setTitleText(R.string.select_expiry_date)
            .build()

        datePicker.show(parentFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener { selection ->
            if (selection != null) {
                view.setText(datePicker.headerText)
            }
        }
    }

    private val requestPermissionLauncherForLocation = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            farmerAuthViewModel.getLastLocation()
            binding.farmLocation.setText(farmerAuthViewModel.locality)
            Toast.makeText(requireContext(), "Location Detected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.deny_perm_text), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data =
                    Uri.parse(String.format("package:%s", requireActivity().packageName))
                startActivity(intent, Bundle.EMPTY)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivity(intent)
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.deny_perm_text), Toast.LENGTH_SHORT)
                .show()
        }
    }



    private fun selectProductType() {
        binding.fragmentProductType.apply {
            itemList = listOf(
                listItem01, listItem02, listItem03, listItem04, listItem05, listItem06, listItem07
            )
        }
        var selectedCardViewIndex: Int = -1
        itemList.forEachIndexed { index, itemView ->
            itemView.setOnClickListener {
                val cardview = itemView as MaterialCardView
                if (selectedCardViewIndex == index) {
                    // if the card view is already selected, reset its background
                    itemView.background = ContextCompat.getDrawable(requireContext(), R.drawable.resetcardviewborder)
                    selectedCardViewIndex = -1
                    cardview.isChecked = false
                } else {
                    // deselect the previously selected card view
                    selectedCardViewIndex.takeIf { it != -1 }?.let { prevIndex ->
                        val prevView = itemList[prevIndex]
                        prevView.background = ContextCompat.getDrawable(
                            requireContext(), R.drawable.resetcardviewborder
                        )
                        (prevView as? MaterialCardView)?.isChecked = false
                    }
                    // select the current card view and set its background to the border drawable
                    itemView.background = ContextCompat.getDrawable(requireContext(), R.drawable.cardviewborder)
                    selectedCardViewIndex = index
//                    farmerListItemViewModel.setIndex(index)//TODO: set index to viewmodel
                    cardview.isChecked = true
                }
            }
        }
        Log.d("TAG", "selectProductType: $selectedCardViewIndex")
    }

    private fun productInfo() {
        val productName = binding.productName.text.toString()
        val productDescription = binding.productDescription.text.toString()
        val harvestedDate = binding.harvestedDate.text.toString()
        val expiryDate = binding.expiryDate.text.toString()
        val productPrice = binding.productPrice.text.toString()
        var productType = "None"
        val farmLocation = binding.farmLocation.text.toString()
        //get user phone number
        Log.d("testx", "${productName},${productDescription},${harvestedDate},${expiryDate},${productPrice},${farmLocation}")

        when (productListViewModel.getIndex()) {
            0 -> {
                productType = "Vegetables"
            }

            1 -> {
                productType = "Fruits"
            }

            2 -> {
                productType = "Handloom"
            }

            3 -> {
                productType = "Manures"
            }

            4 -> {
                productType = "Dairy"
            }

            5 -> {
                productType = "Poultry"
            }

            6 -> {
                productType = "Others"
            }
        }

        bundle = bundleOf(
            "productName" to productName,
            "productDescription" to productDescription,
            "harvestedDate" to harvestedDate,
            "expiryDate" to expiryDate,
            "productPrice" to productPrice,
            "productType" to productType,
            "farmLocation" to farmLocation,
            "dateInMillis" to dateInMillis,
            "ImageUri" to farmerAuthViewModel.getUri().toString()
        )


//        lifecycleScope.launch(Dispatchers.IO) {
//            farmerAuthViewModel.addProduct(
//                    productName,
//                    productDescription,
//                    productPrice,
//                    "0",
//                    "yes",
//                    productListViewModel.getUri(),
//                    harvestedDate,
//                    expiryDate,
//            )
//        }
    }
    private fun isFieldsEmpty(): Boolean {
        return binding.productName.text.toString()
            .isEmpty()
                || binding.productDescription.text.toString()
            .isEmpty()
                || binding.harvestedDate.text.toString()
            .isEmpty()
                ||
                binding.expiryDate.text.toString().isEmpty()
                || binding.productPrice.text.toString()
            .isEmpty()
                || binding.farmLocation.text.toString().isEmpty() || !_isImageImported
    }



}
