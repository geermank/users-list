package com.geermank.userslist.presentation

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import android.graphics.BitmapFactory

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.remove() {
    this.visibility = View.GONE
}

fun View.changeVisibility(show: Boolean) {
    if (show) {
        this.show()
    } else {
        this.remove()
    }
}

fun EditText.setTextIfEmpty(newText: String) {
    if (text.isEmpty()) {
        this.setText(newText)
    }
}

fun TextView.setTextOrRemove(newText: String) {
    if (newText.isEmpty()) {
        this.remove()
    } else {
        this.text = newText
    }
}

fun ImageView.setProfilePicOrDefault(imageUri: String?, @DrawableRes default: Int) {
    if (imageUri.isNullOrEmpty()) {
        setImageResource(default)
    } else {
        setImageBitmap(BitmapFactory.decodeFile(imageUri))
    }
}
