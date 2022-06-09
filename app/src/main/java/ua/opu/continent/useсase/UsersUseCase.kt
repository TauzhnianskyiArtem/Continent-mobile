package ua.opu.continent.useсase

import ua.opu.continent.presentation.adapter.UsersAdapter
import ua.opu.continent.presentation.dto.UserCreateDto

interface UsersUseCase {

    suspend fun bindToGetAllUsers(usersAdapter: UsersAdapter)

    suspend fun saveUser(
        userCreateDto: UserCreateDto
    )
}