package ua.opu.continent.presentation

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ua.opu.continent.presentation.adapter.MessagesAdapter
import ua.opu.continent.presentation.adapter.UsersAdapter
import ua.opu.continent.presentation.dto.MessageCreateDto
import ua.opu.continent.presentation.dto.UserCreateDto
import ua.opu.continent.useсase.AuthenticationUseCase
import ua.opu.continent.useсase.ChatsUseCase
import ua.opu.continent.useсase.PresenceUseCase
import ua.opu.continent.useсase.UsersUseCase

class MainViewModel(
    private val authUseCase: AuthenticationUseCase,
    private val userUseCase: UsersUseCase,
    private val chatsUseCase: ChatsUseCase,
    private val presenceUseCase: PresenceUseCase
) : ViewModel() {


    fun isAuthenticateUser(): Boolean = runBlocking {
        authUseCase.isAuthenticateUser()
    }

    fun sendingCode(
        phoneNumber: String,
        requireActivity: FragmentActivity,
        onCodeSent: (String) -> Unit
    ) = viewModelScope.launch {
        authUseCase.sendingCode(phoneNumber, requireActivity, onCodeSent)
    }

    fun verifyCode(
        verificationId: String,
        otpCode: String,
        completeListener: OnCompleteListener<AuthResult>
    ) = viewModelScope.launch {
        authUseCase.verifyCode(verificationId, otpCode, completeListener)
    }

    fun bindToGetAllUsers(usersAdapter: UsersAdapter) = viewModelScope.launch {
        userUseCase.bindToGetAllUsers(usersAdapter)
    }

    fun saveUser(
        userCreateDto: UserCreateDto
    ) = viewModelScope.launch {
        userUseCase.saveUser(userCreateDto)
    }

    fun setUserPresence(presence: String) = viewModelScope.launch {
        presenceUseCase.setUserPresence(presence)
    }

    fun bindToGetReceiverStatus(receiverUid: String, getStatus: (String) -> Unit) =
        viewModelScope.launch {
            presenceUseCase.bindToGetReceiverStatus(receiverUid, getStatus)
        }

    fun bindToGetAllMessages(senderRoom: String, adapter: MessagesAdapter) = viewModelScope.launch {
        chatsUseCase.bindToGetAllMessages(senderRoom, adapter)
    }

    fun sendMessage(message: MessageCreateDto) = viewModelScope.launch {
        chatsUseCase.sendMessage(message)
    }

    fun sendMessagePhoto(
        message: MessageCreateDto
    ) = viewModelScope.launch {
        chatsUseCase.sendMessagePhoto(message)
    }

    companion object {
        val TAG_FOR_LOGS: String? = MainViewModel::class.simpleName
    }


}

class MainViewModelFactory(
    private val authUseCase: AuthenticationUseCase,
    private val userUseCase: UsersUseCase,
    private val chatsUseCase: ChatsUseCase,
    private val presenceUseCase: PresenceUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(authUseCase, userUseCase, chatsUseCase, presenceUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }

}