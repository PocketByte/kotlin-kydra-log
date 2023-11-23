//
//  InterfaceController.swift
//  kydra-log-example-watch WatchKit Extension
//
//  Created by Denis Shurygin on 21/11/2019.
//  Copyright © 2022 Denis Shurygin. All rights reserved.
//  Licensed under the Apache License, Version 2.0
//

import WatchKit
import Foundation
import KotlinCommon

class InterfaceController: WKInterfaceController {
    
    @IBOutlet weak var textMessage: WKInterfaceTextField!
    @IBOutlet weak var switchStackTrace: WKInterfaceSwitch!
    @IBOutlet weak var switchAsync: WKInterfaceSwitch!
    
    private var logMessage: String = "Hello Kydra Log"
    private var printStackTrace: Bool = false
    private var runAsync: Bool = true
    
    private let commonModule: Common = Common()
    
    override func willActivate() {
        super.willActivate()
        
        textMessage.setText(logMessage)
        switchStackTrace.setOn(printStackTrace)
        switchAsync.setOn(runAsync)
    }
    
    @IBAction func actionMessageChanged(_ value: String?) {
        logMessage = value ?? ""
    }
    
    @IBAction func actionSwitchStackTrace(_ value: Bool) {
        printStackTrace = value
    }
    
    @IBAction func actionSwitchAsync(_ value: Bool) {
        runAsync = value
    }
    
    @IBAction func actionPrintInfo(_ sender: Any?) {
        if runAsync {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printInfo(message: self!.logMessage, stackTrace: self!.printStackTrace)
            }
        } else {
            commonModule.printInfo(message: logMessage, stackTrace: printStackTrace)
        }
    }

    @IBAction func actionPrintDebug(_ sender: Any?) {
        if runAsync {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printDebug(message: self!.logMessage, stackTrace: self!.printStackTrace)
            }
        } else {
            commonModule.printDebug(message: logMessage, stackTrace: printStackTrace)
        }
    }

    @IBAction func actionPrintWarning(_ sender: Any?) {
        if runAsync {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printWarning(message: self!.logMessage, stackTrace: self!.printStackTrace)
            }
        } else {
            commonModule.printWarning(message: logMessage, stackTrace: printStackTrace)
        }
    }

    @IBAction func actionPrintError(_ sender: Any?) {
        if runAsync {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printError(message: self!.logMessage, stackTrace: self!.printStackTrace)
            }
        } else {
            commonModule.printError(message: logMessage, stackTrace: printStackTrace)
        }
    }

}
