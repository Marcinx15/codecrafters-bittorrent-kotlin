import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
// import com.dampcake.bencode.Bencode; - available if you need it!

fun main(args: Array<String>) {

    when (val command = args[0]) {
        "decode" -> {
            val bencodedValue = args[1]
            val decoded = decodeBencode(bencodedValue)
            println(Json.encodeToString(decoded))
        }
        else -> println("Unknown command $command")
    }
}

fun decodeBencode(bencodedString: String): String =
    when {
        bencodedString.first().isDigit() -> {
            val firstColonIndex = bencodedString.indexOfFirst { it == ':' }
            val length = bencodedString.take(firstColonIndex).toInt()
            bencodedString.substring(firstColonIndex + 1, firstColonIndex + 1 + length)
        }
        else -> TODO("Only strings are supported at the moment")
    }

