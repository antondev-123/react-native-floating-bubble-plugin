import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-floating-bubble-plugin' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go & React Native CLI\n';

const FloatingBubblePlugin = NativeModules.FloatingBubblePlugin
  ? NativeModules.FloatingBubblePlugin
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );
export const showFloatingBubble = (x = 50, y = 100) =>
FloatingBubblePlugin.showFloatingBubble(x, y);
export const hideFloatingBubble = () => FloatingBubblePlugin.hideFloatingBubble();
export const checkPermission = () => FloatingBubblePlugin.checkPermission();
export const requestPermission = () => FloatingBubblePlugin.requestPermission();
export const initialize = () => FloatingBubblePlugin.initialize();
// export const isBubbleVisible = () => FloatingBubblePlugin.isBubbleVisible();
// export const setRestoreOnBoot = () => FloatingBubblePlugin.setRestoreOnBoot();
// export const restoreBubble = () => FloatingBubblePlugin.restoreBubble();
export const setRestoreOnBoot = async (restore: boolean) => {
  try {
    await FloatingBubblePlugin.setRestoreOnBoot(restore);
    return true;
  } catch (e) {
    console.error('Error setting restore on boot:', e);
    return false;
  }
};

export const getBubbleState = async () => {
  return await FloatingBubblePlugin.isBubbleVisible();
};

export const restoreBubbleIfNeeded = async () => {
  return await FloatingBubblePlugin.restoreBubble();
};

export default {
  showFloatingBubble,
  hideFloatingBubble,
  requestPermission,
  checkPermission,
  initialize,
  getBubbleState,
  setRestoreOnBoot,
  restoreBubbleIfNeeded,
};
