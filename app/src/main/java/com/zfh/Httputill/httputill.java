package com.zfh.Httputill;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Administrator on 2018\2\25 0025.
 */

public class httputill {

    public String utill(String path, String params){
        try {
            Log.d("httputill","进入");
            URL url=new URL(path);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-type","application/json");
            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setReadTimeout(50000);
            httpURLConnection.setRequestProperty("Charset","UTF-8");
            httpURLConnection.setRequestProperty("Accept","application/json");
            httpURLConnection.connect();
            PrintWriter printWriter=new PrintWriter(httpURLConnection.getOutputStream());
           // Log.d("传进来的json字符串",params);
            printWriter.write(params);
            printWriter.flush();
            printWriter.close();
            if(httpURLConnection.getResponseCode()==200){
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                int len;
                byte[] temp=new byte[1024];
                while ((len=bufferedInputStream.read(temp))!=-1){
                    byteArrayOutputStream.write(temp,0,len);
                    byteArrayOutputStream.flush();
                }
                byteArrayOutputStream.close();
                String result=byteArrayOutputStream.toString("UTF-8");
                return result;
            }else {
                Log.d("返回码", String.valueOf(httpURLConnection.getResponseCode()));
                return "error";
            }
    } catch (ProtocolException e) {
            Log.d("httputill出现异常Exception","ProtocolException e");
            e.printStackTrace();
            return "error";
        } catch (MalformedURLException e) {
            Log.d("httputill出现异常Exception","MalformedURLException e");
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            Log.d("httputill出现异常Exception","IOException e");
            e.printStackTrace();
            return "error";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
  //静态方法 无需实例话，系统一开始已经实例化了
    public static   @NonNull  String lianjie(@NonNull String path, @NonNull String params){
        try {
            Log.d("httputill","进入");
            URL url=new URL(path);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-type","application/json");
            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setReadTimeout(50000);
            httpURLConnection.setRequestProperty("Charset","UTF-8");
            httpURLConnection.setRequestProperty("Accept","application/json");
            httpURLConnection.connect();
            PrintWriter printWriter=new PrintWriter(httpURLConnection.getOutputStream());
            // Log.d("传进来的json字符串",params);
            printWriter.write(params);
            printWriter.flush();
            printWriter.close();
            if(httpURLConnection.getResponseCode()==200){
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                int len;
                byte[] temp=new byte[1024];
                while ((len=bufferedInputStream.read(temp))!=-1){
                    byteArrayOutputStream.write(temp,0,len);
                    byteArrayOutputStream.flush();
                }
                byteArrayOutputStream.close();
                String result=byteArrayOutputStream.toString("UTF-8");
                return  result;
            }else {
                Log.d("返回码", String.valueOf(httpURLConnection.getResponseCode()));
                return "error";
            }
        } catch (ProtocolException e) {
            Log.d("httputill出现异常Exception","ProtocolException e");
            e.printStackTrace();
            return "error";
        } catch (MalformedURLException e) {
            Log.d("httputill出现异常Exception","MalformedURLException e");
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            Log.d("httputill出现异常Exception","IOException e");
            e.printStackTrace();
            return "error";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}
