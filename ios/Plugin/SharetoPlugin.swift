import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(SharetoPlugin)
public class SharetoPlugin: CAPPlugin {
    private let implementation = Shareto()

    @objc func shareImage(_ call: CAPPluginCall) {
        let base64 = call.getString("base64")
        let fileName = call.getString("fileName")
        let phoneNumber = call.getString("phoneNumber")
        let message = call.getString("message")
        guard let bundleId = call.getString("bundleId") else {
            call.reject("bundleId is required")
            return
        }
        
        guard let viewController = self.bridge?.viewController else {
            call.reject("Unable to get view controller")
            return
        }
        
        implementation.shareImage(
            base64: base64,
            fileName: fileName,
            phoneNumber: phoneNumber,
            message: message,
            bundleId: bundleId,
            viewController: viewController
        ) { error in
            if let error = error {
                call.reject(error.localizedDescription)
            } else {
                call.resolve()
            }
        }
    }
    
    @objc func sharePdf(_ call: CAPPluginCall) {
        let base64 = call.getString("base64")
        let fileName = call.getString("fileName")
        let phoneNumber = call.getString("phoneNumber")
        let message = call.getString("message")
        guard let bundleId = call.getString("bundleId") else {
            call.reject("bundleId is required")
            return
        }
        
        guard let viewController = self.bridge?.viewController else {
            call.reject("Unable to get view controller")
            return
        }
        
        implementation.sharePdf(
            base64: base64,
            fileName: fileName,
            phoneNumber: phoneNumber,
            message: message,
            bundleId: bundleId,
            viewController: viewController
        ) { error in
            if let error = error {
                call.reject(error.localizedDescription)
            } else {
                call.resolve()
            }
        }
    }
}
