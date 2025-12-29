data class MenuItem (
    val text: String,
    val route: String
){
    override fun toString(): String {
        return "$text (${route.ifEmpty { "null" }})"
    }
}
