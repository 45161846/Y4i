package com.example.russian.gameClasses.viewmodel.randomaizer

class RandomStorageImpl<T>(
   private val maxSize: Int
): RandomStorage<T> {

    private var incorrectAnswers: ArrayList<T>? = null
    private var correctAnswer: T? = null
    private var correctIndex: Int = -1
    private var randomIndexes = Array(0){it}
    private var size = 0

    override fun setNewValues(items: Collection<T>, detector: CorrectDetector<T>) {

        setValues(items, detector)

//        incorrectAnswers?.let { incorrect ->
//            items.forEach{
//                if(it != correctAnswer && it !in incorrect){
//                    setValues(items, detector)
//                    return
//                }
//            }
//        } ?: {
//            setValues(items, detector)
//        }

    }

    private fun setValues(items: Collection<T>, detector: CorrectDetector<T>){

        incorrectAnswers = ArrayList(items.size - 1)
        randomIndexes = Array(items.size - 1){it}

        items.forEach{item ->
            if(detector.isCorrect(item)){
                correctAnswer = item

            }else{
                incorrectAnswers?.add(item) ?: throw initializationException()
            }
        }

        size = items.size
    }

    override fun shuffle() {
        randomIndexes.shuffle()
        correctIndex = (0 until maxSize).random()
    }

    override fun shuffled(): List<T> {
        shuffle()
        return savedRandom()
    }


    override fun savedRandom(): List<T> {
        incorrectAnswers?.let{incorrectAnswers ->

            val left = incorrectAnswers.slice(0 until correctIndex.coerceAtMost(incorrectAnswers.size))
            val right = incorrectAnswers.slice(correctIndex until (maxSize - 1).coerceAtMost(incorrectAnswers.size))

            correctAnswer ?: throw initializationException()

            val sequence = left + (correctAnswer ?: throw initializationException()) + right

            return sequence
        } ?: return listOf()
    }

    private fun initializationException() = RuntimeException("Collection has not benn initialised")
}

interface CorrectDetector<T>{
    fun isCorrect(obj: T): Boolean
}