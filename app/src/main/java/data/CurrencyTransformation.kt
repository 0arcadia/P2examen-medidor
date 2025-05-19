package data

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.NumberFormat
import java.util.Locale


class CurrencyTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formatted = if (text.text.isNotEmpty()) {
            val number = text.text.toDoubleOrNull() ?: 0.0
            formatToCLP(number)
        } else ""

        return TransformedText(
            AnnotatedString("$formatted"),
            OffsetMapping.Identity
        )
    }
}

fun formatToCLP(value: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    formatter.maximumFractionDigits = if (value % 1 == 0.0) 0 else 2
    return formatter.format(value)
}