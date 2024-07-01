package com.example.safety.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.safety.utils.Models.ContactModel
import com.example.safety.utils.Adapters.InviteAdapter
import com.example.safety.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private val TAG = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firestore
        db = Firebase.firestore

        // Enable Firestore offline data persistence
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listContacts = listOf(
            ContactModel("Ron", 9876543210),
            ContactModel("Harry", 9876543210),
            ContactModel("Hermione", 9876543210),
            ContactModel("Dan", 9876543210),
            ContactModel("Sam", 9876543210),
            ContactModel("Snape", 9876543210)
        )

        val inviteAdapter = InviteAdapter(listContacts)

        val inviteRecycler = requireView().findViewById<RecyclerView>(R.id.recycler_invite)
        inviteRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        inviteRecycler.adapter = inviteAdapter

        // Retrieve user profile from Firestore
        getUserProfile(view)
    }

    private fun getUserProfile(view: View) {
        db.collection("users").document("userProfile")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name = document.getString("name")
                    val address = document.getString("address")
                    val time = document.getString("time")
                    val battery = document.getString("battery")
                    val distance = document.getString("distance")
                    val wifi = document.getString("wifi")

                    // Update UI with retrieved data
                    view.findViewById<TextView>(R.id.name)?.text = name
                    view.findViewById<TextView>(R.id.address)?.text = address
                    view.findViewById<TextView>(R.id.time)?.text = time
                    view.findViewById<TextView>(R.id.battery_percent)?.text = battery
                    view.findViewById<TextView>(R.id.distance_value)?.text = distance
                    view.findViewById<TextView>(R.id.wifi_value)?.text = wifi
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}