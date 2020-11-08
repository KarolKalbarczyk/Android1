package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_element.view.*

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val list = EntryCache.getEntries(getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE))
        viewAdapter = BMIEntryAdapter(list)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}

class BMIEntryAdapter(private val dataset: List<BMIEntry>) :
    RecyclerView.Adapter<BMIEntryAdapter.BMIViewHolder>() {

    class BMIViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BMIViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_element, parent, false)
        return BMIViewHolder(textView)
    }

    override fun onBindViewHolder(holder: BMIViewHolder, position: Int) {
        holder.view.Text.text = dataset[position].toString()
    }

    override fun getItemCount() = dataset.size
}