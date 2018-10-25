package basem.com.propertysearch.screens.data.repository.searchResults

import android.arch.lifecycle.MutableLiveData
import basem.com.propertysearch.common.api.ApiService
import basem.com.propertysearch.common.api.DaggerApiComponent
import basem.com.propertysearch.common.api.Resource
import basem.com.propertysearch.screens.data.model.SearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SearchResultsRepository {

    @Inject
    lateinit var apiService: ApiService
    var disposables: CompositeDisposable = CompositeDisposable()
    init {
        apiService = DaggerApiComponent.create().apiService
    }
    var searchResponseLiveData: MutableLiveData<Resource<SearchResponse>>? = MutableLiveData()

    fun getSearchResults(): MutableLiveData<Resource<SearchResponse>>? {
        disposables.add(apiService.searchResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> searchResponseLiveData?.value = (Resource.loading()) }
                .subscribe(
                        { response -> searchResponseLiveData?.value = (Resource.success(response)) },
                        { throwable -> searchResponseLiveData?.value = (Resource.error(throwable)) }
                ))
        return searchResponseLiveData
    }

    fun clean() {
        disposables.clear()
    }


}