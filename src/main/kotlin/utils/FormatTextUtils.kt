package utils

object FormatTextUtils {
    fun formatTextStringResourcesAndroid(
        key: String,
        text: String,
        translatable: Boolean = true
    ) = "<string name=\"$key\"${if (translatable) "" else " translatable=\"false\""}>$text</string>"
}