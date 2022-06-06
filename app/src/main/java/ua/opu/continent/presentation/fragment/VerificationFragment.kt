package ua.opu.continent.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ua.opu.continent.R
import ua.opu.continent.databinding.FragmentVerificationBinding


class VerificationFragment() :
    Fragment(R.layout.fragment_verification) {

    private lateinit var binding: FragmentVerificationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editNumber.requestFocus()
        binding.continueBtn.setOnClickListener {
            val phoneNumber = binding.editNumber.text.toString()
            findNavController().navigate(
                VerificationFragmentDirections.actionVerificationFragmentToOTPFragment(phoneNumber)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }


}