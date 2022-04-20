import Foundation

@objc public class ZipHelp: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
