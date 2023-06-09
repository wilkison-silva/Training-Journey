package br.com.zemaromba.core_data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.MuscleGroupDao
import br.com.zemaromba.core_data.local.database.dao.SetDao
import br.com.zemaromba.core_data.local.database.dao.TrainingDao
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.MuscleGroupEntity
import br.com.zemaromba.core_data.model.SetEntity
import br.com.zemaromba.core_data.model.TrainingEntity
import br.com.zemaromba.core_data.model.TrainingPlanEntity
import br.com.zemaromba.core_data.model.cross_references.ExerciseAndMuscleGroupCrossRefEntity

@Database(
    entities = [
        ExerciseEntity::class,
        MuscleGroupEntity::class,
        SetEntity::class,
        TrainingEntity::class,
        ExerciseAndMuscleGroupCrossRefEntity::class,
        TrainingPlanEntity::class
    ],
    version = AppDatabase.databaseVersion,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val exerciseDao: ExerciseDao
    abstract val muscleGroupDao: MuscleGroupDao
    abstract val exerciseAndMuscleDao: ExerciseAndMuscleDao
    abstract val setDao: SetDao
    abstract val trainingDao: TrainingDao
    abstract val trainingPlanDao: TrainingPlanDao

    companion object {
        const val databaseVersion = 1
    }

}