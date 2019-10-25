package com.example.w4d1api.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.example.w4d4java_butterknife_firebase.Message
import com.google.firebase.example.w4d4java_butterknife_firebase.R

class MessageAdapter

    : ListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffUtil()){


    override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_view_layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.apply {
            nameText.text = getItem(position).name
            messageText.text = getItem(position).message

            viewGroup.context?.let {
                Glide.with(it) //1
                        .load(getItem(position).photo)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .apply(RequestOptions.circleCropTransform())
                        .into(messageImageview)
                Log.d("LOG_X", getItem(position).photo)
            }

        }
    }
    class MessageViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nameText: TextView = view.findViewById(R.id.name_textview)
        val messageText: TextView = view.findViewById(R.id.message_textview)
        val messageImageview: ImageView = view.findViewById(R.id.message_imageview)
        val viewGroup: ConstraintLayout = view.findViewById(R.id.message_viewlayout)

    }
}

class MessageDiffUtil : DiffUtil.ItemCallback<Message>(){
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.message == newItem.message
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.name == newItem.name && oldItem.message == newItem.message
    }


}

