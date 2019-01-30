/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.hydralogexample

import android.app.Application
import ru.pocketbyte.hydra.log.HydraLog
import ru.pocketbyte.hydra.log.initDefaultAndroid

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        HydraLog.initDefaultAndroid()
    }

}