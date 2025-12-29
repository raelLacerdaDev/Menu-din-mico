fun generateTree(records: Array<String>) : GenericTree<MenuItem> {
    val menu = GenericTree<MenuItem>()
    val positions: MutableMap<Int, Position<MenuItem>> = mutableMapOf()

    records.forEach { record ->
        val parts = record.split(',')

        val id = parts[0].toInt()
        val text = parts[1].trim()
        val route = parts[2].trim()
        val parentId = parts[3].toIntOrNull() ?: 0

        if(menu.root == null) {
            val position = menu.addRoot(MenuItem(text = text,route = route))
            positions[id] = position
        } else {
            val parentPosition = positions[parentId] ?: throw IllegalStateException("Error: Parent $parentId don't have valid position")
            val newItem = menu.add(element = MenuItem(text = text,route = route), parent = parentPosition)
            positions[id] = newItem
        }
    }
    return menu
}

