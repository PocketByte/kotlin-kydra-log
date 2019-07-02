//
//  VievController.swift
//
//  Copyright © 2019 Denis Shurygin. All rights reserved.
//  Licensed under the Apache License, Version 2.0
//

import UIKit
import ios_app_framework

class ViewController: UIViewController {
    
    @IBOutlet weak var textMessage: UITextField?
    @IBOutlet weak var switchStackTrace: UISwitch?
    
    @IBOutlet weak var textMessage2: UITextField?
    @IBOutlet weak var textArguments2: UITextField?
    
    private let commonModule: Common = Common()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func actionPrintInfo(_ sender: Any?) {
        commonModule.printInfo(message: self.textMessage?.text ?? "", stackTrace: self.switchStackTrace?.isOn ?? false)
    }
    
    @IBAction func actionPrintDebug(_ sender: Any?) {
        commonModule.printDebug(message: self.textMessage?.text ?? "", stackTrace: self.switchStackTrace?.isOn ?? false)
    }
    
    @IBAction func actionPrintWarning(_ sender: Any?) {
        commonModule.printWarning(message: self.textMessage?.text ?? "", stackTrace: self.switchStackTrace?.isOn ?? false)
    }
    
    @IBAction func actionPrintError(_ sender: Any?) {
        commonModule.printError(message: self.textMessage?.text ?? "", stackTrace: self.switchStackTrace?.isOn ?? false)
    }
    
    @IBAction func actionPrint2(_ sender: Any?) {
        commonModule.print(message: self.textMessage2?.text ?? "", arguments: self.textArguments2?.text ?? "")
    }

}
