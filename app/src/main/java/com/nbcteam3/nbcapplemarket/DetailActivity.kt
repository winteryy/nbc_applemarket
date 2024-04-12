package com.nbcteam3.nbcapplemarket

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.nbcteam3.nbcapplemarket.databinding.ActivityDetailBinding
import com.nbcteam3.nbcapplemarket.model.Post
import com.nbcteam3.nbcapplemarket.util.toPriceText

class DetailActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MainActivity.ITEM_DATA, Post::class.java)?.let { post ->
                binding.bindPost(post)
            }
        }else {
            (intent.getParcelableExtra(MainActivity.ITEM_DATA) as? Post)?.let { post ->
                binding.bindPost(post)
            }
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun ActivityDetailBinding.bindPost(post: Post) {
        itemImageView.load(post.img)
        itemTitleTextView.text = post.title
        itemContentTextView.text = post.content
        sellerNameTextView.text = post.seller
        sellerLocationTextView.text = post.location
        priceTextView.text = post.price.toPriceText()
    }
}