package com.analytics.analyzer.singletons

import com.analytics.analyzer.objects.AggregateKey
import com.analytics.analyzer.objects.AggregateValue

/**
 * This object is a singleton, TODO
 */
object AggregatesArray {
    private const val AGGREGATES_ARRAY_SIZE = 3
    val aggregates: Array<MutableMap<AggregateKey, AggregateValue>> = Array(AGGREGATES_ARRAY_SIZE) { HashMap() }
    var processingBegan = false

    fun nextIndex(index: Int) = advanceIndex(index, 1)

    fun advanceIndex(index: Int, advance: Int) = (index + advance) % AGGREGATES_ARRAY_SIZE
}
