package formatter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import utils.FileUtils
import utils.FormatTextUtils
import java.io.File

object FormatFromSeparatedFiles {
    fun format() = runBlocking {
        val keysFile = File("./separatedFilesOption/keys.txt")
        val stringsViFile = File("./separatedFilesOption/languages/vi.txt")
        val stringsEnFile = File("./separatedFilesOption/languages/en.txt")

        launch {
            format("./separatedFilesOption/output/value-vi/string.xml", keysFile, stringsViFile)
        }
        launch {
            format("./separatedFilesOption/output/value/string.xml", keysFile, stringsEnFile)
        }
    }

    private suspend fun format(
        fileName: String,
        keysFile: File,
        stringsFile: File
    ) = withContext(Dispatchers.IO) {
        val keysRunner = async { keysFile.readLines() }
        val textsRunner = async { stringsFile.readLines() }
        val keys = keysRunner.await()
        val texts = textsRunner.await()
        if (keys.size == texts.size) {
            val listStringResources = List(keys.size) { index ->
                FormatTextUtils.formatTextStringResourcesAndroid(
                    key = keys[index],
                    text = texts[index],
                    translatable = true
                )
            }
            val stringResources = listStringResources.joinToString(
                prefix = "<resources>\n\t",
                separator = "\n\t",
                postfix = "\n</resources>"
            )
            FileUtils.createFile(fileName, stringResources)
        } else {
            error("key and texts are not have the same size")
        }
    }

}