package basem.com.propertysearch.screens.ui.view_model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import basem.com.propertysearch.common.api.Resource
import basem.com.propertysearch.screens.data.model.*
import basem.com.propertysearch.screens.data.repository.searchResults.DaggerSearchResultsRepositoryComponent
import basem.com.propertysearch.screens.data.repository.searchResults.SearchResultsRepository
import javax.inject.Inject


class SearchResultsViewModel : ViewModel() {

    @Inject
    lateinit var mRepository: SearchResultsRepository


    init {
        mRepository = DaggerSearchResultsRepositoryComponent.create().searchResultsRepository
    }

    fun getSearchResultsFromRep(): MutableLiveData<Resource<SearchResponse>>?
    {
        return mRepository.getSearchResults()
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.clean()
    }

}
