package com.example.safety.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safety.databinding.ContactShowBinding

class ContactAdapter(private val list:MutableList<ContactData>):
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

        private var listener:ContactAdaptorClicksInterface?=null
        fun setListener(listener:ContactAdaptorClicksInterface){
            this.listener=listener
        }
    inner class ContactViewHolder(val binding:ContactShowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
       val binding=ContactShowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.todoTask.text=this.contact
                binding.deleteTask.setOnClickListener{
                    listener?.onDeleteContactBtnClicked(this)
                }
                binding.editTask.setOnClickListener{
                 listener?.onEditContactBtnClicked(this)
                }
            }
        }
    }

    interface ContactAdaptorClicksInterface{
        fun onDeleteContactBtnClicked(contactData: ContactData)
        fun onEditContactBtnClicked(contactData: ContactData)
    }
}