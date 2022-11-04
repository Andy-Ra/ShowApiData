package com.andyra.submission1ivanandyramadhan.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andyra.submission1ivanandyramadhan.Data.Remote.Items
import com.andyra.submission1ivanandyramadhan.View.Detail.DetailUserActivity
import com.andyra.submission1ivanandyramadhan.R
import com.bumptech.glide.Glide

class ListProfileAdapter(private val mList: ArrayList<Items>) : RecyclerView.Adapter<ListProfileAdapter.LisViewHolder>() {
    class LisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvUser: TextView = itemView.findViewById(R.id.tvitemuser)
        var imgPhoto: ImageView = itemView.findViewById(R.id.imgitempp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LisViewHolder {
        val mView: View = LayoutInflater.from(parent.context).inflate(R.layout.itemlistuser, parent, false)
        return LisViewHolder(mView)
    }

    override fun onBindViewHolder(mHolder: LisViewHolder, mposition: Int) {
        val(photo, username) = mList[mposition]
        mHolder.apply {
            Glide.with(itemView.context)
                .load(photo)
                .circleCrop()
                .into(imgPhoto)
            tvUser.text = username
        }

        mHolder.itemView.setOnClickListener{
            val mContext = mHolder.itemView.context
            val sendLogin = mList[mposition].login
            val move = Intent(mContext, DetailUserActivity::class.java)

            move.putExtra(DetailUserActivity.EXTRA_LOGIN, sendLogin)
            mContext.startActivity(move)

        }

    }

    override fun getItemCount(): Int = mList.size

}