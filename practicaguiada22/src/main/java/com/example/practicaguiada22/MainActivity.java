package com.example.practicaguiada22;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends  AppCompatActivity {
    FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //con auth se puede comprobar si el usuario ha iniciado sesion
        auth = FirebaseAuth.getInstance();
        //en vez de en onResume o onPause se crea en el listener porque cuando se traiga de un segundo plano, el fragment tiene que estar creado para no tener que volver a logear
        authListener = firebaseAuth -> { //mediante una expresion lambda se crea un listener, se crea un objeto firebaseAuth
            //se activará cuando se invoque al metodo .addAuthStateListener dentro del metodo onResume
            //cuando se active obtendrá el estado de autenticación en segundo plano, una vez obtenidos los datos hará un callback a la expresion lambda
            FirebaseUser user = firebaseAuth.getCurrentUser();// si está autenticado devolverá null
            Fragment fragment = user != null ? new CustomersFragment() : new LoginFragment();//el CustomersFragment da acceso a la bd
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainerView, fragment)
                    .commit();
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onPause() { //remueve el listener
        super.onPause();
        if (authListener != null)
            auth.removeAuthStateListener(authListener);
    }
}