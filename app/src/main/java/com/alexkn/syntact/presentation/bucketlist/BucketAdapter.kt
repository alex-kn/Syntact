package com.alexkn.syntact.presentation.bucketlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.alexkn.syntact.R
import com.alexkn.syntact.data.model.views.BucketDetail
import com.alexkn.syntact.databinding.BucketListBucketCardBinding
import com.alexkn.syntact.presentation.common.ListItemAdapter
import com.alexkn.syntact.presentation.common.ListItemViewHolder
import java.time.Instant
import java.time.temporal.ChronoUnit.DAYS

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

        private var bucket: BucketDetail? = null

        override fun bindTo(bucket: BucketDetail) {

            this.bucket = bucket
            val created = bucket.createdAt
            val days = DAYS.between(created, Instant.now()) + 1
            val average = bucket.solvedCount.toDouble() / days
            binding.dailyAverage = average.toString()

            binding.bucket = bucket
            itemView.setOnClickListener(this::startFlashcards)
        }

        private fun startFlashcards(view: View) {

            val action = PlayMenuFragmentDirections
                    .actionPlayMenuFragmentToFlashcardFragment(bucket!!.id)
            Navigation.findNavController(view).navigate(action)
        }
    }
}