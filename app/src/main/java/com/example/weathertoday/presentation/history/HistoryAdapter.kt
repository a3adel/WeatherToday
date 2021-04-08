package com.example.weathertoday.presentation.history

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathertoday.R
import com.example.weathertoday.databinding.ItemImageBinding
import java.io.IOException

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ImageViewHolder>() {
    private val uris = ArrayList<Uri>()
    lateinit var context: Context

    inner class ImageViewHolder(private val binder: ItemImageBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(uri: Uri) {
            try {


                Glide.with(context).load(uri).centerCrop().into(binder.photoImageView).onLoadFailed(
                    ContextCompat.getDrawable(
                        context, R.drawable.default_image
                    )
                )


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        context = parent.context
        val binder = ItemImageBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImageViewHolder(binder)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(uris: List<Uri>) {
        Log.d("VIEWMODELMOVIES", "ADAPTER ${uris.size}")

        differ.submitList(uris)
    }
}