package ti.documentscanner;

import static com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG;
import static com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF;
import static com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.documentscanner.GmsDocumentScanner;
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;

public class TiDocumentscannerActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GmsDocumentScannerOptions options = new GmsDocumentScannerOptions.Builder()
                .setGalleryImportAllowed(false)
                .setResultFormats(RESULT_FORMAT_JPEG, RESULT_FORMAT_PDF)
                .setScannerMode(SCANNER_MODE_FULL)
                .build();

        GmsDocumentScanner scanner = GmsDocumentScanning.getClient(options);

        ActivityResultLauncher<IntentSenderRequest> scannerLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartIntentSenderForResult(),
                        result -> {
                            if (result.getResultCode() == RESULT_OK) {
                                GmsDocumentScanningResult scanningResult = GmsDocumentScanningResult.fromActivityResultIntent(result.getData());
                                String[] imgList = new String[scanningResult.getPages().size()];

                                int i = 0;
                                for (GmsDocumentScanningResult.Page page : scanningResult.getPages()) {
                                    Uri imageUri = page.getImageUri();
                                    imgList[i] = imageUri.toString();
                                    ++i;
                                }

                                GmsDocumentScanningResult.Pdf pdf = scanningResult.getPdf();
                                Uri pdfUri = pdf.getUri();
                                int pageCount = pdf.getPageCount();

                                KrollDict kd = new KrollDict();
                                kd.put("imagePaths", imgList);
                                kd.put("pdfPath", pdfUri.toString());
                                kd.put("pdfPageCount", pageCount);

                                TiDocumentscannerModule.callMe(kd);
                                finish();
                            } else {
                              finish();
                            }
                        });
        scanner.getStartScanIntent(TiApplication.getAppRootOrCurrentActivity())
                .addOnSuccessListener(intentSender ->
                        scannerLauncher.launch(new IntentSenderRequest.Builder(intentSender).build()))
                .addOnFailureListener(result -> {
                    Log.i("Error", result.getMessage());
                });
    }
}
