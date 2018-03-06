import com.jrelax.kit.ImageKit;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.IOException;

public class ImageKitTest {
    public static void main(String[] args) {
        try {
            ImageKit.thumb(new File("E:/1.png")).scale(0.4f).asFiles(Rename.SUFFIX_HYPHEN_THUMBNAIL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
