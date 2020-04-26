package com.example.intro.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.example.intro.R
import com.example.intro.databinding.FragmentProfileBinding
import com.example.presentation.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by inject()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(mRoundedImageViewProfilePicture.context)
            .load(R.drawable.intro_joker)
            .centerCrop()
            .error(R.drawable.ic_person_outline)
            .into(mRoundedImageViewProfilePicture)

        viewModel.countMovies()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movieCount.observe(viewLifecycleOwner, Observer {
            binding.favoritesCount = it
        })
    }
}
