package com.list.rados.recyclerviewlibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import androidx.core.widget.toast
import com.list.rados.fast_list.bind
import com.list.rados.fast_list.update
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item_second.view.*

class MainActivity : AppCompatActivity() {

    data class Item(val value: String, val type: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf(Item("first", 2), Item("second", 2), Item("third", 1), Item("fourth", 1), Item("fifth", 1))
        val list2 = listOf(Item("first", 2), Item("third", 1), Item("fifth", 1), Item("sixth", 1))

        recycler_view.bind(list, R.layout.item) { item: Item ->
            item_text.text = item.value
            container.setOnClickListener {
                toast(item.value)
            }
        }

        recycler_view.bind(list)
                .map(layout = R.layout.item, predicate = { it.type == 1 }) { item: Item ->
                    item_text.text = item.value
                    container.setOnClickListener {
                        toast(item.value)
                    }
                }
                .map(layout = R.layout.item_second, predicate = { it.type == 2 }) { item: Item ->
                    item_second_text.text = item.value
                    container_second.setOnClickListener {
                        toast(item.value)
                    }
                }
                .layoutManager(LinearLayoutManager(this))

        delay(2000) {
            recycler_view.update(list2)
        }

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