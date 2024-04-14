package com.nbcteam3.nbcapplemarket

import android.Manifest
import android.animation.ObjectAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.nbcteam3.nbcapplemarket.data.DummyRepo
import com.nbcteam3.nbcapplemarket.databinding.ActivityMainBinding
import com.nbcteam3.nbcapplemarket.model.Post
import com.nbcteam3.nbcapplemarket.util.makeDialog

class MainActivity : AppCompatActivity() {
    private val detailActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == RESULT_OK && it.data?.getBooleanExtra(NEED_TO_REFRESH, false)==true) {
            refreshList()
        }
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ItemListAdapter(object : ItemListAdapter.ItemTouchListener {
            override fun onClick(item: Post) {
                detailActivityLauncher.launch(
                    Intent(this@MainActivity, DetailActivity::class.java).apply {
                        putExtra(ITEM_DATA, item)
                    }
                )
            }

            override fun onLongClick(item: Post) {
                makeDialog(getString(R.string.delete_title), getString(R.string.delete_content)) {
                    if(DummyRepo.deleteItem(item.id)) {
                        refreshList()
                    }
                }
            }
        })
    }

    private val backPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            makeDialog(getString(R.string.exit_title), getString(R.string.exit_content)) {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        createNotificationChannel()
        this.onBackPressedDispatcher.addCallback(backPressedCallBack)

        setRecyclerView()
        setListeners()
    }

    private fun setRecyclerView() {
        binding.sellingListRV.apply {
            adapter = this@MainActivity.adapter
            addItemDecoration(
                DividerItemDecoration(context, LinearLayout.VERTICAL)
            )
            addOnScrollListener(object : OnScrollListener() {
                private val scrollFab = binding.scrollUpFAB
                private val fadeInAnimation = ObjectAnimator.ofFloat(scrollFab, "alpha", 0f, 1f)
                private val fadeOutAnimation = ObjectAnimator.ofFloat(scrollFab, "alpha", 1f, 0f)

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(dy>0 && scrollFab.alpha==0F) {
                        fadeInAnimation.start()
                    }
                }
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(!recyclerView.canScrollVertically(-1) && scrollFab.alpha==1F) {
                        fadeOutAnimation.start()
                    }
                }
            })
        }

        refreshList()
    }

    private fun refreshList() {
        adapter.submitList(DummyRepo.getItemList())
    }

    private fun setListeners() {
        binding.notiButton.setOnClickListener {
            makeNotification()
        }
        binding.scrollUpFAB.setOnClickListener {
            binding.sellingListRV.smoothScrollToPosition(0)
        }
    }

    private fun makeNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.noti_keyword_title))
            .setContentText(getString(R.string.noti_keyword_context))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val NOTIFICATION_ID = 10091
        private const val NOTIFICATION_CHANNEL_ID = "1009"
        private const val NOTIFICATION_CHANNEL_NAME = "apple_market"

        const val NEED_TO_REFRESH = "needToRefresh"
        const val ITEM_DATA = "itemData"
    }
}