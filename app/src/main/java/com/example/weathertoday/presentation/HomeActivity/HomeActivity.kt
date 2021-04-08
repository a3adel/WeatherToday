package com.example.weathertoday.presentation.HomeActivity

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.example.weathertoday.R
import com.example.weathertoday.databinding.ActivityHomeBinding
import com.example.weathertoday.domain.models.WeatherInfo
import com.example.weathertoday.presentation.base.BaseActivity
import com.example.weathertoday.presentation.base.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.example.weathertoday.presentation.base.SingleEvent
import com.example.weathertoday.presentation.history.HistoryActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var menu: Menu? = null
    private var lat: Double? = null
    private var lon: Double? = null
    private lateinit var binder: ActivityHomeBinding
    var photoURI: Uri? = null
    private val viewModel: HomeViewModel by viewModels()
    val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationPermission()


        getCurrentLocation()


    }

    private fun getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getLocationPermission()
            return
        }
        val locationResult = fusedLocationClient.lastLocation
        locationResult.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                viewModel.getGeoWeather(task.result.latitude, task.result.longitude)
                lat = task.result.latitude
                lon = task.result.longitude
            }
        }


    }

    override fun initViews() {
        binder = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binder.root)
        binder.captureButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
        Glide.with(this).load(R.drawable.default_image).fitCenter().into(binder.weatherBgImageView)
    }

    override fun observeViewModels() {
        viewModel.weatherLiveData.observe(this, ::handleWeatherData)
        viewModel.showProgressBarLiveData.observe(this, ::observeShowProgressDialog)
        viewModel.hideProgressBarLiveData.observe(this, ::observeHideProgressDialog)
        viewModel.toastLiveData.observe(this, ::handleToast)


    }
    fun observeShowProgressDialog(singleEvent: SingleEvent<Unit>) {
        singleEvent.getContentIfNotHandled()?.let {
            showProgressDialog(getString(R.string.loading))
        }
    }

    fun observeHideProgressDialog(singleEvent: SingleEvent<Unit>) {
        singleEvent.getContentIfNotHandled()?.let {
            hideProgressDialog()
        }
    }

    private fun handleWeatherData(weatherInfo: WeatherInfo) {
        showToast(weatherInfo.main.temp.toString())
       val alteredBitmap =  drawTempAndWindOnImage(binder.weatherBgImageView.drawable.toBitmap())
        Glide.with(this).load(alteredBitmap).centerInside().into(binder.weatherBgImageView)

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {


            startForResult.launch(takePictureIntent)

        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                val alteredBitmap = drawTempAndWindOnImage(imageBitmap)
                Glide.with(this).load(alteredBitmap).into(binder.weatherBgImageView)
                saveMediaToStorage(alteredBitmap)

                // Handle the Intent
            }
        }

    private fun drawTempAndWindOnImage(imageBitmap: Bitmap): Bitmap {
        val alteredBitmap =
            Bitmap.createBitmap(imageBitmap.width, imageBitmap.height, imageBitmap.config)

        val canvas = Canvas(alteredBitmap)
        val paint = Paint()
        canvas.drawBitmap(imageBitmap, 0f, 0f, paint)
        paint.color = Color.WHITE
        paint.textSize = 10f
        paint.strokeWidth = 1f

        paint.setShadowLayer(1f, 0f, 0f, Color.BLACK)
        canvas.drawText(
            "Temp: ${viewModel.weatherLiveData.value?.main?.temp},\n",
            10f,
            25f,
            paint
        )
        canvas.drawText(
            " Wind speed ${viewModel.weatherLiveData.value?.wind?.speed?.toString()}",
            10f,
            55f,
            paint
        )
        return alteredBitmap
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                    getCurrentLocation()
                } else {
                    showToast(getString(R.string.location_permission_hint))
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return true
    }

    fun getPlaceName(lat: Double, lon: Double): String {
        val gcd = Geocoder(this)
        val addresses = gcd.getFromLocation(lat, lon, 1)
        Log.d("ADD", addresses[0].toString())

        return addresses[0].locality

    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun saveMediaToStorage(bitmap: Bitmap) {
        //Generating a file name
        val filename = "WEATHER_${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let {
                    resolver.openOutputStream(it)
                }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            showToast("Saved to Photos")
        }
        val file = File(
            getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            ), ""
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.history_menuItem) {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}