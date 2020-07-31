package com.platdmit.peterpartnertest.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platdmit.peterpartnertest.app.utilities.enums.MessageType
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel(){
    protected val compositeDisposable = CompositeDisposable()
    val messageLiveData = MutableLiveData<MessageType>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}