package com.test.moladininterviewtest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.moladininterviewtest.databinding.ActivityMainBinding
import com.test.moladininterviewtest.presentation.adapter.UserAdapter
import com.test.moladininterviewtest.presentation.adapter.UserLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinder: ActivityMainBinding

    private val viewModel: UserViewModel by viewModels()
    lateinit var rvAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        initUI()

    }

    override fun onResume() {
        super.onResume()
        observeLiveData()
    }

    private fun initUI(){
        rvAdapter = UserAdapter()

        viewBinder.btnRetryError.setOnClickListener {
            rvAdapter.retry()
        }
        with (viewBinder.rvUser) {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = rvAdapter.withLoadStateHeaderAndFooter(
                header = UserLoadStateAdapter { rvAdapter.retry() },
                footer = UserLoadStateAdapter { rvAdapter.retry() }
            )
        }
        rvAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && rvAdapter.itemCount == 0

            viewBinder.rvUser.isVisible = !isListEmpty
            viewBinder.layoutError.isVisible = false
            // Show loading spinner during initial load or refresh.
            viewBinder.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error

            errorState?.let { load->
                viewBinder.errorText.text = load.error.message
                viewBinder.layoutError.isVisible = true
            }

        }
    }

    private fun observeLiveData() {
        lifecycleScope.launch {
            viewModel.getUser().collectLatest {
                rvAdapter.submitData(it)
            }
        }
    }
}