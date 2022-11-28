package com.test.moladininterviewtest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.test.moladininterviewtest.databinding.ItemListUserBinding
import com.test.moladininterviewtest.domain.model.User

class UserAdapter(): PagingDataAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       return UserViewHolder(
           ItemListUserBinding.inflate(
               LayoutInflater.from(parent.context), parent, false
           )
       )
    }

    class UserDiffCallback: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    class UserViewHolder(
        val binding: ItemListUserBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.apply {
                tvUserName.text = "${user.first_name} ${user.last_name}"
                tvEmail.text = user.email

                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop().override(100,100)
                Glide.with(root).load(user.avatar).apply(requestOptions).into(ivAvatar)
            }

        }
    }

}