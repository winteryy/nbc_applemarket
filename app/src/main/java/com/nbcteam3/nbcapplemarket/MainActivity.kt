package com.nbcteam3.nbcapplemarket

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.nbcteam3.nbcapplemarket.data.DummyRepo
import com.nbcteam3.nbcapplemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ItemListAdapter()
    }
    private val backPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            makeExitDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        createNotificationChannel()
        this.onBackPressedDispatcher.addCallback(backPressedCallBack)

        binding.sellingListRV.apply {
            adapter = this@MainActivity.adapter
            addItemDecoration(
                DividerItemDecoration(context, LinearLayout.VERTICAL)
            )
        }
        binding.notiButton.setOnClickListener {
            makeNotification()
        }
        adapter.submitList(DummyRepo.getItemList())


    }

    private fun makeExitDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.exit_title))
            .setMessage(getString(R.string.exit_content))
            .setIcon(R.drawable.outline_chat_20)
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                finish()
            }
        builder.create().show()
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
        const val NOTIFICATION_ID = 10091
        const val NOTIFICATION_CHANNEL_ID = "1009"
        const val NOTIFICATION_CHANNEL_NAME = "apple_market"
    }
}