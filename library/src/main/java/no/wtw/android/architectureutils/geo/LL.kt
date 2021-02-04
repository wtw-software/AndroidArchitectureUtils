package no.wtw.android.architectureutils.geo

import java.io.Serializable
import java.lang.Math.toRadians
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class LL(val latitude: Double, val longitude: Double) : Serializable {

    companion object {
        private const val EARTH_RADIUS_IN_METERS = 6371000.0
    }

    fun distanceTo(to: LL): Int {
        val latDiff = toRadians(to.latitude - latitude)
        val lngDiff = toRadians(to.longitude - longitude)
        val a = sin(latDiff / 2) * sin(latDiff / 2) +
                cos(toRadians(latitude)) * cos(toRadians(to.latitude)) * sin(lngDiff / 2) * sin(lngDiff / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (EARTH_RADIUS_IN_METERS * c).toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is LL)
            return false
        val o: LL = other
        return o.latitude == latitude && o.longitude == longitude
    }

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        return result
    }

    override fun toString(): String {
        return "lat/lng: $latitude, $longitude"
    }

}