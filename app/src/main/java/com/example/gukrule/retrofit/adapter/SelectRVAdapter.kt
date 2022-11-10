package com.example.gukrule.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.R
import com.example.gukrule.retrofit.adapter.CarouselRVAdapter
import java.util.logging.Handler

class SelectRVAdapter(private val reportDataList: ArrayList<String>, private val keywordDataList: ArrayList<String>)
    : RecyclerView.Adapter<SelectRVAdapter.SelectViewHolder>() {

    var sendDataList = ArrayList<String>(keywordDataList.count())

    class SelectViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectRVAdapter.SelectViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_select_article, parent, false)
        return SelectRVAdapter.SelectViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: SelectRVAdapter.SelectViewHolder, position: Int) {
        val reportItem = holder.itemView.findViewById<Button>(R.id.select_article_button)
        val submitBtn = holder.itemView.findViewById<Button>(R.id.sign_up_submit_btn)
        reportItem.text = reportDataList[position]
        reportItem.setOnClickListener {
            if(sendDataList.count() > 4) {
                if (reportItem.isSelected) {
                    sendDataList.remove(keywordDataList[position])
                    reportItem.isSelected = !(reportItem.isSelected)
//                    submitBtn.visibility = VISIBLE
                }
            } else {
//                if(submitBtn.visibility == VISIBLE){
//                    submitBtn.visibility = INVISIBLE
//                }
                if (!reportItem.isSelected) {
                    sendDataList.add(keywordDataList[position])
                } else {
                    sendDataList.remove(keywordDataList[position])
                }
                reportItem.isSelected = !(reportItem.isSelected)
                Log.d("TAG", "sendDataList size  : ${sendDataList.count()}, $sendDataList")
                Log.d("TAG", "keywordList size  : ${keywordDataList.count()}")
            }

            Log.d("LOG", "isSelected : ${reportItem.isSelected}");

        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }


    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener


    override fun getItemCount(): Int {
        return reportDataList.size
    }

}