package com.example.facebooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.FacebookException;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    Button compartir;
    ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compartir = (Button) findViewById(R.id.btnCompartir);

        compartir.setOnClickListener(
                v -> shared()
        );


        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.facebooktest",                  //Insert your own package name.
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("FGVHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(MainActivity.this, "Si Funciona", Toast.LENGTH_LONG).show();
                            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this, "Accion Cancelada", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(MainActivity.this, "Se Produjo un ERROR", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void shared(){
        try {
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                        .setShareHashtag(new ShareHashtag.Builder()
                                .setHashtag("#Itsur")
                                .build())
                        .build();
                shareDialog.show(linkContent);
                Toast.makeText(this,"Publicando",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Algo paso : " +e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();
            shareDialog.show(linkContent);
            Toast.makeText(this,"Publicando",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}