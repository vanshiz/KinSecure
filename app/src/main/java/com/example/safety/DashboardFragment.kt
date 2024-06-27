package com.example.safety

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.safety.databinding.FragmentDashboardBinding
import com.example.safety.utils.ContactAdapter
import com.example.safety.utils.ContactData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DashboardFragment : Fragment(), AddContactFragment.DialogNextBtnClickListener,
    ContactAdapter.ContactAdaptorClicksInterface {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var databaseRef:DatabaseReference
    private lateinit var binding:FragmentDashboardBinding
    private var popupFragment: AddContactFragment?=null
    private lateinit var adapter:ContactAdapter
    private lateinit var mList:MutableList<ContactData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getDataFromFirebase()
        registerEvents()
    }

    private fun registerEvents(){
        binding.addBtn.setOnClickListener{
            if(popupFragment!=null){
                childFragmentManager.beginTransaction().remove(popupFragment!!).commit()
            }
        popupFragment= AddContactFragment()
            popupFragment!!.setListener(this)
            popupFragment!!.show(
                childFragmentManager,
                AddContactFragment.TAG
            )

        }
    }

    private fun init(view:View){
        databaseRef=FirebaseDatabase.getInstance().reference.child("Contacts")
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager= LinearLayoutManager(context)
        mList=mutableListOf()
        adapter=ContactAdapter(mList)
        adapter.setListener(this)
        binding.recyclerView.adapter=adapter

    }

    private fun getDataFromFirebase(){
        databaseRef.addValueEventListener(object:ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
               mList.clear()
                for(taskSnapshot in snapshot.children){
                    val contactTask=taskSnapshot.key?.let{
                        ContactData(it,taskSnapshot.value.toString())
                    }
                    if(contactTask!=null){
                        mList.add(contactTask)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = DashboardFragment()

    }

    override fun onSaveTask(contacts: String, contactEt: TextInputEditText) {
       databaseRef.push().setValue(contacts).addOnCompleteListener{
           if(it.isSuccessful){
               Toast.makeText(context,"Contact saved successfully!!",Toast.LENGTH_SHORT).show()

           }else{
               Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()
           }
           contactEt.text=null
           popupFragment!!.dismiss()
       }
    }

    override fun onUpdateTask(contactData: ContactData, contactEt: TextInputEditText) {
        val map=HashMap<String,Any>()
        map[contactData.contactId]=contactData.contact
        databaseRef.updateChildren(map).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(context,"Updated Successfully",Toast.LENGTH_SHORT).show()


            }else{
                Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()
            }
            contactEt.text=null
            popupFragment!!.dismiss()
        }

    }

    override fun onDeleteContactBtnClicked(contactData: ContactData) {
        databaseRef.child(contactData.contactId).removeValue().addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onEditContactBtnClicked(contactData: ContactData) {
        if(popupFragment!=null){
            childFragmentManager.beginTransaction().remove(popupFragment!!).commit()
            popupFragment=AddContactFragment.newInstance(contactData.contactId,contactData.contact)
            popupFragment!!.setListener(this)
            popupFragment!!.show(childFragmentManager,AddContactFragment.TAG)
        }
    }
}