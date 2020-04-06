package com.alexkn.syntact.presentation.deckcreation

enum class NumberOfItems(val string: String, val value: Int) {

    LOW("Low", 1), MEDIUM("Medium", 3), HIGH("High", 5);

    fun next(): NumberOfItems = vals[(this.ordinal + 1) % vals.size]

    override fun toString() = string

    companion object {
        private val vals = values()
    }
}