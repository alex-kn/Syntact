package com.alexkn.syntact.presentation.createbucket

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.alexkn.syntact.R
import com.alexkn.syntact.data.model.Phrase
import com.alexkn.syntact.data.model.Template
import com.alexkn.syntact.databinding.BucketCreateChooseTemplateCardBinding
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.function.Consumer


class ChooseTemplateAdapter : ListItemAdapter<Template, ChooseTemplateAdapter.ChooseTemplateViewHolder>() {

    lateinit var createListener: Consumer<Template>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseTemplateViewHolder {

        val binding = DataBindingUtil.inflate<BucketCreateChooseTemplateCardBinding>(LayoutInflater.from(parent.context), R.layout.bucket_create_choose_template_card, parent, false)
        return ChooseTemplateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChooseTemplateViewHolder, position: Int) {

        val template = list[position]
        holder.bindTo(template)
        holder.createButton.setOnClickListener { createListener.accept(template) }
    }

    class ChooseTemplateViewHolder(var databinding: BucketCreateChooseTemplateCardBinding) : ListItemViewHolder<Template>(databinding.root) {

        var createButton: MaterialButton = itemView.findViewById(R.id.chooseButton)

        var flagImage: ImageView = itemView.findViewById(R.id.flagImage)

        override fun bindTo(entity: Template) {
            databinding.template = entity
            val resId = itemView.context.resources.getIdentifier(entity.language.language, "drawable", itemView.context.packageName)
            flagImage.setImageResource(resId)
        }
    }
}

