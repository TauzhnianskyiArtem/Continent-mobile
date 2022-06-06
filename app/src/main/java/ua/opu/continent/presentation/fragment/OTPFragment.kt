package ua.opu.continent.presentation.fragment

import android.Manifest
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Telephony
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ua.opu.continent.presentation.MainViewModel
import ua.opu.continent.presentation.MainViewModelFactory
import ua.opu.continent.R
import ua.opu.continent.databinding.FragmentOtpBinding
import ua.opu.continent.presentation.dialog.ProgressDialog
import ua.opu.continent.presentation.receiver.SmsReceiver

class OTPFragment() : Fragment(R.layout.fragment_otp), SmsReceiver.SmsReceiverListener {

    private lateinit var binding: FragmentOtpBinding
    private val args: OTPFragmentArgs by navArgs()
    private var verificationId: String? = null
    private val receiver = SmsReceiver(this)

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        Log.d(OTPFragment::class.simpleName, "Permission result^ $it")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        val dialog = ProgressDialog.newInstance("Sending OTP...")
        dialog.show(parentFragmentManager, "otpSend")
        val phoneNumber = args.phoneNumber
        binding.phoneLble.text = "Verify $phoneNumber"

        viewModel.sendingCode(phoneNumber!!, requireActivity()) {
            dialog.dismiss()
            verificationId = it
            binding.otpView.requestFocus()
            requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
        }


        binding.continueBtn01.setOnClickListener {
            val otpCode = binding.otpView.text.toString()
            viewModel.verifyCode(verificationId!!, otpCode) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(
                        OTPFragmentDirections.actionOTPFragmentToSetupProfileFragment()
                    )
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        requireActivity().registerReceiver(receiver, filter)
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStop() {
        super.onStop()

        requireActivity().unregisterReceiver(receiver)
    }


    override fun onReceiverResult(code: CharSequence) {
        binding.otpView.text = SpannableStringBuilder(code)
    }
}

