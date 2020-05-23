package vn.edu.ntu.forecastapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import vn.edu.ntu.forecastapp.R

private const val MY_PERMISSION_ACCESS_COARSE_LOCATON = 1
class MainActivity : AppCompatActivity(),KodeinAware {

    override val kodein by closestKodein()
    private val fusedLocationProviderClient:FusedLocationProviderClient by instance<FusedLocationProviderClient>()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)

        navController = this.findNavController(R.id.myNavHost)

        NavigationUI.setupActionBarWithNavController(this,navController)

        bottomnavigationView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this,navController)

        requestLocationPermission()

        if(hasLocationPermission())
            bindLocationManger()
        else
            requestLocationPermission()

    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSION_ACCESS_COARSE_LOCATON
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode  == MY_PERMISSION_ACCESS_COARSE_LOCATON){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                bindLocationManger()
            else
                Toast.makeText(this,"Please, set location manually in settings",Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindLocationManger() {
        LifeCycleBoundLocationManger(this,fusedLocationProviderClient,locationCallback)
    }
    //Kiem tra co cai quyen trong manifest khong
    private fun hasLocationPermission():Boolean{
        return ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


}
