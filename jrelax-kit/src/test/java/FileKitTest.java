import com.jrelax.kit.FileKit;

public class FileKitTest {
    public static void main(String[] args) throws Exception {
//        System.out.println(FileKit.getFilename("a/b/c/a.txt"));
//        System.out.println(FileKit.getFilename("a\\b\\c\\a.txt"));
//
//        System.out.println(FileKit.getType("a.txt"));
//        System.out.println(FileKit.getType("a.png"));
//        System.out.println(FileKit.getType("a.mp3"));
//        System.out.println(FileKit.getType("a.mp4"));
//        System.out.println(FileKit.getType("a.docx"));

        System.out.println(FileKit.beautifyPath("/a/a//////d/dd"));
    }
}
