package com.suxiaoxiang.qqauthorizationsystem.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class CozeBotClient {

    private static final String TOKEN = "pat_Dy5DQ4GpQe5njUKpKOpVvTaw2vAzPsaTASZ1nnbBW4wMHm57fH2OTMfyZwel7YdH";
    private static final String BOT_ID = "7500486820887609385";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String createConversation() throws Exception {
        String apiUrl = "https://api.coze.cn/v1/conversation/create";

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write("{}".getBytes(StandardCharsets.UTF_8));
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            JsonNode json = mapper.readTree(response.toString());
            if (json.path("code").asInt() != 0) {
                System.err.println("创建会话失败: " + json.path("msg").asText());
                return null;
            }
            return json.path("data").path("id").asText();
        }
    }

    public static MessageResult sendStreamMessage(String conversationId, String content) throws Exception {
        String apiUrl = "https://api.coze.cn/v3/chat?conversation_id=" + conversationId;
        String requestBody = String.format(
                "{\"bot_id\":\"%s\",\"user_id\":\"123\",\"stream\":true,\"auto_save_history\":true," +
                        "\"additional_messages\":[{\"role\":\"user\",\"content\":\"%s\",\"content_type\":\"text\"}]}",
                BOT_ID, content);

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "text/event-stream");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setDoOutput(true);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(60000);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            try (BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
                System.err.println("API错误响应: " + errorResponse.toString());
            }
            throw new IOException("HTTP错误码: " + responseCode);
        }

        StringBuilder fullContent = new StringBuilder();
        String messageId = null;
        String chatId = null;
        boolean receivedData = false;
        Set<String> seenContents = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            String currentEvent = null;
            StringBuilder currentData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("event:")) {
                    currentEvent = line.substring(6).trim();
                } else if (line.startsWith("data:")) {
                    currentData.append(line.substring(5).trim());
                } else if (line.isEmpty() && currentEvent != null && !currentData.toString().isEmpty()) {
                    receivedData = true;
                    try {
                        JsonNode eventData = mapper.readTree(currentData.toString());
                        JsonNode data = eventData;

                        if ("conversation.message.completed".equals(currentEvent)) {
                            String completedContent = data.path("content").asText();
                            if (completedContent != null && !completedContent.isEmpty() && !completedContent.startsWith("{") && !completedContent.contains("？") && !completedContent.contains("?")) {
                                if (!seenContents.contains(completedContent)) {
                                    fullContent.append(completedContent);
                                    seenContents.add(completedContent);
                                }
                            }
                            messageId = data.path("id").asText();
                            chatId = data.path("chat_id").asText();
                        } else if ("error".equals(currentEvent)) {
                            System.err.println("API错误: " + data.path("message").asText());
                            return null;
                        }
                    } catch (Exception e) {
                        System.err.println("解析响应数据时出错: " + e.getMessage());
                        return null;
                    }
                    currentEvent = null;
                    currentData = new StringBuilder();
                }
            }
        } catch (IOException e) {
            System.err.println("流式响应读取错误: " + e.getMessage());
            return null;
        }

        if (!receivedData) {
            System.err.println("警告：没有收到任何数据行");
            return null;
        }

        if (fullContent.length() == 0) {
            System.err.println("未收到有效响应内容");
            return null;
        }

        return new MessageResult(fullContent.toString(), messageId, chatId);
    }

    public static class MessageResult {
        private final String content;
        private final String messageId;
        private final String chatId;

        public MessageResult(String content, String messageId, String chatId) {
            this.content = content;
            this.messageId = messageId;
            this.chatId = chatId;
        }

        public String getContent() { return content; }
        public String getMessageId() { return messageId; }
        public String getChatId() { return chatId; }
    }
}