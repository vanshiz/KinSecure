package com.example.safety

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.safety.databinding.FragmentAddContactBinding
import com.example.safety.utils.ContactData
import com.google.android.material.textfield.TextInputEditText

class AddContactFragment : DialogFragment() {


    private lateinit var binding: FragmentAddContactBinding
    private lateinit var listener: DialogNextBtnClickListener
    private var contactData: ContactData? = null

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddContactFragment"

        @JvmStatic
        fun newInstance(contactId: String, contacts: String) = AddContactFragment().apply {
            arguments = Bundle().apply {
                putString("contactId", contactId)
                putString("contact", contacts)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            contactData = ContactData(
                arguments?.getString("contactId").toString(),
                arguments?.getString("contact").toString()
            )
            binding.contactEt.setText(contactData?.contact)
        }
        registerEvents()
    }

    private fun registerEvents() {
        binding.ContactNextBtn.setOnClickListener {
            val contactTask = binding.contactEt.text.toString()
            if (contactTask.isNotEmpty()) {
                if(contactData==null){
                    listener.onSaveTask(contactTask,binding.contactEt)
                }else{
                    contactData?.contact=contactTask
                    listener.onUpdateTask(contactData!!,binding.contactEt)
                }

            } else {
                Toast.makeText(context, "Please enter the number", Toast.LENGTH_SHORT).show()
            }

        }

        binding.ContactClose.setOnClickListener {
            dismiss()
        }
    }


    interface DialogNextBtnClickListener {
        fun onSaveTask(contacts: String, contactEt: TextInputEditText)
        fun onUpdateTask(contactData:ContactData, contactEt: TextInputEditText)


    }

}