package ua.opu.continent.database.model

data class User(
    var uid: String? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var profileImage: String? = null,
    var description: String? = null
) {

    data class Builder(
        private var uid: String? = null,
        private var name: String? = null,
        private var phoneNumber: String? = null,
        private var profileImage: String? = null,
        private var description: String? = null
    ) {


        fun uid(uid: String) = apply { this.uid = uid }
        fun name(name: String) = apply { this.name = name }
        fun phoneNumber(phoneNumber: String) = apply { this.phoneNumber = phoneNumber }
        fun profileImage(profileImage: String) = apply { this.profileImage = profileImage }
        fun description(description: String) = apply { this.description = description }
        fun build() = User(uid, name, phoneNumber, profileImage, description)
    }

}