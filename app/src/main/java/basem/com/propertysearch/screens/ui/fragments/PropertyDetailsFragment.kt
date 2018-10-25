package basem.com.propertysearch.screens.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import basem.com.propertysearch.R
import basem.com.propertysearch.common.utils.EspressoIdlingResource
import basem.com.propertysearch.common.utils.GlideApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.property_details_fragment.*
import kotlinx.android.synthetic.main.property_details_fragment.view.*

class PropertyDetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var v: View
    private var lat: Double? = null
    private var lng: Double? = null
    private lateinit var imgUrl: String
    private lateinit var id: String
    private lateinit var title: String
    private lateinit var description: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.property_details_fragment, container, false)
        activity!!.title = getString(R.string.details)
        setUpMap(savedInstanceState)
        return v
    }

    private fun setUpMap(savedInstanceState: Bundle?) {
        v.propertyMapView.onCreate(savedInstanceState)
        v.propertyMapView.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        v.propertyMapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        EspressoIdlingResource.increment()
        assignData()
        v.propertyMapView.onStart()
    }

    private fun assignData() {
        val args = arguments
        if (args != null) {
            lat = args.getDouble("lat")
            lng = args.getDouble("lng")
            imgUrl = args.getString("thumbUrl")
            id = args.getString("id")
            title = args.getString("title")
            description = args.getString("description")
            titleValue.text = title
            idValue.text = id
            descriptionValue.text = description
            // use this  fake image url , because images from json (not found 404)
            //val fakeImgUrl = "https://picsum.photos/200/300/?random"
            GlideApp.with(this)
                    .load(imgUrl)
                    .placeholder(R.drawable.office)
                    .fitCenter()
                    .into(v.photo)

            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        v.propertyMapView.onStop()
    }

    override fun onPause() {
        v.propertyMapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        v.propertyMapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        v.propertyMapView.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        renderOutdoorUi(map)
        renderIndoorUi(map)
    }

    private fun renderOutdoorUi(map: GoogleMap) {
        if (lat != null && lng != null) {
            val pos = LatLng(lat!!, lng!!)
            val markerOptions = MarkerOptions()
            markerOptions.position(pos)
            map.addMarker(markerOptions)
            map.moveCamera(CameraUpdateFactory.newLatLng(pos))
            map.setMinZoomPreference(12f)
        }
    }

    private fun renderIndoorUi(map: GoogleMap) {
        map.isIndoorEnabled = true
        val uiSettings = map.uiSettings
        uiSettings.isIndoorLevelPickerEnabled = true
    }

}