package com.example.marvellisimo.characterslist

import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.example.marvellisimo.api.MarvelApi
import com.example.marvellisimo.api.entity.Character
import com.example.marvellisimo.api.paging.CharactersDataSourceFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharactersViewModel : ViewModel() {

    var characterList: Observable<PagedList<Character>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 20

    private val sourceFactory: CharactersDataSourceFactory

    init {
        sourceFactory = CharactersDataSourceFactory(compositeDisposable, MarvelApi.getService())

        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build()

        characterList = RxPagedListBuilder(sourceFactory, config)
                .setFetchScheduler(Schedulers.io())
                .buildObservable()
                .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}