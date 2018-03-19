package com.iia.fabien.agregateur.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.iia.fabien.agregateur.R
import com.iia.fabien.agregateur.models.Reservation
import com.iia.fabien.agregateur.views.activities.DATE_DISPLAY_FORMAT
import java.text.SimpleDateFormat

/**
 * Created by fabien on 16/03/18.
 */
class ReservationAdapter(context: Context, reservations: List<Reservation>)
    : ArrayAdapter<Reservation>(context, 0, reservations) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val viewHolder: ReservationViewHolder

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.reservation_list_item,parent, false)
            viewHolder = ReservationViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ReservationViewHolder
        }

        getItem(position).let { resa ->
            viewHolder.description.text = resa.reservable.description
            viewHolder.dateDebut.text = DATE_DISPLAY_FORMAT.format(resa.dateDebut)
            viewHolder.dateFin.text = DATE_DISPLAY_FORMAT.format(resa.dateFin)
        }

        return view
    }


    private class ReservationViewHolder(view: View) {
        val description = view.findViewById<TextView>(R.id.descResa)
        val dateDebut = view.findViewById<TextView>(R.id.dateDebutResa)
        val dateFin =  view.findViewById<TextView>(R.id.dateFinResa)
    }

}