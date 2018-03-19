package com.iia.fabien.agregateur.models

/**
 * Created by fabien on 16/03/18.
 */
enum class TypeEnum(val index: Int) {

    HOTEL(0),
    AVION(1),
    VOITURE(2);
    companion object {
        fun get(index: Int): TypeEnum {
            return values().first { t -> t.index == index }
        }
    }

}