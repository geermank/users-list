package com.geermank.userslist.utils

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore

private const val MEDIA_DOC_PROVIDER_URI_AUTHORITY = "com.android.providers.media.documents"
private const val DOWNLOADS_DOC_PROVIDER_URI_AUTHORITY = "com.android.providers.downloads.documents"

class UriRealPath(private val sourceUri: Uri) {

    fun getRealPath(context: Context): String {
        var imageRealPath = ""
        when {
            DocumentsContract.isDocumentUri(context, sourceUri) -> {
                imageRealPath = getPathFromDocumentProviders(context)
            }
            "content".equals(sourceUri.scheme, ignoreCase = true) -> {
                imageRealPath = getImagePathFromUri(context, sourceUri, null)
            }
            "file".equals(sourceUri.scheme, ignoreCase = true) -> {
                imageRealPath = sourceUri.path ?: ""
            }
        }
        return imageRealPath
    }

    private fun getPathFromDocumentProviders(context: Context): String {
        var documentPath = ""
        val docId = DocumentsContract.getDocumentId(sourceUri)
        if (MEDIA_DOC_PROVIDER_URI_AUTHORITY == sourceUri.authority) {
            val id = docId.split(":")[1]
            val selection = MediaStore.Images.Media._ID + "=" + id
            documentPath = getImagePathFromUri(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
        } else if (DOWNLOADS_DOC_PROVIDER_URI_AUTHORITY == sourceUri.authority) {
            val newUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                docId.toLong()
            )
            documentPath = getImagePathFromUri(context, newUri, null)
        }
        return documentPath
    }

    private fun getImagePathFromUri(context: Context, uri: Uri, selection: String?): String {
        var imagePath = ""
        context.contentResolver.query(
            uri,
            null,
            selection,
            null,
            null
        )?.let { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                imagePath = cursor.getString(index)
            }
            cursor.close()
        }

        return imagePath
    }
}