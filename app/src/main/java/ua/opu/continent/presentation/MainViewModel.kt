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
import ua.opu.continent.presentation.adapter.UserAdapter
import ua.opu.continent.presentation.dto.MessageCreateDto
import ua.opu.continent.presentation.dto.UserCreateDto
import ua.opu.continent.useсase.AuthenticationUseCase
import ua.opu.continent.useсase.ChatsUseCase
import ua.opu.continent.useсase.PresenceUseCase
import ua.opu.continent.useсase.UsersUseCase
import ua.opu.continent.useсase.impl.AuthenticationUseCaseFirebase
import ua.opu.continent.useсase.impl.ChatsUseCaseFirebase
import ua.opu.continent.useсase.impl.PresenceUseCaseFirebase
import ua.opu.continent.useсase.impl.UsersUseCaseFirebase
import ua.opu.continent.useсase.logging.AuthenticationUserCaseLog
import ua.opu.continent.useсase.logging.ChatsUserCaseLog
import ua.opu.continent.useсase.logging.PresenceUserCaseLog
import ua.opu.continent.useсase.logging.UsersUserCaseLog

class MainViewModel() : ViewModel() {
    private var authUseCaseFirebase: AuthenticationUseCase =
        AuthenticationUserCaseLog(AuthenticationUseCaseFirebase(), TAG_FOR_LOGS)
    private var userUseCase: UsersUseCase = UsersUserCaseLog(UsersUseCaseFirebase(), TAG_FOR_LOGS)
    private var chatsUseCase: ChatsUseCase = ChatsUserCaseLog(ChatsUseCaseFirebase(), TAG_FOR_LOGS)
    private var presenceUseCase: PresenceUseCase =
        PresenceUserCaseLog(PresenceUseCaseFirebase(), TAG_FOR_LOGS)

    fun isAuthenticateUser(): Boolean = runBlocking {
        authUseCaseFirebase.isAuthenticateUser()
    }

    fun sendingCode(
        phoneNumber: String,
        requireActivity: FragmentActivity,
        onCodeSent: (String) -> Unit
    ) = viewModelScope.launch {
        authUseCaseFirebase.sendingCode(phoneNumber, requireActivity, onCodeSent)
    }

    fun verifyCode(
        verificationId: String,
        otpCode: String,
        completeListener: OnCompleteListener<AuthResult>
    ) = viewModelScope.launch {
        authUseCaseFirebase.verifyCode(verificationId, otpCode, completeListener)
    }

    fun bindToGetAllUsers(usersAdapter: UserAdapter) = viewModelScope.launch {
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

class MainViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }

}