package basem.com.propertysearch.screens.ui.view_model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import basem.com.propertysearch.common.api.Resource


import basem.com.propertysearch.screens.data.model.*
import basem.com.propertysearch.screens.data.repository.home.DaggerHomeRepositoryComponent
import basem.com.propertysearch.screens.data.repository.home.HomeRepository
import javax.inject.Inject


class HomeViewModel : ViewModel() {


    @Inject
    lateinit var mRepository: HomeRepository


 init {
      mRepository = DaggerHomeRepositoryComponent.create().homeRepository
  }

    fun getSectionsFromRep(): MutableLiveData<Resource<SectionsResponse>>?
    {
       return mRepository.getSections()
    }

    fun getLocationsFromRep(): MutableLiveData<Resource<LocationsResponse>>?
    {
        return mRepository.getLocations()
    }

    fun getTypeFromRep(): MutableLiveData<Resource<TypeResponse>>?
    {
        return mRepository.getType()
    }

    fun getPriceFromRep(): MutableLiveData<Resource<PriceResponse>>?
    {
        return mRepository.getPrice()
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.clean()
    }

}
