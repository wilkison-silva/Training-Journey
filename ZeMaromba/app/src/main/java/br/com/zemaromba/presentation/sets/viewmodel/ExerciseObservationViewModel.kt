package br.com.zemaromba.presentation.sets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.domain.repository.SetRepository
import br.com.zemaromba.presentation.model.ExerciseView
import br.com.zemaromba.presentation.sets.screen.state.ExerciseObservationScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExerciseObservationViewModel @Inject constructor(
    private val setRepository: SetRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseObservationScreenState())
    val state = _state.asStateFlow()

    fun updateObservationValue(value: String) {
        _state.update {
            it.copy(
                observation = value
            )
        }
    }

    fun createSet(
        selectedExercise: ExerciseView,
        trainingId: Long,
        series: String,
        repetitions: String,
        weight: String,
        restingTime: String
    ) {
        viewModelScope.launch {
            setRepository.createSet(
                id = 0,
                exerciseId = selectedExercise.id,
                trainingId = trainingId,
                quantity = series.toInt(),
                repetitions = repetitions.toInt(),
                weight = weight.toInt(),
                observation = _state.value.observation,
                completed = false,
                restingTime = restingTime.toInt()
            )
            _state.update {
                it.copy(navigateBack = true)
            }
        }
    }
}