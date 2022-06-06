package ua.opu.continent.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase
import ua.opu.continent.databinding.DeleteDialogBinding


class DeleteMessageDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DeleteDialogBinding.inflate(layoutInflater)
        val messageId = arguments?.getString("messageId") as String
        val senderRoom = arguments?.getString("senderRoom") as String
        val receiverRoom = arguments?.getString("receiverRoom") as String

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Message")
            .setView(binding.root)
            .create()

        binding.everyone.setOnClickListener {
            messageId.let { it1 ->
                FirebaseDatabase.getInstance().reference
                    .child("chats")
                    .child(senderRoom)
                    .child("messages")
                    .child(it1!!).setValue(null)
            }
            messageId.let { it1 ->
                FirebaseDatabase.getInstance().reference
                    .child("chats")
                    .child(receiverRoom)
                    .child("messages")
                    .child(it1!!).setValue(null)
            }
            dialog.dismiss()
        }
        binding.delete.setOnClickListener {
            messageId.let { it1 ->
                FirebaseDatabase.getInstance().reference
                    .child("chats")
                    .child(senderRoom)
                    .child("messages")
                    .child(it1!!).setValue(null)
            }
            dialog.dismiss()
        }
        binding.cancel.setOnClickListener { dialog.dismiss() }

        return dialog
    }

    companion object {
        fun newInstance(
            messageId: String?,
            senderRoom: String,
            receiverRoom: String
        ): DeleteMessageDialog {
            var dialog = DeleteMessageDialog()
            val args = Bundle()
            args.putString("messageId", messageId)
            args.putString("senderRoom", senderRoom)
            args.putString("receiverRoom", receiverRoom)
            dialog.arguments = args
            return dialog
        }
    }


}