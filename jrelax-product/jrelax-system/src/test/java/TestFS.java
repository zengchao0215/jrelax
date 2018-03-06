import com.jrelax.third.fs.IRemoteFS;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Administrator on 2016-08-23.
 */

public class TestFS {
    public static void main(String[] args) {
        TestFS testFS = new TestFS();
        testFS.create();
        testFS.test();
    }
    IRemoteFS rfs = null;
    public IRemoteFS create(){
        String rmi = "rmi://115.28.243.153:10000/fs";
        try {
            rfs = (IRemoteFS) Naming.lookup(rmi);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
        return rfs;
    }

    private void test() {
        String app = "TestApp";
        try {
//            System.out.println(rfs.mkdir(app, "ok"));
//            System.out.println(rfs.deleteDir(app, "ok", false));
            System.out.println(rfs.upload(app, "abcde.txt", "Hello World!".getBytes()));
//            System.out.println(rfs.upload(app, "/图书目录/测试1", "abcde.txt", "Hello World!".getBytes()));
//            System.out.println(rfs.delete(app, "/图书目录/测试1/abcde.txt"));
//            FileUtils.writeByteArrayToFile(new File("F:/a.txt"), rfs.download(app, "/图书目录/测试1/abcde.txt"));
//            System.out.println(rfs.list(app, "/图书目录/测试1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
