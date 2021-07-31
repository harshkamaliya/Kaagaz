package com.example.camerax.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CameraEntity::class],version = 1)
abstract class CameraDataBase:RoomDatabase() {

    abstract fun getCameraDao():CameraDao

    companion object {

        var INSTANCE: CameraDataBase? = null

        fun getDataBase(context: Context): CameraDataBase {

            if (INSTANCE == null) {

                val builder: Builder<CameraDataBase> = Room.databaseBuilder(
                    context,
                    CameraDataBase::class.java,
                    "IMAGE_DB"
                )

                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()

                return INSTANCE!!

            } else {
                return INSTANCE!!
            }

        }

    }


}