package com.bzh.dytt.ui.home

import android.arch.lifecycle.*
import android.util.Log
import com.bzh.dytt.repository.Repository
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import javax.inject.Inject

class HomeListViewModel @Inject constructor(private val repository: Repository) : ViewModel(), LifecycleObserver {

    private var _isRefresh = false

    private var _isLoadMore = false

    private val _movieListLiveData: MutableLiveData<Resource<List<MovieDetail>>> = MutableLiveData()

    private var _movieRepositoryLiveData: LiveData<Resource<List<MovieDetail>>>? = null

    private var _itemUpdateRepositoryLiveData: LiveData<Resource<MovieDetail>>? = null

    private val detailObserver = Observer<Resource<MovieDetail>> {
        Log.d(TAG, "HomeListViewModel ${it?.data}")
        when (it?.status) {
            Status.SUCCESS -> {
            }
            Status.ERROR -> {
            }
            else -> {
            }
        }
    }

    private val listObserver = Observer<Resource<List<MovieDetail>>> {
        when (it?.status) {
            Status.SUCCESS -> {
                _isRefresh = false
                _movieListLiveData.value = it
            }
            Status.ERROR -> {
                _isRefresh = false
                _movieListLiveData.value = it
            }
            else -> {
                _movieListLiveData.value = it
            }
        }
    }

    val movieListLiveData: LiveData<Resource<List<MovieDetail>>>
        get() = _movieListLiveData

    val moveTypeLiveData: MutableLiveData<HomeViewModel.HomeMovieType> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun active() {
        register()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun inactive() {
        unregister()
    }

    fun loadMore() {
        if (_isLoadMore) {
            return
        }
        unregister()
    }

    fun refresh() {
        if (_isRefresh) {
            return
        }
        unregister()
        _movieRepositoryLiveData = repository.movieList(moveTypeLiveData.value, 1, true)
        _movieRepositoryLiveData?.observeForever(listObserver)
    }

    private fun register() {
        _movieRepositoryLiveData = repository.movieList(moveTypeLiveData.value, 1)
        _movieRepositoryLiveData?.observeForever(listObserver)
    }

    private fun unregister() {
        _isRefresh = false
        _movieRepositoryLiveData?.removeObserver(listObserver)
    }

    fun doUpdateMovieDetail(item: MovieDetail) {
        if (!item.isPrefect) {
            _itemUpdateRepositoryLiveData = repository.movieItemUpdate(item)
            _itemUpdateRepositoryLiveData?.observeForever(detailObserver)
        }
    }

    companion object {
        const val TAG = "HomeListViewModel"
    }
}

