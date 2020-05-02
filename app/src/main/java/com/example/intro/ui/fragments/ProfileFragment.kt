package com.example.intro.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.intro.R
import com.example.intro.databinding.FragmentProfileBinding
import com.example.intro.ui.REQUEST_CODE_CAMERA_RESULT
import com.example.intro.ui.activities.CameraActivity
import com.example.intro.utils.extensions.permissionGranted
import com.example.presentation.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import java.io.File

internal const val REQUEST_CODE_PERMISSIONS = 10
internal const val PERMISSION_CAMERA = Manifest.permission.CAMERA

class ProfileFragment : Fragment(), LifecycleOwner {
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

        setupLayout()

        viewModel.countMovies()
        observeViewModel()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                verifyPermissionAndOpenCamera();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_CAMERA_RESULT -> {
                val photo = data?.getSerializableExtra("photo")
                Glide
                    .with(context!!)
                    .load(photo)
                    .centerCrop()
                    .into(mImageViewViewProfilePicture)
            }
        }
    }

    private fun setupLayout() {
        mImageButtonProfilePicture.setOnClickListener {
            if (activity?.baseContext!!.permissionGranted(PERMISSION_CAMERA))
                startCameraActivity()
            else
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(PERMISSION_CAMERA),
                    REQUEST_CODE_PERMISSIONS
                )
        }
    }

    private fun observeViewModel() {
        viewModel.movieCount.observe(viewLifecycleOwner, Observer {
            binding.favoritesCount = it
        })
    }

    private fun verifyPermissionAndOpenCamera() {
        if (activity?.baseContext!!.permissionGranted(PERMISSION_CAMERA))
            startCameraActivity()
        else
            Toast.makeText(context, "Permissions not granted by the user.", Toast.LENGTH_SHORT)
                .show()
    }

    private fun startCameraActivity() {
        val intent = Intent(activity, CameraActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_CAMERA_RESULT)
    }

}

