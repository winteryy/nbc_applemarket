package com.nbcteam3.nbcapplemarket

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.nbcteam3.nbcapplemarket.data.DummyRepo
import com.nbcteam3.nbcapplemarket.databinding.ActivityDetailBinding
import com.nbcteam3.nbcapplemarket.model.Post
import com.nbcteam3.nbcapplemarket.util.toPriceText

class DetailActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private var favoriteState = false

    private val post by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MainActivity.ITEM_DATA, Post::class.java)
        }else {
            (intent.getParcelableExtra(MainActivity.ITEM_DATA) as? Post)
        }
    }

    private val backPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            setResult()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.onBackPressedDispatcher.addCallback(backPressedCallBack)

        post?.let {
            binding.bindPost(it)
        }

        binding.backButton.setOnClickListener {
            setResult()
            finish()
        }
    }

    private fun ActivityDetailBinding.bindPost(post: Post) {
        itemImageView.load(post.img)
        favoriteState = post.isFavorite
        changeFavoriteState()
        favoriteButton.setOnClickListener {
            favoriteState = !favoriteState
            changeFavoriteState()
        }
        itemTitleTextView.text = post.title
        itemContentTextView.text = post.content
        sellerNameTextView.text = post.seller
        sellerLocationTextView.text = post.location
        priceTextView.text = post.price.toPriceText()
    }

    private fun changeFavoriteState() {
        binding.favoriteButton.load(
            if(favoriteState) R.drawable.baseline_favorite_26_red else R.drawable.baseline_favorite_border_26_black
        )
    }

    private fun updateFavoriteState(): Boolean {
        if(post==null) return false
        val originPost = post!!
        if(originPost.isFavorite != favoriteState) {
            DummyRepo.updateItem(
                originPost.copy(
                    favoriteNum = if(favoriteState) originPost.favoriteNum+1 else originPost.favoriteNum-1,
                    isFavorite = favoriteState
                )
            )
            return true
        }
        return false
    }

    private fun setResult() {
        val intent = Intent().putExtra(MainActivity.NEED_TO_REFRESH, updateFavoriteState())
        Log.d("DetailActivity", "${intent.getBooleanExtra(MainActivity.NEED_TO_REFRESH, false)}")
        setResult(RESULT_OK, intent)
        finish()
    }
}