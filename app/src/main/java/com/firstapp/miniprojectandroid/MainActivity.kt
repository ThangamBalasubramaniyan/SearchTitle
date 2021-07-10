package com.firstapp.miniprojectandroid

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstapp.miniprojectandroid.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private var title: ArrayList<MyDataItem> = arrayListOf()
    private var matchTitle: ArrayList<MyDataItem> = arrayListOf()
    private var myAdapter: MyAdapter = MyAdapter(title)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.API.getData()
            } catch (e: IOException) {
                Log.e(TAG,"IOException, you might not have internet connection")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG,"HttpException, unexpexted response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                myAdapter.userList = response.body()!!
                title = response.body()!!
                binding.progressBar.isVisible = false
            } else {
                Log.e(TAG, "Response not successful")
            }
            binding.progressBar.isVisible = false
        }

        setupRecyclerView()
        performSearch()

    }

    private fun setupRecyclerView() = binding.rvXml.apply {
        myAdapter = MyAdapter(title).also {
            binding.rvXml.adapter = it
            binding.rvXml.adapter!!.notifyDataSetChanged()
        }
        binding.searchView.isSubmitButtonEnabled = true
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun performSearch() {
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String?) {
        matchTitle = arrayListOf()

        text?.let {
            title.forEach {
                if (it.title.contains(text, true)) {
                    matchTitle.add(it)
                }
            }
            updateRecyclerView()
            if (matchTitle.isEmpty()) {
                Toast.makeText(this, "No match found", Toast.LENGTH_SHORT).show()
            }
            updateRecyclerView()
        }
    }
    private fun updateRecyclerView() {
        binding.rvXml.apply {
            myAdapter.userList = matchTitle
            myAdapter.notifyDataSetChanged()
        }
    }
}