/*
 * Copyright © 2019 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydralogexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.pocketbyte.kydralogexample.common.Common

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPrintInfo.setOnClickListener {
            Common.printInfo(txtMessage.text.toString(), cbStackTrace.isChecked)
        }

        btnPrintDebug.setOnClickListener {
            Common.printDebug(txtMessage.text.toString(), cbStackTrace.isChecked)
        }

        btnPrintWarning.setOnClickListener {
            Common.printWarning(txtMessage.text.toString(), cbStackTrace.isChecked)
        }

        btnPrintError.setOnClickListener {
            Common.printError(txtMessage.text.toString(), cbStackTrace.isChecked)
        }
    }
}
