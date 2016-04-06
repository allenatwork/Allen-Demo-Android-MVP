package vn.allenw.model;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Allen on 01-Apr-16.
 */
public class ListUserHelper {

    private String url;
    private OnLoadUserResult onLoadUserResult;
    private List<User> list;

    public ListUserHelper(String url) {
        this.url = url;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public void setOnLoadUserResult(OnLoadUserResult onLoadUserResult) {
        this.onLoadUserResult = onLoadUserResult;
    }

    public void getUserList(boolean useCache) {
        if (!useCache || list == null) {
            Log.d("PJ3", "not use cache");
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        } else {
            Log.d("PJ3", "use cache");
            onLoadUserResult.onLoadUserSuccess(list);
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //do your request in here so that you don't interrupt the UI thread
            try {
                return downloadContent(params[0]);
            } catch (IOException e) {
                return "Unable to retrieve data. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            if (!TextUtils.isEmpty(result)) {
                try {
                    Log.d("PJ3", result);
                    if (list != null && list.size() != 0)
                        list.clear();
                    list = gson.fromJson(result, new TypeToken<List<User>>() {
                    }.getType());
                } catch (JsonSyntaxException ex) {
                    Log.d("PJ3", "Json Syntax Error");
                }

            }
            if (onLoadUserResult != null) {
                if (list != null) onLoadUserResult.onLoadUserSuccess(list);
                else onLoadUserResult.onLoadUserError();
            } else {
                throw new RuntimeException("Please set callback for OnLoadUserResult");
            }
        }
    }

    private String downloadContent(String myurl) throws IOException {
        InputStream is = null;
        HttpURLConnection conn = null;
        int length = 500;

        try {
            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
//            int response = conn.getResponseCode();
            is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } finally {
            if (is != null) {
                is.close();
            }
            conn.disconnect();
        }
    }
}
