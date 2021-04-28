package com.vsnapnewschool.voicesnapmessenger.BioMetric

import android.annotation.TargetApi
import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal



class BiometricManager(
    context: Context,
    title: String,
    subtitle: String,
    description: String,
    negativeButtonText: String,
    errorText: String,
    dialogV23: BiometricDialogV23Interface

) :
    BiometricManagerV23(
        context,
        title,
        subtitle,
        description,
        negativeButtonText,
        errorText,
        dialogV23
    ) {

    private var mCancellationSignal: CancellationSignal = CancellationSignal()

    fun authenticate(biometricCallback: BiometricCallback) {
        if (!BiometricUtils.isSdkVersionSupported) {
            biometricCallback.onSdkVersionNotSupported()
            return
        }
        if (!BiometricUtils.isPermissionGranted(context)) {
            biometricCallback.onBiometricAuthenticationPermissionNotGranted()
            return
        }
        if (!BiometricUtils.isHardwareSupported(context)) {
            biometricCallback.onBiometricAuthenticationNotSupported()
            return
        }
        if (!BiometricUtils.isFingerprintAvailable(context)) {
            biometricCallback.onBiometricAuthenticationNotAvailable()
            return
        }
        displayBiometricDialog(biometricCallback)
    }

    fun cancelAuthentication() {
        if (BiometricUtils.isBiometricPromptEnabled) {
            if (!mCancellationSignal.isCanceled) mCancellationSignal.cancel()
        } else {
            if (!mCancellationSignalV23.isCanceled) mCancellationSignalV23.cancel()
        }
    }

    private fun displayBiometricDialog(biometricCallback: BiometricCallback) {
        if (BiometricUtils.isBiometricPromptEnabled) {
            displayBiometricPrompt(biometricCallback)
        } else {
            displayBiometricPromptV23(biometricCallback)
        }
    }

    @TargetApi(Build.VERSION_CODES.P)
    private fun displayBiometricPrompt(biometricCallback: BiometricCallback) {

        BiometricPrompt.Builder(context)
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeButton(negativeButtonText, context.mainExecutor,
                DialogInterface.OnClickListener { _, _ -> biometricCallback.onAuthenticationCancelled() })
            .build()
            .authenticate(
                mCancellationSignal, context.mainExecutor,
                BiometricCallbackV28(biometricCallback)
            )


        //        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view: View = inflater.inflate(R.layout.dialog_biometric_custom, null)
//        val popupWindow = PopupWindow(
//            view,
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            true
//        )
//        popupWindow.setContentView(view)
//        popupWindow.setTouchable(true)
//        popupWindow.setFocusable(false)
//        popupWindow.setOutsideTouchable(false)
//        val item_title = view.findViewById<TextView>(R.id.item_title)
//        val item_subtitle = view.findViewById<TextView>(R.id.item_subtitle)
//        val item_description = view.findViewById<TextView>(R.id.item_description)
//
//
//
//        item_title.setText(title)
//        item_subtitle.setText(subtitle)
//        item_description.setText(description)
//        val btn_cancel = view.findViewById<TextView>(R.id.btn_cancel)
//        btn_cancel.setOnClickListener {
//            biometricCallback.onAuthenticationCancelled()
//            popupWindow.dismiss()
//        }
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

    }
}