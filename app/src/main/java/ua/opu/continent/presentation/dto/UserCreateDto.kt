package ua.opu.continent.presentation.dto

import android.net.Uri

data class UserCreateDto(
    val selectedImage: Uri?,
    val name: String?,
    val description: String?,
    val onSuccessListener: (Void?) -> Unit
) {
    class Builder() {
        private var selectedImage: Uri? = null
        private var name: String? = null
        private var description: String? = null
        private var onSuccessListener: (Void?) -> Unit = {}


        fun selectedImage(selectedImage: Uri?) = apply { this.selectedImage = selectedImage }
        fun name(name: String?) = apply { this.name = name }
        fun description(description: String?) = apply { this.description = description }
        fun onSuccessListener(onSuccessListener: (Void?) -> Unit) =
            apply { this.onSuccessListener = onSuccessListener }

        fun build() = UserCreateDto(selectedImage, name, description, onSuccessListener)
    }

}