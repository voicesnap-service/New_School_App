@file:Suppress("DEPRECATION")

package com.vsnapnewschool.voicesnapmessenger.Utils

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

object downloadImageclass {
    var mProgressDialog: ProgressDialog? = null
    fun downloadSampleFile(activity: Activity, imageurl: String, folder: String, fileName: String) {
        Log.d("File URL", imageurl)
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog!!.isIndeterminate = true
        mProgressDialog!!.setMessage("Downloading...")
        mProgressDialog!!.setCancelable(false)
        if (!activity.isFinishing) mProgressDialog!!.show()

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)

        val call: Call<ResponseBody?>? = apiInterface.downloadFileWithDynamicUrlAsync(imageurl)
        call!!.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful) {
                    Log.d("DOWNLOADING...", "server contacted and has file")
                    object : AsyncTask<Void?, Void?, Boolean>() {
                        override fun onPostExecute(status: Boolean) {
                            super.onPostExecute(status)
                            if (status) {
                                showAlert(
                                    activity, "Success", "File stored in: $folder/$fileName"
                                )
                            }
                        }

                        override fun doInBackground(vararg params: Void?): Boolean {

                            val writtenToDisk =
                                writeResponseBodyToDisk(response.body(), folder, fileName)
                            Log.d(
                                "DOWNLOADING...", "file download was a success? $writtenToDisk"
                            )
                            return writtenToDisk
                        }
                    }.execute()
                } else {

                    Log.d("DOWNLOADING...", "server contact failed")
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                if (mProgressDialog!!.isShowing) mProgressDialog!!.dismiss()
                Log.e("DOWNLOADING...", "error: $t")
            }
        })
    }

    fun writeResponseBodyToDisk(body: ResponseBody?, folder: String?, fileName: String?): Boolean {
        return try {
            val filepath: String
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                filepath =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .path
            } else {
                filepath = Environment.getExternalStorageDirectory().path
            }

//            val filepath = Environment.getExternalStorageDirectory().path
            val file = File(filepath, folder)
            val dir = File(file.absolutePath)

            if (!dir.exists()) {
                dir.mkdirs()
                println("Dir: $dir")
            }
            val futureStudioIconFile = File(dir, fileName)
            // todo change the file location/name according to your needs
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                inputStream = body!!.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {

                inputStream?.close()
                outputStream?.close()
                if (mProgressDialog!!.isShowing) mProgressDialog!!.dismiss()
            }
        } catch (e: IOException) {
            false
        }
    }

    fun showAlert(activity: Activity?, title: String?, msg: String?) {
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(msg)
        alertDialog.setIcon(R.drawable.gallery_icon)
        alertDialog.setNeutralButton("OK") { dialog, which ->
        }
        alertDialog.show()
    }

}