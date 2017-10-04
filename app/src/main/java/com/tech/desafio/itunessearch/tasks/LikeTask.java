package com.tech.desafio.itunessearch.tasks;

import android.content.Context;

import com.tech.desafio.itunes.model.Music;
import com.tech.desafio.itunessearch.R;
import com.tech.desafio.itunessearch.store.LikedMusicStore;
import com.tech.desafio.utils.tasks.BaseTask;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class LikeTask extends BaseTask<LikeTask.LikeParam, Void, Void> {

    public LikeTask(Context context) {
        super(context,
              context.getResources().getString(R.string.progress_title),
              context.getResources().getString(R.string.progress_message));
    }

    @Override
    protected void doAfterProcessing(Void aVoid) {

    }

    @Override
    protected Void doInBackground(LikeParam... likeParams) {
        LikeParam param = likeParams[0];

        LikedMusicStore musicStore = LikedMusicStore.getInstance(context);

        if (param.getAction() == LikeAction.LIKE){
            musicStore.store(param.getMusic());
            return null;
        }

        if(param.getAction() == LikeAction.DISLIKE){
            musicStore.remove(param.getMusic());
            return null;
        }

        return null;
    }

    public enum LikeAction{
        LIKE,
        DISLIKE
    }

    public static class LikeParam{
        private LikeAction action;
        private Music music;

        public LikeParam(LikeAction action, Music music) {
            this.action = action;
            this.music = music;
        }

        public LikeAction getAction() {
            return action;
        }

        public Music getMusic() {
            return music;
        }
    }
}
