import Foundation
import UIKit
import UniformTypeIdentifiers

@objc public class Shareto: NSObject {
    
    @objc public func shareImage(base64: String?, fileName: String?, phoneNumber: String?, message: String?, bundleId: String, viewController: UIViewController, completion: @escaping (Error?) -> Void) {
        do {
            guard base64 != nil || message != nil else {
                DispatchQueue.main.async {
                    completion(NSError(domain: "SharetoError", code: 1, userInfo: [NSLocalizedDescriptionKey: "Either base64 string or message is required."]))
                }
                return
            }
            
            var fileUrl: URL? = nil
            if let base64Data = base64 {
                fileUrl = try saveBase64ToFile(base64Data: base64Data, fileName: fileName ?? UUID().uuidString + ".png")
            }
            
            shareFile(fileUrl: fileUrl, mimeType: "image/*", phoneNumber: phoneNumber, message: message, bundleId: bundleId, viewController: viewController, completion: completion)
        } catch {
            DispatchQueue.main.async {
                completion(error)
            }
        }
    }
    
    @objc public func sharePdf(base64: String?, fileName: String?, phoneNumber: String?, message: String?, bundleId: String, viewController: UIViewController, completion: @escaping (Error?) -> Void) {
        do {
            guard base64 != nil || message != nil else {
                DispatchQueue.main.async {
                    completion(NSError(domain: "SharetoError", code: 1, userInfo: [NSLocalizedDescriptionKey: "Either base64 string or message is required."]))
                }
                return
            }
            
            var fileUrl: URL? = nil
            if let base64Data = base64 {
                fileUrl = try saveBase64ToFile(base64Data: base64Data, fileName: fileName ?? UUID().uuidString + ".pdf")
            }
            
            shareFile(fileUrl: fileUrl, mimeType: "application/pdf", phoneNumber: phoneNumber, message: message, bundleId: bundleId, viewController: viewController, completion: completion)
        } catch {
            DispatchQueue.main.async {
                completion(error)
            }
        }
    }
    
    private func saveBase64ToFile(base64Data: String, fileName: String) throws -> URL {
        guard let decodedData = Data(base64Encoded: base64Data) else {
            throw NSError(domain: "SharetoError", code: 2, userInfo: [NSLocalizedDescriptionKey: "Invalid base64 data"])
        }
        
        let fileManager = FileManager.default
        let tempDirectory = fileManager.temporaryDirectory
        let fileUrl = tempDirectory.appendingPathComponent(fileName)
        
        try decodedData.write(to: fileUrl)
        return fileUrl
    }
    
    private func shareFile(fileUrl: URL?, mimeType: String, phoneNumber: String?, message: String?, bundleId: String, viewController: UIViewController, completion: @escaping (Error?) -> Void) {
        var items: [Any] = []
        
        if let url = fileUrl {
            items.append(url)
        }
        
        if let text = message {
            items.append(text)
        }
        
        guard !items.isEmpty else {
            DispatchQueue.main.async {
                completion(NSError(domain: "SharetoError", code: 3, userInfo: [NSLocalizedDescriptionKey: "No content to share"]))
            }
            return
        }
        
        // All UI operations must be on the main thread
        DispatchQueue.main.async {
            let activityViewController = UIActivityViewController(activityItems: items, applicationActivities: nil)
            
            // Configure for WhatsApp - exclude all activities except the specified bundle ID
            if !bundleId.isEmpty {
                var excludedTypes: [UIActivity.ActivityType] = []
                // Add common activity types to exclude, but keep the bundleId one
                excludedTypes.append(contentsOf: [
                    .postToFacebook,
                    .postToTwitter,
                    .postToWeibo,
                    .message,
                    .mail,
                    .print,
                    .copyToPasteboard,
                    .assignToContact,
                    .saveToCameraRoll,
                    .addToReadingList,
                    .postToFlickr,
                    .postToVimeo,
                    .postToTencentWeibo,
                    .airDrop
                ])
                activityViewController.excludedActivityTypes = excludedTypes
            }
            
            // Configure popover for iPad
            if let popover = activityViewController.popoverPresentationController {
                popover.sourceView = viewController.view
                popover.sourceRect = CGRect(x: viewController.view.bounds.midX, y: viewController.view.bounds.midY, width: 0, height: 0)
                popover.permittedArrowDirections = []
            }
            
            viewController.present(activityViewController, animated: true) {
                completion(nil)
            }
        }
    }
}
