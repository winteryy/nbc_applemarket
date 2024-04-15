package com.nbcteam3.nbcapplemarket

import android.Manifest
import android.animation.ObjectAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.google.android.material.snackbar.Snackbar
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

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            makeNotification()
        }else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                makeDialog(getString(R.string.request_permission_title),
                    getString(R.string.request_permission_msg)) {
                    requestNotificationPermission()
                }
            }else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.noti_failure_no_permission),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
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

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(dy>0 && scrollFab.alpha==0F) {
                        ObjectAnimator.ofFloat(scrollFab, "alpha", 0f, 1f).start()
                    }
                }
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(!recyclerView.canScrollVertically(-1) && scrollFab.alpha==1F) {
                        ObjectAnimator.ofFloat(scrollFab, "alpha", 1f, 0f).start()
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

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_NOTIFICATION_PERMISSION_CODE)
        }
    }

    private fun makeNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(getString(R.string.noti_keyword_title))
                    .setContentText(getString(R.string.noti_keyword_context))
                    .setSmallIcon(R.drawable.apple)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notificationBuilder.build())
            } else {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getString(R.string.noti_keyword_title))
                .setContentText(getString(R.string.noti_keyword_context))
                .setSmallIcon(R.drawable.apple)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notificationBuilder.build())
        }
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
        private const val REQUEST_NOTIFICATION_PERMISSION_CODE = 1

        const val NEED_TO_REFRESH = "needToRefresh"
        const val ITEM_DATA = "itemData"
    }
}