package com.example.androiddes.Adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddes.Model.CipherText
import com.example.androiddes.R

class CipherTextAdapter(cipherTextList: ArrayList<CipherText>) : RecyclerView.Adapter<CipherTextAdapter.ViewHolder>(){
    var cipherTextList = ArrayList<CipherText>()

    init {
        this.cipherTextList = cipherTextList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.cipher_title)
        val tvCreateDate: TextView = itemView.findViewById(R.id.cipher_text)
        val tvCipherText: TextView = itemView.findViewById(R.id.created_at)
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
        holder.tvCreateDate.text = cipherTextList[position].create_date
        holder.tvCipherText.text = cipherTextList[position].cipherText
    }
}