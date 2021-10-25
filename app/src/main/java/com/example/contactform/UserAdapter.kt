package com.example.contactform

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var stdList:ArrayList<UserModel> = ArrayList()
    private var onClickItem:((UserModel) -> Unit)? = null
    private var onClickDeleteItem:((UserModel) -> Unit)? = null

    fun addItems(items:ArrayList<UserModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (UserModel)->Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem(callback:(UserModel)->Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card,parent,false)
    )


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(std)}
    }

    override fun getItemCount(): Int {
       return stdList.size
    }
    class UserViewHolder(var view: View):RecyclerView.ViewHolder(view){
        private var id =view.findViewById<TextView>(R.id.tvid)
        private var name =view.findViewById<TextView>(R.id.tvName)
        private var email =view.findViewById<TextView>(R.id.tvEmail)
        private var phone =view.findViewById<TextView>(R.id.tvPhone)
        var btnDelete =view.findViewById<Button>(R.id.btnDelete)


        fun bindView(std:UserModel){
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email
            phone.text = std.phone
        }
    }
}