package ua.opu.continent.database.repository

import ua.opu.continent.database.dao.UsersDao
import ua.opu.continent.database.dao.impl.UsersDaoFirebase
import ua.opu.continent.database.model.User
import ua.opu.continent.presentation.adapter.UserAdapter

object UsersRepository {
    private val usersDao: UsersDao = UsersDaoFirebase

    suspend fun bindToGetAllUsers(usersAdapter: UserAdapter) {
        usersDao.bindToGetAllUsers(usersAdapter)
    }

    suspend fun saveUser(uid: String, user: User, onSuccessListener: (Void?) -> Unit) {
        usersDao.saveUser(uid, user, onSuccessListener)
    }
}