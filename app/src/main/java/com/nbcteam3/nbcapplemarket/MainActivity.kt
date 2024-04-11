package com.nbcteam3.nbcapplemarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nbcteam3.nbcapplemarket.data.DummyRepo
import com.nbcteam3.nbcapplemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = ItemListAdapter()
        binding.sellingListRV.adapter = adapter
        adapter.submitList(DummyRepo.getItemList())

    }
}