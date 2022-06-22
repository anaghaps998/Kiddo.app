package com.example.kiddoapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class parentReg extends AppCompatActivity implements TextWatcher {

    TextView t1;
    EditText e1;
    TextView t2;
    ImageView im;
    RadioGroup rg;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;
    EditText e6;
    EditText e7;
    EditText e8;
    EditText e9;
    Button btn;
    SharedPreferences sh;
    String ip, url;
    Bitmap bitmap;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_reg);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sh.getString("ip","");
        url=sh.getString("url","")+"/parentReg";
        //Toast.makeText(getApplicationContext() ,""+url, Toast.LENGTH_SHORT).show();

        im=(ImageView)findViewById(R.id.imageView);
        e1=(EditText)findViewById(R.id.editTextTextPersonName3);
        rg=(RadioGroup)findViewById(R.id.radioGroup2);
        rb1=(RadioButton)findViewById(R.id.radioButton3);
        rb2=(RadioButton)findViewById(R.id.radioButton4);
        rb3=(RadioButton)findViewById(R.id.radioButton5);
        e2=(EditText)findViewById(R.id.editTextNumber);
        e3=(EditText)findViewById(R.id.editTextNumber2);
        e4=(EditText)findViewById(R.id.editTextTextEmailAddress2);
        e5=(EditText)findViewById(R.id.editTextTextPersonName6);
        e6=(EditText)findViewById(R.id.editTextTextPersonName7);
        e7=(EditText)findViewById(R.id.editTextTextPersonName8);
        e8=(EditText)findViewById(R.id.editTextTextPassword2);
        e9=(EditText)findViewById(R.id.editTextTextPassword3);
        btn=(Button)findViewById(R.id.button3);
        e9.addTextChangedListener(this);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flg=0;
                final String name=e1.getText().toString();//e1.getText().toString();
                final String age=e2.getText().toString();
                final String phno=e3.getText().toString();
                final String email=e4.getText().toString();
                final String place=e5.getText().toString();
                final String post=e6.getText().toString();
                final String pin=e7.getText().toString();
                final String pwd=e8.getText().toString();

                final String gender = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                if(name.length()<2){
                    e1.setError("This field cannot be blank");
                    flg++;
                }
                if(!name.matches("[a-zA-Z ]+")){
                    flg++;
                    e1.setError("This field can only contain alphabets");
                }
                if(age.length()<2){
                    e2.setError("Enter a valid age");
                    flg++;
                }

                int agee=Integer.parseInt(age);
                if(agee<18 )
                {
                    flg++;
                    e2.setError("Invalid age group");
                }
                if((phno.length()<10 )|| (phno.length()>10)){
                    e3.setError("Enter a valid phone number");
                    flg++;
                }
                if(email.length()<2){
                    e4.setError("This field cannot be blank");
                    flg++;
                }
                if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
                {
                    e4.setError("invalid email");
                    flg++;
                }

                if(place.length()<2){
                    e5.setError("This field cannot be blank");
                    flg++;
                }
                if(!place.matches("[a-zA-Z ]+")){
                    flg++;
                    e5.setError("This field can only contain alphabets");
                }
                if(post.length()<2){
                    e6.setError("This field cannot be blank");
                    flg++;
                }
                if(!post.matches("[a-zA-Z ]+")){
                    flg++;
                    e6.setError("This field can only contain alphabets");
                }
                if((pin.length()<6 )|| (pin.length()>6)){
                    e7.setError("Invalid pincode");
                    flg++;
                }
                if(pwd.length()<4){
                    e8.setError("This field cannot be blank");
                    flg++;
                }
                if(flg==0) {
                    uploadBitmap(name, gender, age, phno, email, place, post, pin, pwd);
                    url = sh.getString("url", "") + "/parentReg";
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                im.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //converting to bitarray
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final String name, final String gender, final String age, final String phno, final String email, final String place, final String post,final String pin,final String pwd) {


        pd = new ProgressDialog(parentReg.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();


                            JSONObject obj = new JSONObject(new String(response.data));

                            if(obj.getString("status").equals("ok")){
                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), login.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Registration failed" ,Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences o = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("name", name);//passing to python
                params.put("gender",gender);
                params.put("age", age);//passing to python
                params.put("phno", phno);//passing to python
                params.put("email", email);//passing to python
                params.put("place", place);
                params.put("post", post);
                params.put("pin", pin);
                params.put("pwd", pwd);
//                params.put("confpwd", confpwd);

                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!e8.getText().toString().equalsIgnoreCase(e9.getText().toString())){
            e9.setError("Missmatch");
    }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}








