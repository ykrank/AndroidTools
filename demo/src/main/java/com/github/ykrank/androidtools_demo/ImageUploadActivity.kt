package com.github.ykrank.androidtools_demo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.ykrank.androidtools.ui.LibBaseActivity
import com.github.ykrank.androidtools.widget.uploadimg.LibImageUploadFragment
import com.github.ykrank.androidtools_demo.databinding.LayoutFramelayoutBinding

class ImageUploadActivity : LibBaseActivity() {
    private lateinit var binding: LayoutFramelayoutBinding

    private lateinit var fragment: ImageUploadFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.layout_framelayout)

        if (savedInstanceState == null) {
            fragment = ImageUploadFragment()
            supportFragmentManager.beginTransaction().add(binding.container.id, fragment, LibImageUploadFragment.TAG).commit()
        } else {
            fragment = supportFragmentManager.findFragmentByTag(LibImageUploadFragment.TAG) as ImageUploadFragment
        }
    }
}