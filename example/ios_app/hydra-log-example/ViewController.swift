//
//  VievController.swift
//
//  Created by Dash on 28.05.2018.
//  Copyright © 2018 PcketByte.ru. All rights reserved.
//

import UIKit
import ios_app_framework

class ViewController: UIViewController {
    
    @IBOutlet weak var textMessage: UITextField?
    @IBOutlet weak var switchStackTrace: UISwitch?
    
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

}
