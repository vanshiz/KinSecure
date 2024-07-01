package com.example.safety

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.safety.databinding.FragmentProfileBinding
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.sendInvite.setOnClickListener {
            sendInvite()
        }
        return binding.root
    }

    private fun sendInvite() {
        val mail = binding.inviteMail.text.toString()
        Log.d("Mail89", "sendInvite: $mail")

        val data = hashMapOf(
            "invite_status" to 0,
            "email" to mail
        )

        val senderMail = "bhargava.vanshika29@gmail.com"

        firestore.collection("users")
            .document(senderMail)
            .collection("invites")
            .document(mail).set(data)
            .addOnSuccessListener {
                Log.d("Firestore", "Invite successfully sent")
                binding.inviteMail.text.clear()  // Clear the input field
                getInvites() // Refresh invites list
            }.addOnFailureListener {
                Log.e("Firestore", "Error sending invite", it)
            }
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

        val inviteRecycler = binding.recyclerInvite
        inviteRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        inviteRecycler.adapter = inviteAdapter

        getInvites()
    }

    private fun getInvites() {
        val senderMail = "bhargava.vanshika29@gmail.com" // Change this to the actual sender email or user ID

        firestore.collection("users")
            .document(senderMail)
            .collection("invites").get()
            .addOnSuccessListener { querySnapshot ->
                val list: ArrayList<String> = ArrayList()
                for (document in querySnapshot.documents) {
                    if (document.getLong("invite_status") == 0L) {
                        val email = document.getString("email") ?: ""
                        list.add(email)
                    }
                }

                Log.d("invite89", "Fetched invites: $list")

                val adapter = InviteMailAdapter(list, this)
                binding.invitationRecycler.layoutManager = LinearLayoutManager(requireContext())
                binding.invitationRecycler.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting invites", exception)
            }
    }



    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    fun onDenyClick(mail: String) {
        Log.d("invite89", "onDenyClick: $mail")

        val data = hashMapOf(
            "invite_status" to -1
        )

        val senderMail = "bhargava.vanshika29@gmail.com"

        firestore.collection("users")
            .document(senderMail)
            .collection("invites")
            .document(mail).set(data)
            .addOnSuccessListener {
                getInvites() // Refresh invites list after updating the status
            }.addOnFailureListener {
                Log.e("Firestore", "Error updating invite", it)
            }
    }
}
