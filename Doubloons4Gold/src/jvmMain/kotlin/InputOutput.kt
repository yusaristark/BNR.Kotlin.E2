actual fun output(message: String) = println(message)
actual fun getInput(prompt: String): String {
    output(prompt)
    return readLine() ?: ""
}