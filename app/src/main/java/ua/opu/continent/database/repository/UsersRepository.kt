package ua.opu.continent.database.repository

import ua.opu.continent.database.dao.UsersDao
import ua.opu.continent.database.model.User
import ua.opu.continent.presentation.adapter.UsersAdapter

class UsersRepository(private val usersDao: UsersDao) {

    suspend fun bindToGetAllUsers(usersAdapter: UsersAdapter) {
        usersDao.bindToGetAllUsers(usersAdapter)
    }

    suspend fun saveUser(uid: String, user: User, onSuccessListener: (Void?) -> Unit) {
        usersDao.saveUser(uid, user, onSuccessListener)
    }
}