package com.albertogfv.runningapp.repositories

import com.albertogfv.runningapp.db.Run
import com.albertogfv.runningapp.db.RunDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao: RunDao
    ){
        suspend fun insertRun(run: Run) = runDao.insertRun(run)

        suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

        fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

        fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()

        fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()

        fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()

        fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

        fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

        fun getTotalDistance() = runDao.getTotalDistance()

        fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

        fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()

    }