package com.example.memoraapp.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [MemoryEntity::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (
            from = 2,
            to = 3,
            spec = RenameCategoryToCategoraSpec::class
        )
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memoryDao(): MemoryDao
}

@RenameColumn(tableName = "memories", fromColumnName = "category", toColumnName = "category")
class RenameCategoryToCategoraSpec : AutoMigrationSpec

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(sql =
            """ 
            ALTER TABLE memories 
            ADD COLUMN category TEXT NOT NULL DEFAULT ''
            """.trimIndent()
        )
    }
}
