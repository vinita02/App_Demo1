package com.example.App_Test

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_contact.view.*

class ContactsAdapter(val mContext: Context, val mData: MutableList<contact_Entity>,listener: OnItemClickListener):RecyclerView.Adapter<ContactsAdapter.ViewHolder> () {

    var listenerContact: OnItemClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(mContext).inflate(R.layout.row_contact,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var currentContact: contact_Entity = mData[position]
        holder.name.text = mData[position].name
        holder.number.text = mData[position].number.toString()

        holder.bind(currentContact,listenerContact)

        Glide.with(this!!.mContext!!).load(this!!.mData!![position].img).into(holder.image)

       /* holder.idLinear.setOnClickListener{

            mData!!.get(position).id
            val intent = Intent(mContext, SecondActivity::class.java)
            intent.putExtra("id_value", mData!!.get(position).id)
            mContext?.startActivity(intent)

        }*/


    }

    fun addAll(address: List<contact_Entity>) {
        mData.clear()
        mData.addAll(address)
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(contact:contact_Entity)
    }


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
          var name :TextView = itemView.tv_name

        var   number:TextView = itemView.tv_number

        var idLinear = itemView.findViewById<RelativeLayout>(R.id.idLinear)

        var image = itemView.findViewById<ImageView>(R.id.image)

        fun bind(contact: contact_Entity, listener: OnItemClickListener) {
            idLinear.setOnClickListener {
                listener.onItemClick(contact)
            }
        }
    }
}