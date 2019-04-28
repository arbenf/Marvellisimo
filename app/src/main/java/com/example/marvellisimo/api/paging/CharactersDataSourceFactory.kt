package com.example.marvellisimo.api.paging

import android.arch.paging.DataSource
import com.example.marvellisimo.api.MarvelApi
import com.example.marvellisimo.api.entity.Character
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val marvelApi: MarvelApi
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelApi, compositeDisposable)
    }
}