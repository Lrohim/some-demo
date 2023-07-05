package com.shop.util;

import com.shop.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 实现FastDFS文件管理
 * 文件上传
 * 文件下载
 * 文件删除
 * 文件信息获取
 * Storage信息获取
 * Tracker信息获取
 */
public class FastDFSUtil {

    /****
     * 加载Tracker连接信息
     */
    static {
        try{
            //查找classpath下的文件路径
            String filename= new ClassPathResource("fdfs_client.conf").getPath();
            //加载Tracker链接信息
            ClientGlobal.init(filename);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     * @param fastDFSFile
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        StorageClient storageClient = getStorageClient();
        //通过StorageClient访问Storage，实现文件上传，并且获取文件上传后的file_id
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), null);
        return uploads;
    }

    /**
     * 获取文件信息
     * @param groupName
     * @param remoteName
     * @return
     */
    public FileInfo getFileInfo(String groupName,String remoteName) throws Exception {
        StorageClient storageClient = getStorageClient();
        FileInfo fileInfo=storageClient.get_file_info(groupName,remoteName);
        return fileInfo;
    }

    /**
     * 下载文件
     * @param groupName
     * @param remoteName
     * @return
     * @throws Exception
     */
    public static InputStream downFile(String groupName, String remoteName) throws Exception {
        StorageClient storageClient = getStorageClient();
        byte[] bytes = storageClient.download_file(groupName, remoteName);
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 删除指定文件
     * @param groupName
     * @param remoteName
     * @return
     * @throws Exception
     */
    public boolean deleteFile(String groupName, String remoteName) throws Exception {
        TrackerServer trackerServer=getTrackServer();
        //通过TrackerServer的链接信息可以获取Storage的链接信息，创建StorageClient对象存储Storage的链接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        int i = storageClient.delete_file(groupName, remoteName);
        return i>=1;
    }

    /**
     * 获取可用storage信息
     * @throws Exception
     */
    public static StorageServer getStorages() throws Exception {
        //创建一个Tracker访问的客户端对象
        TrackerClient trackerClient=new TrackerClient();
        //通过TrackerClient访问TrackerServer服务，获取链接信息
        TrackerServer trackerServer=trackerClient.getConnection();
        //获取可用storage信息
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        return storeStorage;
    }

    /**
     * 文件所在storage端口和IP
     * @param groupName
     * @param remoteName
     * @return
     * @throws Exception
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteName) throws Exception {
        //创建一个Tracker访问的客户端对象
        TrackerClient trackerClient=new TrackerClient();
        //通过TrackerClient访问TrackerServer服务，获取链接信息
        TrackerServer trackerServer=trackerClient.getConnection();
        //获取组的信息
        ServerInfo[] fetchStorages = trackerClient.getFetchStorages(trackerServer, groupName, remoteName);
        return fetchStorages;
    }

    /**
     * 获取Tracker信息
     * @throws Exception
     */
    public static String getTrackerInfo() throws Exception {
        TrackerServer trackerServer=getTrackServer();
        String ip=trackerServer.getInetSocketAddress().getHostString();
        int tracker_http_port=ClientGlobal.getG_tracker_http_port();
        String url="http://"+ip+":"+tracker_http_port;
        return url;
    }

    /**
     * 获取TrackerServer
     * @return
     * @throws Exception
     */
    public static TrackerServer getTrackServer() throws Exception {
        //创建一个Tracker访问的客户端对象
        TrackerClient trackerClient=new TrackerClient();
        //通过TrackerClient访问TrackerServer服务，获取链接信息
        TrackerServer trackerServer=trackerClient.getConnection();
        return trackerServer;
    }


    public static StorageClient getStorageClient() throws Exception {
        //创建一个Tracker访问的客户端对象
        TrackerClient trackerClient=new TrackerClient();
        //通过TrackerClient访问TrackerServer服务，获取链接信息
        TrackerServer trackerServer=trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }


}
