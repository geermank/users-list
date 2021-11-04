package com.geermank.userslist.presentation

interface ListItemClickListener<T> {
    fun onItemClickListener(item: T)
    fun onItemLongClickListener(item: T)
}