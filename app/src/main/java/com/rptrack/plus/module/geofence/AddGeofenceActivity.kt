package com.chase.track.module.geofence

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.rptrack.plus.ApplicationActivity
import com.rptrack.plus.DataModel.deleteGeofence.DeleteGeofenceResponse
import com.rptrack.plus.DataModel.error_response.ErrorResponse
import com.rptrack.plus.DataModel.geofence.GeofenceDatum
import com.rptrack.plus.DataModel.geofence.GeofencePoints
import com.rptrack.plus.DataModel.geofence.createGeofence.CreateGeofenceReq
import com.rptrack.plus.DataModel.geofence.createGeofence.CreateGeofenceResponse
import com.rptrack.plus.R
import com.rptrack.plus.Retrofit.APIUtility
import com.rptrack.plus.module.geofence.adapter.ShapeTypeAdapter
import com.rptrack.plus.utilities.CommonUtils
import com.rptrack.plus.utilities.Constant
import com.rptrack.plus.utilities.Preferences

class AddGeofenceActivity : AppCompatActivity(), OnMapReadyCallback, ShapeTypeAdapter.SelectShape, View.OnClickListener {

    val latLngList = ArrayList<LatLng>();
    val pointsList = ArrayList<GeofencePoints>();
    var polyline_path: Polyline? = null
    var mMap: GoogleMap? = null
    val arrayList = ArrayList<Int>();
    var circleShape: Circle? = null
    var markerFirst: Marker? = null;
    var markerLast: Marker? = null;
    var apiUtility: APIUtility? = null;
    var circleRadiousValue: Int = 10;
    var seekbarLayout: LinearLayout? = null;
    var circleRadiosText: TextView? = null
    var progress: SeekBar? = null;
    private val COLOR_WHITE_ARGB = -0x1
    private val COLOR_GREEN_ARGB = -0xc771c4
    private val POLYGON_STROKE_WIDTH_PX = 8
    var datum: GeofenceDatum? = null;
    var isUpdateing: Boolean = false;
    var builder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_geofence)
        supportActionBar!!.hide()
        val backIcon = findViewById<View>(R.id.back_icon) as ImageView
        val refreshIcon = findViewById<View>(R.id.refresh_icon) as ImageView
        val textTitle = findViewById<View>(R.id.text_title) as TextView
        textTitle.text = "Create Gofence"
        refreshIcon.visibility = View.GONE
        backIcon.setOnClickListener { v: View? -> finish() }


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        apiUtility = ApplicationActivity.apiUtility;
        mapFragment?.getMapAsync(this)

        // arrayList.add(R.drawable.ic_line)
        val selectionType: RecyclerView = findViewById(R.id.shapeType)
        val btnSave: LinearLayout = findViewById(R.id.save_layout)
        circleRadiosText = findViewById(R.id.circle_radious_text);
        seekbarLayout = findViewById(R.id.seek_bar_layout);
        progress = findViewById(R.id.seek_bar)

        if (intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE) != null) {
            datum = intent.getSerializableExtra(Constant.INTENT_SERIALIZABLE) as GeofenceDatum;
            isUpdateing = true;
            Toast.makeText(this, datum!!.name, Toast.LENGTH_SHORT).show()
            Preferences.setPreference_int(this, Constant.SELECTED_SHAPE, datum!!.geomType)
            textTitle.text = datum!!.name
            arrayList.add(R.drawable.delete)
        } else {
            arrayList.add(R.drawable.ic_polygon)
            arrayList.add(R.drawable.ic_circle)
        }

        if (Preferences.getPreference_int(this, Constant.SELECTED_SHAPE) == 2) {
            seekbarLayout?.visibility = View.VISIBLE
        } else {
            seekbarLayout?.visibility = View.GONE
        }


        btnSave.setOnClickListener(this)

        /*  selectionType.apply {
              layoutManager = LinearLayoutManager(this@CreateGeofenceActivity, RecyclerView.VERTICAL, false)
              adapter = ShapeTypeAdapter(this@CreateGeofenceActivity, arrayList, this@CreateGeofenceActivity);
          }*/


        selectionType.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        val adapter = ShapeTypeAdapter(this, this)
        selectionType.adapter = adapter

        adapter.updateListData(arrayList)



        progress?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                circleRadiousValue = (progress * 40);
                circleRadiosText?.setText("Circle Radious: " + (progress * 40).toString() + "Mtr");

                if (fromUser) {
                    circleShape!!.radius = (progress * 40).toDouble()
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;
        //mMap.setBuildingsEnabled(true);
        val mapSettings = mMap!!.uiSettings
        mapSettings.isZoomControlsEnabled = true;
        CommonUtils.setMapType(googleMap, Preferences.getPreference_int(this, Constant.MAPTYPE));
        val routes = PolylineOptions().width(5f).color(Color.BLUE)
        polyline_path = googleMap.addPolyline(routes);
        googleMap.apply {
            val india = LatLng(20.5937, 78.9629)
            moveCamera(CameraUpdateFactory.newLatLng(india))
        }
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(6.0f));

        googleMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(latLng: LatLng) {
                onMapClicked(Preferences.getPreference_int(this@AddGeofenceActivity, Constant.SELECTED_SHAPE), latLng);
            }
        })


        if (isUpdateing) {
            if (datum!!.geomType == 2) {
                circleRadiosText?.setText("Circle Radious: " + datum?.radius + "Mtr");
                progress?.setProgress((datum!!.radius.toInt()) / 40)
                for ((index, value) in datum!!.points.withIndex()) {
                    val latLng = LatLng(value.lat, value.lng)
                    onMapClicked(datum!!.geomType, latLng)
                }
            } else {

                builder=LatLngBounds.Builder()
                for ((index, value) in datum!!.points.withIndex()) {

                    val latLng = LatLng(value.lat, value.lng)
                    onMapClicked(datum!!.geomType, latLng)
                    builder.include(latLng)
                }
                val bounds = builder.build()
                val padding = 50 // offset from edges of the map in pixels
                val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                mMap?.moveCamera(cu)

            }
        }

    }

    var polygon1: Polygon? = null
    private fun onMapClicked(shape: Int, latLng: LatLng) {




        when (shape) {
            1 -> {
                if (latLngList!!.size == 0) {
                    latLngList!!.add(0, latLng)
                    latLngList!!.add(1, latLng)
                }
                latLngList.add(latLngList!!.size - 1, latLng)


                polygon1?.remove()


                polygon1 = mMap?.addPolygon(PolygonOptions()
                        .clickable(true)
                        .addAll(latLngList))
                polygon1?.let { stylePolygon(it) }
                if (!isUpdateing) {
                    mMap!!.addMarker(MarkerOptions().position(latLng))

                }
                /*if (latLngList.size == 0) {
                    latLngList.add(latLng);
                    latLngList.add(latLng);
                } else {
                    latLngList.add(latLngList.size - 1, latLng)
                    polygon1?.let { stylePolygon(it) }
                    mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    mMap?.moveCamera(cu)
                }*/

                /* if (latLngList.size == 1) {
                     markerFirst = mMap!!.addMarker(MarkerOptions().position(latLngList.get(0)))
                 } else if (latLngList.size == 2) {
                     markerLast?.remove()
                     markerLast = mMap!!.addMarker(MarkerOptions().position(latLngList.get(latLngList.size - 1)))
                 } else {
                     markerFirst?.remove();
                     markerLast?.remove()
                     val polygon1 = mMap?.addPolygon(PolygonOptions()
                             .clickable(true)
                             .addAll(latLngList))
                     polygon1?.let { stylePolygon(it) }
                     // mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                     mMap?.moveCamera(cu)*/
                // }
            }

            2 -> {
                latLngList.clear()
                latLngList.add(latLng);
                if (markerFirst != null) {
                    markerFirst?.remove();
                }
                markerFirst = mMap?.addMarker(MarkerOptions().position(latLng));
                circleShape = mMap?.addCircle(drawCircle(latLng, circleShape))
                circleShape!!.radius = circleRadiousValue.toDouble()

                val cu = CameraUpdateFactory.newLatLngBounds(CommonUtils.toBounds(markerFirst?.position, circleRadiousValue.toDouble()), 50)
                mMap?.moveCamera(cu)
            }

            else -> {
                latLngList.add(latLng);
                updatePolyline(latLng)
            }
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.save_layout ->
                if (latLngList.size > 0) {
                    showSaveDialog(Preferences.getPreference_int(this, Constant.SELECTED_SHAPE), isUpdateing);
                } else {
                    CommonUtils.alert(this, "Please add a Valid Geofence")
                }
        }
    }


    private fun showSaveDialog(shape: Int, isUpdate: Boolean) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_save_shape)
        val titleMessge = dialog.findViewById(R.id.txt_message_title) as TextView
        val geofecename = dialog.findViewById(R.id.geofence_name) as EditText
        titleMessge.setText("Create Geofence")
        if (isUpdate) {
            titleMessge.setText("Update Geofence")
            geofecename.setText(datum?.name)

        }


        val yesBtn = dialog.findViewById(R.id.save_btn) as TextView
        yesBtn.setOnClickListener {
            if (isUpdate) {
                for ((index, value) in latLngList.withIndex()) {
                    val gp = GeofencePoints(value.latitude, value.longitude)
                    if (!pointsList.contains(gp)) {
                        pointsList.add(gp);
                    }
                }

                var geofenceReq = CreateGeofenceReq(datum!!.id, datum!!.customerId, shape, geofecename.text.toString(), pointsList, circleRadiousValue)
                updateAttemp(geofenceReq)
            } else {
                for ((index, value) in latLngList.withIndex()) {
                    val gp = GeofencePoints(value.latitude, value.longitude)
                    if (!pointsList.contains(gp)) {
                        pointsList.add(gp);
                    }
                }
                if (shape == 1) {
                    pointsList.add(pointsList.get(0));
                    circleRadiousValue = 0;
                }
                var geofenceReq = CreateGeofenceReq(0, 0, shape, geofecename.text.toString(), pointsList, circleRadiousValue)
                saveShapeAttemp(geofenceReq)
            }
            dialog.dismiss()
        }
        val cancelBtn = dialog.findViewById(R.id.btn_cancel) as TextView
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onShapeChangeListener(shape: Int) {
        if (isUpdateing) {
            if (shape == 1) {
                delterGeofence(datum!!.id)
            }
            return
        }


        polygon1 = null;
        if (shape != Preferences.getPreference_int(this, Constant.SELECTED_SHAPE)) {
            mMap?.clear();
            latLngList.clear();
        } else {
            val routes = PolylineOptions().width(5f).color(Color.BLUE)
            polyline_path = mMap?.addPolyline(routes);
        }
        if (shape == 2) {
            seekbarLayout?.visibility = View.VISIBLE
        } else {
            seekbarLayout?.visibility = View.GONE
        }

    }

    fun saveShapeAttemp(geofenceReq: CreateGeofenceReq) {
        apiUtility?.createGeofenceAttampt(this, true, geofenceReq, object : APIUtility.APIResponseListener<CreateGeofenceResponse?> {
            override fun onReceiveResponse(response: CreateGeofenceResponse?) {
                //CommonUtils.alert(this@AddGeofenceActivity, response?.geofenceResult?.stringData)
                finish()
            }

            override fun onErrorResponse(errorResponse: ErrorResponse?) {
                CommonUtils.alert(this@AddGeofenceActivity, errorResponse?.errorMessage?.stringMessage)

            }

            override fun onFailureResponse(string: String?) {
                CommonUtils.alert(this@AddGeofenceActivity, string)
            }

        })
    }

    fun updateAttemp(geofenceReq: CreateGeofenceReq) {
        apiUtility?.updateGeofenceAttampt(this, true, geofenceReq, object : APIUtility.APIResponseListener<CreateGeofenceResponse?> {
            override fun onReceiveResponse(response: CreateGeofenceResponse?) {
                // CommonUtils.alert(this@AddGeofenceActivity, response?.geofenceResult?.stringData)
                finish()
            }

            override fun onErrorResponse(errorResponse: ErrorResponse?) {
                CommonUtils.alert(this@AddGeofenceActivity, errorResponse?.errorMessage?.stringMessage)

            }

            override fun onFailureResponse(string: String?) {
                CommonUtils.alert(this@AddGeofenceActivity, string)
            }

        })
    }

    fun delterGeofence(geofenceId: Int) {

        var url = Constant.BASE_URL + "Geofence/" + geofenceId

        apiUtility?.deleteGeofence(this, true, url, object : APIUtility.APIResponseListener<DeleteGeofenceResponse?> {
            override fun onReceiveResponse(response: DeleteGeofenceResponse?) {
                // CommonUtils.alert(this@AddGeofenceActivity, response?.responseMessage)
                finish()
            }

            override fun onErrorResponse(errorResponse: ErrorResponse?) {
                CommonUtils.alert(this@AddGeofenceActivity, errorResponse?.errorMessage?.stringMessage)

            }

            override fun onFailureResponse(string: String?) {
                CommonUtils.alert(this@AddGeofenceActivity, string)
            }

        })
    }


    fun updatePolyline(newlatlng: LatLng) {
        val points: MutableList<LatLng> = polyline_path!!.getPoints()
        /*if (!points.contains(newlatlng)) {
            points.add(newlatlng)
        }
        if (points.size == 1) {
            markerFirst = mMap!!.addMarker(MarkerOptions().position(points.get(0)))
        }
        if (points.size > 1) {
            markerLast?.remove()
            markerLast = mMap!!.addMarker(MarkerOptions().position(points.get(points.size - 1)))
        }
        polyline_path!!.setPoints(points)*/
        val index = 0
        if (polyline_path!!.points.size == 0) {
            polyline_path!!.points.add(0, newlatlng)
            polyline_path!!.points.add(1, newlatlng)
        }
        polyline_path!!.points.add(polyline_path!!.points.size - 2, newlatlng)
    }

    private fun stylePolygon(polygon: Polygon) {
        var pattern: List<PatternItem>? = null
        var strokeColor = Color.BLACK
        var fillColor = 0x30ff0000
        polygon.strokePattern = pattern
        polygon.strokeWidth = POLYGON_STROKE_WIDTH_PX.toFloat()
        polygon.strokeColor = strokeColor
        polygon.fillColor = fillColor
    }

    fun confimationDialog() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(R.string.app_name)
        //set message for alert dialog
        builder.setMessage("Are you sure Want to save?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
        }
        //performing cancel action
        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            //  Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun drawCircle(point: LatLng, circle: Circle?): CircleOptions {
        // Instantiating CircleOptions to draw a circle around the marker
        val circleOptions = CircleOptions()
        // Specifying the center of the circle
        circleOptions.center(point)

        // Radius of the circle
        circleOptions.radius(20.0)

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK)

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000)

        // Border width of the circle
        circleOptions.strokeWidth(2f)
        // Adding the circle to the GoogleMap
        circle?.remove()
        // circle =mMap.addCircle(circleOptions)
        return circleOptions;
    }
}