package com.gaurav.testrg.database

/**
 * Created by jonesrandom on 11/25/17.
 *
 * #JanganLupaBahagia
 *
 */
class DatabaseConstan {

    companion object {
        val DATABASE_NAME = "DB_NAME"
        val DATABASE_VERSION = 1

        val DATABASE_TABEL_USER = "DB_USER"
        val DATABASE_TABEL_TASK = "DB_TASK"
        //user rows
        val ROW_ID = "_id"
        val ROW_NAMA = "nama"
        val ROW_EMAIL = "email"
        val ROW_PASSWORD = "password"
        //task rows
        val ROW_TITLE = "title"
        val ROW_DESCRIPTION = "description"

        val QUERY_CREATE_USER = "CREATE TABLE IF NOT EXISTS $DATABASE_TABEL_USER ($ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, $ROW_NAMA TEXT , $ROW_EMAIL TEXT , $ROW_PASSWORD TEXT)"
        val QUERY_CREATE_TASK = "CREATE TABLE IF NOT EXISTS $DATABASE_TABEL_TASK ($ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, $ROW_TITLE TEXT , $ROW_DESCRIPTION TEXT)"
        val QUERY_UPGRADE_USER = "DROP TABLE IF EXISTS $DATABASE_TABEL_USER"
        val QUERY_UPGRADE_TASK = "DROP TABLE IF EXISTS $DATABASE_TABEL_TASK"
    }
}