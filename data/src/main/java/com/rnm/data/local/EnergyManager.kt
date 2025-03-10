package com.rnm.data.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class EnergyManager @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    private var timer: Job? = null

    fun start() {
        checkIfNeedRecharging()
        manageTimer()
    }

    private fun manageTimer() {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.getEnergyRechargeTime().collectLatest {
                if (it != 0L) {
                    startEnergyRechargeTimer()
                }
            }
        }
    }

    private fun checkIfNeedRecharging() {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.getEnergyLevel().collectLatest {
                if (it < 10) {
                    val endTime = dataStoreManager.getEnergyRechargeTime().first()
                    if (endTime > System.currentTimeMillis()) {
                        println("timer: energy $it")
                        val difference = endTime - System.currentTimeMillis()
                        val differenceInUses = difference / 600000 + 1
                        val additionalTime = (10 - (differenceInUses + it) * 600000) + difference
                        val newEndTime = endTime + additionalTime
                        dataStoreManager.setEnergyRechargeTime(newEndTime)
                    } else {
                        val newEndTime = System.currentTimeMillis() + (600000 * (10 - it))
                        dataStoreManager.setEnergyRechargeTime(newEndTime)
                    }
                } else {
                    println("timer: 10 energy")
                    dataStoreManager.setEnergyRechargeTime(0L)
                }
            }

        }
    }

    private suspend fun startEnergyRechargeTimer() {
        println("timer: setTimer")
        timer?.cancel()
        val endTime = dataStoreManager.getEnergyRechargeTime().first()
        val firstTick = (endTime - System.currentTimeMillis()) % 600000

        timer = setEnergyRechargeTimer(
            endTime = endTime,
            tickSynchro = 15000,
            onFinish = {
                CoroutineScope(Dispatchers.IO).launch {
                    setEnergyLevel(10)
                }
            },
            onTick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val energyLevel = dataStoreManager.getEnergyLevel().first()
                    setEnergyLevel(energyLevel + 1)
                }
            },
            onTickSync = {}
        )
        timer?.start()
    }

    private fun setEnergyRechargeTimer(
        endTime: Long,
        tick: Long = 600000,
        delayMillis: Long = 1000,
        tickSynchro: Long = 15000,
        firstTick: Long = 600000,
        onFinish: () -> Unit,
        onTick: () -> Unit,
        onTickSync: () -> Unit
    ) = CoroutineScope(Dispatchers.Default).launch {
        println("timer: startTimer")

        val currentTime = System.currentTimeMillis()
        var tickTime = currentTime + firstTick
        var tickSynchroTime = currentTime + tickSynchro

        while (System.currentTimeMillis() < endTime) {
            if (System.currentTimeMillis() > tickTime) {
                onTick()
                tickTime = System.currentTimeMillis() + tick
            }
            if (System.currentTimeMillis() > tickSynchroTime) {
                onTickSync()
                tickSynchroTime = System.currentTimeMillis() + tickSynchro
            }
            delay(delayMillis)
        }
        onFinish()
    }

    private suspend fun setEnergyLevel(energyLevel: Int) {
        dataStoreManager.setEnergyLevel(energyLevel)
    }

}