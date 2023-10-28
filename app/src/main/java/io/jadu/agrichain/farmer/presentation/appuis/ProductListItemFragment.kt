package io.jadu.agrichain.presentation.appuis

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
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
import io.jadu.agrichain.R
import io.jadu.agrichain.databinding.FragmentProductListItemBinding
import io.jadu.agrichain.farmer.viewmodel.ProductListItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductListItemFragment : Fragment() {
    private lateinit var binding: FragmentProductListItemBinding
    private lateinit var itemList: List<CardView>
    private lateinit var auth: FirebaseAuth
    private var dateInMillis:Long = 0L
    private var bundle = Bundle()
    private var _isImageImported = false
    private val productListViewModel: ProductListItemViewModel by viewModels()
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

        binding.PreviewAddedProduct.setOnClickListener {
            if (isFieldsEmpty()) {
                Toast.makeText(
                    requireContext(), getString(R.string.Fields_not_empty), Toast.LENGTH_SHORT
                ).show()
//                isDescriptionEmpty()
//                isProductNameEmpty()
//                isHarvestedDateEmpty()
//                isExpiryDateEmpty()
//                isProductPriceEmpty()
//                isFarmLocationEmpty()
//                isImageNotSelected()
                binding.productName.error = "Enter Product Name"
                binding.productDescription.error = "Enter Product Description"
                binding.harvestedDate.error = "Enter Harvested Date"
                binding.expiryDate.error = "Enter Expiry Date"
                binding.productPrice.error = "Enter Product Price"
                binding.farmLocation.error = "Enter Farm Location"
                binding.warningImgNotSelected.visibility = View.VISIBLE
            } else {
                productInfo()
                findNavController().navigate(
                    R.id.action_productListItemFragment_to_productPreviewFragment, bundle
                )
            }
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
        var productType = ""
        val farmLocation = binding.farmLocation.text.toString()
        //get user phone number

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
            "ImageUri" to productListViewModel.getUri().toString(),
        )


//        insertData to local
        lifecycleScope.launch(Dispatchers.IO) {
//            farmerListItemViewModel.insertListItemTypes(
//                ListItemTypes(
//                    productType,
//                    productName,
//                    farmerListItemViewModel.getUri().toString(),
//                    productDescription,
//                    harvestedDate,
//                    expiryDate,
//                    productPrice,
//                    auth.currentUser?.phoneNumber.toString()
//                )
//            )
        }
    }
    private fun isFieldsEmpty(): Boolean {
        return binding.productName.text.toString()
            .isEmpty() || binding.productDescription.text.toString()
            .isEmpty() || binding.harvestedDate.text.toString()
            .isEmpty() || binding.expiryDate.text.toString()
            .isEmpty() || binding.productPrice.text.toString()
            .isEmpty() || binding.farmLocation.text.toString().isEmpty() || !_isImageImported
    }
}
