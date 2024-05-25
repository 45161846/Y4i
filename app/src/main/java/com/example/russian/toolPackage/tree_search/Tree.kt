package com.example.russian.toolPackage.tree_search

class Tree() {

    class TreeNode (
        var children: Array<TreeNode?> = arrayOfNulls<TreeNode>(257),
        var childrenAmount: Int = 0
    )

    val root: TreeNode = TreeNode()

    fun insert(word: String) {
        var r = root
        for (c in word) {
            if (r.children[c.code] == null) {
                r.children[c.code] = TreeNode()
            }
            r = r.children[c.code]!!
        }
        r.childrenAmount = 0
    }

    fun search(word: String): Boolean {
        var r = root
        for (c in word) {
            if (r.children[c.code] == null) {
                return false
            }
            r = r.children[c.code]!!
        }
        return r.childrenAmount == 0
    }

    fun startsWith(prefix: String): Boolean {
        var r = root
        for (c in prefix) {
            if (r.children[c.code] == null) {
                return false
            }
            r = r.children[c.code]!!
        }
        return true
    }
    
//    fun getParseNodeToStrings(node: TreeNode): List<String>{
//
//    }
    
}