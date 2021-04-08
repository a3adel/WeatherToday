package com.example.weathertoday.presentation.base

interface OnRecyclerViewItemClickListener<T> {
    fun onItemClicked(item: T)
}