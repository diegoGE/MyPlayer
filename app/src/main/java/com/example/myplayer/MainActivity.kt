package com.example.myplayer


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.myplayer.MediaItem.Type
import com.example.myplayer.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val adapter = MediaAdapter(){
        startActivity<DetailActivity>(DetailActivity.EXTRA_ID to it.id)
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recycler.adapter = adapter
        updateItems(binding)
    }

    private fun updateItems(binding: ActivityMainBinding,filter: Int = R.id.filter_all){
        lifecycleScope.launch(Dispatchers.Main) {
            binding.progress.visibility = View.VISIBLE
            adapter.items = withContext(Dispatchers.IO){getFilteredItems(filter)}
            binding.progress.visibility = View.GONE
        }
    }

    private fun getFilteredItems(filter: Int): List<MediaItem>{
        return MediaProvider.getItems().let {media ->
            when(filter){
                R.id.filter_photos -> media.filter { it.type == Type.PHOTO }
                R.id.filter_videos -> media.filter { it.type == Type.VIDEO }
                R.id.filter_all -> media
                else -> emptyList()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        updateItems(binding,item.itemId)
        return super.onOptionsItemSelected(item)
    }
}

