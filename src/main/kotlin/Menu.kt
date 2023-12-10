import formatter.FormatFromSeparatedFiles

object Menu {
    operator fun invoke() {
        greeting()
            val option = readlnOrNull()
            when (option) {
                "1" -> FormatFromSeparatedFiles.format()
                "2" -> Unit //todo: make format xml later
                "q", "Q" -> println("Bye~")
            }
    }

    private fun greeting(){
        println("String resources formatter!")
        println("1. Format from separate file string and key")
        println("2. Format from xml file")
        println("Q. Exit")
    }
}