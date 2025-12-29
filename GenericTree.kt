class GenericTree<T> {

    private var _root: Node<T>? = null
    val root: Position<T>? get() = _root

    var size = 0
        private set

    // nested class
    private data class Node<T>(override var element: T) : Position<T> {
        val children = mutableListOf<Node<T>>()
        var parent: Node<T>? = null
    }

    fun addRoot(element: T): Position<T> {
        if (_root != null) {
            throw IllegalStateException("Root already exists.")
        }
        val newNode = Node(element)
        _root = newNode
        size += 1
        return newNode
    }

    fun add(element: T, parent: Position<T>): Position<T> {
        if(_root == null) {
            throw IllegalStateException("You must add root!")
        }

        val newNode = Node(element)
        val parentNode = validatePosition(parent)

        newNode.parent = parentNode
        parentNode.children.add(newNode)
        size += 1

        return newNode
    }

    private fun validatePosition(position: Position<T>): Node<T> {
        val node = position as? Node<T> ?: throw IllegalStateException("Position invalid.")
        return node
    }

    fun children(position: Position<T>): List<Position<T>> {
        val node = validatePosition(position)
        return node.children
    }

    fun elements() : List<T> {
        val list = mutableListOf<T>()
        preOrder {
            list.add(it.element)
        }
        return list.toList()
    }

    fun positions(): List<Position<T>> {
        val list = mutableListOf<Position<T>>()
        preOrder {
            list.add(it)
        }
        return list.toList() // O(n) but safety
    }

    fun find(target: T): Position<T>? {
        return findRecursive(_root ?: return null, target)
    }
    private fun findRecursive(p: Position<T>,target: T): Position<T>? {
        if (p.element == target) {
            return p
        }
        for (child in children(p)) {
            val result = findRecursive(child, target)
            if (result != null) {
                return result
            }
        }
        return null
    }

    fun printTree() {
        printRecursive(_root ?: throw NoSuchElementException("Empty tree!"), 0)
    }

    private fun printRecursive(node: Position<T>, depth: Int = 0) {
        val spaces = "    ".repeat(depth)
        println("$spaces${node.element}")
        val children = children(node)
        for (child in children) {
            printRecursive(child, depth + 1)
        }
    }

    // dfs
    fun preOrder(callAction: (Position<T>) -> Unit) {
        preOrderRecursive(_root ?: throw IllegalArgumentException("Root is null!"), callAction)
    }
    private fun preOrderRecursive(node: Position<T>, callAction: (Position<T>) -> Unit) {
        callAction(node)
        for(child in children(node)) {
            preOrderRecursive(child, callAction)
        }
    }

    fun postOrder(callAction: (Position<T>) -> Unit) {
        postOrderRecursive(_root ?: throw IllegalArgumentException("Root is null!"), callAction)
    }
    private fun postOrderRecursive(node: Position<T>, callAction: (Position<T>) -> Unit) {
        for (child in children(node)) {
            postOrderRecursive(child, callAction)
        }
        callAction(node)
    }

    fun isExternal(p: Position<T>): Boolean {
        val node = validatePosition(p)
        return node.children.isEmpty()
    }

    fun isRoot(p: Position<T>): Boolean {
        val node = validatePosition(p)
        return node == _root
    }

    fun parent(p: Position<T>): Position<T>? {
        val node = validatePosition(p)
        return node.parent
    }

    fun replace(target: Position<T>, element: T) : Position<T> {
        val node = validatePosition(target)
        node.element = element
        return node
    }

    fun remove(p: Position<T>) {
        val node = validatePosition(p)
        if (node == _root) {
            _root = null
            size = 0
            return
        }
        val parent = node.parent
        var count = 0
        preOrderRecursive(node) {
            count++
            markAsRemoved(it)
        }
        parent?.children?.remove(node)
        size -= count
    }

    private fun markAsRemoved (p: Position<T>) {
        val  node = validatePosition(p)
        node.parent = node
    }

    fun bfs(callAction: (Position<T>) -> Unit) {
        if (_root == null) {
            throw IllegalStateException("Tree is empty!")
        }
        val queue = Queue<Position<T>>()
        queue.add(_root ?: throw IllegalArgumentException("Root is null!"))
        while (queue.size > 0) {
            val position = queue.remove()
            callAction(position?.value ?: continue)
            for (child in children(position.value)) {
                queue.add(child)
            }
        }
    }
}
