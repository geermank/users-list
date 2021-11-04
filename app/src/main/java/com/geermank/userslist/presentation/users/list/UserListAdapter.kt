package com.geermank.userslist.presentation.users.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geermank.userslist.R
import com.geermank.userslist.databinding.UserListItemBinding
import com.geermank.userslist.presentation.ListItemClickListener
import com.geermank.userslist.presentation.setProfilePicOrDefault
import com.geermank.userslist.presentation.setTextOrRemove
import com.geermank.userslist.presentation.users.model.UserUiModel
import java.util.*

class UserListAdapter(
    users: List<UserUiModel> = emptyList(),
    private val itemClickListener: ListItemClickListener<UserUiModel>
) : RecyclerView.Adapter<UserViewHolder>() {

    private val users: MutableList<UserUiModel> = LinkedList(users)

    fun clearAndUpdate(users: List<UserUiModel>) {
        val changeStartIndex = this.users.size
        val itemsInsertedCount = users.size
        this.users.clear()
        this.users.addAll(users)
        notifyItemRangeInserted(changeStartIndex, itemsInsertedCount)
    }

    fun remove(item: UserUiModel) {
        val position = users.indexOf(item)
        if (position != -1) {
            users.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvUserName: TextView
    private val tvUserBio: TextView
    private val ivUserProfilePic: ImageView

    init {
        UserListItemBinding.bind(view).run {
            this@UserViewHolder.tvUserName = tvUserName
            this@UserViewHolder.tvUserBio = tvUserBio
            this@UserViewHolder.ivUserProfilePic = ivUserProfilePic
        }
    }

    fun bind(user: UserUiModel, clickListener: ListItemClickListener<UserUiModel>) {
        setUpItemUi(user)
        itemView.setOnClickListener { clickListener.onItemClickListener(user) }
        itemView.setOnLongClickListener {
            clickListener.onItemLongClickListener(user)
            true
        }
    }

    private fun setUpItemUi(user: UserUiModel) {
        user.run {
            tvUserName.text = name
            tvUserBio.setTextOrRemove(bio)
            ivUserProfilePic.setProfilePicOrDefault(profilePic, R.drawable.ic_default_profile_pic)
        }
    }
}
