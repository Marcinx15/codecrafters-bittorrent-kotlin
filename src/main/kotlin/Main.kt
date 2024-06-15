import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

// import com.dampcake.bencode.Bencode; - available if you need it!

fun main(args: Array<String>) {

    when (val command = args[0]) {
        "decode" -> {
            val bencodedValue = args[1]
            val decoded = decodeBencode(bencodedValue)
            println(decoded)
        }
        else -> println("Unknown command $command")
    }
}

fun decodeBencode(bencodedString: String): String =
    when {
        bencodedString.first().isDigit() -> {
            val firstColonIndex = bencodedString.indexOfFirst { it == ':' }
            val length = bencodedString.take(firstColonIndex).toInt()
            Json.encodeToString(bencodedString.substring(firstColonIndex + 1, firstColonIndex + 1 + length))
        }

        bencodedString.first() == 'i' -> {
            val numberSubstring = bencodedString.drop(1).takeWhile { it != 'e' }
            if (numberSubstring.length > 1 && numberSubstring[0] == '0') {
                throw InvalidFormatException
            } else if (numberSubstring.length >= 2 && numberSubstring[0] == '-' && numberSubstring[1] == '0') {
                throw InvalidFormatException
            } else {
                Json.encodeToString(numberSubstring.toLong())
            }
        }

        else -> TODO()
    }

object InvalidFormatException : Exception()

