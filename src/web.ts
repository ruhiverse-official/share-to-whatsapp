import { WebPlugin } from '@capacitor/core';

import type { SharetoPlugin } from './definitions';

export class SharetoWeb extends WebPlugin implements SharetoPlugin {
  async shareImage(options: { base64: string; fileName?: string; phoneNumber: string, bundleId: string }): Promise<void> {
    console.log('shareImage', options);
    // Implement web sharing logic if needed
  }

  async sharePdf(options: { base64: string; fileName?: string; phoneNumber: string, bundleId: string }): Promise<void> {
    console.log('sharePdf', options);
    // Implement web sharing logic if needed
  }
}
