package com.example.boutiqueshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.Executor;
import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class Login extends AppCompatActivity {

    Button btn_fp, btn_fppin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asignar referencias de botones desde la vista
        btn_fp = findViewById(R.id.btn_fp);
        btn_fppin = findViewById(R.id.btn_fppin);

        // Verificar si el dispositivo admite la autenticación biométrica
        checkBioMetricSupported();

        // Crear el executor para la autenticación biométrica
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(Login.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            // Este método se llama automáticamente cuando la autenticación de la huella dactilar tiene éxito
            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            // Este método se llama automáticamente cuando la autenticación de la huella dactilar falla
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Intento de huella no reconocido
                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        // Realizar acción cuando se presiona el botón "btn_fp" para la autenticación de la huella dactilar
        btn_fp.setOnClickListener(view -> {
            // Llamar al método para mostrar el cuadro de diálogo de autenticación biométrica
            BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
            promptInfo.setNegativeButtonText("Cancel");
            // Activar la devolución de llamada cuando tenga éxito
            biometricPrompt.authenticate(promptInfo.build());
        });

        // Realizar acción cuando se presiona el botón "btn_fppin" para la autenticación de la huella dactilar con PIN
        btn_fppin.setOnClickListener(view -> {
            // Llamar al método para mostrar el cuadro de diálogo de autenticación biométrica
            BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
            promptInfo.setDeviceCredentialAllowed(true);
            // Activar la devolución de llamada cuando tenga éxito
            biometricPrompt.authenticate(promptInfo.build());
        });
    }

    BiometricPrompt.PromptInfo.Builder dialogMetric() {
        // Mostrar el cuadro de diálogo de autenticación biométrica
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login")
                .setSubtitle("Log in using your biometric credential");
    }

    // Verificar si el dispositivo admite la autenticación biométrica
    void checkBioMetricSupported() {
        BiometricManager manager = BiometricManager.from(this);
        String info = "";
        switch (manager.canAuthenticate(BIOMETRIC_WEAK | BIOMETRIC_STRONG)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                info = "App can authenticate using biometrics.";
                enableButton(true);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                info = "No biometric features available on this device.";
                enableButton(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                info = "Biometric features are currently unavailable.";
                enableButton(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                info = "Need register at least one finger print";
                enableButton(false, true);
                break;
            default:
                info = "Unknown cause";
                enableButton(false);
        }

        // Establecer el mensaje en el TextView para ver qué ocurre con el sensor del dispositivo
        TextView txinfo = findViewById(R.id.tx_info);
        txinfo.setText(info);
    }

    void enableButton(boolean enable) {
        // Habilitar o deshabilitar los botones
        btn_fp.setEnabled(enable);
        btn_fppin.setEnabled(enable);
    }

    void enableButton(boolean enable, boolean enroll) {
        enableButton(enable);

        if (!enroll) return;
        // Si es necesario registrar la huella dactilar, abrir la configuración para establecer la huella dactilar o el PIN
        final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
        enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
        startActivity(enrollIntent);
    }
}
