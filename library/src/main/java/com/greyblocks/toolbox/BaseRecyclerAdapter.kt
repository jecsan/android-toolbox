package com.greyblocks.toolbox
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T : Any, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {

    private var items: ArrayList<T> = ArrayList()
    var onClick: ((item:T)->Unit)? = null
    var onLongClick: ((item:T)->Unit)? = null

    constructor(items: List<T> = arrayListOf()) {
        this.items = items as ArrayList<T>
    }

    protected fun onItemClicked(item:T){
        onClick?.invoke(item)
    }

    constructor() {
        items = ArrayList()
    }

    fun getItems(): List<T> {
        return items
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    open fun setItems(newItems: List<T>) {
        this.items = newItems as ArrayList<T>
        notifyDataSetChanged()
    }

    open fun updateItems(newItems: List<T>) {

        notifyDataSetChanged()
    }

    open fun updateItem(item: T) {
        val index = items.indexOf(item)
        if (index > -1) {
            items[index] = item
            notifyItemChanged(index)
        }
    }

    open fun setItem(index: Int, item: T) {
        items[index] = item

    }

    open fun add(index:Int, item: T) {
        items.add(index, item)
        notifyDataSetChanged()
    }

    open fun add(item: T) {
        items.add(item)
        notifyDataSetChanged()
    }

    open fun addAll(newItems: List<T>) {
        val originalIndex = itemCount
        items.addAll(newItems)

        notifyDataSetChanged()
    }

    open fun addOrUpdateAll(newItems: List<T>) {
        items.clear()
        notifyDataSetChanged()
        val originalIndex = itemCount
        newItems.forEach {
            if (!items.contains(it)) {
                items.add(it)
            }
        }
        notifyItemRangeInserted(originalIndex, itemCount)
    }


    open fun removeItem(item: T) {
        val index = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, itemCount)
    }

    open fun removeItem(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index+1, itemCount)
    }

    open fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    protected open fun bindItem(item: T, v: VH) {

    }

    protected fun inflateViewHolder(layout: Int, viewGroup: ViewGroup): View {
        return LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
    }


}
