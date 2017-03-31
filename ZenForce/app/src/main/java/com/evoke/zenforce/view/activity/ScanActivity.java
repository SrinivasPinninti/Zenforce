package com.evoke.zenforce.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.database.beanentity.BarCodeEntityBean;
import com.evoke.zenforce.model.database.dao.BarCodeDAO;
import com.evoke.zenforce.utility.Util;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
        RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "ScanActivity";

    private static final String URL = "http://ec2-52-39-171-149.us-west-2.compute.amazonaws.com/zenforce/visit/insertProductDetails";

    private BarCodeEntityBean bean;

    private long mVisitId;

    private long insertId1;
    private long insertId2;
    private long insertId3;
    private long insertId4;


    //------------------------ Barcode Info -------------------------//

    private EditText barcode;
    private TextView name;
    private TextView expDate;
    private TextView price;
    private TextView weight;
    private TextView temp;
    private ImageView type;
    private Button scan;

    //------------------------ Ques 1 -------------------------//

    private RelativeLayout rl_ques1;
    private TextView tv_ques1;
    private RadioGroup rg_ques1;
    private RadioButton rb_yes1;
    private RadioButton rb_no1;

    private String ques1_ans = "0";

    //------------------------ Ques 2 -------------------------//

    private RelativeLayout rl_ques2;
    private TextView tv_ques2;
    private Spinner sp_position;

    private String ques2_ans = "Top";

    //------------------------ Ques 3 -------------------------//

    private RelativeLayout rl_ques3;
    private TextView tv_ques3;
    private RadioGroup rg_ques3;
    private RadioButton rb_yes3;
    private RadioButton rb_no3;

    private String ques3_ans = "0";

    //------------------------ Ques 4 -------------------------//

    private RelativeLayout rl_ques4;
    private TextView tv_ques4;
    private RadioGroup rg_ques4;
    private RadioButton rb_yes4;
    private RadioButton rb_no4;
    private TextView tv_take_photo;
    private ImageView iv_take_photo;
    private ImageView iv_discount_coupon;


    //------------------------Slide up------------------------//

    private TextView tv_email;
    private TextView tv_save;
    private TextView tv_save_mail;

    private RelativeLayout rl_choose_action;

    private String ques4_ans = "0";

    private String image_string;
    private RelativeLayout mRootView;
    private ScrollView root_scroll;




//    private byte[] byteArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Report Form");
        initUI();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mVisitId = bundle.getLong("visitId");
            Log.d(TAG, "mVisitId : " + mVisitId);
        }

//        Log.d(TAG, "insertId1 : " + insertId1);
//        Log.d(TAG, "insertId2 : " + insertId2);
//        Log.d(TAG, "insertId3 : " + insertId3);
//        Log.d(TAG, "insertId4 : " + insertId4);


        if (insertId1 == 0) {

            BarCodeEntityBean bean = new BarCodeEntityBean();
            bean.setNumber("8901808004035");
            bean.setName("Eco Valley Organic Green Tea");
            bean.setExp("01-07-2019");
            bean.setPrice("150.00");
            bean.setType("Veg");
            bean.setWeight("250 gms");
            bean.setTemp("10c");
            bean.setTimeStamp(System.currentTimeMillis());

            BarCodeDAO dao = BarCodeDAO.getSingletonInstance(this);
            insertId1 = dao.insert(bean);
        }

        if (insertId2 == 0) {

            BarCodeEntityBean bean1 = new BarCodeEntityBean();
            bean1.setNumber("8957461201238");
            bean1.setName("Maggie Noodles");
            bean1.setExp("08-31-2018");
            bean1.setPrice("90.00");
            bean1.setType("NonVeg");
            bean1.setWeight("250 gms");
            bean1.setTemp("5c");
            bean1.setTimeStamp(System.currentTimeMillis());

            BarCodeDAO dao1 = BarCodeDAO.getSingletonInstance(this);
            insertId2 = dao1.insert(bean1);
        }

        if (insertId3 == 0) {

            BarCodeEntityBean bean2 = new BarCodeEntityBean();
            bean2.setNumber("7957451201231");
            bean2.setName("Boost");
            bean2.setExp("11-25-2021");
            bean2.setPrice("280.00");
            bean2.setType("Veg");
            bean2.setWeight("400 gms");
            bean2.setTemp("10c");
            bean2.setTimeStamp(System.currentTimeMillis());

            BarCodeDAO dao2 = BarCodeDAO.getSingletonInstance(this);
            insertId3 = dao2.insert(bean2);
        }

        if (insertId4 == 0) {

            BarCodeEntityBean bean3 = new BarCodeEntityBean();
            bean3.setNumber("8957234512019");
            bean3.setName("Himalaya");
            bean3.setExp("12-12-2018");
            bean3.setPrice("160.00");
            bean3.setType("Veg");
            bean3.setWeight("200 gms");
            bean3.setTemp("4c");
            bean3.setTimeStamp(System.currentTimeMillis());

            BarCodeDAO dao3 = BarCodeDAO.getSingletonInstance(this);
            insertId4 = dao3.insert(bean3);
        }
    }

    private void initUI() {

        mRootView       = (RelativeLayout) findViewById(R.id.root);
        root_scroll       = (ScrollView) findViewById(R.id.root_scroll);

        //------------------------ Barcode Info -------------------------//

        barcode         = (EditText) findViewById(R.id.et_barcode_number);
        name            = (TextView) findViewById(R.id.name);
        expDate         = (TextView) findViewById(R.id.expDate);
        price           = (TextView) findViewById(R.id.price);
        weight          = (TextView) findViewById(R.id.weight);
        temp            = (TextView) findViewById(R.id.temp);

        type            = (ImageView) findViewById(R.id.type);
        scan            = (Button) findViewById(R.id.btn_scan);


        //--------------------------Ques 1 -------------------- //

        rl_ques1       = (RelativeLayout) findViewById(R.id.rl_ques1);
        tv_ques1        = (TextView) findViewById(R.id.tv_ques1);
        rg_ques1        = (RadioGroup) findViewById(R.id.rg_ques1);
        rb_yes1         = (RadioButton) findViewById(R.id.rb_yes1);
        rb_no1         = (RadioButton) findViewById(R.id.rb_no1);

        rg_ques1.setOnCheckedChangeListener(this);

        //--------------------------Ques 2 -------------------- //

        rl_ques2        = (RelativeLayout) findViewById(R.id.rl_ques2);
        tv_ques2        = (TextView) findViewById(R.id.tv_ques2);
        sp_position     = (Spinner) findViewById(R.id.sp_position);

        //--------------------------Ques 3 -------------------- //

        rl_ques3       = (RelativeLayout) findViewById(R.id.rl_ques3);
        tv_ques3        = (TextView) findViewById(R.id.tv_ques3);
        rg_ques3        = (RadioGroup) findViewById(R.id.rg_ques3);
        rb_yes3         = (RadioButton) findViewById(R.id.rb_yes3);
        rb_no3         = (RadioButton) findViewById(R.id.rb_no3);

        rg_ques3.setOnCheckedChangeListener(this);

        //--------------------------Ques 4 -------------------- //

        rl_ques4       = (RelativeLayout) findViewById(R.id.rl_ques4);
        tv_ques4       = (TextView) findViewById(R.id.tv_ques4);
        rg_ques4       = (RadioGroup) findViewById(R.id.rg_ques4);
        rb_yes4        = (RadioButton) findViewById(R.id.rb_yes4);
        rb_no4         = (RadioButton) findViewById(R.id.rb_no4);

        tv_take_photo = (TextView) findViewById(R.id.tv_take_photo);
        iv_take_photo = (ImageView) findViewById(R.id.iv_take_photo);
        iv_discount_coupon = (ImageView) findViewById(R.id.iv_discount_coupon);

        rg_ques4.setOnCheckedChangeListener(this);


        //------------------------slide up --------------------//

        tv_email        = (TextView) findViewById(R.id.tv_email);
        tv_save         = (TextView) findViewById(R.id.tv_save);
        tv_save_mail    = (TextView) findViewById(R.id.tv_save_mail);

        tv_email.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_save_mail.setOnClickListener(this);


        rl_choose_action = (RelativeLayout) findViewById(R.id.rl_choose_action);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.positions_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_position.setAdapter(adapter);
        sp_position.setOnItemSelectedListener(this);

        iv_take_photo   = (ImageView) findViewById(R.id.iv_take_photo);
        iv_take_photo.setOnClickListener(this);


    }

    //product barcode mode
    public void scanBar(View v) {
        IntentIntegrator scanIntegrator = new IntentIntegrator(ScanActivity.this);
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, " onActivityResult requestCode : " + requestCode);
        if (requestCode == 700) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, " PHOTO RESULT_OK ");
                String imagePath = intent.getStringExtra("imagePath");
                previewCapturedImage(imagePath);

            }
        } else {

            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            Log.d(TAG, "scanningResult : " + scanningResult);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                Log.d(TAG, "FORMAT: " + scanFormat);
                Log.d(TAG, "CONTENT: " + scanContent);

                if (scanContent != null) {
                    getProductDetails(scanContent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No Barcode detected!", Toast.LENGTH_SHORT);
                    toast.show();
                }


            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No Barcode detected!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    private void previewCapturedImage(String imagePath) {

        try {
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            iv_discount_coupon.setVisibility(View.VISIBLE);
            iv_discount_coupon.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte [] byte_arr = stream.toByteArray();
            image_string = Base64.encodeToString(byte_arr, Base64.DEFAULT);
            Log.d(TAG, " image_string : " + image_string);

//            Bitmap bm = BitmapFactory.decodeFile(imagePath);
//            ByteArrayOutputStream bao = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//            byte[] ba = bao.toByteArray();
//            byteArr = Base64.encode(ba, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception...");
        }
    }

    private void getProductDetails(String content) {
        BarCodeDAO dao = new BarCodeDAO(this);
        BarCodeEntityBean bean = dao.getData(content);
        Log.d(TAG, " bean : " + bean);

        if (bean != null) {
            updateUI(bean);
        }
    }

    private void updateUI(BarCodeEntityBean bean) {

        this.bean = bean;

        barcode.setText(bean.getNumber());
        name.setText("Name : " + bean.getName());
        expDate.setText("Exp Date : " + bean.getExp());
        price.setText("MRP Rs : " + bean.getPrice());
        weight.setText("Net Weight : " + bean.getWeight());
        temp.setText("Temp : " + bean.getTemp());

        String type1 = bean.getType();
        type.setImageResource(type1.equals("Veg") ? R.drawable.veg : R.drawable.non_veg);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ques2_ans = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_upload_activity_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_submit:
//                slideUpDown();
                if (bean != null) {
                    slideUpDown();
                } else {

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void slideUpDown() {

        Log.d(TAG, " slideUpDown Called menu show : " + isMenuShown());

        if (isMenuShown()) {

            Log.d(TAG, " hide Menu.....");

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
            animation.setStartOffset(0);
            rl_choose_action.startAnimation(animation);
            rl_choose_action.setVisibility(View.GONE);

        } else {

            Log.d(TAG, " display Menu.....");

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
            animation.setStartOffset(0);
            rl_choose_action.startAnimation(animation);
            rl_choose_action.setVisibility(View.VISIBLE);
        }

    }

    private boolean isMenuShown() {
        return rl_choose_action.getVisibility() == View.VISIBLE;
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_take_photo:
                Intent photoIntent = new Intent(this, PhotoActivity.class);
                Log.d(TAG, " startActivityForResult PhotoActivity.....");
                startActivityForResult(photoIntent, 700);
                break;
            case R.id.tv_email:

                if (Util.isEmpty(barcode)) {

                    Toast.makeText(this,  "Fields are empty!", Toast.LENGTH_SHORT).show();

                } else {

                    slideUpDown();
                    sendEmailWithPdf(getPdfFile());
//                    finish();
//                sendEmailWithPdf(createPdf());

                }

                break;
            case R.id.tv_save:
                new DataUploadTask(this).execute();
                slideUpDown();
                break;
            case R.id.tv_save_mail:
                new DataUploadTask(this).execute();
                slideUpDown();
                break;

        }
    }

    public String getJsonObj(BarCodeEntityBean bean) {


        try {

            JSONObject main = new JSONObject();
            JSONObject req = new JSONObject();

            req.put("visit_id", "404");
            req.put("barcode", bean.getNumber());
            req.put("name", bean.getName());
            req.put("product_type", bean.getType());
            req.put("exp_date", bean.getExp());
            req.put("weight", bean.getWeight());
            req.put("price", bean.getPrice());
            req.put("temp", bean.getTemp());

            req.put("out_of_stock", ques1_ans);
            req.put("shelf_position", ques2_ans);
            req.put("discount_available", ques4_ans);
            req.put("competitors_available", ques3_ans);

            req.put("image", image_string);
            req.put("time", getCurrentTime());

            main.put("request",req);

            Log.d(TAG, " jsonObject1 : " + main.toString());

            return  main.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (group.getId()) {

            case R.id.rg_ques1:
                if (checkedId == R.id.rb_no1) {
                    ques1_ans = "1";
                    rl_ques2.setVisibility(View.VISIBLE);
                    rl_ques3.setVisibility(View.VISIBLE);
                    rl_ques4.setVisibility(View.VISIBLE);
                } else {
                    ques1_ans = "0";
                    rl_ques2.setVisibility(View.GONE);
                    rl_ques3.setVisibility(View.GONE);
                    rl_ques4.setVisibility(View.GONE);
                }
                break;

            case R.id.rg_ques3:
                ques3_ans = ((checkedId == R.id.rb_yes3) ? "1" : "0");
                break;

            case R.id.rg_ques4:
                if (checkedId == R.id.rb_yes4) {
                    ques4_ans = "1";
                    tv_take_photo.setVisibility(View.VISIBLE);
                    iv_take_photo.setVisibility(View.VISIBLE);
                } else {
                    ques4_ans = "0";
                    tv_take_photo.setVisibility(View.GONE);
                    iv_take_photo.setVisibility(View.GONE);
                }
                break;

        }

    }

    private class DataUploadTask extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        private Context context;

        public DataUploadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Submitting form..");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String result =  postCall(URL, getJsonObj(bean));
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (s != null) {
                Toast.makeText(context, "Report form submitted successfully.", Toast.LENGTH_LONG).show();
                sendEmailWithPdf(getPdfFile());
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            } else {
                Toast.makeText(context, "Report form submission failed.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String postCall(String requestURL, String json) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authkey", "efd4deb17cf6bfe7e617c8784c8dced512fab37b");
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);


            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(json);

            writer.flush();
            writer.close();
            os.close();
            int responseCode=con.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, " response : " + response);

        return response;
    }


    private String getCurrentTime() {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        return (String) df.format("yyyy-MM-dd hh:mm:ss", new java.util.Date());
    }

    private File getPdfFile() {

        //Create a directory for your PDF
        File pdfDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "ZenForceApp");

        if (!pdfDir.exists()){
            if (!pdfDir.mkdirs()){
                Log.e(TAG, " directory already exist....");
                return pdfDir;
            }
        }

        PdfDocument document = new PdfDocument();

        // repaint the user's text into the page
        View view = findViewById(R.id.root_scroll);

        // crate a page description
        int pageNumber = 1;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(view.getWidth(),
                view.getHeight() + 550, pageNumber).create();

        // create a new page from the PageInfo
        PdfDocument.Page page = document.startPage(pageInfo);

        view.draw(page.getCanvas());

        // do final processing of the page
        document.finishPage(page);

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        String pdfName = "pdfdemo"
                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

//        File outputFile = new File("/sdcard/PDFDemo_AndroidSRC/", pdfName);
        File outputFile = new File(pdfDir, pdfName);

        try {
            outputFile.createNewFile();
            OutputStream out = new FileOutputStream(outputFile);
            document.writeTo(out);
            document.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;
    }

    private void sendEmailWithPdf(File pdfFile) {

        Log.d(TAG, " pdfFile : " + pdfFile);
        if (pdfFile == null) {
            return;
        }

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, "receiver_email_address");
        email.putExtra(Intent.EXTRA_SUBJECT, "subject");
        email.putExtra(Intent.EXTRA_TEXT, "email body");
        Uri uri = Uri.fromFile(pdfFile);
        email.putExtra(Intent.EXTRA_STREAM, uri);
        Log.d(TAG, " uri : " + uri.toString());
        email.setType("application/pdf");
        email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(email);
    }



}