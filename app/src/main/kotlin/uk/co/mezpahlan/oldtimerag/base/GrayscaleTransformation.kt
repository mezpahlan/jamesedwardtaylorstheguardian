/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.mezpahlan.oldtimerag.base

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode

import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

import java.io.IOException

import uk.co.mezpahlan.oldtimerag.R

import android.graphics.Bitmap.createBitmap
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Shader.TileMode.REPEAT

class GrayscaleTransformation(private val picasso: Picasso) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val result = createBitmap(source.width, source.height, source.config)
        val noise: Bitmap
        try {
            noise = picasso.load(R.drawable.noise).get()
        } catch (e: IOException) {
            throw RuntimeException("Failed to apply transformation! Missing resource.")
        }

        val shader = BitmapShader(noise, REPEAT, REPEAT)

        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(colorMatrix)

        val paint = Paint(ANTI_ALIAS_FLAG)
        paint.colorFilter = filter

        val canvas = Canvas(result)
        canvas.drawBitmap(source, 0f, 0f, paint)

        paint.colorFilter = null
        paint.shader = shader
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)

        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), paint)

        source.recycle()
        noise.recycle()

        return result
    }

    override fun key(): String {
        return "grayscaleTransformation()"
    }
}