package basem.com.propertysearch.screens.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



  data class TypeResponse(
          @SerializedName("property_types")
          @Expose
          val propertyType: ArrayList<PropertyType>? = null
  )






data class PropertyType (

    @SerializedName("id")
    @Expose
     val id:Int?=null,

    @SerializedName("title")
    @Expose
    val title:String?=null
    )

