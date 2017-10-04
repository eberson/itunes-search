package com.tech.desafio.itunessearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tech.desafio.itunes.model.Music;
import com.tech.desafio.itunessearch.R;
import com.tech.desafio.itunessearch.tasks.LikeTask;
import com.tech.desafio.utils.adapter.BaseAdapter;
import com.tech.desafio.utils.image.ImageLoader;

import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class MusicAdapter extends BaseAdapter<Music> {

    private int lastPosition = -1;
    private ImageLoader imageLoader;

    private boolean useCommands;

    public MusicAdapter(Context context) {
        this(context, true);
    }

    public MusicAdapter(Context context, boolean useCommands){
        super(context);

        this.useCommands = useCommands;

        imageLoader = new ImageLoader(context, R.mipmap.ic_launcher);
    }

    public MusicAdapter(Context context, List<Music> elements) {
        super(context, elements);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MusicViewHolder viewHolder;

        if (view == null){
            int idLayout = useCommands ? R.layout.layout_item_search : R.layout.layout_item_liked;

            view = LayoutInflater.from(context).inflate(idLayout,
                                                        viewGroup,
                                                        false);

            viewHolder = new MusicViewHolder();
            viewHolder.artist = (TextView) view.findViewById(R.id.music_artist);
            viewHolder.name = (TextView) view.findViewById(R.id.music_name);
            viewHolder.photo = (ImageView) view.findViewById(R.id.music_photo);

            if (useCommands){
                viewHolder.setButtons((ImageButton)view.findViewById(R.id.btn_like),
                                      (ImageButton)view.findViewById(R.id.btn_dislike));
            }

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (MusicViewHolder) view.getTag();
        }

        Animation animation =
                AnimationUtils.loadAnimation(context, (position > lastPosition) ?
                                                       R.anim.up_from_bottom :
                                                       R.anim.down_from_top);

        view.startAnimation(animation);
        lastPosition = position;

        Music music = getItem(position);

        viewHolder.music = music;
        viewHolder.name.setText(music.getName());
        viewHolder.artist.setText(music.getArtist());


        imageLoader.displayImage(music.getPhoto(), viewHolder.photo);

        return view;
    }

    private class MusicViewHolder{
        private TextView name;
        private TextView artist;
        private ImageView photo;

        private ImageButton like;
        private ImageButton dislike;

        private Music music;

        public void setButtons(ImageButton like, ImageButton dislike){
            this.like = like;
            this.like.setTag(this);
            this.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Music music = ((MusicViewHolder) view.getTag()).music;
                    new LikeTask(context).execute(new LikeTask.LikeParam(LikeTask.LikeAction.LIKE,
                                                  music));
                }
            });

            this.dislike = dislike;
            this.dislike.setTag(this);
            this.dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Music music = ((MusicViewHolder) view.getTag()).music;
                    new LikeTask(context).execute(new LikeTask.LikeParam(LikeTask.LikeAction.DISLIKE,
                                                  music));
                }
            });
        }
    }
}
