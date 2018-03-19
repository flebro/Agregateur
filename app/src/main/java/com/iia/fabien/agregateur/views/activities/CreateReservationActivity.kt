package com.iia.fabien.agregateur.views.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import com.iia.fabien.agregateur.R
import com.iia.fabien.agregateur.models.Reservable
import com.iia.fabien.agregateur.models.TypeEnum
import com.iia.fabien.agregateur.services.DataAggregator
import kotlinx.android.synthetic.main.activity_create_reservation.*
import kotlinx.android.synthetic.main.content_create_reservation.*
import java.util.*


class CreateReservationActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reservation)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showDateDebutPicker(view: View) {
        showDatePicker(dateDebutResaDisplay)
    }

    fun showDateFinPicker(view: View) {
        showDatePicker(dateFinResaDisplay)
    }

    fun showDatePicker(displayView: TextView) {
        val texte = displayView.text
        val date = Calendar.getInstance()
        if (texte.isNotEmpty()) {
            date.time = DATE_DISPLAY_FORMAT.parse(texte.toString())
        }
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val day = date.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, DatePickerListener(this, displayView), year, month, day).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val cal = GregorianCalendar(year, month, day)
        findViewById<TextView>(R.id.dateDebutResaDisplay).apply {
            text = DATE_DISPLAY_FORMAT.format(cal.time)
        }
    }

    fun onDateChange() {
        if (dateDebutResaDisplay.text.isNotEmpty()) {
            if (dateFinResaDisplay.text.isNullOrBlank()) {
                dateFinResaDisplay.text = dateDebutResaDisplay.text
            }
            if (dateFinResaDisplay.text.isNotEmpty()) {
                val reservables = DataAggregator.listObjetsDisponibles(TypeEnum.get(intent.getIntExtra(ITEM_TYPE, 0)),
                        DATE_DISPLAY_FORMAT.parse(dateDebutResaDisplay.text.toString()),
                        DATE_DISPLAY_FORMAT.parse(dateFinResaDisplay.text.toString()))

                val adapter = ArrayAdapter<Reservable>(
                        this, android.R.layout.simple_spinner_item, reservables)
                        .apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

                reservableSpinner.adapter = adapter
            }
        }
    }

    fun onReserver(view: View) {
        val selection = reservableSpinner.selectedItem as Reservable
        val dateDebut = DATE_DISPLAY_FORMAT.parse(dateDebutResaDisplay.text.toString())
        val dateFin = DATE_DISPLAY_FORMAT.parse(dateFinResaDisplay.text.toString())
        DataAggregator.createReservation(selection, dateDebut, dateFin)
        finish()
    }

    inner class DatePickerListener(context: CreateReservationActivity, displayView: TextView) : DatePickerDialog.OnDateSetListener {
        val parent = context
        val view = displayView
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            val cal = GregorianCalendar(year, month, day)
            view.text = DATE_DISPLAY_FORMAT.format(cal.time)
            parent.onDateChange()
        }
    }

}
