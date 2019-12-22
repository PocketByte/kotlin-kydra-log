//
//  VievController.swift
//
//  Copyright © 2019 Denis Shurygin. All rights reserved.
//  Licensed under the Apache License, Version 2.0
//

import UIKit
import common_ios

class ViewController: UIViewController {
    
    @IBOutlet weak var textMessage: UITextField!
    @IBOutlet weak var switchStackTrace: UISwitch!
    @IBOutlet weak var switchAsync: UISwitch!
    
    private let commonModule: Common = Common()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func actionPrintInfo(_ sender: Any?) {
        let message = self.textMessage.text ?? ""
        let printStackTrace = self.switchStackTrace.isOn
        if switchAsync.isOn {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printInfo(message: message, stackTrace: printStackTrace)
            }
        } else {
            commonModule.printInfo(message: message, stackTrace: printStackTrace)
        }
    }
    
    @IBAction func actionPrintDebug(_ sender: Any?) {
        let message = self.textMessage.text ?? ""
        let printStackTrace = self.switchStackTrace.isOn
        if switchAsync.isOn {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printDebug(message: message, stackTrace: printStackTrace)
            }
        } else {
            commonModule.printDebug(message: message, stackTrace: printStackTrace)
        }
    }
    
    @IBAction func actionPrintWarning(_ sender: Any?) {
        let message = self.textMessage.text ?? ""
        let printStackTrace = self.switchStackTrace.isOn
        if switchAsync.isOn {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printWarning(message: message, stackTrace: printStackTrace)
            }
        } else {
            commonModule.printWarning(message: message, stackTrace: printStackTrace)
        }
    }
    
    @IBAction func actionPrintError(_ sender: Any?) {
        let message = self.textMessage.text ?? ""
        let printStackTrace = self.switchStackTrace.isOn
        if switchAsync.isOn {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printError(message: message, stackTrace: printStackTrace)
            }
        } else {
            commonModule.printError(message: message, stackTrace: printStackTrace)
        }
    }

}
