package com.example.intro.ui.activities

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.intro.R
import com.example.intro.databinding.ActivityMovieDetailBinding
import com.example.presentation.binding.MovieBinding
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.trends_item_container.view.*

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        val bundle = intent.extras

        bundle?.let {
            val movie = bundle.getSerializable("movie") as MovieBinding
            binding.movie = movie
            mToolbarDetail.title = movie?.title
        }
        binding.lifecycleOwner = this

        setSupportActionBar(mToolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAppBarDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset * -1 > mImageViewCollapseDetail.height / 2)
                mToolbarDetail.navigationIcon?.setColorFilter(ContextCompat.getColor(this@MovieDetailActivity, R.color.black), PorterDuff.Mode.SRC_ATOP);
            else
                mToolbarDetail.navigationIcon?.setColorFilter(ContextCompat.getColor(this@MovieDetailActivity, R.color.white), PorterDuff.Mode.SRC_ATOP)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
