package ua.opu.continent.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ua.opu.continent.databinding.ProgressDialogBinding


class ProgressDialog : DialogFragment() {

    private lateinit var binding: ProgressDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = ProgressDialogBinding.inflate(layoutInflater)

        binding.textProgress.text = arguments?.getString("message")

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setCancelable(false)
            .create()
    }


    companion object {
        fun newInstance(message: String): ProgressDialog {
            var dialog = ProgressDialog()
            val args = Bundle()
            args.putString("message", message)
            dialog.arguments = args
            return dialog
        }
    }
}