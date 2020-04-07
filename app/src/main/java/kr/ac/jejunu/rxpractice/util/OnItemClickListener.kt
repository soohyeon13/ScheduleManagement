package kr.ac.jejunu.rxpractice.util

interface OnItemClickListener<T> {
    fun onItemClick(data:T)
    fun onItemMoneyClick(data: T)
    fun onItemDelete(data: T)
}