package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentLibraryBooksAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.ImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*


class ParentBooksDue : Fragment() {
    private val booklist = ArrayList<ImageClass>()
    internal lateinit var booksAdapter: ParentLibraryBooksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SelectedFiles()
        recyclerview.layoutManager = GridLayoutManager(context,2) as RecyclerView.LayoutManager?
        booksAdapter = ParentLibraryBooksAdapter(booklist, context)
        recyclerview!!.adapter = booksAdapter
        (activity as BaseActivity?)!!.searchView!!.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)

                return false
            }
        })
    }
    private fun SelectedFiles() {
        var movieModel = ImageClass(R.drawable.leotolstoy,"War 'N' Peace","Leo Tolstoy","","")
        booklist.add(movieModel)

        movieModel = ImageClass(R.drawable.schoolbook,"School Slam Book","Author Slam","","")
        booklist.add(movieModel)

        movieModel = ImageClass( R.drawable.book1,"Name of the Book","John Abraham","","")
        booklist.add(movieModel)

        movieModel = ImageClass( R.drawable.tombook,"Book Name school","Tom Hughes","","")
        booklist.add(movieModel)
        movieModel = ImageClass( R.drawable.book3,"Five Feet Apart","Andrie Jemine","","")
        booklist.add(movieModel)
        movieModel = ImageClass( R.drawable.book1,"Book Name","John Green","","")
        booklist.add(movieModel)

    }
    fun filter(s: String) {

        val assignment: ArrayList<ImageClass> = ArrayList<ImageClass>()
        for (d in booklist) {
            val value: String = d.Day!!.toLowerCase() + d.Month!!.toLowerCase()
            if (value.contains(s.toLowerCase())) {
                assignment.add(d)
//                lblNoRecordsFound.setVisibility(View.GONE)
            } else if (!value.contains(s) && assignment.size == 0) {
//                lblNoRecordsFound.setVisibility(View.VISIBLE)
            }
        }
        if (booklist.size != 0)
            booksAdapter.update(assignment);
    }


}