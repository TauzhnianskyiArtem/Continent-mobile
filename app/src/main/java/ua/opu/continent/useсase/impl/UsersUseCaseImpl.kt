package ua.opu.continent.useсase.impl

import androidx.annotation.WorkerThread
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ua.opu.continent.presentation.adapter.UserAdapter
import ua.opu.continent.database.repository.UsersRepository
import ua.opu.continent.database.model.User
import ua.opu.continent.presentation.dto.UserCreateDto
import ua.opu.continent.useсase.UsersUseCase

class UsersUseCaseImpl : UsersUseCase {
    private val usersRepository: UsersRepository = UsersRepository()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()

    @WorkerThread
    override suspend fun bindToGetAllUsers(usersAdapter: UserAdapter) {
        usersRepository.bindToGetAllUsers(usersAdapter)
    }

    @WorkerThread
    override suspend fun saveUser(
        userCreateDto: UserCreateDto
    ) {

        val uid = FirebaseAuth.getInstance().uid
        val phone = FirebaseAuth.getInstance().currentUser!!.phoneNumber
        val name: String = userCreateDto.name!!
        val description: String = userCreateDto.description!!
        val selectedImage = userCreateDto.selectedImage

        val user = User.Builder()
            .uid(uid!!)
            .name(name)
            .phoneNumber(phone!!)
            .description(description)
            .build()

        if (selectedImage == null) {
            usersRepository.saveUser(uid, user, userCreateDto.onSuccessListener)
            return
        }

        val reference = storage.reference.child("Profiles").child(uid)

        reference.putFile(selectedImage).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener { uri ->
                    GlobalScope.launch {
                        user.profileImage = uri.toString()
                        usersRepository.saveUser(uid, user, userCreateDto.onSuccessListener)
                    }
                }
            }
        }

    }
}