package com.iia.fabien.agregateur.models

import java.util.*

/**
 * Created by fabien on 19/03/18.
 */
class Reservable(val id: Int,
                 val description: String,
                 val type: TypeEnum,
                 val source: UUID) {
    override fun toString(): String {
        return description
    }
}