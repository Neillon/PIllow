package com.example.intro.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.intro.adapters.IntroAdapter
import com.example.intro.R
import com.example.intro.extensions.setDrawable
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    private val introSliderAdapter = IntroAdapter()
    private val sharedPreferences by lazy {
        getSharedPreferences(
            getString(R.string.app_preferences),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        verifyPreferences()

        introSliderViewPager.adapter = introSliderAdapter

        setupIndicators()
        setCurrentIndicator(0)

        introSliderViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        mButtonNext.setOnClickListener {
            if (introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount)
                introSliderViewPager.currentItem += 1
            else
                disableIntroPreferenceAndGoMainActivity()
        }

        mButtonSkip.setOnClickListener {
            disableIntroPreferenceAndGoMainActivity()
        }
    }

    private fun disableIntroPreferenceAndGoMainActivity() {
        disableIntroPreference()
        goToMainAcitvity()
    }

    private fun verifyPreferences() {
        val showIntro = sharedPreferences.getBoolean(getString(R.string.preference_show_intro_key), true)
        if (!showIntro) {
            goToMainAcitvity()
        }
    }

    private fun disableIntroPreference() {
        with(sharedPreferences.edit()) {
            putBoolean(getString(R.string.preference_show_intro_key), false)
            commit()
        }
    }

    private fun goToMainAcitvity() {
        Intent(applicationContext, HomeActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(8, 0, 8, 0)
            }

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setDrawable(applicationContext, R.drawable.indicator_inactive)
            indicators[i]?.layoutParams = layoutParams
            mContainerIndicators.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = mContainerIndicators.childCount

        for (i in 0 until childCount) {
            val indicator = mContainerIndicators[i] as ImageView
            if (i == index) {
                indicator.setDrawable(
                    applicationContext,
                    R.drawable.indicator_active
                )
                continue
            }

            indicator.setDrawable(
                applicationContext,
                R.drawable.indicator_inactive
            )
        }
    }

}
