package com.analytics.analyzer.singletons

import com.analytics.analyzer.objects.AggregateKey
import com.analytics.analyzer.objects.AggregateValue

/**
 * This object is a singleton, TODO
 */

typealias AggregateBucket = MutableMap<AggregateKey, AggregateValue>

object AggregatesSingleton {

    private const val AGGREGATES_ARRAY_SIZE = 10

    val buckets: Array<AggregateBucket> = Array(AGGREGATES_ARRAY_SIZE) { HashMap() }

    @Volatile
    var firstDataBucketBeginTime: Long = 0

    @Volatile
    var timestampSecondsMax: Long = 0

    @Volatile
    var processingBegan = false

    fun nextIndex(index: Int) = advanceIndex(index, 1)

    fun advanceIndex(index: Int, advance: Int) = (index + advance) % AGGREGATES_ARRAY_SIZE
}
