export interface SharetoPlugin {
  shareImage(options: { base64?: string, fileName?: string, phoneNumber?: string, message?: string, bundleId: string }): Promise<void>;
  sharePdf(options: { base64?: string, fileName?: string, phoneNumber?: string, message?: string, bundleId: string }): Promise<void>;
}
