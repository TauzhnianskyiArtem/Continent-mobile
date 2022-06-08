package ua.opu.continent.database.dao.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ua.opu.continent.database.dao.UsersDao
import ua.opu.continent.database.model.User
import ua.opu.continent.presentation.adapter.UserAdapter

object UsersDaoFirebase : UsersDao {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()


    override suspend fun bindToGetAllUsers(usersAdapter: UserAdapter) {
        val users = ArrayList<User>()
        database.reference.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                users.clear()
                for (snapshot1 in snapshot.children) {
                    val user: User? = snapshot1.getValue(User::class.java)
                    if (!user!!.uid.equals(FirebaseAuth.getInstance().uid)) users.add(user)
                }
                usersAdapter.submitList(users.toList())
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override suspend fun saveUser(uid: String, user: User, onSuccessListener: (Void?) -> Unit) {
        database.reference
            .child("users")
            .child(uid)
            .setValue(user)
            .addOnSuccessListener(onSuccessListener)
    }
}