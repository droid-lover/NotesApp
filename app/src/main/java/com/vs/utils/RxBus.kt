package com.vs.veronica.utils

import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Sachin
 */
object RxBus {

    val showBackButton = BehaviorSubject.create<Boolean>()
}