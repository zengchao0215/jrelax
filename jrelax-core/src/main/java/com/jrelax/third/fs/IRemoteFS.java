package com.jrelax.third.fs;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRemoteFS extends Remote {
    int TAG_SUCCESS = 10;
    int TAG_EXISTS = 11;
    int TAG_NOT_EXISTS = 12;
    int TAG_NOT_DIR = 13;
    int TAG_DIR_NOT_EMPTY = 14;

    /**
     * 上传文件 文件自动重命名，并且由文件系统来决定文件的存放位置
     *
     * @param app      应用名称
     * @param filename 文件名
     * @param stream   文件流
     * @return
     */
    public String upload(String app, String filename, byte[] stream) throws RemoteException;

    /**
     * 上传文件
     *
     * @param app      应用名称
     * @param dir      保存文件夹
     * @param filename 文件名
     * @param stream   文件流
     * @return 文件服务器保存路径
     * @throws RemoteException
     */
    public String upload(String app, String dir, String filename, byte[] stream) throws RemoteException;


    /**
     * 创建文件夹
     *
     * @param app 应用名称
     * @param dir 文件夹名称
     * @return 返回结果
     */
    public int mkdir(String app, String dir) throws RemoteException;

    /**
     * 删除文件
     *
     * @param app      应用名称
     * @param filepath 文件路径
     * @return
     */
    public int delete(String app, String filepath) throws RemoteException;

    /**
     * 删除文件夹
     *
     * @param app      应用名称
     * @param dir      文件夹路径
     * @param clearAll 是否删除文件夹下所有文件
     * @return
     */
    public int deleteDir(String app, String dir, boolean clearAll) throws RemoteException;

    /**
     * 下载文件流
     *
     * @param app      应用名称
     * @param filepath 文件路劲
     * @return
     */
    public byte[] download(String app, String filepath) throws RemoteException;

    /**
     * 文件夹浏览
     *
     * @param app 应用名称
     * @param dir 文件夹路径
     * @return
     */
    public List<String> list(String app, String dir) throws RemoteException;

    /**
     * 文件搜索
     *
     * @param app
     * @param key
     * @return
     */
    public List<String> search(String app, String key) throws RemoteException;
}
