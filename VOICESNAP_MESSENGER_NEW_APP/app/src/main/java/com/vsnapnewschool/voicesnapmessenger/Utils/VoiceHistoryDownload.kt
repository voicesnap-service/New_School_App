package com.vsnapnewschool.voicesnapmessenger.Utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

object VoiceHistoryDownload {
    var mProgressDialog: ProgressDialog? = null
    fun downloadHistoryFile(activity: Context?, urldata: String, folder: String?, fileName: String) {
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog!!.isIndeterminate = true
        mProgressDialog!!.setMessage("Downloading...")
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.show()
        Log.d("FileHistoryURL", urldata)

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)

        val call: Call<ResponseBody?>? = apiInterface.downloadFileWithDynamicUrlAsync(urldata)
        call!!.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                mProgressDialog!!.dismiss()
                if (response.isSuccessful) {
                    Log.d("DOWNLOADING...", "server contacted and has file")

                    object : AsyncTask<Void?, Void?, Boolean>() {
                        protected fun doInBackground(vararg voids: Void): Boolean {
                            val writtenToDisk =
                                writeResponseBodyToDisk(activity!!,response.body(), folder, fileName)
                            Log.d("DOWNLOADING...", "file download was a success? $writtenToDisk")
                            return writtenToDisk
                        }

                        override fun onPostExecute(status: Boolean) {
                            super.onPostExecute(status)
                            if (status) {
                                Log.d("SucessDownloaded...", "sucess")

                            }
                        }

                        override fun doInBackground(vararg params: Void?): Boolean? {
//                            TODO("Not yet implemented")
                            val writtenToDisk =
                                writeResponseBodyToDisk(activity!!,response.body(), folder, fileName)
                            Log.d("DOWNLOADING...", "file download was a success? $writtenToDisk")
                            return writtenToDisk
                        }
                    }.execute()
                } else {

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

            //String filepath = Environment.getExternalStorageDirectory().getPath();


            //String filepath = Environment.getExternalStorageDirectory().getPath();
            val filepath: String

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                filepath=activity.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()
            } else {
                filepath = Environment.getExternalStorageDirectory().getPath()
            }


            val file = File(filepath, folder)
            val dir = File(file.absolutePath)
            println("Dir: $dir")
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


}


