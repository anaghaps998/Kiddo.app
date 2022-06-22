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

public class AddChild extends AppCompatActivity {

    TextView tv;
    EditText et;
    ImageView im;
    TextView tv1;
    RadioGroup rg;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4;
    EditText et5;
    Button btn;
    SharedPreferences sh;
    String ip, url;
    Bitmap bitmap;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        et = (EditText) findViewById(R.id.editTextTextPersonName4);
        im = (ImageView) findViewById(R.id.imageButton);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rb1 = (RadioButton) findViewById(R.id.radioButton3);
        rb2 = (RadioButton) findViewById(R.id.radioButton4);
        rb3 = (RadioButton) findViewById(R.id.radioButton5);
        et1 = (EditText) findViewById(R.id.editTextNumber3);
        et2 = (EditText) findViewById(R.id.editTextNumber4);
        et3 = (EditText) findViewById(R.id.editTextTextPersonName5);
        et4=(EditText) findViewById(R.id.editText);
        et5=(EditText)findViewById(R.id.editText2);
        btn = (Button) findViewById(R.id.button4);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sh.getString("ip","");
        url=sh.getString("url","")+"/AddChild";

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
                final String name = et.getText().toString();
                final String age = et1.getText().toString();
                final String standard = et2.getText().toString();
                final String school = et3.getText().toString();
                final String un = et4.getText().toString();
                final String pwd = et5.getText().toString();
                final String gender = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();

                int agee=Integer.parseInt(age);
                if(agee<8 || agee>16)
                {
                    flg++;
                    et1.setError("Invalid age group");
                }
                if(name.length()<3)
                {
                    flg++;
                    et.setError("This field cannot be blank");
                }
                if(!name.matches("[a-zA-Z ]+")){
                    flg++;
                    et.setError("This field can only contain alphabets");
                }
                if(standard.length()<1)
                {
                    flg++;
                    et2.setError("This field cannot be blank");
                }
                if(school.length()<1)
                {
                    flg++;
                    et3.setError("This field cannot be blank");
                }
                if(!school.matches("[a-zA-Z ]+")){
                    flg++;
                    et3.setError("Enter a valid school name");
                }
                if(un.length()<1)
                {
                    flg++;
                    et4.setError("This field cannot be blank");
                }
                if(pwd.length()<1)
                {
                    flg++;
                    et5.setError("This field cannot be blank");
                }
                if(flg==0) {
                    uploadBitmap(name, age, standard, school, gender, un, pwd);
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
    private void uploadBitmap(final String name, final String age, final String standard, final String school, final String gender,final String username,final String password) {


        pd = new ProgressDialog(AddChild.this);
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
                                Toast.makeText(getApplicationContext(), "Child Added", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Adding child failed" ,Toast.LENGTH_SHORT).show();
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
                params.put("age", age);//passing to python
                params.put("standard", standard);//passing to python
                params.put("school", school);//passing to python
                params.put("gender", gender);
                params.put("username", username);//passing to python
                params.put("pwd", password);
                params.put("id",sh.getString("pid",""));
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

}

