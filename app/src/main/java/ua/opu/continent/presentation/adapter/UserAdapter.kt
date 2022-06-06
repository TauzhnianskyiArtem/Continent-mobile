package ua.opu.continent.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ua.opu.continent.R
import ua.opu.continent.databinding.ItemProfileBinding
import ua.opu.continent.database.model.User

class UserAdapter(
    private val context: Context,
    private val onClick: (User) -> Unit
) :
    ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback) {

    inner class UserViewHolder(itemView: View, onClick: (User) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val binding: ItemProfileBinding = ItemProfileBinding.bind(itemView)

        fun bind(user: User) {
            this.binding.username.text = user.name
            this.binding.description.text = user.description
            Glide.with(context).load(user.profileImage)
                .placeholder(R.drawable.avatar)
                .into(this.binding.profile)
            this.itemView.setOnClickListener {
                user.let(onClick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var v = LayoutInflater.from(context).inflate(
            R.layout.item_profile,
            parent, false
        )
        return UserViewHolder(v, onClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = getItem(position)
        holder.bind(user)
    }

    object UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            newItem.uid == oldItem.uid

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = newItem == oldItem
    }
}