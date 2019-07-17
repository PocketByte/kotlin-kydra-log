//
//  VievController.swift
//
//  Copyright © 2019 Denis Shurygin. All rights reserved.
//  Licensed under the Apache License, Version 2.0
//

import UIKit
import ios_app_framework

class ViewController: UIViewController {
    
    @IBOutlet weak var textMessage: UITextField!
    @IBOutlet weak var switchStackTrace: UISwitch!
    @IBOutlet weak var switchAsync: UISwitch!
    
    private let commonModule: Common = Common()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func actionPrintInfo(_ sender: Any?) {
        if switchAsync.isOn {
            DispatchQueue.global(qos: .background).async {
                self.doPrintInfo()
            }
        } else {
            doPrintInfo()
        }
    }
    
    @IBAction func actionPrintDebug(_ sender: Any?) {
        if switchAsync.isOn {
            DispatchQueue.global(qos: .background).async {
                self.doPrintDebug()
            }
        } else {
            doPrintDebug()
        }
    }
    
    @IBAction func actionPrintWarning(_ sender: Any?) {
        if switchAsync.isOn {
            DispatchQueue.global(qos: .background).async {
                self.doPrintWarning()
            }
        } else {
            doPrintWarning()
        }
    }
    
    @IBAction func actionPrintError(_ sender: Any?) {
        if switchAsync.isOn {
            DispatchQueue.global(qos: .background).async {
                self.doPrintError()
            }
        } else {
            doPrintError()
        }
    }
    
    private func doPrintInfo() {
        commonModule.printInfo(message: self.textMessage.text ?? "", stackTrace: self.switchStackTrace.isOn)
    }
    
    private func doPrintDebug() {
        commonModule.printDebug(message: self.textMessage?.text ?? "", stackTrace: self.switchStackTrace.isOn)
    }
    
    private func doPrintWarning() {
        commonModule.printWarning(message: self.textMessage?.text ?? "", stackTrace: self.switchStackTrace.isOn)
    }
    
    private func doPrintError() {
        commonModule.printError(message: self.textMessage?.text ?? "", stackTrace: self.switchStackTrace.isOn)
    }

}
