package com.tutsplus.recyclerviewselectiontutorial

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import androidx.recyclerview.selection.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var tracker: SelectionTracker<Long>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myList = listOf(
                Person("Alice", "555-0111"),
                Person("Bob", "555-0119"),
                Person("Carol", "555-0141"),
                Person("Dan", "555-0155"),
                Person("Eric", "555-0180"),
                Person("Craig", "555-0145")
        )

        my_rv.layoutManager = LinearLayoutManager(this)
        my_rv.setHasFixedSize(true)

        val adapter = MyAdapter(myList, this)
        my_rv.adapter = adapter

        tracker = SelectionTracker.Builder<Long>(
                "selection-1",
                my_rv,
                StableIdKeyProvider(my_rv),
                MyLookup(my_rv),
                StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything())
         .build()

        if(savedInstanceState != null)
            tracker?.onRestoreInstanceState(savedInstanceState)

        adapter.setTracker(tracker!!)

        tracker?.addObserver(object: SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                val nItems:Int? = tracker?.selection?.size()

                if(nItems!=null && nItems > 0) {
                    title = "$nItems items selected"
                    supportActionBar?.setBackgroundDrawable(
                            ColorDrawable(Color.parseColor("#ef6c00")))
                } else {
                    title = "RVSelection"
                    supportActionBar?.setBackgroundDrawable(
                            ColorDrawable(getColor(R.color.colorPrimary)))
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if(outState != null)
            tracker?.onSaveInstanceState(outState)
    }
}
