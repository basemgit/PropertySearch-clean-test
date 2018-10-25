package basem.com.propertysearch.screens.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



  data class PriceResponse(
          @SerializedName("price_filters")
          @Expose
          val priceFilters: ArrayList<PriceFilters>? = null
  )






data class PriceFilters (

    @SerializedName("value")
    @Expose
     val value:Int?=null,

    @SerializedName("section")
    @Expose
    val section:PriceSection?=null

    )

data class PriceSection (

        @SerializedName("id")
        @Expose
        val id:Int?=null
)
