package com.example.boutiqueshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class preLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pre_login);

        //Animación

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.dezplazamiento_abajo);

        ImageView logoimageView = findViewById(R.id.logoimageView);
        TextView textView = findViewById(R.id.textView);
        TextView textViewdos = findViewById(R.id.textViewdos);

        logoimageView.setAnimation(animation1);
        textView.setAnimation(animation2);
        textViewdos.setAnimation(animation2);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(preLogin.this, loginInicio.class);
                startActivity(intent);
                finish(); // Opcional: si no deseas que el usuario pueda volver a esta actividad con el botón "Atrás"
            }
        }, 4000);
    }
}