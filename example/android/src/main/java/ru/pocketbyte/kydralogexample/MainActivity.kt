/*
 * Copyright © 2022 Denis Shurygin. All rights reserved.
 * Licensed under the Apache License, Version 2.0
 */

package ru.pocketbyte.kydralogexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.pocketbyte.kydralogexample.common.Common
import ru.pocketbyte.kydralogexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPrintInfo.setOnClickListener {
            Common.printInfo(binding.txtMessage.text.toString(), binding.cbStackTrace.isChecked)
        }

        binding.btnPrintDebug.setOnClickListener {
            Common.printDebug(binding.txtMessage.text.toString(), binding.cbStackTrace.isChecked)
        }

        binding.btnPrintWarning.setOnClickListener {
            Common.printWarning(binding.txtMessage.text.toString(), binding.cbStackTrace.isChecked)
        }

        binding.btnPrintError.setOnClickListener {
            Common.printError(binding.txtMessage.text.toString(), binding.cbStackTrace.isChecked)
        }
    }
}
