package com.android.basicstructure.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.basicstructure.R
import com.android.basicstructure.databinding.DashboardCategoriesItemBinding
import com.android.basicstructure.model.response.DashboardCategoriesData

/**
 * Created by JeeteshSurana.
 */
class DashboardCategoriesAdapter(
    private var context: Context,
    private var mList: List<DashboardCategoriesData>,
    private val mItemClickListener: ItemClickListener
) : RecyclerView.Adapter<DashboardCategoriesAdapter.CommonAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonAdapterViewHolder {
        return CommonAdapterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_categories,
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
        setMargin(position, holder)
    }

    private fun setMargin(position: Int, holder: CommonAdapterViewHolder) {
        Log.e("TAG","setMargin() position--> $position mList.size --> ${mList.size}")
        if (position == mList.size-1) {
            holder.binding.flCategories.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(context.resources.getDimension(R.dimen.dp_14).toInt(),
                    0,
                    context.resources.getDimension(R.dimen.dp_14).toInt(),
                    0
                )
            }
        } else {
            holder.binding.flCategories.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(
                    context.resources.getDimension(R.dimen.dp_14).toInt(),
                    0,
                    0,
                    0
                )
            }
        }
    }

    class CommonAdapterViewHolder(var binding: DashboardCategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(list: DashboardCategoriesData) =
            binding.apply {
                mData = list
                executePendingBindings()
            }
    }

    interface ItemClickListener {
        fun itemClick(position: Int)
    }
}
