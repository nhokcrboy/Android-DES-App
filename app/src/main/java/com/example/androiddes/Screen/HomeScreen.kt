package com.example.androiddes.Screen

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.chaquo.python.Python
import com.example.androiddes.Adapter.CipherTextAdapter
import com.example.androiddes.Model.CipherText
import com.example.androiddes.R
import com.example.androiddes.databinding.FragmentHomeScreenBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar


class HomeScreen : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private val python = Python.getInstance()
    private val cipherTextList: ArrayList<CipherText> = ArrayList()
    private lateinit var adapter: CipherTextAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeScreenBinding.bind(view)

        binding.floatButton.setOnClickListener {
            showDialog()
        }

        cipherTextList.clear()

        getCipherText()
        adapter = CipherTextAdapter(cipherTextList, object : CipherTextAdapter.OnItemClickListener {
            override fun onItemClick(bundle: Bundle) {
                Log.d("HomeScreen", bundle.toString())
                findNavController(requireParentFragment()).navigate(R.id.action_homeScreen_to_detailScreen, bundle)
            }
        })
        binding.CipherList.adapter = adapter
        binding.CipherList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
    }

    private fun getCipherText() {
        db.collection("CipherText").get().addOnSuccessListener { result ->
            for (document in result) {
                val cipherText = CipherText(
                    document.data["title"].toString(),
                    document.data["cipherText"].toString(),
                    document.data["create_date"].toString()
                )
                Log.d("CipherText", "${document.id} => ${document.data}")
                cipherTextList.add(cipherText)
            }
            adapter.cipherTextList = cipherTextList
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDateTime(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("MM-dd")
        val formatted = formatter.format(calendar.time)

        return formatted
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.input_dialogue)
        dialog.setTitle("Add New Cipher Text")

        dialog.findViewById<Button>(R.id.save).setOnClickListener {
            val encrypt = python.getModule("des")
            val key = dialog.findViewById<TextView>(R.id.keytext).text.toString()
            val plainText = dialog.findViewById<TextView>(R.id.text_plain).text.toString()

            val encrypted = encrypt.callAttr("encrypt", plainText, key).toString()

            val data: Map<String,Any> = hashMapOf(
                "title" to dialog.findViewById<TextView>(R.id.title).text.toString(),
                "cipherText" to encrypted,
                "key" to key,
                "create_date" to getCurrentDateTime()
            )

            db.collection("CipherText").add(data)
            dialog.dismiss()
        }

        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(window.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = layoutParams
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}