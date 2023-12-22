package com.meanwhile.leakedflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LeakingViewModel @Inject constructor(private val repository: WeekendRepository): ViewModel(), MainViewModel {

    private val _uiState = MutableStateFlow(UiState(0, 0))
    override val uiState: Flow<UiState> = _uiState

    override fun refresh() {
        viewModelScope.launch {
            repository.getIncrementingCount().collect { data ->
                val randomIdToAvoidDistinctUntilChange = Random.nextLong()
                _uiState.emit(UiState(data, randomIdToAvoidDistinctUntilChange))
            }
        }
    }
}
