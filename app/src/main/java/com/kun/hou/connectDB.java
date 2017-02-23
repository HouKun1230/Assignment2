package com.kun.hou;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by kun on 2017-02-22.
 */

public class connectDB extends AsyncTask<Void, String, String> {


    ProgressDialog mProgressDialog;
    Context context;
    private String url = "";
    private String user = "";
    private String pass="";

    public connectDB(Context context, String pwd, String url)
    {
        this.context = context;
        this.url = url;
        this.pass = pwd;
    }

    protected void onPreExecute() {
        mProgressDialog = ProgressDialog.show(context, "",
                "Please wait, getting database...");
    }

    protected String doInBackground(Void... params) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection(url, user, pass);
            java.sql.Statement st = con.createStatement();
            String sql = "Select into heart Value from Temperature where UserId = " + "\"" + "001" + "\"" ;
            java.sql.ResultSet rs = st.executeQuery(sql);


//            while (rs.next()) {
////                    String field= rs.getString("field");
////                    MainActivity.playerList.add(new String(field));
//                encrypt en = new encrypt();
//                tem  = en.decrypt(key, intiVector, rs.getString("Value"));
//
//            }
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "Complete";
    }

    protected void onPostExecute(String result) {
        if (result.equals("Complete")) {
            mProgressDialog.dismiss();
        }
    }


}
