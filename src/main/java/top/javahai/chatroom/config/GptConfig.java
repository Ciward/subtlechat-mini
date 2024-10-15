package top.javahai.chatroom.config;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.javahai.chatroom.utils.HttpRequest;
import top.javahai.chatroom.utils.HttpUtils;

import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.billing.BillingUsage;
import com.unfbx.chatgpt.entity.billing.CreditGrantsResponse;
import com.unfbx.chatgpt.entity.billing.Subscription;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.entity.completions.Completion;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import com.unfbx.chatgpt.sse.ConsoleEventSourceListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 百度文心一言接入
 */
public class GptConfig {
//    public static String getToken(){
//        String token = "";
//        String url = "https://aip.baidubce.com/oauth/2.0/token?client_id=ECU7TJEA7CVK1jL2ES6vzgH8&client_secret=p0W3slm0opKfMWWR09j6px9vj8aY80J5&grant_type=client_credentials";
//        try {
//            token = HttpUtils.httpPost(url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        JSONObject jsonObject = JSONObject.parseObject(token);
//        String access_token = jsonObject.getString("access_token");
//        return access_token;
//    }
public static String getToken() {
    String token = "";
    String url = "https://aip.baidubce.com/oauth/2.0/token?client_id=ML2S2Kmn7e6rVAE2OBC1g0RG&client_secret=HuYbCQeGtbmcbiPjBflaQOfKuuZH6GiM&grant_type=client_credentials";
    try {
        token = HttpUtils.httpPost(url);
    } catch (Exception e) {
        e.printStackTrace();
    }
    JSONObject jsonObject = JSONObject.parseObject(token);
    if (jsonObject != null && jsonObject.containsKey("access_token")) {
        return jsonObject.getString("access_token");
    } else {
        return null; // 返回空值或者其他默认值，表示获取 token 失败
    }
}
    public static String getMessage(String content) {
        String requestMethod = "POST";
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token="+getToken();//post请求时格式
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role","user");
        msg.put("content",content);
        ArrayList<HashMap> messages = new ArrayList<>();
        messages.add(msg);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", messages);
        String outputStr = JSON.toJSONString(requestBody);
        JSON json = HttpRequest.httpRequest(url,requestMethod,outputStr,"application/json");
        JSONObject jsonObject = JSONObject.parseObject(json.toJSONString());
        String result = jsonObject.getString("result");
        System.out.println(result);
        return result;
    }

    public static List<String> preMessage(String content) {
        String requestMethod = "POST";
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token="+getToken();//post请求时格式
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role","user");
        content = "请将下面一句话提取关键信息，将结果以分词的形式展示出来。下面是我的输入文本："+content;
        msg.put("content",content);
        ArrayList<HashMap> messages = new ArrayList<>();
        messages.add(msg);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", messages);
        String outputStr = JSON.toJSONString(requestBody);
        JSON json = HttpRequest.httpRequest(url,requestMethod,outputStr,"application/json");
        JSONObject jsonObject = JSONObject.parseObject(json.toJSONString());
        List<String> result = Arrays.asList(jsonObject.getString("result").split(" "));
        System.out.println(result);
        return result;
    }

    public static String resMessage(String question,List<String> content1) {
        String requestMethod = "POST";
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token="+getToken();//post请求时格式
        HashMap<String, String> msg = new HashMap<>();
        msg.put("role","user");
        String content = String.join("\n", content1);//将所有匹配到的信息存入
        //用GPT来进行匹配
        content = "请在下面的数据表中找到最符合问题的答案,如果找不到，那么从网络中搜索答案输出。问题："+question+"\n数据表：\n"+content;
        System.out.println(content);
        msg.put("content",content);
        ArrayList<HashMap> messages = new ArrayList<>();
        messages.add(msg);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", messages);
        String outputStr = JSON.toJSONString(requestBody);
        JSON json = HttpRequest.httpRequest(url,requestMethod,outputStr,"application/json");
        JSONObject jsonObject = JSONObject.parseObject(json.toJSONString());
        String result = jsonObject.getString("result");
        System.out.println(result);
        return result;
    }

    public static JSONObject RAGchat(String content) {
        // data = {
        //         "query": "综合评价总分的详细组成？",
        //         "mode": "local_kb",
        //         "kb_name": "campus",
        //         "top_k": 6,
        //         "score_threshold": 1.0,
        //         "history": [],
        //         "stream": true,
        //         "model": "custom-glm4-chat",
        //         "temperature": 0.7,
        //         "max_tokens": 0,
        //         "prompt_name": "default",
        //         "return_direct": false
        //         }
        String requestMethod = "POST";
        String url = "http://127.0.0.1:1145/chat/kb_chat";//post请求时格式
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", content);
        requestBody.put("model", "custom-glm4-chat");
        requestBody.put("mode", "local_kb");
        requestBody.put("kb_name", "campus");
        requestBody.put("top_k", 6);
        requestBody.put("score_threshold", 1.0);
        requestBody.put("history", new ArrayList<>());
        requestBody.put("stream", true);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 0);
        requestBody.put("prompt_name", "default");
        requestBody.put("return_direct", false);
        String outputStr = JSON.toJSONString(requestBody);
        JSON json = HttpRequest.httpRequest(url,requestMethod,outputStr,"application/json");
        JSONObject jsonObject = JSONObject.parseObject(json.toJSONString());
        return jsonObject;
    }
    public static JSONObject openRAGchat(String content) {
        
        String requestMethod = "POST";
        String url = "http://127.0.0.1:1145/chat/kb_chat";//post请求时格式
        OpenAiClient openAiClient = OpenAiClient.builder()
                .apiKey(Arrays.asList("EMPTY"))
                .apiHost(url)
                //自定义key的获取策略：默认KeyRandomStrategy
                //.keyStrategy(new KeyRandomStrategy())
                //自己做了代理就传代理地址，没有可不不传
//                .apiHost("https://自己代理的服务器地址/")
                .build();
        Message message = Message.builder().role(Message.Role.USER).content(content).build();
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        chatCompletion.setStream(true);
        chatCompletion.setTemperature(0.7);
        chatCompletion.setModel("custom-glm4-chat");
        
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
        JSONObject jsonObject = new JSONObject();
        chatCompletionResponse.getChoices().forEach(e -> {
            System.out.println(e.getMessage());
        });
        return jsonObject;
    }
}
