package ua.opu.continent.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import ua.opu.continent.R
import ua.opu.continent.database.model.Message
import ua.opu.continent.databinding.ReceiveMsgBinding
import ua.opu.continent.databinding.SendMsgBinding


class MessagesAdapter(
    private val context: Context,
    private val onClick: (Message) -> Unit
) :
    ListAdapter<Message, RecyclerView.ViewHolder?>(MessageDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.send_msg, parent, false)
            SentViewHolder(view, onClick)
        } else {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.receive_msg, parent, false)
            ReceiverViewHolder(view, onClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message: Message = getItem(position)
        return if (FirebaseAuth.getInstance().uid == message.senderId) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message: Message = getItem(position)
        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            viewHolder.bind(message)
        } else {
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.bind(message)
        }


    }

    inner class SentViewHolder(itemView: View, val onClick: (Message) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        var binding = SendMsgBinding.bind(itemView)

        fun bind(message: Message) {
            if (message.message.equals("photo")) {
                this.binding.image.visibility = View.VISIBLE
                this.binding.message.visibility = View.GONE
                this.binding.mLinear.visibility = View.GONE
                Glide.with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(this.binding.image)
            }
            this.binding.message.text = message.message
            this.itemView.setOnLongClickListener {
                message.let(onClick)
                false
            }

        }

    }

    inner class ReceiverViewHolder(itemView: View, val onClick: (Message) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        var binding: ReceiveMsgBinding = ReceiveMsgBinding.bind(itemView)

        fun bind(message: Message) {
            if (message.message.equals("photo")) {
                this.binding.image.visibility = View.VISIBLE
                this.binding.message.visibility = View.GONE
                this.binding.mLinear.visibility = View.GONE
                Glide.with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(this.binding.image)
            }
            this.binding.message.text = message.message
            this.itemView.setOnLongClickListener {
                message.let(onClick)
                false
            }

        }

    }

    object MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
            newItem == oldItem

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
            newItem.hashCode() == oldItem.hashCode()

    }

    companion object {
        private const val ITEM_SENT = 1
        private const val ITEM_RECEIVE = 2
    }
}