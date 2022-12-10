package com.example.netbond

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.netbond.databinding.AcceptUserTemplateBinding
import com.example.netbond.databinding.FragmentUserSearchBarBinding
import com.example.netbond.databinding.SearchUserTemplateBinding
import com.google.firebase.firestore.FirebaseFirestore


class UserSearchBarFragment : Fragment() {

    private lateinit var binding: FragmentUserSearchBarBinding
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var usersRef = db.collection("users")
    private var actualUsername = "johndoe"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserSearchBarBinding.inflate(inflater, container, false)
        setSearchBarListener()
        setReceivedRequests(actualUsername)
        return binding.root
    }

    private fun setSearchBarListener() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.usersList.removeAllViews()
                if (!s.isNullOrEmpty()) {
                    binding.pendingRequests.visibility = View.GONE
                    setSearchUsers(s)
                } else {
                    binding.pendingRequests.visibility = View.VISIBLE
                    setReceivedRequests(actualUsername)
                }
            }
        })
    }

    private fun setSearchUsers(searchedText: CharSequence) {
        usersRef.get().addOnSuccessListener { users ->
            users.forEach { user ->
                val name = user.get("name").toString()
                if (name.contains(searchedText)) {
                    val bind = SearchUserTemplateBinding.inflate(layoutInflater, binding.usersList, true)
                    bind.nameUser.text = name
                    bind.userPoints.text = user.get("n_points").toString()
                    val username = "@" + user.get("username").toString()
                    bind.userName.text = username
                    // bind.user.tag = username
                    bind.root.setOnClickListener { view ->
                        view.setBackgroundColor(resources.getColor(R.color.gray, null))
                        findNavController().navigate(R.id.feedFragment)
                    }
                }
            }
        }
    }

    private fun setReceivedRequests(username: String) {
        usersRef.whereEqualTo("username", username).get().addOnSuccessListener {
            val actualUserDoc = it.single()
            usersRef
                .document(actualUserDoc.id)
                .collection("receivedRequests")
                .get().addOnSuccessListener { requests ->
                    requests.forEach { request ->
                        usersRef.whereEqualTo("username", request.id).get().addOnSuccessListener {
                            val userDoc = it.single()
                            val bind = AcceptUserTemplateBinding.inflate(layoutInflater, binding.usersList, true)
                            bind.nameUser.text = userDoc.get("name").toString()
                            val username = "@" + userDoc.get("username").toString()
                            bind.userName.text = username
                            bind.userPoints.text = userDoc.get("n_points").toString()
                        }
                    }
                }
        }
    }

}