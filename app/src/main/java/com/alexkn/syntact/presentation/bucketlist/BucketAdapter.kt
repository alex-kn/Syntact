package com.alexkn.syntact.presentation.bucketlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.alexkn.syntact.R
import com.alexkn.syntact.app.TAG
import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.databinding.BucketListBucketCardBinding
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import com.google.android.material.button.MaterialButton
import java.time.Instant
import java.time.temporal.ChronoUnit.DAYS
import kotlin.math.ceil

class BucketAdapter : ListItemAdapter<BucketDetail, BucketAdapter.BucketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BucketViewHolder {

        val dataBinding = DataBindingUtil
                .inflate<BucketListBucketCardBinding>(LayoutInflater.from(parent.context), R.layout.bucket_list_bucket_card, parent, false)

        val drawable = ResourcesCompat.getDrawable(parent.resources, R.drawable.fr, null)
        dataBinding.flag = drawable


        return BucketViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: BucketViewHolder, position: Int) {

        val bucket = list[position]
        holder.bindTo(bucket)
    }

    class BucketViewHolder(private val binding: BucketListBucketCardBinding) : ListItemViewHolder<BucketDetail>(binding.root) {

        private lateinit var bucket: BucketDetail

        override fun bindTo(entity: BucketDetail) {

            this.bucket = entity

            binding.bucket = entity

            binding.imageView2.clipToOutline = true

            val resId = itemView.context.resources.getIdentifier(entity.language.language, "drawable", itemView.context.packageName)
            val drawable = ResourcesCompat.getDrawable(itemView.resources, resId, null)
            binding.flag = drawable

            binding.progress = ceil(100 - bucket.dueCount.toDouble() / (bucket.itemCount - bucket.disabledCount) * 100).toInt()

            val startButton = itemView.findViewById<MaterialButton>(R.id.startButton)
            startButton.setOnClickListener(this::startFlashcards)

            val optionsButton = itemView.findViewById<MaterialButton>(R.id.optionsButton)
            optionsButton.setOnClickListener(this::showBucketDetails)


        }

        private fun startFlashcards(view: View) {

            val action = PlayMenuFragmentDirections
                    .actionPlayMenuFragmentToFlashcardFragment(bucket.id)
            Navigation.findNavController(view).navigate(action)
        }

        private fun showBucketDetails(view: View) {
            val action = PlayMenuFragmentDirections.actionPlayMenuFragmentToBucketDetailsFragment(bucket.id)
            Navigation.findNavController(view).navigate(action)
        }
    }
}
