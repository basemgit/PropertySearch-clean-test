package basem.com.propertysearch.screens.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.Navigation.findNavController
import basem.com.propertysearch.R
import basem.com.propertysearch.common.api.Resource
import basem.com.propertysearch.common.api.Status
import basem.com.propertysearch.common.utils.ConnectionCheck
import basem.com.propertysearch.common.utils.DaggerConnectionCheckComponent
import basem.com.propertysearch.common.utils.EspressoIdlingResource
import basem.com.propertysearch.common.utils.ui_operaitons.DaggerUiOperationsComponent
import basem.com.propertysearch.common.utils.ui_operaitons.UiOperations
import basem.com.propertysearch.screens.data.model.*
import basem.com.propertysearch.screens.ui.view_model.HomeViewModel
import basem.com.propertysearch.testing.OpenForTesting
import kotlinx.android.synthetic.main.main_fragment.*


@Suppress("DEPRECATION")
@OpenForTesting
class HomeFragment : Fragment() {


    lateinit var connection: ConnectionCheck
    lateinit var uiOperations: UiOperations
    private var spinCityAdapter: ArrayAdapter<String>? = null
    private var spinAreaAdapter: ArrayAdapter<String>? = null
    private var spinTypeAdapter: ArrayAdapter<String>? = null
    private var spinPriceAdapter: ArrayAdapter<String>? = null
    private var locationsList: ArrayList<Locations>? = null
    private var cityList: ArrayList<String>? = null
    private var areaList: ArrayList<String>? = null
    private var typeList: ArrayList<String>? = null
    private var priceList: ArrayList<String>? = null

    private var cityId: Int = 0

    private lateinit var viewModel: HomeViewModel


    companion object {
        var cityString: String = ""
        var areaString: String = ""
        var typeString: String = ""
        const val userSectionSaleId = 1
        const val userSectionRentId = 2
        var userSectionId: Int = userSectionSaleId
        fun isValidSearchCriteria(): Boolean {
            return cityString.isNotEmpty() && areaString.isNotEmpty() && typeString.isNotEmpty() && userSectionId > 0
        }

    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDagger()

    }

    private fun setUpDagger() {
        connection = DaggerConnectionCheckComponent.create().connection
        uiOperations = DaggerUiOperationsComponent.create().uiOperations
    }

    val isRunningTest: Boolean by lazy {
        try {
            Class.forName("android.support.test.espresso.Espresso")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        activity!!.title = getString(R.string.home)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEspressoTesting()
        init()
    }

    fun setUpEspressoTesting() {
        if (isRunningTest) {
            forRentBtn.isEnabled = true
            forSaleBtn.isEnabled = true
            uiOperations.hideLoadingState(progressBar)
        }

    }

    private fun init() {
        locationsList = ArrayList()
        cityList = ArrayList()
        areaList = ArrayList()
        typeList = ArrayList()
        priceList = ArrayList()
        setUpSpinners()
        handleButtons()
    }

    private fun setUpSpinners() {
        setUpCitySpinner()
        setUpAreaSpinner()
        setUpTypeSpinner()
        setUpPriceSpinner()
    }

    private fun setUpCitySpinner() {
        spinCityAdapter = ArrayAdapter(context, R.layout.spinner_item, cityList)
        spinCityAdapter!!.setDropDownViewResource(R.layout.spinner_item)
        spinnerCity!!.adapter = spinCityAdapter
        spinnerCity!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                cityString = parent.getItemAtPosition(position).toString()
                cityId = parent.getItemIdAtPosition(position).toInt()
                fetchLocations()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                cityString = ""
                fetchLocations()
            }
        }
    }

    private fun setUpAreaSpinner() {
        spinAreaAdapter = ArrayAdapter(context, R.layout.spinner_item, areaList)
        spinAreaAdapter!!.setDropDownViewResource(R.layout.spinner_item)
        spinnerArea!!.adapter = spinAreaAdapter
        spinnerArea!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                areaString = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                areaString = ""
            }
        }

    }

    private fun setUpTypeSpinner() {
        spinTypeAdapter = ArrayAdapter(context, R.layout.spinner_item, typeList)
        spinTypeAdapter!!.setDropDownViewResource(R.layout.spinner_item)
        spinnerType.adapter = spinTypeAdapter
        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                typeString = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                typeString = ""
            }
        }
    }

    private fun setUpPriceSpinner() {
        spinPriceAdapter = ArrayAdapter(context, R.layout.spinner_item, priceList)
        spinPriceAdapter!!.setDropDownViewResource(R.layout.spinner_item)
        spinnerMaxPrice.adapter = spinPriceAdapter
        spinnerMinPrice.adapter = spinPriceAdapter
    }

    private fun handleButtons() {
        handleForSaleBtn()
        handleForRentBtn()
        handleSearchBtn()
    }

    private fun handleForSaleBtn() {
        forSaleBtn.setOnClickListener {
            uiOperations.changeBtnsColor(forSaleBtn, forRentBtn, context)
            forSaleBtn.isEnabled = false
            forRentBtn.isEnabled = true
            userSectionId = userSectionSaleId
            fetchPrice()
        }
    }

    private fun handleForRentBtn() {
        forRentBtn.setOnClickListener {
            uiOperations.changeBtnsColor(forRentBtn, forSaleBtn, context)
            forRentBtn.isEnabled = false
            forSaleBtn.isEnabled = true
            userSectionId = userSectionRentId
            fetchPrice()
        }
    }

    private fun handleSearchBtn() {
        searchBtn?.setOnClickListener {
            if (isValidSearchCriteria()) {
                findNavController(it).navigate(R.id.next_action, null)
            } else {
                uiOperations.renderToast(context, R.string.Please_select_city)
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        fetchData()
    }

    private fun fetchData() {
        fetchSection()
        fetchLocations()
        fetchType()
        fetchPrice()

    }


    private fun fetchSection() {
        if (connection.isInternetAvailable(context)) {
            EspressoIdlingResource.increment()// App is busy until further notice

            viewModel.getSectionsFromRep()!!.observe(this, Observer { response: Resource<SectionsResponse>? -> processResponse(response) })
        } else {
            uiOperations.hideLoadingState(progressBar)
            uiOperations.renderToast(context, R.string.No_Internet_Connection)
        }
    }

    private fun fetchLocations() {
        if (connection.isInternetAvailable(context)) {
            EspressoIdlingResource.increment()
            viewModel.getLocationsFromRep()!!.observe(this, Observer { response: Resource<LocationsResponse>? -> processResponse(response) })
        } else {
            uiOperations.hideLoadingState(progressBar)
            uiOperations.renderToast(context, R.string.No_Internet_Connection)
        }
    }

    private fun fetchType() {
        if (connection.isInternetAvailable(context)) {
            EspressoIdlingResource.increment()
            viewModel.getTypeFromRep()!!.observe(this, Observer { response: Resource<TypeResponse>? -> processResponse(response) })
        } else {
            uiOperations.hideLoadingState(progressBar)
            uiOperations.renderToast(context, R.string.No_Internet_Connection)
        }
    }

    private fun fetchPrice() {
        if (connection.isInternetAvailable(context)) {
            EspressoIdlingResource.increment()
            viewModel.getPriceFromRep()!!.observe(this, Observer { response: Resource<PriceResponse>? -> processResponse(response) })

        } else {
            uiOperations.hideLoadingState(progressBar)
            uiOperations.renderToast(context, R.string.No_Internet_Connection)
        }

    }

    private fun processResponse(response: Resource<Any>?) {
        when (response!!.status) {
            Status.LOADING -> uiOperations.renderLoadingState(progressBar)
            Status.SUCCESS -> validateData(response.data)
            Status.ERROR -> handleErrorState(response.error)
        }
    }


    private fun validateData(data: Any?) {
        uiOperations.hideLoadingState(progressBar)

        when (data) {
            is SectionsResponse -> {
                if (data.sections!!.isEmpty()) uiOperations.renderToast(context, R.string.unavailable_data)
                else renderSections(data.sections)
            }
            is LocationsResponse -> {
                if (data.locationCity!!.isEmpty()) uiOperations.renderToast(context, R.string.unavailable_data)
                else renderCities(data.locationCity)

            }
            is TypeResponse -> {
                if (data.propertyType!!.isEmpty()) uiOperations.renderToast(context, R.string.unavailable_data)
                else renderType(data.propertyType)
            }
            is PriceResponse -> {
                if (data.priceFilters!!.isEmpty()) uiOperations.renderToast(context, R.string.unavailable_data)
                else renderPrice(data.priceFilters)


            }


        }


    }

    private fun handleErrorState(throwable: Throwable?) {
        uiOperations.hideLoadingState(progressBar)
        uiOperations.renderToast(context, R.string.went_wrong)
        uiOperations.logError(throwable)

    }

    private fun renderSections(data: ArrayList<Section>?) {
        for (i in 0 until data!!.size - 1) {
            userSectionId = if (isForSaleAndMain(data[i])) {
                uiOperations.changeBtnsColor(forSaleBtn, forRentBtn, context)
                userSectionSaleId
            } else {
                uiOperations.changeBtnsColor(forRentBtn, forSaleBtn, context)
                userSectionRentId
            }
        }
        forSaleBtn.isEnabled = true
        forRentBtn.isEnabled = true
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()// Set app as idle.
        }
    }

    private fun isForSaleAndMain(section: Section): Boolean {
        return section.title.equals("For Sale") && section.main == true
    }

    private fun renderCities(data: ArrayList<Locations>?) {
        cityList!!.clear()
        locationsList!!.clear()
        data?.let { locationsList!!.addAll(it) }
        for (i in 0 until locationsList!!.size - 1) {
            if (isCitySearchable(locationsList!![i])) {
                cityList!!.add(locationsList!![i].title.toString())
            }
        }
        spinCityAdapter!!.notifyDataSetChanged()
        validateAreas(locationsList!![cityId].children)

    }

    private fun isCitySearchable(city: Locations): Boolean {
        return city.searchable == true
    }

    private fun validateAreas(areas: ArrayList<Area>?) {
        when (areas!!.isEmpty()) {
            true -> handleEmptyArea()
            false -> renderAreas(areas)
        }
    }

    private fun handleEmptyArea() {
        uiOperations.renderToast(context, R.string.unavailable_areas)
        areaList!!.clear()
        spinAreaAdapter!!.notifyDataSetChanged()
    }


    private fun renderAreas(areas: ArrayList<Area>?) {
        areaList!!.clear()
        for (i in 0 until areas!!.size - 1) {
            areaList!!.add(areas[i].title.toString())
        }
        spinAreaAdapter!!.notifyDataSetChanged()
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }

    }

    private fun renderType(types: ArrayList<PropertyType>?) {
        typeList!!.clear()
        for (i in 0 until types!!.size - 1) {
            typeList!!.add(types[i].title.toString())
        }
        spinTypeAdapter!!.notifyDataSetChanged()
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()// Set app as idle.
        }
    }


    private fun renderPrice(prices: ArrayList<PriceFilters>) {
        priceList!!.clear()
        for (i in 0 until prices.size - 1) {
            when (userSectionId) {
                userSectionSaleId -> renderSalePrice(prices[i])
                userSectionRentId -> renderRentPrice(prices[i])
            }
        }
        spinPriceAdapter!!.notifyDataSetChanged()
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
    }

    private fun renderSalePrice(price: PriceFilters) {
        if (price.section!!.id == userSectionSaleId) {
            priceList!!.add(price.value.toString())
        }

    }

    private fun renderRentPrice(price: PriceFilters) {
        if (price.section!!.id == userSectionRentId) {
            priceList!!.add(price.value.toString())
        }
    }


}
