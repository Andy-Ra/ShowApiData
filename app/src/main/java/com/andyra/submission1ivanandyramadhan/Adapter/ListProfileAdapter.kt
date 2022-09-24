package com.andyra.submission1ivanandyramadhan.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andyra.submission1ivanandyramadhan.Data.Items
import com.andyra.submission1ivanandyramadhan.DetailUser
import com.andyra.submission1ivanandyramadhan.R
import com.bumptech.glide.Glide

class ListProfileAdapter(val mlist: ArrayList<Items>) : RecyclerView.Adapter<ListProfileAdapter.LisViewHolder>() {
    class LisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvuser: TextView = itemView.findViewById(R.id.tvitemuser)
        var imgphoto: ImageView = itemView.findViewById(R.id.imgitempp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LisViewHolder {
        val mview: View = LayoutInflater.from(parent.context).inflate(R.layout.itemlistuser, parent, false)
        return LisViewHolder(mview)
    }

    override fun onBindViewHolder(mholder: LisViewHolder, mposition: Int) {
        val(photo, username) = mlist[mposition]
        mholder.apply {
            Glide.with(itemView.context)
                .load(photo)
                .circleCrop()
                .into(imgphoto)
            tvuser.text = username
        }

        mholder.itemView.setOnClickListener(){
            val mcontext = mholder.itemView.context
            val sendlogin = mlist[mposition].login
            val pindah = Intent(mcontext, DetailUser::class.java)

            pindah.putExtra(DetailUser.EXTRA_LOGIN, sendlogin)
            mcontext.startActivity(pindah)

        }

    }

    override fun getItemCount(): Int = mlist.size

}