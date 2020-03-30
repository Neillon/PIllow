package com.example.intro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.intro.model.IntroItem
import com.example.intro.R

class IntroAdapter() : RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {

    private val items = listOf(
        IntroItem(
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            subTitle = "Donec vehicula justo nec consectetur molestie.",
            image = R.drawable.intro_kong
        ),
        IntroItem(
            title = "Proin vitae nisi dui. Curabitur vitae elementum tellus.",
            subTitle = "Suspendisse eget purus quis augue condimentum mattis.",
            image = R.drawable.intro_guardians
        ),
        IntroItem(
            title = "Nam tempor lorem a massa mollis, ut tristique massa iaculis.",
            subTitle = "Curabitur tortor purus, consectetur luctus accumsan ultrices, accumsan nec enim.",
            image = R.drawable.intro_joker
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        return IntroViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.intro_item_container, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class IntroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle = itemView.findViewById<TextView>(R.id.mTextTitleIntroItem)
        private val subTitle = itemView.findViewById<TextView>(R.id.mTextSubtitleIntroItem)
        private val image = itemView.findViewById<ImageView>(R.id.mImageViewIntroItem)

        fun bind(item: IntroItem) {
            textTitle.text = item.title
            subTitle.text = item.subTitle
            Glide
                .with(itemView)
                .load(item.image)
                .centerCrop()
                .into(image)
        }

    }
}