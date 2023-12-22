package com.meanwhile.leakedflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FixedViewModel @Inject constructor(private val repository: WeekendRepository): ViewModel(), MainViewModel {

    private val trigger = MutableSharedFlow<Unit>(replay = 1)
    override val uiState: Flow<UiState> = trigger.flatMapLatest {
        repository.getIncrementingCount().map { data ->
            val randomIdToAvoidDistinctUntilChange = Random.nextLong()
            UiState(data, randomIdToAvoidDistinctUntilChange)
        }
    }

    override fun refresh() {
        viewModelScope.launch {
            trigger.emit(Unit)
        }
    }
}
