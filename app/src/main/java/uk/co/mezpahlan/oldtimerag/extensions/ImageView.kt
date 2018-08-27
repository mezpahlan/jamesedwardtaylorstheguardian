package uk.co.mezpahlan.oldtimerag.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso
import uk.co.mezpahlan.oldtimerag.R
import uk.co.mezpahlan.oldtimerag.base.GrayscaleTransformation

fun ImageView.loadUrl(url: String) {
    Picasso.with(context)
            .load(url)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.old_time_rag_no_text)
            .error(R.drawable.old_time_rag_no_text)
            .transform(GrayscaleTransformation(Picasso.with(context)))
            .into(this)
}