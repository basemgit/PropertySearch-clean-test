package basem.com.propertysearch.screens.data.repository.home

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import basem.com.propertysearch.common.api.ApiService
import basem.com.propertysearch.common.api.DaggerApiComponent
import basem.com.propertysearch.common.api.Resource
import basem.com.propertysearch.screens.data.model.*
import basem.com.propertysearch.testing.OpenForTesting
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class HomeRepository{


    @Inject
    lateinit var apiService: ApiService



    var disposables: CompositeDisposable = CompositeDisposable()

    init {
        apiService = DaggerApiComponent.create().apiService
    }

    var sectionsResponseLiveData: MutableLiveData<Resource<SectionsResponse>>? = MutableLiveData()

    var locationsResponseLiveData: MutableLiveData<Resource<LocationsResponse>>? = MutableLiveData()

    var typeResponseLiveData: MutableLiveData<Resource<TypeResponse>>? = MutableLiveData()

    var priceResponseLiveData: MutableLiveData<Resource<PriceResponse>>? = MutableLiveData()

    var searchResponseLiveData: MutableLiveData<SearchResponse>? = MutableLiveData()




    fun getSections(): MutableLiveData<Resource<SectionsResponse>>? {
        disposables.add(apiService.sectionsResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> sectionsResponseLiveData?.value = (Resource.loading()) }
                .subscribe(
                        { response -> sectionsResponseLiveData?.value = (Resource.success(response)) },
                        { throwable -> sectionsResponseLiveData?.value = (Resource.error(throwable)) }
                ))
        return sectionsResponseLiveData
    }


    fun getLocations(): MutableLiveData<Resource<LocationsResponse>>? {
       disposables.add(apiService.loactionsResponse.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .doOnSubscribe { _ -> locationsResponseLiveData?.value = (Resource.loading()) }
               .subscribe(
                       { response -> locationsResponseLiveData?.value = (Resource.success(response)) },
                       { throwable -> locationsResponseLiveData?.value = (Resource.error(throwable)) }
               ))

        return locationsResponseLiveData
    }


    fun getType(): MutableLiveData<Resource<TypeResponse>>? {
        disposables.add(apiService.typeResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> typeResponseLiveData?.value = (Resource.loading()) }
                .subscribe(
                        { response -> typeResponseLiveData?.value = (Resource.success(response)) },
                        { throwable -> typeResponseLiveData?.value = (Resource.error(throwable)) }
                ))

        return typeResponseLiveData
    }

    fun getPrice(): MutableLiveData<Resource<PriceResponse>>? {
        disposables.add(apiService.priceResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> priceResponseLiveData?.value = (Resource.loading()) }
                .subscribe(
                        { response -> priceResponseLiveData?.value = (Resource.success(response)) },
                        { throwable -> priceResponseLiveData?.value = (Resource.error(throwable)) }
                ))

        return priceResponseLiveData
    }

    fun getSearchResults(): MutableLiveData<SearchResponse>? {
        if (apiService != null) {
            apiService.searchResponse.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<SearchResponse> {
                        override fun onSubscribe(d: Disposable) {
                            disposables.add(d)
                        }

                        override fun onNext(response: SearchResponse) {
                            searchResponseLiveData?.postValue(response)
                        }

                        override fun onError(e: Throwable) {
                            Log.d("err", e.message.toString())
                        }

                        override fun onComplete() {

                        }
                    })
        }
        return searchResponseLiveData
    }


    fun clean() {
        disposables.clear()
    }


}