package com.android.basicstructure.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.basicstructure.R
import com.android.basicstructure.databinding.CartItemBinding
import com.android.basicstructure.model.response.CartData

/**
 * Created by JeeteshSurana.
 */
class CartAdapter(
    private var context: Context,
    private var mList: List<CartData>,
    private val mItemClickListener: ItemClickListener
) : RecyclerView.Adapter<CartAdapter.CommonAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonAdapterViewHolder {
        return CommonAdapterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_cart,
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
        holder.binding.imgPlus.setOnClickListener {
            mItemClickListener.itemClick(position)
        }

        setMargin(position, holder)
    }

    private fun setMargin(
        position: Int,
        holder: CommonAdapterViewHolder
    ) {
        if (position == mList.size - 1) {
            holder.binding.mcvMain.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(
                    0,
                    context.resources.getDimension(R.dimen.dp_24).toInt(),
                    0,
                    context.resources.getDimension(R.dimen.dp_24).toInt()
                )
            }
        } else {
            holder.binding.mcvMain.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(
                    0,
                    context.resources.getDimension(R.dimen.dp_24).toInt(),
                    0,
                    0
                )
            }
        }
    }

    class CommonAdapterViewHolder(var binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(list: CartData) =
            binding.apply {
                mData = list
                executePendingBindings()
            }
    }

    interface ItemClickListener {
        fun itemClick(position: Int)
    }
}
