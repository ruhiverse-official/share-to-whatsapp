package com.pareshgami.shareto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import android.widget.Toast;

public class Shareto {

    private static final String TAG = "Shareto";
    private Context context;

    public Shareto(Context context) {
        this.context = context;
    }

    public void shareImage(String base64, String fileName, String phoneNumber, String message, String bundleId) {
        try {
            if (base64 == null && message == null) {
                throw new IllegalArgumentException("Either base64 string or message is required.");
            }

            File imageFile = base64 != null ? saveBase64ToFile(base64, fileName != null ? fileName : UUID.randomUUID().toString() + ".png") : null;
            Uri imageUri = imageFile != null ? FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".fileprovider",
                    imageFile
            ) : null;
            shareFile(imageUri, "image/*", phoneNumber, message, bundleId);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Error in shareImage: " + e.getMessage(), e);
            showToast("Error: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Error saving image: " + e.getMessage(), e);
            showToast("Failed to save image.");
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error: " + e.getMessage(), e);
            showToast("An unexpected error occurred.");
        }
    }

    public void sharePdf(String base64, String fileName, String phoneNumber, String message, String bundleId) {
        try {
            if (base64 == null && message == null) {
                throw new IllegalArgumentException("Either base64 string or message is required.");
            }

            Log.i(TAG, "Sharing PDF with message: " + message);

            File pdfFile = base64 != null ? saveBase64ToFile(base64, fileName != null ? fileName : UUID.randomUUID().toString() + ".pdf") : null;
            Uri pdfUri = pdfFile != null ? FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".fileprovider",
                    pdfFile
            ) : null;
            shareFile(pdfUri, "application/pdf", phoneNumber, message, bundleId);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Error in sharePdf: " + e.getMessage(), e);
            showToast("Error: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Error saving PDF: " + e.getMessage(), e);
            showToast("Failed to save PDF.");
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error: " + e.getMessage(), e);
            showToast("An unexpected error occurred.");
        }
    }

    private File saveBase64ToFile(String base64Data, String fileName) throws IOException {
        try {
            byte[] decodedBytes = Base64.decode(base64Data, Base64.DEFAULT);
            File directory = context.getExternalFilesDir(null);
            File file = new File(directory, fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(decodedBytes);
            }
            return file;
        } catch (IOException e) {
            Log.e(TAG, "Error decoding base64 data: " + e.getMessage(), e);
            throw e;
        }
    }

    private void shareFile(Uri fileUri, String mimeType, String phoneNumber, String message, String bundleId) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            if (fileUri != null) {
                shareIntent.setType(mimeType);
                shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            } else {
                shareIntent.setType("text/plain");
            }
            if (message != null) {
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            }
            shareIntent.setPackage(bundleId);
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                shareIntent.putExtra("jid", phoneNumber + "@s.whatsapp.net");
            }
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            context.startActivity(Intent.createChooser(shareIntent, "Share with"));
        } catch (android.content.ActivityNotFoundException e) {
            Log.e(TAG, "WhatsApp not found: " + e.getMessage(), e);
            showToast("WhatsApp is not installed.");
        } catch (Exception e) {
            Log.e(TAG, "Error sharing file: " + e.getMessage(), e);
            showToast("Failed to share file.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
