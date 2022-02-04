package com.android.basicstructure.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.basicstructure.R
import com.android.basicstructure.databinding.DashboardItemBinding
import com.android.basicstructure.model.response.DashboardData

/**
 * Created by JeeteshSurana.
 */
class DashboardAdapter(
    private var mList: List<DashboardData>,
    private val mItemClickListener: ItemClickListener
) : RecyclerView.Adapter<DashboardAdapter.CommonAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonAdapterViewHolder {
        return CommonAdapterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_dashboard,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: CommonAdapterViewHolder, position: Int) {
        val banner = mList[position]
        holder.bindData(banner)
        holder.itemView.setOnClickListener {
            mItemClickListener.itemClick(position)
        }
    }

    class CommonAdapterViewHolder(var binding: DashboardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(list: DashboardData) =
            binding.apply {
                mData = list
                executePendingBindings()
            }
    }

    interface ItemClickListener {
        fun itemClick(position: Int)
    }
}
