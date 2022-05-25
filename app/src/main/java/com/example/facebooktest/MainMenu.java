package com.example.facebooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

public class MainMenu extends AppCompatActivity {

    private Button btnPublishLink;
    private EditText txtLink;
    private ShareButton shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnPublishLink = findViewById(R.id.btnPublishLink);
        txtLink = findViewById(R.id.txtLink);
        // Se obtiene el boton de compartir proporcionado por el SDK ded facebook
        shareButton = (ShareButton) findViewById(R.id.share_btn);

        // En el boton de compartir define un ShareLinkContent para poder compartirlo mediante el boton de compartir de facebook
        btnPublishLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se crea el link con base al textview que se define en la aplicacion
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(txtLink.getText().toString()))
                        .build();
                // Agrega el ShareLinkContent al shareButton
                shareButton.setShareContent(content);
            }
        });
    }
}