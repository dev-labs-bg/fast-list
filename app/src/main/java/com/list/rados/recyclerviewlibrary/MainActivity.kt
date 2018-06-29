package com.list.rados.recyclerviewlibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.list.rados.fast_list.bind
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item_second.view.*

class MainActivity : AppCompatActivity() {

    data class Item(val value: String, val type: Int)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf(Item("fast", 1), Item("recycler", 2), Item("view", 1))

        recycler_view.bind(list, R.layout.item){
            item_text.text = it.value
        }

        recycler_view.bind(list)
                .map(layout = R.layout.item, predicate = { it.type == 1}) {
                    item_text.text = it.value
                }
                .map(layout = R.layout.item_second, predicate = { it.type == 2}) {
                    item_second_text.text = it.value
                }
                .layoutManager(LinearLayoutManager(this))
    }


}
