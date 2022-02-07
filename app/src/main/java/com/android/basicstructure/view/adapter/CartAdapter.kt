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
            holder.binding.root.requestFocus()
            holder.binding.edtItemQuantity.isCursorVisible = false
            if (holder.binding.edtItemQuantity.text.toString().isNotEmpty()) {
                var currentQuantity = holder.binding.edtItemQuantity.text.toString().toInt()
                if (currentQuantity < 9999) {
                    currentQuantity += 1
                    mList[position].itemQuantity = currentQuantity
                    mList[position].totalAmount = mList[position].itemAmount * currentQuantity
                    holder.binding.edtItemQuantity.setText(currentQuantity.toString())
                    mItemClickListener.itemClick(position)
                } else {
                    mItemClickListener.showMessage(context.resources.getString(R.string.warning_max_amount))
                }
            } else {
                holder.binding.edtItemQuantity.setText("1")
            }
        }

        holder.binding.imgMinus.setOnClickListener {
            mItemClickListener.itemClick(position)
            holder.binding.root.requestFocus()
            holder.binding.edtItemQuantity.isCursorVisible = false
            if (holder.binding.edtItemQuantity.text.toString().isNotEmpty()) {
                var currentQuantity = holder.binding.edtItemQuantity.text.toString().toInt()
                if (currentQuantity > 1) {
                    currentQuantity -= 1
                    mList[position].itemQuantity = currentQuantity
                    mList[position].totalAmount = mList[position].itemAmount * currentQuantity
                    holder.binding.edtItemQuantity.setText(currentQuantity.toString())
                    mItemClickListener.itemClick(position)
                } else {
                    mItemClickListener.showMessage(context.resources.getString(R.string.warning_less_amount))
                }
            } else {
                holder.binding.edtItemQuantity.setText("1")
            }
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
        fun showMessage(message: String)
    }
}
