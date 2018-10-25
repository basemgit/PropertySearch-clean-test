package basem.com.propertysearch

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import basem.com.propertysearch.common.api.Resource

import basem.com.propertysearch.screens.data.repository.home.HomeRepository
import basem.com.propertysearch.screens.ui.view_model.HomeViewModel
import org.mockito.MockitoAnnotations
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import basem.com.propertysearch.screens.data.model.*
import org.junit.*


@RunWith(JUnit4::class)
class HomeFragmentTest {

    private val homeViewModel = HomeViewModel()
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
    fun testSections() {
        val foo = MutableLiveData<Resource<SectionsResponse>>()
        `when`(homeRepository.getSections()).thenReturn(foo)
        val observer = mock<Observer<Resource<SectionsResponse>>>()
        homeViewModel.getSectionsFromRep()!!.observeForever(observer)
        verify(observer, never()).onChanged(null)
    }

    @Test
    fun testLocations() {
        val foo = MutableLiveData<Resource<LocationsResponse>>()
        `when`(homeRepository.getLocations()).thenReturn(foo)
        val observer = mock<Observer<Resource<LocationsResponse>>>()
        homeViewModel.getLocationsFromRep()!!.observeForever(observer)
        verify(observer, never()).onChanged(null)
         }

    @Test
    fun testType() {
        val foo = MutableLiveData<Resource<TypeResponse>>()
        `when`(homeRepository.getType()).thenReturn(foo)
        val observer = mock<Observer<Resource<TypeResponse>>>()
        homeViewModel.getTypeFromRep()!!.observeForever(observer)
        verify(observer, never()).onChanged(null)
    }

    @Test
    fun testPrice() {
        val foo = MutableLiveData<Resource<PriceResponse>>()
        `when`(homeRepository.getPrice()).thenReturn(foo)
        val observer = mock<Observer<Resource<PriceResponse>>>()
        homeViewModel.getPriceFromRep()!!.observeForever(observer)
        verify(observer, never()).onChanged(null)
    }



}
