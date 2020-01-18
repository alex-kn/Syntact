package com.alexkn.syntact.presentation.deckselection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R
import java.util.*

class DeckSelectionLanguageAdapter(private val dataset: List<Locale>) : RecyclerView.Adapter<DeckSelectionLanguageAdapter.DeckSelectionLanguageViewHolder>() {

    private val onClickSubject: MutableLiveData<Locale> = MutableLiveData();

    private var checkedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckSelectionLanguageViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.deck_selection_language_sheet_item, parent, false)

        return DeckSelectionLanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeckSelectionLanguageViewHolder, position: Int) {

        val locale = dataset[position]
        holder.textView.text = locale.displayLanguage
        holder.flag.clipToOutline = true

        val context = holder.itemView.context
        val resId = context.resources.getIdentifier(locale.language, "drawable", context.packageName)
        holder.flag.setImageResource(resId)

        holder.itemView.setOnClickListener {
            onClickSubject.value = locale
            holder.flag.alpha = 1f
            holder.itemView.isActivated = true
            if (checkedPosition != holder.adapterPosition) {
                notifyItemChanged(checkedPosition)
                checkedPosition = holder.adapterPosition
            }
        }

        if (checkedPosition == -1) {
            holder.flag.alpha = 0.25f
            holder.itemView.isActivated = false

        } else {
            if (checkedPosition == holder.adapterPosition) {
                holder.flag.alpha = 1f
                holder.itemView.isActivated = true
            } else {
                holder.flag.alpha = 0.25f
                holder.itemView.isActivated = false
            }
        }
    }


    override fun getItemCount(): Int = dataset.size

    fun getLanguage(): LiveData<Locale> {
        return onClickSubject
    }

    class DeckSelectionLanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textView: TextView = itemView.findViewById(R.id.chooseLanguageTextView)
        var flag: ImageView = itemView.findViewById(R.id.listFlagImage)
    }
}
