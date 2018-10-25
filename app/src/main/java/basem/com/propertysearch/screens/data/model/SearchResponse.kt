package basem.com.propertysearch.screens.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



  data class SearchResponse(
          @SerializedName("listings")
          @Expose
          val resultListings: ResultListing? = null
  )






data class ResultListing (

    @SerializedName("items")
    @Expose
     val listingItems:ArrayList<ListingItem>?=null


    )

data class ListingItem
(
        @SerializedName("main_photo")
        @Expose
        val mainPhoto:MainPhoto?=null,

        @SerializedName("id")
        @Expose
        val id:Int?=null,

        @SerializedName("title")
        @Expose
        val title:String?=null,

        @SerializedName("description")
        @Expose
        val description:String?=null,

        @SerializedName("address")
        @Expose
        val address:String?=null,

        @SerializedName("price")
        @Expose
        val price:Int?=null,

        @SerializedName("center_lat")
        @Expose
        val center_lat:Double?=null,

        @SerializedName("center_lng")
        @Expose
        val center_lng:Double?=null



)

data class MainPhoto
(
        @SerializedName("file")
        @Expose
        val file:PhotoFile?=null
)

data class PhotoFile
(
        @SerializedName("thumbnails")
        @Expose
        val thumbnails:PhotoThumbnails?=null
)

data class PhotoThumbnails
(
        @SerializedName("small")
        @Expose
        val thumbSmall:String?=null
)