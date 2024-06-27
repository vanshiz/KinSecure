package com.example.safety

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMembers = listOf<MemberModel>(
            MemberModel(
                "DAD",
                "23rd Avenue, 4th floor, Dublin Street, Dublin",
                "85%",
                "250"
            ),
            MemberModel(
                "MOM",
                "15th building, 2nd floor, Lindenstraße, Dublin",
                "75%",
                "230"
            ),
            MemberModel(
                "BRO",
                "10th building, 3rd floor, Shibuya Street, Dublin",
                "90%",
                "240"
            ),
            MemberModel(
                "SIS",
                "10th building, 3rd floor, Shibuya Street, Dublin",
                "70%",
                "240"
            ),


        )

        val adapter = MemberAdapter(listMembers)
        val recycler=requireView().findViewById<RecyclerView>(R.id.recycler_member)
        recycler.layoutManager=LinearLayoutManager(requireContext())
        recycler.adapter=adapter



    }
    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}