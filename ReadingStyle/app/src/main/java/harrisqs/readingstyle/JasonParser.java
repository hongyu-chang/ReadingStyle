package harrisqs.readingstyle;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by HarrisQs on 2017/1/22.
 */

public class JasonParser extends AsyncTask<String , Integer , String>
{
    private String bookStoreURL = "https://cloud.culture.tw/frontsite/trans/emapOpenDataAction.do?method=exportEmapJson&typeId=M";
    private HttpURLConnection connectTheWeb;
    private JSONArray jsonArrayOfData;
    private ArrayList<String> storeNameArray = new ArrayList<>();
    private ArrayList<String> cityNameArray = new ArrayList<>();
    private ArrayList<String> addressArray = new ArrayList<>();
    private ArrayList<String> businessHoursArray = new ArrayList<>();
    private ArrayList<String> pictureArray = new ArrayList<>();
    private ArrayList<String> phoneArray = new ArrayList<>();
    private ArrayList<String> emailArray = new ArrayList<>();
    private ArrayList<String> facebookArray = new ArrayList<>();
    private ArrayList<String> websiteArray = new ArrayList<>();
    private ArrayList<String> arriveWayArray = new ArrayList<>();
    private ArrayList<String> introArray = new ArrayList<>();
    private ArrayList<String> longitudeArray = new ArrayList<>();
    private ArrayList<String> latitudeArray = new ArrayList<>();

    @Override
    protected void onPreExecute() //執行前 設定可以在這邊設定
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) //主要的執行任務
    {
        connectInternet();
        readAndParseData();
        storeData();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) //執行中 可以在這邊告知使用者進度
    {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) //執行後 完成背景任務
    {
        super.onPostExecute(result);
        //TODO:Toast.makeText(getApplicationContext(), "更新完成", Toast.LENGTH_SHORT).show();
    }

    private void connectInternet()
    {
        try
        {
            URL connectBookStoreData = new URL(bookStoreURL);
            connectTheWeb = (HttpURLConnection) connectBookStoreData.openConnection();
            connectTheWeb.setDoInput(true);
            connectTheWeb.setDoOutput(true);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void readAndParseData()
    {
        try
        {
            BufferedReader dataReader = new BufferedReader(new InputStreamReader(connectTheWeb.getInputStream(), "UTF-8"));
            jsonArrayOfData = new JSONArray(dataReader.readLine());
            dataReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void storeData()
    {
        try
        {
            for(int i = 0; i < jsonArrayOfData.length(); i++)
            {
                storeNameArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("name")));
                cityNameArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("cityName")));
                addressArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("address")));
                businessHoursArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("openTime")));
                phoneArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("phone")));
                emailArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("email")));
                facebookArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("facebook")));
                websiteArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("website")));
                arriveWayArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("arriveWay")));
                longitudeArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("longitude")));
                latitudeArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("latitude")));
                //TODO: pictureArray 要再設計一下
                if(jsonArrayOfData.getJSONObject(i).opt("representImage") == null || jsonArrayOfData.getJSONObject(i).opt("representImage") == "null")
                {
                    pictureArray.add("null");
                }
                else
                {
                    pictureArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("representImage")));
                }
                if(jsonArrayOfData.getJSONObject(i).opt("intro") == null)
                {
                    introArray.add("無簡介");
                }
                else
                {
                    introArray.add(String.valueOf(jsonArrayOfData.getJSONObject(i).opt("cityName")));
                }
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}
