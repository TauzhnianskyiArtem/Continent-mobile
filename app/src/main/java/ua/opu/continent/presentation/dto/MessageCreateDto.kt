package ua.opu.continent.presentation.dto

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask

data class MessageCreateDto(
    val uriPhoto: Uri?,
    val message: String?,
    val senderRoom: String?,
    val receiverRoom: String?,
    val onCompleteListener: (Task<UploadTask.TaskSnapshot>) -> Unit
) {
    class Builder() {
        private var uriPhoto: Uri? = null
        private var message: String? = null
        private var senderRoom: String? = null
        private var receiverRoom: String? = null
        private var onCompleteListener: (Task<UploadTask.TaskSnapshot>) -> Unit = {}


        fun uriPhoto(uriPhoto: Uri) = apply { this.uriPhoto = uriPhoto }
        fun message(message: String) = apply { this.message = message }
        fun senderRoom(senderRoom: String) = apply { this.senderRoom = senderRoom }
        fun receiverRoom(receiverRoom: String) = apply { this.receiverRoom = receiverRoom }
        fun onCompleteListener(onCompleteListener: (Task<UploadTask.TaskSnapshot>) -> Unit) =
            apply { this.onCompleteListener = onCompleteListener }

        fun build() =
            MessageCreateDto(uriPhoto, message, senderRoom, receiverRoom, onCompleteListener)
    }

}