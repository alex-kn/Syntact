package com.alexkn.syntact.presentation.bucketlist

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alexkn.syntact.R

abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val background = ColorDrawable()
    private val backgroundColor = context.getColor(R.color.color_background)
    private val icon = context.getDrawable(R.drawable.ic_delete_black_24dp)
    private val inWidth = icon.intrinsicWidth
    private val inHeight = icon.intrinsicHeight

    init {
        icon.setColorFilter(context.getColor(R.color.color_error), PorterDuff.Mode.SRC_IN)
    }


    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Draw the red delete background
        background.color = backgroundColor
        background.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
        )


        background.draw(c)

        // Calculate position of delete icon
        val iconTop = itemView.top + (itemHeight - inHeight) / 2
        val iconMargin = (itemHeight - inHeight) / 2
        val iconLeft = itemView.right - iconMargin - inWidth
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + inHeight

        // Draw the delete icon
        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}