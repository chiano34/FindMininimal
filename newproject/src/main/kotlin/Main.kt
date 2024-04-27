import java.io.File

var elements = mutableListOf(mutableListOf("",""))
//путь для текстовых файлов
val dirName="D:/Новая папка/dataset/labels/"
//путь для входных изображений и txt
val inputF="D:\\Новая папка\\dataset\\"
//путь для вывода txt и jpg
var outputF="D:\\Новая папка\\dataCopy\\"
var minimum=0
fun readFile(fileName: String){
    var fil=dirName+fileName
    var lines = File(fil).readLines()
    var a=mutableListOf("")
    a.removeFirst()
    for(i in 0..(lines.size-1)){
        a.add(lines[i][0].toString())
    }
    a.add(fileName)
    elements.add(a)
}
fun listFilesInDirectory(directoryPath: String) {
    val directory = File(directoryPath)
    if (directory.exists() && directory.isDirectory) {
        val files = directory.listFiles()
        println("Список файлов в директории $directoryPath:")
        files.forEach { file ->
            if (file.isFile) {
                readFile(file.name)
            }
        }
    } else {
        println("Указанная директория не существует или не является директорией.")
    }
}

fun canTouch():MutableList<Int>{
    var count = mutableListOf(0,0,0,0,0,0,0,0,0,0)
    var canTouch= mutableListOf(0)
    canTouch.removeFirst()
    for(value in elements){
        for(i in 0..value.size-2){
            count[value[i].toInt()]++
        }
    }
    for(i in 0..9){
        if(minimum<count[i]){
            canTouch.add(i)
        }
    }
    val n = canTouch.size
    for (i in 0 until n - 1) {
        for (j in 0 until n - i - 1) {
            if (count[canTouch[j]] < count[canTouch[j + 1]]) {
                val temp = canTouch[j]
                canTouch[j] = canTouch[j + 1]
                canTouch[j + 1] = temp
            }
        }
    }

    return canTouch
}
//функция для удаления выбранных значений
fun deleteElem(a:Int,b:Int):Int{
    var k=0
    val iterator = elements.iterator()
    while (iterator.hasNext()) {
        var count = mutableListOf(0,0,0,0,0,0,0,0,0,0)
        var coin=0
        val element = iterator.next()
        for(i in 0..element.size-2){
                count[element[i].toInt()]++
            if (element[i].toInt()==a||element[i].toInt()==b){
                coin++
            }
        }
                var err=0
                var d=count()
                for(i in 0..count().size-1){
                    if(d[i]-count[i]<minimum){
                        err=1
                    }
                }
        if (coin>1&&err==0) {
            iterator.remove()
            k++
            if(a !in canTouch() || b !in canTouch()){
                return k
            }
        }
    }
    return k
}
//функция для поиска чисел для удаления
fun reduce(){
    var cur=0
    var start=0
    var lastSize=0
    while(true) {
        var can = canTouch()
        var index=can[start].toInt()
        lastSize=can.size
        var k=0
        if(can[start]==can[cur]&&count()[index]-minimum<2){
            k=0
        }
        else{
            var k=deleteElem(can[start],can[cur])
        }
        if(canTouch().size<2){
            break
        }
        if(lastSize>canTouch().size){
            start=0
            cur=start
        }
        else if(k==0&&cur+1<(canTouch().size)){
            cur++
        }
        else if(k==0&&lastSize==canTouch().size&&start+1<canTouch().size){
            start++
            cur=start
        }
        else if(k==0&&lastSize==canTouch().size&&start==canTouch().size-1){
            break
        }
    }
}
//подсчёт количества функций
fun count():MutableList<Int>{
    var count = mutableListOf(0,0,0,0,0,0,0,0,0,0)
    for(value in elements){
        for(i in 0..value.size-2){
            count[value[i].toInt()]++
        }
    }
    return count
}
fun countOut(){
    var count = mutableListOf(0,0,0,0,0,0,0,0,0,0)
    for(value in elements){
        for(i in 0..value.size-2){
            count[value[i].toInt()]++
        }
    }
    for(i in 0..9){
        print(" $i:"+count[i]+"")
    }
    println()
}
//минимум среди всех чисел
fun min():Int{
    var count = mutableListOf(0,0,0,0,0,0,0,0,0,0)
    for(value in elements){
        for(i in 0..value.size-2){
            count[value[i].toInt()]++
        }
    }
    var minvalue=count[0]
    for(i in 0..9){
        if(minvalue>count[i]){
            minvalue=count[i]
        }
    }
    return minvalue
}

fun copyFiles(files: MutableList<MutableList<String>>){
    for(file in files){
        var srcFtxt=File(inputF+"labels\\"+file[file.size-1])
        var dstFtxt=File(outputF+"labels\\"+ file[file.size-1])
        var srcFjpg =File(inputF+"images\\"+ file[file.size-1].substring(0,file[file.size-1].length-4)+".jpg")
        var dstFjpg=File(outputF+"images\\"+ file[file.size-1].substring(0,file[file.size-1].length-4)+".jpg")
        srcFjpg.copyTo(dstFjpg, overwrite = true)
        srcFtxt.copyTo(dstFtxt, overwrite = true)
    }
}
fun main() {
    elements.removeFirst()
    listFilesInDirectory(dirName)
    minimum=min()
    reduce()
    countOut()
    copyFiles(elements)
}