package kr.ac.jejunu.rxpractice.ui.input_salse.data

data class Salse(
    val content : Map<String,Boolean>
) {
    operator fun get(word: String): Boolean {
        return content.getValue(word)
    }
}