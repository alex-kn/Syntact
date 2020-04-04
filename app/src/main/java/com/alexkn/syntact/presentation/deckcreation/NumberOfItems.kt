package com.alexkn.syntact.presentation.deckcreation

enum class NumberOfItems(val string: String, val value: Int) {

    LOW("Low", 2), MEDIUM("Medium", 5), HIGH("High", 10);

    fun next(): NumberOfItems = vals[(this.ordinal + 1) % vals.size]

    override fun toString() = string

    companion object {
        private val vals = values()
    }
}