@file:Suppress("DEPRECATION")

package com.vsnapnewschool.voicesnapmessenger.Utils

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.Interfaces.Refreshlistener

import com.vsnapnewschool.voicesnapmessenger.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.sql.DriverManager.println

object DownloadVoice {
    var mProgressDialog: ProgressDialog? = null
    fun downloadSampleFile(
        activity: Context?,
        urldata: String,
        folder: String?,
        fileName: String,
        refreshlistener: Refreshlistener
    ) {
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog!!.isIndeterminate = true
        mProgressDialog!!.setMessage("Downloading...")
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.show()
        Log.d("File URL", urldata)

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)

        val call: Call<ResponseBody?>? = apiInterface.downloadFileWithDynamicUrlAsync(urldata)
        call!!.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                mProgressDialog!!.dismiss()
                if (response.isSuccessful) {
                    Log.d("DOWNLOADING...", "server contacted and has file")

                    object : AsyncTask<Void?, Void?, Boolean>() {
                        protected fun doInBackground(vararg voids: Void): Boolean {
                            val writtenToDisk = writeResponseBodyToDisk(activity!!,response.body(), folder, fileName)
                            Log.d("DOWNLOADING...", "file download was a success? $writtenToDisk")
                            return writtenToDisk
                        }

                        override fun onPostExecute(status: Boolean) {
                            super.onPostExecute(status)
                            if (status) {
                                refreshlistener.refresh()

                                //AlertDownload(activity, "Success", "File stored in: $folder/$fileName", refreshlistener)
                            }
                        }

                        override fun doInBackground(vararg params: Void?): Boolean? {
                            val writtenToDisk =
                                writeResponseBodyToDisk(activity!!,response.body(), folder, fileName)
                            Log.d("DOWNLOADING...", "file download was a success? $writtenToDisk")
                            return writtenToDisk
                        }
                    }.execute()
                } else {
                    AlertDownload(
                        activity,
                        "Exists",
                        "This Voice is Already exists in your Folder: $folder/$fileName",
                        refreshlistener
                    )

                    Log.d("DOWNLOADING...", "server contact failed")
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                if (mProgressDialog!!.isShowing) mProgressDialog!!.dismiss()
                mProgressDialog!!.dismiss()
                Log.e("DOWNLOADING...", "error: $t")
            }
        })
    }


    fun writeResponseBodyToDisk(activity:Context,body: ResponseBody?, folder: String?, fileName: String?): Boolean {
        return try {
           // val filepath = Environment.getExternalStorageDirectory().path
            val filepath: String

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                filepath=activity.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()
            } else {
                filepath = Environment.getExternalStorageDirectory().getPath()
            }


            val file = File(filepath, folder)
            val dir = File(file.absolutePath)
            println("body: $body")
            if (!dir.exists()) {
                dir.mkdirs()
                println("Dir: $dir")
            }
            var futureStudioIconFile = File(dir, fileName) //"Hai.mp3"
            if (!futureStudioIconFile.exists()) {
                val futureStudioIconFile1 = File(dir, fileName)
                futureStudioIconFile = futureStudioIconFile1
            }
            println("futureStudioIconFile: $futureStudioIconFile")

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
                mProgressDialog!!.dismiss()
            }
        } catch (e: IOException) {
            false
        }
    }

    fun AlertDownload(
        activity: Context?,
        title: String?,
        msg: String?,
        refreshlistener: Refreshlistener
    ) {
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(msg)
        alertDialog.setPositiveButton(
            R.string.teacher_btn_ok,
            DialogInterface.OnClickListener { dialog,
                                              which ->
                dialog.cancel()

            })
        alertDialog.show()
    }


}