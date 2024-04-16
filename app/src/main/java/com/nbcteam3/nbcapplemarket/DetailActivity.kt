package com.nbcteam3.nbcapplemarket

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.android.material.snackbar.Snackbar
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
        //버전 처리
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MainActivity.ITEM_DATA, Post::class.java)
        }else {
            (intent.getParcelableExtra(MainActivity.ITEM_DATA) as? Post)
        }
    }

    private val backPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            readyToFinish()
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
            readyToFinish()
            finish()
        }
    }

    /**
     * Post 데이터를 DetailActivity의 뷰에 바인딩
     *
     * @param post      MainActivity로부터 받은 Post 데이터
     */
    private fun ActivityDetailBinding.bindPost(post: Post) {
        itemImageView.load(post.img)
        favoriteState = post.isFavorite
        changeFavoriteStateImage()
        favoriteButton.setOnClickListener {
            //좋아요 버튼 클릭 리스너
            favoriteState = !favoriteState
            if(favoriteState) {
                Snackbar.make(it, getString(R.string.set_favorite), Snackbar.LENGTH_SHORT).show()
            }
            changeFavoriteStateImage()
        }
        itemTitleTextView.text = post.title
        itemContentTextView.text = post.content
        sellerNameTextView.text = post.seller
        sellerLocationTextView.text = post.location
        priceTextView.text = post.price.toPriceText()
    }

    /**
     * 좋아요 버튼 눌렸을 때, 좋아요 버튼의 동적인 UI 변화
     * 좋아요 상태일 시 붉은색 하트, 좋아요 상태 아닐 시 빈 하트
     */
    private fun changeFavoriteStateImage() {
        binding.favoriteButton.load(
            if(favoriteState) R.drawable.baseline_favorite_26_red else R.drawable.baseline_favorite_border_26_black
        )

    }

    /**
     * 임시로 저장해 두었던 좋아요 상태를 DummyRepo에 업데이트
     * 기존의 좋아요 상태와 달라졌을 때만 업데이트
     *
     * @return Boolean | 업데이트가 발생했다면 true 리턴, 발생하지 않았다면 false 리턴
     */
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

    /**
     * MainActivity에 업데이트 발생여부 전달 준비
     * UI 상의 back button 클릭 시, 유저가 뒤로 가기 할 시 호출
     */
    private fun readyToFinish() {
        val intent = Intent().putExtra(MainActivity.NEED_TO_REFRESH, updateFavoriteState())
        setResult(RESULT_OK, intent)
    }
}