package ua.opu.continent.presentation.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ua.opu.continent.R
import ua.opu.continent.databinding.FragmentSetupProfileBinding
import ua.opu.continent.presentation.MainViewModel
import ua.opu.continent.presentation.MainViewModelFactory
import ua.opu.continent.presentation.dialog.ProgressDialog
import ua.opu.continent.presentation.dto.UserCreateDto

class SetupProfileFragment() : Fragment(R.layout.fragment_setup_profile) {
    private lateinit var binding: FragmentSetupProfileBinding
    private lateinit var dialog: ProgressDialog
    private var selectedImage: Uri? = null

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    private val contentLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            binding.imageView.setImageURI(it)
            selectedImage = it
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = ProgressDialog.newInstance("Updating profile...")

        binding.imageView.setOnClickListener {
            contentLauncher.launch("image/*")
        }

        binding.continueBtn02.setOnClickListener(View.OnClickListener {
            val name: String = binding.nameBox.text.toString()
            val description = binding.descriptionBox.text.toString()

            if (name.isEmpty()) {
                binding.nameBox.error = "Please type a name"
                return@OnClickListener
            }

            if (description.isEmpty()) {
                binding.nameBox.error = "Please type a description"
                return@OnClickListener
            }
            dialog.show(parentFragmentManager, "updProfile")

            val userCreateDto = UserCreateDto.Builder()
                .selectedImage(selectedImage)
                .name(name)
                .description(description)
                .onSuccessListener {
                    dialog.dismiss()
                    findNavController().navigate(
                        SetupProfileFragmentDirections.actionSetupProfileFragmentToMainFragment2()
                    )
                }
                .build()
            viewModel.saveUser(userCreateDto)

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetupProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}