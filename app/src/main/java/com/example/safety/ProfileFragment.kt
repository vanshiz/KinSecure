package com.example.safety

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ProfileFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listContacts= listOf<ContactModel>(
            ContactModel("Ron",9876543210),
            ContactModel("Harry",9876543210),
            ContactModel("Hermione",9876543210),
            ContactModel("Dan",9876543210),
            ContactModel("Sam",9876543210),
            ContactModel("Snape",9876543210)

        )

        val inviteAdapter= InviteAdapter(listContacts)

        val inviteRecycler=requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        inviteRecycler.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        inviteRecycler.adapter=inviteAdapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}