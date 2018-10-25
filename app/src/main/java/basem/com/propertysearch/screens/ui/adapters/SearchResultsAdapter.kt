package basem.com.propertysearch.screens.ui.adapters

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import basem.com.propertysearch.R
import basem.com.propertysearch.screens.data.model.SearchResponse
import kotlinx.android.synthetic.main.search_list_item.view.*


class SearchResultsAdapter(private val items: SearchResponse, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private var lat: Double? = null
    private var lng: Double? = null
    private lateinit var thumbUrl: String
    private lateinit var id: String
    private lateinit var description: String
    private lateinit var titleTxt: String
    private lateinit var args: Bundle

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        assignData(holder, position)
        holder.parent.setOnClickListener { handleClick(position, it) }
    }

    private fun assignData(holder: ViewHolder, position: Int) {
        holder.title.text = items.resultListings!!.listingItems!![position].title
        holder.price.text = items.resultListings.listingItems!![position].price.toString()
        holder.address.text = items.resultListings.listingItems[position].address
    }

    private fun handleClick(position: Int, it: View) {
        extractCellDataOnClick(position)
        transferData(lat, lng, thumbUrl, id, titleTxt, description, it)
    }

    private fun extractCellDataOnClick(position: Int) {
        lat = items.resultListings!!.listingItems!![position].center_lat
        lng = items.resultListings.listingItems!![position].center_lng
        thumbUrl = items.resultListings.listingItems[position].mainPhoto!!.file!!.thumbnails!!.thumbSmall!!
        id = items.resultListings.listingItems[position].id.toString()
        titleTxt = items.resultListings.listingItems[position].title!!
        description = items.resultListings.listingItems[position].description!!
    }

    private fun transferData(lat: Double?, lng: Double?, thumbUrl: String, id: String, titleTxt: String, description: String, view: View) {
        args = Bundle()
        args.putDouble("lat", lat!!)
        args.putDouble("lng", lng!!)
        args.putString("thumbUrl", thumbUrl)
        args.putString("id", id)
        args.putString("title", titleTxt)
        args.putString("description", description)
        Navigation.findNavController(view).navigate(R.id.next_action, args)
    }

    override fun getItemCount(): Int {
        return items.resultListings!!.listingItems!!.size
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.title
    val price: TextView = view.price
    val address: TextView = view.address
    val parent: CardView = view.cardView
}

