package no.wtw.android.architectureutils.example.activity

import android.content.Context
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.wtw.android.architectureutils.adapter.ListItemAdapter
import no.wtw.android.architectureutils.example.R
import no.wtw.android.architectureutils.example.databinding.RecyclerViewActivityBinding
import no.wtw.android.architectureutils.model.Listable
import no.wtw.android.architectureutils.view.ListItemView

class RecyclerViewActivity : AppCompatActivity() {

    private val viewBinding by viewBinding<RecyclerViewActivityBinding>()
    private lateinit var adapter: ListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ListItemAdapter(this)
        viewBinding.apply {
            list.layoutManager = LinearLayoutManager(this@RecyclerViewActivity, RecyclerView.VERTICAL, false)
            list.addItemDecoration(DividerItemDecoration(this@RecyclerViewActivity, DividerItemDecoration.VERTICAL))
            list.adapter = adapter
        }
        adapter.setOnItemClickListener { _: Int, _: ListItemView?, _: Listable? -> Toast.makeText(this, "onItemClick", Toast.LENGTH_SHORT).show() }
        adapter.setonItemExtraClickListener({ _: Int, _: ListItemView?, _: Listable? -> Toast.makeText(this, "onItemExtraClick", Toast.LENGTH_SHORT).show() }, R.drawable.ic_more_vert_black_24dp)
        for (i in 0..9) adapter.add(ExampleItem())
    }

    private inner class ExampleItem : Listable {
        override fun getTitle(context: Context): String {
            return "foo"
        }

        override fun getSubTitle(context: Context): String {
            return "bar"
        }

        override fun getIconResourceId(context: Context): Int {
            return R.drawable.ic_person_black_24dp
        }
    }

}