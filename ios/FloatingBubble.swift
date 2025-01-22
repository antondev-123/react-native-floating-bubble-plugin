@objc(FloatingBubble)
class FloatingBubble: NSObject {

  @objc(initialize:withRejecter:)
  func initialize(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
  }

  @objc(showFloatingBubble:withY:withResolver:withRejecter:)
  func showFloatingBubble(x: Int, y: Int, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
  }

  @objc(hideFloatingBubble:withRejecter:)
  func hideFloatingBubble(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
  }

  @objc(isBubbleVisible:withRejecter:)
  func isBubbleVisible(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
  }

  @objc(requestPermission:withRejecter:)
  func requestPermission(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
  }

  @objc(checkPermission:withRejecter:)
  func checkPermission(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
  }

  @objc(reopenApp)
  func reopenApp() {
  }
}
