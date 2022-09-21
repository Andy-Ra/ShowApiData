package com.andyra.submission1ivanandyramadhan.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andyra.submission1ivanandyramadhan.Data.ProfileData
import com.andyra.submission1ivanandyramadhan.DetailUser
import com.andyra.submission1ivanandyramadhan.R
import com.bumptech.glide.Glide

class ListProfileAdapter(val mlist: ArrayList<ProfileData>) : RecyclerView.Adapter<ListProfileAdapter.LisViewHolder>() {
    class LisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvname: TextView = itemView.findViewById(R.id.tvitemname)
        var tvuser: TextView = itemView.findViewById(R.id.tvitemuser)
        var tvcomp: TextView = itemView.findViewById(R.id.tvitemcompany)
        var tvlocation: TextView = itemView.findViewById(R.id.tvitemlocation)
        var imgphoto: ImageView = itemView.findViewById(R.id.imgitempp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LisViewHolder {
        val mview: View = LayoutInflater.from(parent.context).inflate(R.layout.itemlistuser, parent, false)
        return LisViewHolder(mview)
    }

    override fun onBindViewHolder(mholder: LisViewHolder, mposition: Int) {
        val(_, photo, name, username, location, company) = mlist[mposition]
        mholder.apply {
            Glide.with(itemView.context)
                .load(photo)
                .circleCrop()
                .into(imgphoto)
            tvname.text = name
            tvuser.text = "@"+username
            tvcomp.text = " "+company
            tvlocation.text = " "+location
        }

        mholder.itemView.setOnClickListener(){
            val mcontext = mholder.itemView.context
            val sendprofile = ProfileData(
                mlist[mposition].id,
                mlist[mposition].avatarUrl,
                mlist[mposition].name,
                mlist[mposition].login,
                mlist[mposition].location,
                mlist[mposition].company,
                mlist[mposition].following,
                mlist[mposition].followers,
                mlist[mposition].publicRepos
            )
            val pindah = Intent(mcontext, DetailUser::class.java)
            pindah.putExtra(DetailUser.EXTRA_LOGIN, sendprofile)
            mcontext.startActivity(pindah)

        }

    }

    override fun getItemCount(): Int = mlist.size

}