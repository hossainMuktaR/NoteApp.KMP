package com.hossian.noteappkmp.utils

sealed class OrderType{
    data object Ascending: OrderType()
    data object Descending: OrderType()
}