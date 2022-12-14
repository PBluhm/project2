package com.example.project2mobileapps

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHandler
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEM_COL + " TEXT, "
                + DAY_COL + " DATE)")

        db.execSQL(query)
    }

    fun addNewTodo(
        item: String?,
        day: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ITEM_COL, item)
        values.put(DAY_COL, day)

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "Project2"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "TodoList"
        private const val ID_COL = "id"
        private const val ITEM_COL = "TODO"
        private const val DAY_COL = "Day"
    }

    fun readItem(day: String?): ArrayList<UserModel> {
        val db = this.readableDatabase
        val selectedValues = arrayOf(day)
        val cursorPeople: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $DAY_COL = ?", selectedValues)
        val userModel: ArrayList<UserModel> = ArrayList()

        if (cursorPeople.moveToFirst()) {
            do {
                userModel.add(
                    UserModel(
                        cursorPeople.getString(1),
                        cursorPeople.getString(2)
                    )
                )
            } while (cursorPeople.moveToNext())
        }

        cursorPeople.close()
        return userModel
    }

    fun removeItem(todo: String) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$ITEM_COL=?", arrayOf(todo))
        db.close()
    }

}
