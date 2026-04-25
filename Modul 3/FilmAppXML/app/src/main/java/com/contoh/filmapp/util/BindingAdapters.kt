package com.contoh.filmapp.util

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.contoh.filmapp.R

/**
 * Fungsi bantuan untuk memuat gambar dari URL menggunakan Coil.
 * * @param url Link gambar internet
 * @param radius
 */
fun ImageView.loadImage(url: String, radius: Float = 12f) {
    this.load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
        if (radius > 0f) {
            transformations(RoundedCornersTransformation(radius))
        }
    }
}