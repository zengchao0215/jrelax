package com.jrelax.kit.observer;

import java.util.Vector;


public class Observable {
	private boolean changed = false;
	private Vector<Observer> obs;
	
	public Observable(){
		obs = new Vector<Observer>();
	}
	/**
	 * 注册
	 * @param ob
	 */
	public void addObserver(Observer ob){
		if(ob == null)
			throw new NullPointerException();
		if(!obs.contains(ob))
			this.obs.addElement(ob);
	}
	/**
	 * 解注册
	 * @param ob
	 */
	public void deleteObserver(Observer ob){
		this.obs.removeElement(ob);
	}
	/**
	 * 解注册所有
	 */
	public void deleteObservers(){
		this.obs.removeAllElements();
	}
	/**
	 * 通知所有Observer
	 */
	public void notifyObservers(){
		notifyObservers(null);
	}
	public void notifyObservers(Object arg){
		synchronized (this) {
			if(!changed)
				return;
			clearChanged();
		}
		for(Observer ob : this.obs){
			ob.update(this,arg);
		}
		
	}
	/**
	 * 改变
	 */
	public synchronized void setChanged(){
		this.changed = true;
	}
	/**
	 * 清除改变状态
	 */
	public synchronized void clearChanged(){
		this.changed = false;
	}
	/**
	 * 判断是否改变
	 * @return
	 */
	public synchronized boolean hasChanged(){
		return this.changed;
	}
	/**
	 * 注册者数量
	 * @return
	 */
	public synchronized int countObservers(){
		return this.obs.size();
	}
}
