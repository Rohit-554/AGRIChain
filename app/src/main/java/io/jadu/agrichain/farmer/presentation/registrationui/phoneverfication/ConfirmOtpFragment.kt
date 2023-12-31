package io.jadu.agrichain.presentation.registrationui.phoneverfication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import io.jadu.agrichain.R
import io.jadu.agrichain.databinding.FragmentConfirmOtpBinding


class ConfirmOtpFragment : Fragment() {
    private lateinit var binding: FragmentConfirmOtpBinding
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private var phoneAuthId: String? = null
    private var editTexts = arrayOfNulls<EditText>(6)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmOtpBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
        phoneAuthId = arguments?.getString("verificationId")


        editTexts = arrayOf(
            binding.etOtp01,
            binding.etOtp02,
            binding.etOtp03,
            binding.etOtp04,
            binding.etOtp05,
            binding.etOtp06
        )
        editTexts.forEach { editText ->
            editText?.addTextChangedListener(textWatcher)
        }
        binding.btnVerify.setOnClickListener {
            binding.lottieProgress.visibility = View.VISIBLE
            binding.btnVerify.visibility = View.GONE
            verifyOtp()
        }


        return binding.root
    }

    private fun verifyOtp() {
        val otp01 = binding.etOtp01.text.toString()
        val otp02 = binding.etOtp02.text.toString()
        val otp03 = binding.etOtp03.text.toString()
        val otp04 = binding.etOtp04.text.toString()
        val otp05 = binding.etOtp05.text.toString()
        val otp06 = binding.etOtp06.text.toString()
        val otp = "$otp01$otp02$otp03$otp04$otp05$otp06"


        val credential = PhoneAuthProvider.getCredential(phoneAuthId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    updateUiAfterVerification()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text =
                            requireActivity().getString(R.string.InvalidOtp)
                        binding.lottieProgress.visibility = View.GONE
                        binding.btnVerify.visibility = View.VISIBLE
                    }
                }
            }
    }

    private fun updateUiAfterVerification() {
        binding.lottieProgress.visibility = View.GONE
        //trim first +91 from phonenumber
        val phoneNumber = currentUser?.phoneNumber.toString().substring(3)
        Log.d("PhoneVerificationFragment", "updateUiAfterVerification: $phoneNumber")
        uploadPhoneNumberToRemoteDatabase(phoneNumber)
        findNavController().navigate(R.id.action_confirmOtpFragment_to_homeFragment)
    }

    private fun uploadPhoneNumberToRemoteDatabase(phoneNumber: String){
        Log.d("PhoneVerificationFragment", "uploadPhoneNumberToRemoteDatabase: $phoneNumber")
//        farmerListItemViewModel.setPhone(phoneNumber)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 1) {
                for (i in editTexts.indices) {
                    val editText = editTexts[i]
                    if (editText === requireActivity().currentFocus) {
                        if (i < editTexts.size - 1) {
                            editTexts[i + 1]?.requestFocus()
                        }
                        break
                    }
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            val currentEditText = requireActivity().currentFocus as? EditText
            if (count == 1 && after == 0) {
                if (currentEditText != null) {
                    val currentIndex = editTexts.indexOf(currentEditText)
                    if (currentIndex > 0) {
                        editTexts[currentIndex - 1]?.requestFocus()
                    }
                }
            }
        }


        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

}