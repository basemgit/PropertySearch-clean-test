package basem.com.propertysearch.screens.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



  data class LocationsResponse(
          @SerializedName("locations")
          @Expose
          val locationCity: ArrayList<Locations>? = null
  )






data class Locations (

    @SerializedName("id")
    @Expose
     val id:Int?=null,

    @SerializedName("title")
    @Expose
    val title:String?=null,


    @SerializedName("searchable")
    @Expose
    val searchable:Boolean?=null,


    @SerializedName("children")
    @Expose
    val children:ArrayList<Area>?=null

)

data class Area
(
        @SerializedName("id")
        @Expose
        val id:Int?=null,

        @SerializedName("title")
        @Expose
        val title:String?=null,


        @SerializedName("searchable")
        @Expose
        val searchable:Boolean?=null
)
