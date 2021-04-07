package com.example.weathertoday.presentation.base

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

abstract class BaseActivity : AppCompatActivity() {
    private var progressDialog: SweetAlertDialog? = null
    protected var locationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observeViewModels()
    }

    abstract fun initViews()
    abstract fun observeViewModels()

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun handleToast(singleEvent: SingleEvent<String>) {
        singleEvent.getContentIfNotHandled()
            ?.let { showToast(it) }
    }

    fun observeShowProgressDialog(singleEvent: SingleEvent<Unit>) {
        singleEvent.getContentIfNotHandled()?.let {
            showProgressDialog("Loading")
        }
    }

    fun observeHideProgressDialog(singleEvent: SingleEvent<Unit>) {
        singleEvent.getContentIfNotHandled()?.let {
            hideProgressDialog()
        }
    }

    fun showProgressDialog(title: String, cancelable: Boolean = false) {
        progressDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog?.apply {
            progressHelper?.barColor = Color.GREEN
            titleText = title
            setCancelable(cancelable)
            show()
        }

    }

    fun hideProgressDialog() {
        progressDialog?.hide()
    }

    protected fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }
}