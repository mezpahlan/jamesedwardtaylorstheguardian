package uk.co.mezpahlan.oldtimerag.extensions

fun String.convertDateFormat(): String {
    val year = this.substring(0, 4)
    val month = this.substring(5, 7)
    val date = this.substring(8, 10)

    return "$date-$month-$year"
}

fun String.stripHtml(): String {
    return this.replace("<[^>]*>".toRegex(), "")
}

fun String.wrapArticleHtml(): String {
    return """
        <!DOCTYPE html>
        <html>
            <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <link rel="stylesheet" href="style.css">
            </head>
            <body>${this}</body>
        </html>
        """
}