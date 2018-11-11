package com.tutsplus.recyclerviewselectiontutorial

import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup

class MyLookup(private val rv: RecyclerView)
    : ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent)
            : ItemDetails<Long>? {

        val view = rv.findChildViewUnder(event.x, event.y)
        if(view != null) {
            return (rv.getChildViewHolder(view) as MyViewHolder)
                    .getItemDetails()
        }
        return null

    }
}