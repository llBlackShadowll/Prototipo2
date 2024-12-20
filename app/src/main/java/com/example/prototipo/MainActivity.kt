package com.example.prototipo

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.prototipo.databinding.ActivityMainBinding
import com.example.prototipo.databinding.ActivityLavadoVehiculosBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private var lavadoVehiculosBinding: ActivityLavadoVehiculosBinding? = null // Manejar el binding del layout de lavado de vehículos

    // Variable para rastrear el estado del layout actual
    private var currentLayout: CurrentLayout = CurrentLayout.MAIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el DrawerToggle
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar el botón Hamburger
        binding.imgHamburger.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        // Manejar las opciones del menú del Drawer
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_inicio -> {
                    // Acción para Inicio
                    showMainLayout()
                }
                R.id.menu_reserva -> {
                    // Acción para abrir la actividad de reserva
                    val intent = Intent(this, ReservaActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_salir -> {
                    finish() // Cierra la aplicación
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Configurar los botones de las opciones principales
        binding.imgLavadoVehiculos.setOnClickListener {
            showLavadoVehiculosLayout()
        }

        binding.imgServiciosEspeciales.setOnClickListener {
            showServiciosEspecialesLayout()
        }

        binding.imgMecanica.setOnClickListener {
            showMecanicaLayout()
        }

        binding.imgOtrosLavados.setOnClickListener {
            showOtrosLavadosLayout()
        }

        // Configurar el carrusel (ViewPager2)
        setupCarousel()
    }

    override fun onBackPressed() {
        // Manejar el botón "Atrás" según el layout actual
        when (currentLayout) {
            CurrentLayout.LAVADO_VEHICULOS,
            CurrentLayout.SERVICIOS_ESPECIALES,
            CurrentLayout.MECANICA,
            CurrentLayout.OTROS_LAVADOS -> {
                // Volver al layout principal
                showMainLayout()
            }
            CurrentLayout.MAIN -> {
                super.onBackPressed() // Cerrar la aplicación si estamos en el layout principal
            }
        }
    }

    private fun setupCarousel() {
        val imageList = listOf(
            R.drawable.ic_example_image1,
            R.drawable.ic_example_image2,
            R.drawable.ic_example_image3
        )
        val adapter = ImageAdapter(imageList)
        binding.viewPager.adapter = adapter

        // Configurar el comportamiento del ViewPager2
        binding.viewPager.apply {
            offscreenPageLimit = 3
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    private fun showLavadoVehiculosLayout() {
        // Infla el layout de lavado de vehículos
        lavadoVehiculosBinding = ActivityLavadoVehiculosBinding.inflate(layoutInflater)
        setContentView(lavadoVehiculosBinding!!.root)
        currentLayout = CurrentLayout.LAVADO_VEHICULOS // Actualiza el estado del layout actual

        // Configurar botones "Que incluye"
        lavadoVehiculosBinding!!.btnLavadoExpreso.setOnClickListener {
            showBottomSheetDialog(R.layout.lavado_expreso)
        }

        lavadoVehiculosBinding!!.btnLavadoEconomico.setOnClickListener {
            showBottomSheetDialog(R.layout.lavado_economico)
        }

        lavadoVehiculosBinding!!.btnLavadoEjecutivo.setOnClickListener {
            showBottomSheetDialog(R.layout.lavado_ejecutivo)
        }

        lavadoVehiculosBinding!!.btnLavadoInterior.setOnClickListener {
            showBottomSheetDialog(R.layout.lavado_interior)
        }

        lavadoVehiculosBinding!!.btnLavadoCompleto.setOnClickListener {
            showBottomSheetDialog(R.layout.lavado_completo)
        }
    }

    private fun showServiciosEspecialesLayout() {
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.activity_servicios_especiales, null)
        setContentView(view)
        currentLayout = CurrentLayout.SERVICIOS_ESPECIALES
    }

    private fun showMecanicaLayout() {
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.activity_mecanica, null)
        setContentView(view)
        currentLayout = CurrentLayout.MECANICA
    }

    private fun showOtrosLavadosLayout() {
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.activity_otros_lavados, null)
        setContentView(view)
        currentLayout = CurrentLayout.OTROS_LAVADOS
    }

    private fun showBottomSheetDialog(layoutRes: Int) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(layoutRes, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun showMainLayout() {
        // Regresa al layout principal
        setContentView(binding.root)
        lavadoVehiculosBinding = null // Limpiar el binding del otro layout
        currentLayout = CurrentLayout.MAIN
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

// Enum para rastrear el estado actual del layout
enum class CurrentLayout {
    MAIN,
    LAVADO_VEHICULOS,
    SERVICIOS_ESPECIALES,
    MECANICA,
    OTROS_LAVADOS
}