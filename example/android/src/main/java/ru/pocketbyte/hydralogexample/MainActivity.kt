package ru.pocketbyte.hydralogexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.pocketbyte.hydralogexample.common.Common

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
