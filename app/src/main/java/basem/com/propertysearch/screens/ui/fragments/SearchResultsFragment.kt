package basem.com.propertysearch.screens.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import basem.com.propertysearch.R
import basem.com.propertysearch.common.api.Resource
import basem.com.propertysearch.common.api.Status
import basem.com.propertysearch.common.utils.ConnectionCheck
import basem.com.propertysearch.common.utils.DaggerConnectionCheckComponent
import basem.com.propertysearch.common.utils.EspressoIdlingResource
import basem.com.propertysearch.common.utils.ui_operaitons.DaggerUiOperationsComponent
import basem.com.propertysearch.common.utils.ui_operaitons.UiOperations
import basem.com.propertysearch.screens.data.model.SearchResponse
import basem.com.propertysearch.screens.ui.adapters.SearchResultsAdapter
import basem.com.propertysearch.screens.ui.view_model.SearchResultsViewModel
import kotlinx.android.synthetic.main.search_results_fragment.*


class SearchResultsFragment : Fragment() {


    lateinit var connection: ConnectionCheck
    lateinit var uiOperations: UiOperations


    private lateinit var viewModel: SearchResultsViewModel


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDagger()
    }

    private fun setUpDagger() {
        connection = DaggerConnectionCheckComponent.create().connection
        uiOperations = DaggerUiOperationsComponent.create().uiOperations

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        activity!!.title = getString(R.string.results)
        return inflater.inflate(R.layout.search_results_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(SearchResultsViewModel::class.java)
        fetchSearchResults()
    }

    private fun fetchSearchResults() {
        if (connection.isInternetAvailable(context)) {
            EspressoIdlingResource.increment()

            viewModel.getSearchResultsFromRep()!!.observe(this, Observer { response: Resource<SearchResponse>? -> processResponse(response) })
        } else {
            uiOperations.hideLoadingState(progressBar2)
            uiOperations.renderToast(context, R.string.No_Internet_Connection)
        }
    }

    private fun processResponse(response: Resource<Any>?) {
        when (response!!.status) {
            Status.LOADING -> uiOperations.renderLoadingState(progressBar2)
            Status.SUCCESS -> validateData(response.data)
            Status.ERROR -> handleErrorState(response.error)
        }
    }

    private fun validateData(data: Any?) {
        uiOperations.hideLoadingState(progressBar2)
        when (data) {
            is SearchResponse -> {
                if (data.resultListings!!.listingItems!!.isEmpty()) uiOperations.renderToast(context, R.string.unavailable_data)
                else renderSearchResults(data)
            }

        }
    }

    private fun renderSearchResults(data: SearchResponse?) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SearchResultsAdapter(data!!, this.context!!)
        recyclerView.adapter.notifyDataSetChanged()

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
    }


    private fun handleErrorState(throwable: Throwable?) {
        uiOperations.hideLoadingState(progressBar2)
        uiOperations.renderToast(context, R.string.went_wrong)
        uiOperations.logError(throwable)

    }


}




