package com.tech.desafio.utils.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by oliveieb on 04/10/2017.
 */

public abstract class BaseAdapter<T extends Comparable> extends android.widget.BaseAdapter {

    private List<T> elements;
    protected Context context;

    public BaseAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public BaseAdapter(Context context, List<T> elements) {
        this.elements = elements;
        this.context = context;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public T getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    public void add(T element){
        if (element == null){
            throw new IllegalArgumentException("Element cannot be null");
        }

        elements.add(element);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> elements){
        if (elements == null){
            throw new IllegalArgumentException("Elements cannot be null");
        }

        this.elements.addAll(elements);

        Collections.sort(this.elements);

        notifyDataSetChanged();
    }

    public void remove(T element){
        if (element == null){
            return;
        }

        elements.remove(element);
        notifyDataSetChanged();
    }

    public T remove(int position){
        T removed = elements.remove(position);
        notifyDataSetChanged();
        return removed;
    }

    public void clear(){
        elements.clear();
        notifyDataSetChanged();
    }
}
