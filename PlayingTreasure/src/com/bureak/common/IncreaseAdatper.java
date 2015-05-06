package com.bureak.common;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @Title: IncreaseAdatper
 * @Package ${package_name}
 * @Description: 适配器封装类简版
 * Created by bushijie33@gmail.com on 2013/11/2.
 */
public class IncreaseAdatper<T> extends BaseAdapter {
    public IncreaseAdatper() {}

    public static interface OnLoadPositionListener {
        public void onLoadPosition(int max, int position);
    }

    protected List<T> array;

    public IncreaseAdatper(List<T> array) {
        this.array = array;
    }


    public void increase(List<T> array) {
        if (this.array == null) {
            this.array = array;
        } else {
            this.array.addAll(array);
        }
        notifyDataSetChanged();
    }

    public void add(T t) {
        if (this.array == null) {
            this.array = new ArrayList<T>();
        }
        this.array.add(t);
        notifyDataSetChanged();
    }
   
    public void remove(T t, Comparable<T> c) {
        if (this.array != null) {
            this.array.remove(t);
        }
    }

    public boolean contains(T t) {
        if (this.array != null) {
            return this.array.contains(t);
        }
        return false;
    }

    public void update(List<T> array) {
        this.array = array;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (this.array != null) {
            this.array.remove(position);
            notifyDataSetChanged();
        }
    }


    public void clear() {
        if (this.array != null) {
            this.array.clear();
            notifyDataSetChanged();
        }
    }
    public void addItemLast(List<T> datas) {
        array.addAll(datas);
        notifyDataSetChanged();
    }

    public void addItemTop(List<T> datas) {
        clear();
        for (T info : datas) {
            array.add(info);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return array == null ? 0 : array.size();
    }

    @Override
    public T getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }
    
}
