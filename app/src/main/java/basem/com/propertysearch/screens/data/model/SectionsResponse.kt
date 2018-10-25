package basem.com.propertysearch.screens.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



  data class SectionsResponse(
          @SerializedName("sections")
          @Expose
          val sections: ArrayList<Section>? = null
  )






data class Section (

    @SerializedName("id")
    @Expose
     val id:Int?=null,

    @SerializedName("title")
    @Expose
    val title:String?=null,


    @SerializedName("main")
    @Expose
    val main:Boolean?=null,

    @SerializedName("searchable")
    @Expose
    val searchable:Boolean?=null


)
