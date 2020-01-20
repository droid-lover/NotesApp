package com.vs.utils

import com.vs.models.ActionPerformed
import com.vs.models.Note
import io.reactivex.subjects.PublishSubject

/**
 * Created by Sachin
 */
object RxBus {

//    val actionPerformed = PublishSubject.create<Note>()
    val actionPerformed = PublishSubject.create<ActionPerformed>()
    val showActionDailog = PublishSubject.create<Note>()
}