package com.list.rados.fast_list

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by Radoslav Yankov on 29.06.2018
 * radoslavyankov@gmail.com
 */

open class RecyclerViewAdapter<T>(private var items: MutableList<T>, private var list: RecyclerView
) : RecyclerView.Adapter<BaseHolder<T>>() {

    private inner class BindMap(val layout: Int, var type: Int = 0, val bind: View.(item: T) -> Unit, val predicate: (item: T) -> Boolean)

    private var bindMap = mutableListOf<BindMap>()
    private var typeCounter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        return bindMap.first { it.type == viewType }.let {
            BaseHolder(LayoutInflater.from(parent.context).inflate(it.layout,
                    parent, false), viewType)
        }
    }

    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        val item = items.get(position)
        holder.bind(item, bindMap.first { it.type == holder.holderType }.bind)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = try {
        bindMap.first { it.predicate(items[position]) }.type
    } catch (e: Exception) {
        0
    }

    fun map(@LayoutRes layout: Int, predicate: (item: T) -> Boolean, bind: View.(item: T) -> Unit): RecyclerViewAdapter<T> {
        bindMap.add(BindMap(layout, typeCounter++, bind, predicate))
        list.adapter = this
        return this
    }

    fun layoutManager(manager: RecyclerView.LayoutManager): RecyclerViewAdapter<T> {
        list.layoutManager = manager
        return this
    }

}

class BaseHolder<T>(override val containerView: View, val holderType: Int) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(entry: T, func: View.(item: T) -> Unit) {
        containerView.apply {
            func(entry)

        }
    }
}

fun <T> RecyclerView.bind(items: List<T>): RecyclerViewAdapter<T> {
    return RecyclerViewAdapter(items.toMutableList(), this)
}

fun <T> RecyclerView.bind(items: List<T>, @LayoutRes singleLayout: Int = 0, singleBind: (View.(item: T) -> Unit)): RecyclerViewAdapter<T> {
    return RecyclerViewAdapter(items.toMutableList(), this
    ).map(singleLayout, { true }, singleBind)
}

