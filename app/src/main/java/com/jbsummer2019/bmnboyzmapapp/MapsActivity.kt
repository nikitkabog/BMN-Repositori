package com.jbsummer2019.bmnboyzmapapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerRepository
import com.google.android.gms.maps.model.Marker
import com.jbsummer2019.bmnboyzmapapp.entity.MarkerEntity
import kotlinx.android.synthetic.main.activity_marker.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    val repository = MarkerRepository()
    init {
        repository.add(MarkerEntity(LatLng(59.933270, 30.343388), "Аничков мост", "Аничков мост", R.drawable.chiz))
        repository.add(MarkerEntity(LatLng(59.932219, 30.324958), "Грифоны на Банковском мосту", "Грифоны на Банковском мосту", R.drawable.chiz))
        repository.add(MarkerEntity(LatLng(59.927701, 30.310945), "Дом Раскольникова", "Дом Раскольникова", R.drawable.chiz))
        repository.add(MarkerEntity(LatLng(59.924624, 30.303270), "Дом старухи-процентщицы", "Дом старухи-процентщицы", R.drawable.chiz))
        repository.add(MarkerEntity(LatLng(59.940021, 30.338057), "Михайловский замок", "Михайловский замок", R.drawable.chiz))
        repository.add(MarkerEntity(LatLng(59.940134, 30.328878), "Храм Спаса на Крови", "Храм Спаса на Крови", R.drawable.chiz))
        repository.add(MarkerEntity(LatLng(59.945767, 30.372960), "Таврический сад", "Таврический сад", R.drawable.chiz))
        repository.add(MarkerEntity(LatLng(59.941688, 30.338012),"Чижик-пыжик", "основа", R.drawable.chiz))
        repository.add(MarkerEntity(LatLng(59.939872, 30.314523), "Эрмитаж", "Эрмитаж", R.drawable.chiz))

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        button.setOnClickListener {
            val intent = Intent(this, menu::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val piter = LatLng(59.941688, 30.338012)
//        val a = mMap.addMarker(MarkerOptions().position(piter).title("Чижик-пыжик"))
        mMap.addAllMarkersEntites(repository.getAll())
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(piter, 13.0f))
        mMap.setOnMarkerClickListener(this)
    }


    override fun onMarkerClick(currentMarker: Marker?): Boolean {
        val results = repository.searchByTitle(currentMarker!!.title)
        results.forEach {
            val intent = Intent(this, MarkerActivity::class.java)
            intent.putExtra("position", it.position.toString())
            intent.putExtra("title", it.title)
            intent.putExtra("text", it.text)
            intent.putExtra(MarkerActivity.IMAGE_KEY, it.imageId)
            startActivity(intent)
        }
        return true
    }
}

fun GoogleMap.addAllMarkersEntites(list : ArrayList<MarkerEntity>){
    list.forEach {
        this.addMarker(MarkerOptions().position(it.position).title(it.title))
    }
}
