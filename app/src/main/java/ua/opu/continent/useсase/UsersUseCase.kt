package ua.opu.continent.useсase

import ua.opu.continent.presentation.adapter.UserAdapter
import ua.opu.continent.presentation.dto.UserCreateDto

interface UsersUseCase {

    suspend fun bindToGetAllUsers(usersAdapter: UserAdapter)

    suspend fun saveUser(
        userCreateDto: UserCreateDto
    )
}