import java.text.NumberFormat

actual fun Double.formatAsCurrency(): String = NumberFormat.getCurrencyInstance().format(this)