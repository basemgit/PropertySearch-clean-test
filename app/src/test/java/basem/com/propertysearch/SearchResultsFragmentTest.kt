package basem.com.propertysearch

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import basem.com.propertysearch.common.api.Resource
import basem.com.propertysearch.screens.data.model.SearchResponse
import basem.com.propertysearch.screens.data.repository.home.HomeRepository
import basem.com.propertysearch.screens.ui.view_model.SearchResultsViewModel
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class SearchResultsFragmentTest {

    private val searchResultsViewModel = SearchResultsViewModel()
    @Mock
    private val homeRepository: HomeRepository = HomeRepository()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun testSearchResults() {
        val foo = MutableLiveData<SearchResponse>()
        `when`(homeRepository.getSearchResults()).thenReturn(foo)
        val observer = mock<Observer<Resource<SearchResponse>>>()
        searchResultsViewModel.getSearchResultsFromRep()!!.observeForever(observer)
        verify(observer, never()).onChanged(null)
    }




}
