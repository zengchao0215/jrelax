import com.jrelax.third.tuple.Tuple2;

public class TestTuple {
    public static void main(String[] args) {
        Tuple2<String, Integer> tuple = new Tuple2<>("a", null);

        System.out.println(tuple.first());

        System.out.println(tuple.last());
    }
}
