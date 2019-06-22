package com.alexkn.syntact.presentation.createbucket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.alexkn.syntact.R
import java.util.Locale

class ChooseLanguageAdapter(private val dataset: List<Locale>) : RecyclerView.Adapter<ChooseLanguageAdapter.ChooseLanguageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseLanguageViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bucket_create_choose_child, parent, false)

        return ChooseLanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChooseLanguageViewHolder, position: Int) {
        val realPos = position % dataset.size
        holder.textView.text = dataset[realPos].displayLanguage
    }

    override fun getItemCount(): Int = Int.MAX_VALUE


    fun getItemAt(position: Int): Locale = dataset[position]

    class ChooseLanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textView: TextView = itemView.findViewById(R.id.chooseLanguageTextView)

    }
}
