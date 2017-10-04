package com.tech.desafio.itunessearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.tech.desafio.itunes.model.Music;
import com.tech.desafio.itunessearch.R;
import com.tech.desafio.itunessearch.image.ImageLoader;

import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public class MusicAdapter extends CustomBaseAdapter<Music> {

    private int lastPosition = -1;
    private ImageLoader imageLoader;

    public MusicAdapter(Context context) {
        super(context);

        imageLoader = new ImageLoader(context);
    }

    public MusicAdapter(Context context, List<Music> elements) {
        super(context, elements);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MusicViewHolder viewHolder;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_search,
                                                        viewGroup,
                                                        false);

            viewHolder = new MusicViewHolder();
            viewHolder.artist = (TextView) view.findViewById(R.id.music_artist);
            viewHolder.name = (TextView) view.findViewById(R.id.music_name);
            viewHolder.photo = (ImageView) view.findViewById(R.id.music_photo);

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

        viewHolder.name.setText(music.getName());
        viewHolder.artist.setText(music.getArtist());

        imageLoader.displayImage(music.getPhoto(), viewHolder.photo);

        return view;
    }

    private class MusicViewHolder{
        private TextView name;
        private TextView artist;
        private ImageView photo;
    }
}
