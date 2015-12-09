package com.geevisoft.itestapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.geevisoft.itestapp.connection.DBconnection;
import com.geevisoft.itestapp.user.Subject;
import com.geevisoft.itestapp.user.Test;
import com.geevisoft.itestapp.user.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Alvaro on 15/11/2015.
 */
public class ServerRequest {
    private static Context context;
    private ProgressDialog progressDialog;

    private static final String SERVER_ADDRESS = DBconnection.SERVER_ADDRESS;
    private static final String POST_FUNC = "func";
    private static final String POST_USERNAME = "username";
    private static final String POST_PASSWORD = "password";
    private static final String POST_SUBJECT = "subjectID";
    public static User user;

    protected ServerRequest(Context c){
        this.context = c;
        this.progressDialog = new ProgressDialog(c);
    }

    protected void sendLogin(String username, String password){
        LoginTask l = new LoginTask();
        l.execute(username, password, "login");
    }

    // <Params, Progress, Result>
    private class LoginTask extends AsyncTask<String, Void, Boolean> {
        private String username;
        private String password;
        private String func;

        private String msg;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Conectando...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            this.username = params[0];
            this.password = params[1];
            this.func = params[2];

            ArrayList<NameValuePair> p = new ArrayList<>();
            p.add(new BasicNameValuePair(ServerRequest.POST_USERNAME, username));
            p.add(new BasicNameValuePair(ServerRequest.POST_PASSWORD, password));
            p.add(new BasicNameValuePair(ServerRequest.POST_FUNC, func));
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(ServerRequest.SERVER_ADDRESS);

            try {
                post.setEntity(new UrlEncodedFormEntity(p));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                JSONObject result = new JSONObject(EntityUtils.toString(entity));
                if(result.getString(ServerRequest.POST_USERNAME).equals("null")) {
                    this.msg = "Usuario o contraseña incorrectos";
                    return false;
                } else
                    return true;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                if (!isConnected()) // Check Internet Connection
                    this.msg = "Comprueba tu conexión a Internet";
                else
                    msg = "Error al intentar acceder. Inténtalo más tarde";
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result == true) {
                new Prefs(ServerRequest.context, this.username, this.password);
                getOverview(this.username, this.password);
            } else
                Toast.makeText(ServerRequest.context, msg, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }


    protected void getOverview(String username, String password){
        OverviewTask ov = new OverviewTask();
        ov.execute(username, password, "getOverview");
    }

    private class OverviewTask extends AsyncTask<String, Void, Boolean> {
        private String username;
        private String password;
        private String func;

        private String msg;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Cargando asignaturas...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            this.username = params[0];
            this.password = params[1];
            this.func = params[2];

            ArrayList<NameValuePair> p = new ArrayList<>();
            p.add(new BasicNameValuePair(ServerRequest.POST_USERNAME, username));
            p.add(new BasicNameValuePair(ServerRequest.POST_PASSWORD, password));
            p.add(new BasicNameValuePair(ServerRequest.POST_FUNC, func));
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(ServerRequest.SERVER_ADDRESS);
            ServerRequest.user = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(p));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();

                JSONArray result = new JSONArray(EntityUtils.toString(entity));
                if (result.length() > 0) {
                    ServerRequest.user = new User(this.username, this.password);
                    for (int i = 0; i<result.length(); i++) { // Every subject of this user
                        JSONArray subject = result.getJSONArray(i);
                        Subject s = new Subject(decode(subject.get(0).toString()), decode(subject.get(1).toString()));
                        JSONArray tests = subject.getJSONArray(2);
                        for (int j = 0; j<tests.length(); j++) { // Every test of this subject
                            JSONArray tests_details = tests.getJSONArray(j);
                            s.addTest(new Test(decode(tests_details.get(0).toString()), /* Test title */
                                                tests_details.get(1).toString())/* Test start date */
                            );
                        }
                        user.addSubject(s);
                    }
                    return true;
                } else {
                    this.msg = "No se encontraron asignaturas.";
                    return false;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                if (!isConnected()) // Check Internet Connection
                    this.msg = "Comprueba tu conexión a Internet";
                else
                    this.msg = "Error al intentar acceder. Inténtalo más tarde";
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Intent intent = new Intent(ServerRequest.context, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User", ServerRequest.user);
                intent.putExtras(bundle);
                ServerRequest.context.startActivity(intent);  // Change to profile activity

                ((Activity) ServerRequest.context).finish();
            } else
                Toast.makeText(ServerRequest.context, msg, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }

    }


    protected void getSubjectPanel(User user, int subjectPos, String subjectID){
        SubjectPanelTask sp = new SubjectPanelTask();
        ServerRequest.user = user;
        sp.execute(user.getUsername(), user.getPassword(), Integer.toString(subjectPos), subjectID, "getSubjectPanel");
    }

    private class SubjectPanelTask extends AsyncTask<String, Void, Boolean>{
        private String username;
        private String password;
        private int subjectPos;
        private String subjectID;
        private String func;

        private String msg;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Cargando asignatura...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            this.username = params[0];
            this.password = params[1];
            this.subjectPos = Integer.parseInt(params[2]);
            this.subjectID = params[3];
            this.func = params[4];

            ArrayList<NameValuePair> p = new ArrayList<>();
            p.add(new BasicNameValuePair(ServerRequest.POST_USERNAME, username));
            p.add(new BasicNameValuePair(ServerRequest.POST_PASSWORD, password));
            p.add(new BasicNameValuePair(ServerRequest.POST_SUBJECT, subjectID));
            p.add(new BasicNameValuePair(ServerRequest.POST_FUNC, func));
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(ServerRequest.SERVER_ADDRESS);

            try {
                post.setEntity(new UrlEncodedFormEntity(p));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();

                JSONArray result = new JSONArray(EntityUtils.toString(entity));
                if (result.length()>0){

                    JSONArray subject = result;
                    Subject s = new Subject(decode(subject.get(0).toString()), decode(subject.get(1).toString()));
                    JSONArray tests = subject.getJSONArray(2);
                    for (int j = 0; j<tests.length(); j++) { // Every test of this subject
                        JSONArray tests_details = tests.getJSONArray(j);
                        if (tests_details.get(6).toString().equals("true")) /* Test is done */
                            s.addTest(new Test(decode(tests_details.get(0).toString()), /* Test title */
                                    tests_details.get(1).toString(),                    /* Test mark */
                                    tests_details.get(2).toString(),                    /* Test max mark */
                                    tests_details.get(3).toString(),                    /* Test start date */
                                    tests_details.get(4).toString(),                    /* Test end date*/
                                    tests_details.get(5).toString()                     /* Test max time */
                            ));
                        else /* Test is not done */
                            s.addTest(new Test(decode(tests_details.get(0).toString()), /* Test title */
                                    tests_details.get(3).toString(),                    /* Test start date */
                                    tests_details.get(4).toString(),                    /* Test end date*/
                                    tests_details.get(5).toString()                     /* Test max time */
                            ));
                    }
                    user.updateSubject(subjectPos, s);
                    return true;
                } else {
                    this.msg = "No se pudo acceder a la asignatura.";
                    return false;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                if (!isConnected()) // Check Internet Connection
                    this.msg = "Comprueba tu conexión a Internet";
                else
                    msg = "Error al intentar acceder. Inténtalo más tarde";
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){
                Intent intent = new Intent(ServerRequest.context, SubjectActivity.class);
                intent.putExtra("subject", subjectPos);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User", ServerRequest.user);
                intent.putExtras(bundle);
                ServerRequest.context.startActivity(intent);
            } else
                Toast.makeText(ServerRequest.context, msg, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }


    /**
     * true = connected | false = disconnected
     */
    private boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();

    }

    /**
     * Decode (from UTF-8) to ISO-8859-1
     */
    private String decode(String s){
        try {
            return new String(s.getBytes("ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            return "null";
        }
    }
}
