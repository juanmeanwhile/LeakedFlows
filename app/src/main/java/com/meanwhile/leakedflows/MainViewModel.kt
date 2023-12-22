package com.meanwhile.leakedflows

import kotlinx.coroutines.flow.Flow

/**
 * Interface to abstract LeakedViewModel and FixedViewModel. It does not have any benefit outside of this sample
 */
interface MainViewModel {

    fun refresh()

    val uiState: Flow<UiState>
}
