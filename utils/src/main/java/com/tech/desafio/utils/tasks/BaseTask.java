package com.tech.desafio.utils.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by oliveieb on 04/10/2017.
 */

public abstract class BaseTask<Params, Progress, Result> extends
                           AsyncTask<Params, Progress, Result> {

    private final String progressTitle;
    private final String progressMessage;

    protected String errorMessage;

    protected Context context;
    private ProgressDialog progress;

    public BaseTask(Context context, String progressTitle, String progressMessage) {
        this.context = context;
        
        this.progressTitle = progressTitle;
        this.progressMessage = progressMessage;
    }

    protected abstract void doAfterProcessing(Result result);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progress = new ProgressDialog(context);
        progress.setIndeterminate(true);
        progress.setTitle(progressTitle);
        progress.setMessage(progressMessage);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        progress.show();
    }

    @Override
    protected final void onPostExecute(Result result) {
        super.onPostExecute(result);

        if (errorMessage != null){
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i("ITUNES", "getting results with no error...");

        doAfterProcessing(result);

        progress.dismiss();
    }
}
