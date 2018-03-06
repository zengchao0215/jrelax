import com.jrelax.orm.query.HQLBuilder;

public class SQLBuilder {
    public static void main(String[] args) {
        HQLBuilder builder = new HQLBuilder("select * from Table");
        builder.where("name", "like", "%张三%");
        builder.where("name1", "like", "%张三%");
        builder.where("name2", "like", "%张三%");
        builder.where("name3", "like", "%张三%");
        builder.in("code", "1, 2, 3, 4");
        builder.group("sex").having("AVG(age) > 15");
        builder.order("age", "desc");

        System.out.println(builder.getHQL());
        System.out.println(builder.getParams().length);
    }
}
