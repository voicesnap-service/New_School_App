package com.vsnapnewschool.voicesnapmessenger.Utils

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class DownloadAndSaveImageTask  (context: Context) : AsyncTask<String, Unit, Unit>() {
    private var mContext: WeakReference<Context> = WeakReference(context)
      var filepath:String=""

    override fun doInBackground(vararg params: String?) {
        val url = params[0]
        val requestOptions = RequestOptions().override(100)
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        mContext.get()?.let {
            val bitmap = Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .submit()
                .get()

            try {

                var file = File(it.filesDir, "Images")
                if (!file.exists()) {
                    file.mkdir()
                }

                file = File(file, ".jpg")
                val out = FileOutputStream(file)
                Log.d("out",out.toString())

                Log.d("filepath",file.path.toString())
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
                out.flush()
                out.close()
                Log.i("Seiggailion", "Image saved.")

            } catch (e: Exception) {
                Log.i("Seiggailion", "Failed to save image.")
            }
        }
    }
}