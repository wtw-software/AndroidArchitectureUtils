package no.wtw.android.architectureutils.geo

import java.io.Serializable

data class LL(val latitude: Double, val longitude: Double) : Serializable {

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