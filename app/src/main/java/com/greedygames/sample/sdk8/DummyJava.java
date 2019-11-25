package com.greedygames.sample.sdk8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DummyJava extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button translate = new Button(this);
                TextView postText = new TextView(getApplicationContext());

                String number = "3";

                String output = "";

                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://www.google.com");

                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept-Charset","UTF-8");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    connection.connect();

                    String parameters = "random parameters";
                    OutputStream out = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(out, "UTF-8"));
                    writer.write(parameters);
                    writer.flush();
                    writer.close();
                    out.close();

                    int responseCode = connection.getResponseCode();

                    if(responseCode == HttpURLConnection.HTTP_OK){
                        output += "Status: True\n";
                        String line;
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        while ((line = reader.readLine()) != null) {
                            String[] list = line.split(",");
                            String[] message = list[2].split(":");
                            output += message[1];
                        }
                        reader.close();
                    }
                    else{
                        output += "Status: False\n";
                    }
                    postText.setText(output);

                    AlertDialog.Builder builder = new AlertDialog.Builder(DummyJava.this);
                    builder.setMessage(output);
                    builder.setTitle("Magic Number");
                    builder.setCancelable(true);
                    builder.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }
                    );
                    AlertDialog alert = builder.create();
                    alert.show();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }


            }
}
