package com.iia.fabien.agregateur.views.fragments

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iia.fabien.agregateur.R
import com.iia.fabien.agregateur.models.TypeEnum
import com.iia.fabien.agregateur.services.DataAggregator
import com.iia.fabien.agregateur.views.activities.ITEM_TYPE
import com.iia.fabien.agregateur.views.adapter.ReservationAdapter

/**
 * Created by fabien on 16/03/18.
 */
class ReservationListFragment : ListFragment() {

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_reservation_list, container, false)
    }

    override fun onResume() {
        super.onResume()

        val typeEnumIndex = arguments.getInt(ITEM_TYPE)

        val reservationList = DataAggregator.listReservationsByType(TypeEnum.get(typeEnumIndex))

        listView.adapter = ReservationAdapter(context, reservationList)
    }
}