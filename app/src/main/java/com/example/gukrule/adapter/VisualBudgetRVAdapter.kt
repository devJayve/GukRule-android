package com.example.gukrule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.R
import com.example.gukrule.data.BudgetData

class VisualBudgetRVAdapter(private val budgetList: ArrayList<BudgetData>)
    : RecyclerView.Adapter<VisualBudgetRVAdapter.VisualBudgetHolder>() {

    private lateinit var itemClickListener : OnItemClickListener

    class VisualBudgetHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisualBudgetHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_budget, parent, false)
        return VisualBudgetHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: VisualBudgetHolder, position: Int) {
        val budgetIcon = holder.itemView.findViewById<ImageView>(R.id.budget_icon)
        val category1 = holder.itemView.findViewById<TextView>(R.id.category_1)
        val content = holder.itemView.findViewById<TextView>(R.id.content)

        //budgetIcon.setImageDrawable()
        category1.text = budgetList[position].program_name
        content.text = budgetList[position].budget_title

        // 리스트 내 budget card 클릭 이벤트
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return budgetList.size
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

}