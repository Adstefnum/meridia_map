package com.example.meridia_map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.meridia_map.ui.theme.Meridia_mapTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.android.libraries.maps.model.PolylineOptions
import com.google.android.libraries.maps.model.*
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material.Surface as Surface1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Meridia_mapTheme {
                // A surface container using the 'background' color from the theme

                Surface1(color = MaterialTheme.colors.background) {
                    GoogleMaps()
                }
            }
        }
    }
}
@Composable
fun GoogleMaps() {

    var mapView = rememberMapViewWithLifeCycle()

    Box(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AndroidView(
                {mapView}
            ) { mapView ->
                CoroutineScope(Dispatchers.Main).launch {

                val map = mapView.awaitMap()
                    
                        //map.mapType=1
                        map.uiSettings.isZoomControlsEnabled = true
                      val mark1 = LatLng(17.385, 78.4867) //Hyderabad
                            val mark2 = LatLng(18.5204, 73.8567) //Hyderabad

                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mark1, 12f))
                            val markerOptions =  MarkerOptions()
                                .title("Hyderabad")
                                .position(mark1)
                            map.addMarker(markerOptions)

                            val markerOptions2 =  MarkerOptions()
                                .title("Pune")
                                .position(mark2)
                            map.addMarker( markerOptions2 )

                            map.addPolyline(
                                PolylineOptions().add(
                                    mark1,
                                    LatLng(28.5204, 53.8567),
                                    LatLng(30.5204, 66.8567),
                                    mark2,

                                    )

                                ).color = R.color.purple_500


                }
            }
        }

    }

}


@Composable
fun rememberMapViewWithLifeCycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = com.google.maps.android.ktx.R.id.map_frame
        }
    }
    val lifeCycleObserver = rememberMapLifecycleObserver(mapView)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifeCycle,mapView) {
        lifeCycle.addObserver(lifeCycleObserver)
        onDispose {
            lifeCycle.removeObserver(lifeCycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }