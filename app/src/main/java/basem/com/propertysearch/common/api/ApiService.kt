package basem.com.propertysearch.common.api


import basem.com.propertysearch.screens.data.model.*

import io.reactivex.Observable

import retrofit2.http.GET

interface ApiService {

    @get:GET("section.json")
    val sectionsResponse: Observable<SectionsResponse>

    @get:GET("locations.json")
    val loactionsResponse: Observable<LocationsResponse>

    @get:GET("property_type.json")
    val typeResponse: Observable<TypeResponse>

    @get:GET("price_filter.json")
    val priceResponse: Observable<PriceResponse>

    @get:GET("search.json")
    val searchResponse: Observable<SearchResponse>

}
