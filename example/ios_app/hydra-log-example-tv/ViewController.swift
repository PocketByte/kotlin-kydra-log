//
//  ViewController.swift
//  kydra-log-example-tv
//
//  Created by Dash on 21/11/2019.
//  Copyright © 2019 PcketByte.ru. All rights reserved.
//

import UIKit
import common_tv

class ViewController: UIViewController {
    
    @IBOutlet weak var textMessage: UITextField!
    @IBOutlet weak var scStackTrace: UISegmentedControl!
    @IBOutlet weak var scAsync: UISegmentedControl!
    
    private let commonModule: Common = Common()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func actionPrintInfo(_ sender: Any?) {
        let message = self.textMessage.text ?? ""
        let printStackTrace = self.scStackTrace.selectedSegmentIndex == 0
        if scAsync.selectedSegmentIndex == 0 {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printInfo(message: message, stackTrace: printStackTrace)
            }
        } else {
            commonModule.printInfo(message: message, stackTrace: printStackTrace)
        }
    }
    
    @IBAction func actionPrintDebug(_ sender: Any?) {
        let message = self.textMessage.text ?? ""
        let printStackTrace = self.scStackTrace.selectedSegmentIndex == 0
        if scAsync.selectedSegmentIndex == 0 {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printDebug(message: message, stackTrace: printStackTrace)
            }
        } else {
            commonModule.printDebug(message: message, stackTrace: printStackTrace)
        }
    }
    
    @IBAction func actionPrintWarning(_ sender: Any?) {
        let message = self.textMessage.text ?? ""
        let printStackTrace = self.scStackTrace.selectedSegmentIndex == 0
        if scAsync.selectedSegmentIndex == 0 {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printWarning(message: message, stackTrace: printStackTrace)
            }
        } else {
            commonModule.printWarning(message: message, stackTrace: printStackTrace)
        }
    }
    
    @IBAction func actionPrintError(_ sender: Any?) {
        let message = self.textMessage.text ?? ""
        let printStackTrace = self.scStackTrace.selectedSegmentIndex == 0
        if scAsync.selectedSegmentIndex == 0 {
            DispatchQueue.global(qos: .background).async { [weak self] in
                self?.commonModule.printError(message: message, stackTrace: printStackTrace)
            }
        } else {
            commonModule.printError(message: message, stackTrace: printStackTrace)
        }
    }

}
