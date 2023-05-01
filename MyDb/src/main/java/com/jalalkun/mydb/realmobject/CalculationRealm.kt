package com.jalalkun.mydb.realmobject

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class CalculationRealm: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var source: String = ""
    var result: String = ""
}