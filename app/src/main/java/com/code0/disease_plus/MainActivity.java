package com.code0.disease_plus;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.tiagohm.markdownview.MarkdownView;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap;

    boolean check = true;

    Button SelectImageGallery, UploadImageServer;

    ImageView imageView;

    EditText imageName;

    ProgressDialog progressDialog ;

    String GetImageNameEditText;

    String ImageName = "image_name" ;

    String ImagePath = "image_path" ;

    String ServerUploadPath ="http://192.168.43.245:5000/" ;

    final String TAG ="TAAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);
        SelectImageGallery = (Button)findViewById(R.id.buttonSelect);
        UploadImageServer = (Button)findViewById(R.id.buttonUpload);
        SelectImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });


        UploadImageServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ImageUploadToServerFunction();

            }
        });
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

           Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void ImageUploadToServerFunction()
    {

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        Log.d(TAG, ConvertImage);
       final ProgressDialog progressDialog= new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Processing your request...");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, ServerUploadPath,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        try
                        {


                            JSONArray jsonArray1 = new JSONArray(response);
                            JSONObject jsonObject = jsonArray1.getJSONObject(0);
                            String disease = jsonObject.getString("disease");
                            jsonObject = jsonArray1.getJSONObject(1);
                            String percantage = jsonObject.getString("percantage");
                           // Toast.makeText(MainActivity.this, percantage, Toast.LENGTH_SHORT).show();


                            String tmpHtml = "<html> <b>a<b/> whole bunch of html stuff</html>";
                            String htmlTextStr = Html.fromHtml(tmpHtml).toString();


                            MarkdownView mMarkdownView;


                            if(disease=="blightvirus")
                           {
                               mMarkdownView = (MarkdownView)findViewById(R.id.markdown_view);
                               mMarkdownView.loadMarkdown(" # Early Blight\n" +
                                       "Common on tomato and potato plants, early blight is caused by the fungus Alternaria solani and occurs throughout the United States. Symptoms first appear on the lower, older leaves as small brown spots with concentric rings that form a “bull’s eye” pattern. As the disease matures, it spreads outward on the leaf surface causing it to turn yellow, wither and die. Eventually the stem, fruit and upper portion of the plant will become infected. Crops can be severely damaged.\n" +
                                       "\n" +
                                       "Early blight overwinters on infected plant tissue and is spread by splashing rain, irrigation, insects and garden tools. The disease is also carried on tomato seeds and in potato tubers. In spite of its name, early blight can occur any time throughout the growing season. High temperatures (80-85˚F.) and wet, humid conditions promote its rapid spread. In many cases, poorly nourished or stressed plants are attacked.\n" +
                                       "\n" +
                                       "# Treatment\n" +
                                       "\n" +
                                       "1. Prune or stake plants to improve air circulation and reduce fungal problems.\n" +
                                       "2. Make sure to disinfect your pruning shears (one part bleach to 4 parts water) after each cut.\n" +
                                       "3. Keep the soil under plants clean and free of garden debris. Add a layer of organic compost to prevent the spores from splashing back up onto vegetation.\n" +
                                       "4. Drip irrigation and soaker hoses can be used to help keep the foliage dry.\n" +
                                       "5. For best control, apply copper-based fungicides early, two weeks before disease normally appears or when weather forecasts predict a long period of wet weather. Alternatively, begin treatment when disease first appears, and repeat every 7-10 days for as long as needed.\n" +
                                       "6. Containing copper and pyrethrins, Bonide® Garden Dust is a safe, one-step control for many insect attacks and fungal problems. For best results, cover both the tops and undersides of leaves with a thin uniform film or dust. Depending on foliage density, 10 oz will cover 625 sq ft. Repeat applications every 7-10 days, as needed.\n" +
                                       "7. SERENADE Garden is a broad spectrum, preventative bio-fungicide recommended for the control or suppression of many important plant diseases. For best results, treat prior to foliar disease development or at the first sign of infection. Repeat at 7-day intervals or as needed.\n" +
                                       "8. Remove and destroy all garden debris after harvest and practice crop rotation the following year.\n" +
                                       "9. Burn or bag infected plant parts. Do NOT compost.\n" +
                                       "\n" +
                                       "\n");
                           }
                           else
                           {
                               mMarkdownView = (MarkdownView)findViewById(R.id.markdown_view);
                               mMarkdownView.loadMarkdown("# Maize streak virus\n" +
                                       "# Damage/Symptoms\n" +
                                       "The virus causes a white to yellowish streaking on the leaves.\n" +
                                       "The streaks are very narrow, more or less broken and run parallel along the leaves.\n" +
                                       "Eventually the leaves turn yellow with long lines of green patches\n" +
                                       "Plants infected at early stage usually do not produce any cobs.\n" +
                                       "Yield losses in East Africa vary between 33 and 55% under natural infection conditions\n" +
                                       "# Control\n" +
                                       "1. Use of tolerant / resistant varieties\n" +
                                       "2. Early rouging\n" +
                                       "3. Eradication of grass weeds\n" +
                                       "4. control vector by spraying with dimethoate, malathion\n" +
                                       "5. Avoid overlap of two maize crops\n" +
                                       "6. Crop rotation\n" +
                                       "7. Use certified maize seed");

                           }



                        } catch (JSONException e) {
                          Log.d("exception", String.valueOf(e));
                        }


                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                         Toast.makeText(getApplicationContext(), "Error: " + String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> paramss = new HashMap<String, String>();
                paramss.put("image",ConvertImage);
                paramss.put("user","jjmomanyis");
                return paramss;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);







    }


}