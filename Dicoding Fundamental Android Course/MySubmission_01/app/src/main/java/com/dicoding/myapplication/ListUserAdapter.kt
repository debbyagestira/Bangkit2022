package com.dicoding.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username, name, avatar) = listUser[position]
        holder.tvUsername.text = username
        holder.tvName.text = name
        holder.imgAvatar.setImageResource(avatar)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.absoluteAdapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}