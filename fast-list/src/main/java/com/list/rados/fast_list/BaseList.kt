package com.list.rados.fast_list

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
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

    /**
     * The function used for mapping types to layouts
     * @param layout - The ID of the XML layout of the given type
     * @param predicate - Function used to sort the items. For example, a Type field inside your items class with different values for different types.
     * @param bind - The "binding" function between the item and the layout. This is the standard "bind" function in traditional ViewHolder classes. It uses Kotlin Extensions
     * so you can just use the XML names of the views inside your layout to address them.
     */
    fun map(@LayoutRes layout: Int, predicate: (item: T) -> Boolean, bind: View.(item: T) -> Unit): RecyclerViewAdapter<T> {
        bindMap.add(BindMap(layout, typeCounter++, bind, predicate))
        list.adapter = this
        return this
    }

    /**
     * Sets up a layout manager for the recycler view.
     */
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

/**
 * Dynamic list bind function. It should be followed by one or multiple .map calls.
 * @param items - Generic list of the items to be displayed in the list
 */
fun <T> RecyclerView.bind(items: List<T>): RecyclerViewAdapter<T> {
    layoutManager = LinearLayoutManager(context)
    return RecyclerViewAdapter(items.toMutableList(), this)
}


/**
 * Simple list bind function.
 * @param items - Generic list of the items to be displayed in the list
 * @param singleLayout - The layout that will be used in the list
 * @param singleBind - The "binding" function between the item and the layout. This is the standard "bind" function in traditional ViewHolder classes. It uses Kotlin Extensions
 * so you can just use the XML names of the views inside your layout to address them.
 */
fun <T> RecyclerView.bind(items: List<T>, @LayoutRes singleLayout: Int = 0, singleBind: (View.(item: T) -> Unit)): RecyclerViewAdapter<T> {
    layoutManager = LinearLayoutManager(context)
    return RecyclerViewAdapter(items.toMutableList(), this
    ).map(singleLayout, { true }, singleBind)
}

