package ua.opu.continent.database.model

data class Message(
    var messageId: String? = null,
    var message: String? = null,
    var senderId: String?= null,
    var imageUrl: String?= null,
    var timeStamp: Long = 0
){
    data class Builder(
        private var messageId: String? = null,
        private var message: String? = null,
        private var senderId: String? = null,
        private var imageUrl: String?= null,
        private var timeStamp: Long = 0
    ) {


        fun messageId(messageId: String) = apply { this.messageId = messageId }
        fun message(message: String) = apply { this.message = message }
        fun senderId(senderId: String) = apply { this.senderId = senderId }
        fun imageUrl(imageUrl: String) = apply { this.imageUrl = imageUrl }
        fun timeStamp(timeStamp: Long) = apply { this.timeStamp = timeStamp }
        fun build() = Message(messageId, message, senderId, imageUrl, timeStamp)
    }

}