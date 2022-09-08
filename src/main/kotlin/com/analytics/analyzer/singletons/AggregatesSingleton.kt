package com.analytics.analyzer.singletons

import com.analytics.analyzer.objects.AggregateKey
import com.analytics.analyzer.objects.AggregateValue

/**
 * This object is a singleton, TODO
 */

typealias AggregateBucket = MutableMap<AggregateKey, AggregateValue>

object AggregatesSingleton {

    private var bucket1: AggregateBucket = HashMap()

    private var bucket2: AggregateBucket = HashMap()

    private var currentBucket = 1

    @Synchronized
    fun putData(key: AggregateKey, price: Int) {
        val bucket =
            if (currentBucket == 1)
                bucket1
            else
                bucket2
        val value = bucket[key] ?: AggregateValue(0, 0)
        value.count++
        value.sumPrice += price
        bucket[key] = value
    }

    @Synchronized
    fun swapAndGetOldWriteBucket(): AggregateBucket {
        if (currentBucket == 1) {
            currentBucket = 2
            return bucket1
        } else {
            currentBucket = 1
            return bucket2
        }
    }
}
