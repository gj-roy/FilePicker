package com.nareshchocha.filepickerlibrary.picker

import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.nareshchocha.filepickerlibrary.utilities.appConst.Const
import timber.log.Timber
import java.io.File
import java.io.IOException

object PickerUtils {

    fun <I, O> AppCompatActivity.selectFile(
        contract: ActivityResultContract<I, O>,
        resultCallBack: (result: O) -> Unit,
    ): ActivityResultLauncher<I> {
        return registerForActivityResult(contract) { result ->
            resultCallBack(result)
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun createMediaFileFolder(
        folderFile: File,
        fileName: String,
    ): File {
        if (!folderFile.exists() && !folderFile.mkdirs()) {
            Timber.tag(Const.LogTag.FILE_PICKER_ERROR).d("failed to create directory")
        }
        return File(folderFile.path + File.separator + fileName)
    }

    fun Context.createFileGetUri(mFile: File): Uri? {
        if (!mFile.exists()) {
            try {
                mFile.createNewFile()
            } catch (e: IOException) {
                Timber.tag(Const.LogTag.FILE_PICKER_ERROR).d(e.toString())
            } catch (e: SecurityException) {
                Timber.tag(Const.LogTag.FILE_PICKER_ERROR).d(e.toString())
            }
        }
        val uri = getUriForFile(mFile)?.apply {
        }

        return uri
    }

    private fun Context.getUriForFile(mFile: File): Uri? {
        return FileProvider.getUriForFile(
            this.applicationContext,
            applicationContext.packageName +
                Const.AUTHORITY,
            mFile,
        )
    }
}
