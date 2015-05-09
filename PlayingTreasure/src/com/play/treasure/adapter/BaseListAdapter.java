package com.play.treasure.adapter;

import java.util.ArrayList;
import java.util.List;

import android.widget.BaseAdapter;

/**
 * @ClassName: BaseListAdapter
 * @Description: 自定义Adapter
 * @author 王超
 * @date 2014年4月9日 下午3:11:58
 * 
 * @param <T>
 */
public abstract class BaseListAdapter<T> extends BaseAdapter 
{

	protected final List<T> list;

	/**
	 * 构造方法
	 */
	public BaseListAdapter() {
		super();
		this.list = new ArrayList<T>();
	}

	/**
	 * @Title: addAll
	 * @Description: 添加list
	 * @param @param list
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean addAll(List<? extends T> list) {
		return this.list.addAll(list);
	}

	/**
	 * @Title: clear
	 * @Description: 清空
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void clear() {
		if (list != null) {
			this.list.clear();
		}
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	public T getItem(int i)
	{
		return this.list.get(i);
	}

	public long getItemId(int id) {
		return id;
	}

	public boolean isEmpty() {
		return list == null ? true : list.isEmpty();
	}

	/**
	 * @Title: remove
	 * @Description: 移除
	 * @param @param i
	 * @param @return 设定文件
	 * @return T 返回类型
	 * @throws
	 */
	public T remove(int i) {
		return list.remove(i);
	}

	public boolean remove(T t) {
		return list.remove(t);
	}

	/**
	 * @Title: addItem
	 * @Description: 添加一个
	 * @param @param t 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void addItem(T t) {
		this.list.add(t);
	}

	/**
	 * @Title: addItem
	 * @Description: 指定位置插入
	 * @param @param location
	 * @param @param t 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void addItem(int location, T t) {
		this.list.add(location, t);
	}
}
