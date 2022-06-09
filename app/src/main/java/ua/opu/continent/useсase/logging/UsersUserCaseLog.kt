package ua.opu.continent.useсase.logging

import android.util.Log
import ua.opu.continent.presentation.adapter.UsersAdapter
import ua.opu.continent.presentation.dto.UserCreateDto
import ua.opu.continent.useсase.UsersUseCase

class UsersUserCaseLog(private val originalUserCase: UsersUseCase, private val tag: String?) :
    UsersUseCase {

    override suspend fun bindToGetAllUsers(usersAdapter: UsersAdapter) {
        Log.d(tag, "Function: bindToGetAllUsers")
        originalUserCase.bindToGetAllUsers(usersAdapter)
    }

    override suspend fun saveUser(userCreateDto: UserCreateDto) {
        Log.d(tag, "Function: saveUser, User name: ${userCreateDto.name}")
        originalUserCase.saveUser(userCreateDto)
    }
}