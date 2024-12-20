package com.example.prototipo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.prototipo.databinding.ActivityReservaBinding
import java.text.SimpleDateFormat
import java.util.*

class ReservaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el Spinner de Servicios
        val servicios = resources.getStringArray(R.array.servicios)
        val serviciosAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            servicios
        )
        serviciosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerServicio.adapter = serviciosAdapter

        // Configurar el Spinner de Opciones según el Servicio
        binding.spinnerServicio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val opciones = when (position) {
                    0 -> resources.getStringArray(R.array.opciones_lavado_vehiculos)
                    1 -> resources.getStringArray(R.array.opciones_servicios_especiales)
                    2 -> resources.getStringArray(R.array.opciones_mecanica)
                    3 -> resources.getStringArray(R.array.opciones_otros_lavados)
                    else -> emptyArray()
                }
                val opcionesAdapter = ArrayAdapter(
                    this@ReservaActivity,
                    android.R.layout.simple_spinner_item,
                    opciones
                )
                opcionesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerOpciones.adapter = opcionesAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Opcional: manejar el caso en el que no se selecciona nada
            }
        }

        // Configurar Spinner de Fecha con los próximos 7 días
        val dateList = getNext7Days()
        val dateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dateList)
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFecha.adapter = dateAdapter

        // Configurar TimePicker
        binding.timePicker.setIs24HourView(true)

        // Configurar el botón de Reservar
        binding.btnReservar.setOnClickListener {
            val servicioSeleccionado = binding.spinnerServicio.selectedItem.toString()
            val opcionSeleccionada = binding.spinnerOpciones.selectedItem.toString()
            val fechaSeleccionada = binding.spinnerFecha.selectedItem.toString()
            val hora = "${binding.timePicker.hour}:${binding.timePicker.minute}"

            val mensaje = "Servicio: $servicioSeleccionado\nOpción: $opcionSeleccionada\nFecha: $fechaSeleccionada\nHora: $hora"
            android.widget.Toast.makeText(this, mensaje, android.widget.Toast.LENGTH_LONG).show()
        }
    }

    private fun getNext7Days(): List<String> {
        val dateList = mutableListOf<String>()
        val sdf = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
        val calendar = Calendar.getInstance()
        for (i in 0..6) {
            dateList.add(sdf.format(calendar.time))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return dateList
    }
}