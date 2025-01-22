import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-floating-bubble-plugin' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const FloatingBubble = NativeModules.FloatingBubble
  ? NativeModules.FloatingBubble
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );
export const reopenApp = () => FloatingBubble.reopenApp();
export const showFloatingBubble = (x = 50, y = 100) =>
  FloatingBubble.showFloatingBubble(x, y);
export const hideFloatingBubble = () => FloatingBubble.hideFloatingBubble();
export const checkPermission = () => FloatingBubble.checkPermission();
export const requestPermission = () => FloatingBubble.requestPermission();
export const initialize = () => FloatingBubble.initialize();
export const isBubbleVisible = () => FloatingBubble.isBubbleVisible();
export default {
  showFloatingBubble,
  hideFloatingBubble,
  requestPermission,
  checkPermission,
  initialize,
  reopenApp,
  isBubbleVisible,
};
