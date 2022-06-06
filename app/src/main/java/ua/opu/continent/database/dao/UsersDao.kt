package ua.opu.continent.database.dao

import ua.opu.continent.presentation.adapter.UserAdapter
import ua.opu.continent.database.model.User

interface UsersDao {

    suspend fun bindToGetAllUsers(usersAdapter: UserAdapter)
    suspend fun saveUser(uid: String, user: User, onSuccessListener: (Void?) -> Unit)
}