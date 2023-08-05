package com.example.boutiqueshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtPass;
    private EditText txtConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registro);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);

        txtNombre = findViewById(R.id.nameET);
        txtEmail = findViewById(R.id.emailET);
        txtPass = findViewById(R.id.RcontraseñaET);
        txtConfirmPass = findViewById(R.id.RconfirmContraseñaET);

        TextView textViewCuatro = findViewById(R.id.textViewCuatro);

        textViewCuatro.setAnimation(animation1);

        Button signUpButton = findViewById(R.id.signUpBtn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBtnInsertar(view);
                vibrateButton(signUpButton);
            }
        });

        TextView btnLogin = findViewById(R.id.BtnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
            }
        });
    }

    public void clickBtnInsertar(View view) {
        String nombre = txtNombre.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPass.getText().toString().trim();
        String confirmarPassword = txtConfirmPass.getText().toString().trim();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Snackbar.make(view, "Por favor completa todos los campos", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(confirmarPassword)) {
            Snackbar.make(view, "Lo sentimos, las contraseñas no coinciden", Snackbar.LENGTH_LONG)
                    .show();
            return;
        }

        String url = "http://192.168.1.70/inventarioboutique/insertar.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest resultadoPost = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Snackbar.make(view, "Felicidades Usuario agregado exitosamente", Snackbar.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view, "Error: " + error.toString(), Snackbar.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("name", txtNombre.getText().toString());
                parametros.put("email", txtEmail.getText().toString());
                parametros.put("password", txtPass.getText().toString());
                return parametros;
            }
        };
        queue.add(resultadoPost);
    }


    private void vibrateButton(Button button) {
        final float originalY = button.getTranslationY();
        button.animate()
                .translationYBy(10f)
                .setDuration(50)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        button.animate()
                                .translationYBy(-20f)
                                .setDuration(50)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        button.animate()
                                                .translationYBy(10f)
                                                .setDuration(50)
                                                .start();
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }
}