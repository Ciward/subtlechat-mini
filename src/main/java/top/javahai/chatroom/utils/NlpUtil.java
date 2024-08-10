package top.javahai.chatroom.utils;
import com.hankcs.hanlp.HanLP;
import java.util.List;

//NLP
public class NlpUtil {
    public static List<String> getWords(String text){
        List<String> strings = HanLP.extractKeyword(text, 2);
        System.out.println(strings);
        return strings;
    }

    public static void main(String[] args) {
        getWords("我们学校什么时候开学");
    }
}