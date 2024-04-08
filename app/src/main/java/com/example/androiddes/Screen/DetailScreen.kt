package com.example.androiddes.Screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.chaquo.python.Python
import com.example.androiddes.Model.CipherText
import com.example.androiddes.R
import com.example.androiddes.databinding.FragmentDetailScreenBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Suppress("DEPRECATION")
class DetailScreen : Fragment() {
    private var _binding: FragmentDetailScreenBinding? = null
    private val binding get() = _binding!!
    var title: String = ""
    var cipherText: String = ""
    val db = FirebaseFirestore.getInstance()
    lateinit var key: String
    val python = Python.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailScreenBinding.bind(view)

        title = arguments?.getString("title").toString()
        cipherText = arguments?.getString("cipherText").toString()

        lifecycleScope.launchWhenCreated {
            key = getKey(cipherText)
        }

        binding.checkButton.setOnClickListener{
            Log.d("DetailScreen", binding.keyInput.text.toString())
            Log.d("DetailScreen", key)
            if (binding.keyInput.text.toString() == key) {
                val decrypt = python.getModule("des")
                val result = decrypt.callAttr("decrypt", cipherText, key)
                binding.result.text = result.toString()
            } else {
                binding.result.text = "Key is incorrect"
            }
        }

        binding.detailTitle.text = title
        binding.cipherText.text = cipherText
    }

    private suspend fun getKey(cipher: String): String {
        var key = ""
        if (cipher != "") {
            val snapshot = db.collection("CipherText")
                .whereEqualTo("cipherText", cipher)
                .get()
                .await()

            if (snapshot.documents.isNotEmpty()) {
                key = snapshot.documents[0].data?.get("key").toString()
            }
        }
        return key
    }

    companion object {

    }
}