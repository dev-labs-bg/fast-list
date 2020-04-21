package com.list.rados.recyclerviewlibrary

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.list.rados.fast_list.LayoutFactory
import com.list.rados.fast_list.bind
import com.list.rados.fast_list.update
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item_custom.view.*
import kotlinx.android.synthetic.main.item_second.view.*

class MainActivity : AppCompatActivity() {

    data class Item(val value: String, val type: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf(Item("first", 2), Item("second", 2), Item("third", 1), Item("fourth", 1), Item("fifth", 1))
        val list2 = listOf(Item("first", 2), Item("third", 1), Item("fifth", 1), Item("sixth", 3))

        recycler_view.bind(list, R.layout.item) { item: Item, position: Int ->
            item_text.text = item.value
            container.setOnClickListener {
                toast(item.value)
            }
        }

        recycler_view.bind(list)
                .map(layout = R.layout.item, predicate = {it:Item, _ -> it.type == 1 }) { item: Item, position: Int ->
                    item_text.text = item.value
                    container.setOnClickListener {
                        toast(item.value)
                    }
                }
                .map(layout = R.layout.item_second, predicate = {it:Item, _ ->  it.type == 2 }) { item: Item, position: Int ->
                    item_second_text.text = item.value
                    container_second.setOnClickListener {
                        toast(item.value)
                    }
                }
                .map(layoutFactory = LocalFactory(this), predicate = {it:Item, _ ->  it.type == 3 }) { item: Item, position: Int ->
                    item_custom_text.text = item.value
                    container_custom.setOnClickListener {
                        toast(item.value)
                    }
                }
                .layoutManager(LinearLayoutManager(this))

        delay(2000) {
            recycler_view.update(list2)
        }

    }

    private fun toast(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }
}

class LocalFactory(val activity: AppCompatActivity) : LayoutFactory {
    override fun createView(parent: ViewGroup, type: Int): View {
        return LayoutInflater.from(activity).inflate(R.layout.item_custom,
                parent, false)
    }
}

fun delay(delay: Long, func: () -> Unit) {
    val handler = Handler()
    handler.postDelayed({
        try {
            func()
        } catch (e: Exception) {
            println(e.toString())
        }
    }, delay)
}