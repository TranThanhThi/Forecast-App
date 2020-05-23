package vn.edu.ntu.forecastapp.internal

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*

fun<T> lazyDeferred(block:suspend CoroutineScope.()->T):Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}
fun<T> Task<T>.asDeferred():Deferred<T>{
    val deferred = CompletableDeferred<T>()

    this.addOnSuccessListener {
        deferred.complete(it)
    }
    this.addOnFailureListener {
        deferred.completeExceptionally(it)
    }
    return deferred
}