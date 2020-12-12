package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_element.view.*

class BMIEntryAdapter() :
    RecyclerView.Adapter<BMIEntryAdapter.BMIViewHolder>() {

    class BMIViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var dataset: List<BMIEntryModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(dataset: List<BMIEntryModel>) : this(){
        this.dataset = dataset
    }



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