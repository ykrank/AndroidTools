package com.github.ykrank.androidtools_demo

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ykrank.androidtools_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.tvVideo.setOnClickListener { startActivity(Intent(this, FileExplorerActivity::class.java)) }
    }
}
