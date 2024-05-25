package com.example.russian.toolPackage.tree_search

import com.example.russian.database.Word

class Tree{

    class TreeNode (
        var children: Array<TreeNode?> = arrayOfNulls<TreeNode>(2000),
        var childrenAmount: Int = 0,
        val wordObject: Word? = null,
        var isWord: Boolean = false
    )

    val root: TreeNode = TreeNode()

    fun insert(word: Word) {
        var r = root
        val arr = word.value.toCharArray()
        arr.forEachIndexed { index, c ->
            r.childrenAmount++
            if (r.children[c.code] == null || index == arr.size - 1) {
                val grandChildrenAmount = if(r.children[c.code] == null){
                    0
                }else{
                    r.children[c.code]!!.childrenAmount
                }
                r.children[c.code] = TreeNode(wordObject = word, childrenAmount = grandChildrenAmount)
            }
            r = r.children[c.code]!!
        }
        r.isWord = true
    }

    fun insert(words: List<Word>){
        words.forEach{
            insert(it)
        }
    }

    fun startsWith(prefix: String): TreeNode {
        var r = root
        for (c in prefix) {
            if (r.children[c.code] == null) {
                return TreeNode()
            }
            r = r.children[c.code]!!
        }
        return r
    }

    fun getAllByPrefix(pref: String): List<Word>{
        val startNode = startsWith(pref)
        return parseNodeToStrings(startNode)

    }
    
    private fun parseNodeToStrings(node: TreeNode): List<Word>{

        if(node.childrenAmount == 0){
            return if(node.isWord) {
                listOf(node.wordObject!!)
            }else{
                emptyList()
            }
        }
        val addition = if(node.isWord){
            1
        }else{
            0
        }
        val words = arrayOfNulls<Word>(node.childrenAmount + addition)

        var ind = 0
        node.children.forEach{
            if(it != null){
                val childWords = parseNodeToStrings(it)
                for(i in childWords.indices){
                    words[ind + i] = childWords[i]
                }
                ind += childWords.size
            }
        }
        if(node.isWord){
            words[words.size - 1] = node.wordObject
        }

        val ans = List(words.size){
            words[it]!!
        }

        return ans
    }
    
}