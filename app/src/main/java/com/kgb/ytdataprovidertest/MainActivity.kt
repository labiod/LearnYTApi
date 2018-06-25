package com.kgb.ytdataprovidertest

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable

class MainActivity : Activity() {
    private lateinit var subscripction : Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var todoObservable = Observable.create(ObservableOnSubscribe<Todo> { e ->
            var todos = getTodos()
            for ( t in todos) {
                e.onNext(t)
                SystemClock.sleep(1000)
            }
            e.onComplete()
        })
        subscripction = todoObservable.subscribe({ t -> Log.d("[KGB]", t.name) })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!subscripction.isDisposed) {
            subscripction.dispose()
        }
    }

    private fun getTodos(): List<Todo> {
        var result = ArrayList<Todo>()
        result.add(Todo("test1"))
        result.add(Todo("test2"))
        result.add(Todo("test3"))
        result.add(Todo("test4"))
        return result
    }
}
