package com.example.weathertoday.presentation.history

import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weathertoday.databinding.ActivityHistoryBinding
import com.example.weathertoday.presentation.base.BaseActivity

class HistoryActivity : BaseActivity() {
    private lateinit var binder: ActivityHistoryBinding
    val adapter = HistoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initViews() {
        binder = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binder.root)
        val uris = getImages()
        adapter.submitList(uris)
        binder.imagesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binder.imagesRecyclerView.adapter = adapter
    }

    override fun observeViewModels() {
    }

    fun getImages(): ArrayList<Uri> {
        val uris = ArrayList<Uri>()
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )
        val selection = "${MediaStore.Images.Media.SIZE} >= ?"
        val selectionArgs = arrayOf(
            0.toString()
        )
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        applicationContext.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder

        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                // Use an ID column from the projection to get
                // a URI representing the media item itself.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                uris.add(contentUri)
            }
        }
        return uris
    }
}