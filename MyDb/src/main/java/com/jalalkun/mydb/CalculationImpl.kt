package com.jalalkun.mydb

import com.jalalkun.mydb.DbHeler.getRandomKey
import com.jalalkun.mydb.realmobject.CalculationRealm
import com.jalalkun.mymodel.Calculation
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class CalculationImpl: CalculationDb {
    private val config = RealmConfiguration.Builder(schema = setOf(CalculationRealm::class)).encryptionKey(getRandomKey(BuildConfig.KEY_ENCRYPT)).build()
    private fun realm() : Realm{
        return Realm.open(config)
    }

    override suspend fun insertCalcutalion(calculation: Calculation): Boolean {
        val r = realm()
        r.writeBlocking {
            copyToRealm(
                CalculationRealm().apply {
                    source = calculation.source
                    result = calculation.result
                }
            )
            return@writeBlocking true
        }
        r.close()
        return false
    }

    override suspend fun getAllCalculation(): List<Calculation> {
        val r = realm()
        val l = r.query(CalculationRealm::class).find()
        val m = mutableListOf<Calculation>()
        l.forEach {
            m.add(
                Calculation(
                    id = it._id.toString(),
                    source = it.source,
                    result = it.result
                )
            )
        }
        r.close()
        return m
    }
}