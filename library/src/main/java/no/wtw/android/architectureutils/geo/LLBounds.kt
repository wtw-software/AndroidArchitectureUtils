package no.wtw.android.architectureutils.geo

import java.io.Serializable

@Suppress("DIVISION_BY_ZERO")
data class LLBounds(val southWest: LL, val northEast: LL) : Serializable {

    class Builder {

        private var minLat = 1.0 / 0.0
        private var maxLat = -1.0 / 0.0
        private var minLng = 0.0 / 0.0
        private var maxLng = 0.0 / 0.0

        fun include(lls: List<LL>): Builder {
            lls.forEach { ll -> include(ll) }
            return this
        }

        fun include(ll: LL): Builder {
            minLat = minLat.coerceAtMost(ll.latitude)
            maxLat = maxLat.coerceAtLeast(ll.latitude)

            val lng: Double = ll.longitude
            if (java.lang.Double.isNaN(this.minLng)) {
                minLng = lng
            } else {
                if (if (minLng <= maxLng) lng in minLng..maxLng else minLng <= lng || lng <= maxLng) {
                    return this
                }
                if (a(minLng, lng) < b(maxLng, lng)) {
                    minLng = lng
                    return this
                }
            }
            this.maxLng = lng
            return this
        }

        private fun a(a: Double, lng: Double): Double {
            return (a - lng + 360.0) % 360.0
        }

        private fun b(a: Double, lng: Double): Double {
            return (lng - a + 360.0) % 360.0
        }

        fun build(): LLBounds {
            if (java.lang.Double.isNaN(minLng)) throw IllegalStateException("No points included")
            return LLBounds(LL(this.minLat, this.minLng), LL(maxLat, maxLng))
        }

    }

    fun getCenter(): LL {
        val lat: Double = (southWest.latitude + northEast.latitude) / 2.0
        val maxLng: Double = northEast.longitude
        var minLng: Double
        val lng: Double
        if (southWest.longitude.also { minLng = it } <= maxLng) {
            lng = (maxLng + minLng) / 2.0
        } else {
            lng = (maxLng + 360.0 + minLng) / 2.0
        }
        return LL(lat, lng)
    }

}