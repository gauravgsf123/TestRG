package com.gaurav.testrg.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.gaurav.testrg.model.Task
import com.gaurav.testrg.model.Users

/**
 * Created by jonesrandom on 11/14/17.
 *
 * #JanganLupaBahagia
 *
 */
class DatabaseHelper(ctx: Context) : SQLiteOpenHelper(
    ctx,
    DatabaseConstan.DATABASE_NAME,
    null,
    DatabaseConstan.DATABASE_VERSION
) {

    companion object {
        private lateinit var INSTANCE: DatabaseHelper
        private lateinit var database: SQLiteDatabase
        private var databaseOpen: Boolean = false

        fun closeDatabase() {
            if (database.isOpen && databaseOpen) {
                database.close()
                databaseOpen = false

                Log.i("Database", "Database close")
            }
        }

        fun initDatabaseInstance(ctx: Context): DatabaseHelper {
            INSTANCE = DatabaseHelper(ctx)
            return INSTANCE
        }



        //***********************************Task database*********************************

        fun insertUserData(user: Users): Long {

            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database", "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseConstan.ROW_NAMA, user.nama)
            values.put(DatabaseConstan.ROW_EMAIL, user.email)
            values.put(DatabaseConstan.ROW_PASSWORD, user.password)
            return database.insert(DatabaseConstan.DATABASE_TABEL_USER, null, values)
        }

        fun updateUserData(user: Users): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database", "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseConstan.ROW_NAMA, user.nama)
            values.put(DatabaseConstan.ROW_EMAIL, user.email)
            values.put(DatabaseConstan.ROW_PASSWORD, user.password)
            return database.update(
                DatabaseConstan.DATABASE_TABEL_USER,
                values,
                "${DatabaseConstan.ROW_ID} = ${user.id}",
                null
            )
        }

        fun getUserData(email: String):Users{
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
            }
            val user = Users()
            val selectQuery = "select * from "+DatabaseConstan.DATABASE_TABEL_USER +" where "+DatabaseConstan.ROW_EMAIL+" = ?"
            val cursor = database.rawQuery(
                selectQuery, arrayOf(email))
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {
                        user.id = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_ID))
                        user.nama = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_NAMA))
                        user.email = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_EMAIL))
                        user.password = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_PASSWORD))

                    } while (cursor.moveToNext())
                }
            }
            return user
        }

        fun getUserData(email: String, password: String):Users{
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
            }
            val user = Users()
            val selectQuery = "select * from "+DatabaseConstan.DATABASE_TABEL_USER +" where "+DatabaseConstan.ROW_EMAIL+" = ? and "+DatabaseConstan.ROW_PASSWORD+" = ? "
            val cursor = database.rawQuery(
                selectQuery, arrayOf(email,password))
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {
                        user.id = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_ID))
                        user.nama = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_NAMA))
                        user.email = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_EMAIL))
                        user.password = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_PASSWORD))

                    } while (cursor.moveToNext())
                }
            }
            return user
        }

        fun getUserAllData(): MutableList<Users> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
            }

            val data: MutableList<Users> = ArrayList()
            val cursor = database.rawQuery(
                "SELECT * FROM ${DatabaseConstan.DATABASE_TABEL_USER}",
                null
            )
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {

                        val user = Users()
                        user.id = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_ID))
                        user.nama = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_NAMA))
                        user.email = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_EMAIL))
                        user.password = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_PASSWORD))
                        data.add(user)

                    } while (cursor.moveToNext())
                }
            }
            return data
        }

        fun deleteUserData(id: Int): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database", "Database Open")
            }
            return database.delete(
                DatabaseConstan.DATABASE_TABEL_USER,
                "${DatabaseConstan.ROW_ID} = $id",
                null
            )
        }



        //***********************************Task database*********************************

        fun insertTaskData(task: Task): Long {

            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
            }

            val values = ContentValues()
            values.put(DatabaseConstan.ROW_TITLE, task.title)
            values.put(DatabaseConstan.ROW_DESCRIPTION, task.description)
            return database.insert(DatabaseConstan.DATABASE_TABEL_TASK, null, values)
        }

        fun updateTaskData(task: Task): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database", "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseConstan.ROW_TITLE, task.title)
            values.put(DatabaseConstan.ROW_DESCRIPTION, task.description)
            return database.update(
                DatabaseConstan.DATABASE_TABEL_TASK,
                values,
                "${DatabaseConstan.ROW_ID} = ${task.id}",
                null
            )
        }

        fun getTaskData(title: String):Task{
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
            }
            val task = Task()
            val selectQuery = "select * from "+DatabaseConstan.DATABASE_TABEL_TASK +" where "+DatabaseConstan.ROW_TITLE+" = ?"
            val cursor = database.rawQuery(
                selectQuery, arrayOf(title))
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {
                        task.id = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_ID))
                        task.title = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_TITLE))
                        task.description = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_DESCRIPTION))

                    } while (cursor.moveToNext())
                }
            }
            return task
        }



        fun getTaskAllData(): MutableList<Task> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
            }

            val data: MutableList<Task> = ArrayList()
            val cursor = database.rawQuery(
                "SELECT * FROM ${DatabaseConstan.DATABASE_TABEL_TASK}",
                null
            )
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {

                        val task = Task()
                        task.id = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_ID))
                        task.title = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_TITLE))
                        task.description = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_DESCRIPTION))

                        data.add(task)

                    } while (cursor.moveToNext())
                }
            }
            return data
        }

        fun deleteTaskData(id: Int): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database", "Database Open")
            }
            return database.delete(
                DatabaseConstan.DATABASE_TABEL_TASK,
                "${DatabaseConstan.ROW_ID} = $id",
                null
            )
        }

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(DatabaseConstan.QUERY_CREATE_USER)
        p0?.execSQL(DatabaseConstan.QUERY_CREATE_TASK)
        Log.i("DATABASE", "DATABASE CREATED")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DatabaseConstan.QUERY_UPGRADE_USER)
        p0?.execSQL(DatabaseConstan.QUERY_UPGRADE_TASK)
        Log.i("DATABASE", "DATABASE UPDATED")
    }

}