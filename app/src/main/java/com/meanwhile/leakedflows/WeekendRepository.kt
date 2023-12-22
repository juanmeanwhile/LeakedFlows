package com.meanwhile.leakedflows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeekendRepository @Inject constructor(){

    fun getIncrementingCount(): Flow<Int> {
        return flow {
            var count = 0

            while (true) {
                emit(count)
                count++
                delay(1000)
            }
        }
    }
}
