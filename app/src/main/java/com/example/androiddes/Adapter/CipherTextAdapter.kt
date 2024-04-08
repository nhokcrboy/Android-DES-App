package com.example.androiddes.Adapter

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddes.Model.CipherText
import com.example.androiddes.R
import com.example.androiddes.Screen.HomeScreen

class CipherTextAdapter(cipherTextList: ArrayList<CipherText>, listener: OnItemClickListener) : RecyclerView.Adapter<CipherTextAdapter.ViewHolder>(){
    var cipherTextList: ArrayList<CipherText>
    var listener: OnItemClickListener

    init {
        this.cipherTextList = cipherTextList
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(bundle: Bundle)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.cipher_title)
        val tvCreateDate: TextView = itemView.findViewById(R.id.created_at)
        val tvCipherText: TextView = itemView.findViewById(R.id.cipher_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cipher_text, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cipherTextList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = cipherTextList[position].title
        holder.tvCreateDate.text = cipherTextList[position].createDate
        holder.tvCipherText.text = cipherTextList[position].cipherText

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("title", cipherTextList[position].title)
                putString("cipherText", cipherTextList[position].cipherText)
            }
            listener.onItemClick(bundle)
        }
    }
}