package br.com.zemaromba.data.repository

import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.sources.local.database.dao.SetDao
import br.com.zemaromba.domain.model.Set
import br.com.zemaromba.domain.repository.SetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class SetRepositoryImpl(
    private val setDao: SetDao,
    private val exerciseDao: ExerciseDao
) : SetRepository {

    override fun getSetsByTrainingId(trainingId: Long): Flow<List<Set>> = callbackFlow {

        setDao
            .getSetsWithExerciseByTrainingId(trainingId = trainingId)
            .collectLatest { setsWithExercises ->
                val sets = setsWithExercises.map { setWithExercise ->
                    val exercise =
                        exerciseDao
                            .getExerciseWithMuscleGroups(exerciseId = setWithExercise.exercise.id)
                            .map { exerciseAndMusclesMap ->
                                exerciseAndMusclesMap
                                    .key
                                    .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
                            }.first()
                    setWithExercise.set.toSet(exercise)
                }
                trySend(sets)
            }
    }

    override suspend fun completeSet(setId: Long, isCompleted: Boolean) {
        setDao.completeSet(
            setId = setId,
            completed = !isCompleted
        )
    }
}