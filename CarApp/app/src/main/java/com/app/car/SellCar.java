package com.app.car;

//import com.app.car.CarModel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
public class SellCar extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_CAMERA = 2;
    private Button Gallery;
    private Button Camera;
    private Button NextBtn;
    private EditText Make;
    private EditText Fuel;
    private EditText Assembly;
    private EditText Transmission;
    private ImageView DClick;
    private ImageView DoubleClickFuel;
    private ImageView DoubleClickAssembly;
    private ImageView DoubleClickTransmission;

    private String[] carMakes = {"Toyota", "Honda", "BMW", "Ford", "Chevrolet","Suzuki","KIA"};
    private String[] carFuel = {"Petrol","Diesel","LPG","CNG","HYBRID","Electric"};
    private  String[] carAssembly = {"Local","Imported"};
    private  String[] carTransmission = {"Automatic","Manual"};

    private byte[] galleryImage; // Field for holding the gallery image data
    private byte[] cameraImage; // Field for holding the camera image data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_car);

        Make = findViewById(R.id.make);
        Fuel = findViewById(R.id.fuel);
        Assembly = findViewById(R.id.assembly);
        Transmission = findViewById(R.id.transmission);
        DClick = findViewById(R.id.DoubleClick);
        DoubleClickFuel = findViewById(R.id.DoubleClickFuel);
        DoubleClickAssembly = findViewById(R.id.DoubleClickAssembly);
        DoubleClickTransmission = findViewById(R.id.DoubleClickTransmission);
        NextBtn = findViewById(R.id.NextBtn);
        Gallery = findViewById(R.id.galleryButton);
        Camera=findViewById(R.id.cameraButton);

        DClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCarMakeDialog();
            }
        });
        DoubleClickFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCarFuelDialog();
            }
        });
        DoubleClickAssembly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCarAssemblyDialog();
            }
        });
        DoubleClickTransmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCarTransmissionDialog();
            }
        });
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SellCar.this, after_sell.class);
                startActivity(intent);
            }
        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }
    private void showCarMakeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Car Make");
        builder.setItems(carMakes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedMake = carMakes[which];
                Make.setText(selectedMake);
            }
        });
        builder.show();
    }
    private void showCarFuelDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Car Fuel");
        builder.setItems(carFuel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedMake = carFuel[which];
                Fuel.setText(selectedMake);
            }
        });
        builder.show();
    }
    private void showCarAssemblyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Car Assembly");
        builder.setItems(carAssembly, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedMake = carAssembly[which];
                Assembly.setText(selectedMake);
            }
        });
        builder.show();
    }
    private void showCarTransmissionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Car Transmission");
        builder.setItems(carTransmission, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedMake = carTransmission[which];
                Transmission.setText(selectedMake);
            }
        });
        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, REQUEST_GALLERY);
    }
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }
}