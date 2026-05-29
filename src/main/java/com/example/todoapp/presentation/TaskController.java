package com.example.todoapp.presentation;

import com.example.todoapp.dao.TaskDao;
import com.example.todoapp.business.service.JsonUtils;
import com.example.todoapp.business.model.Task;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;

public class TaskController {
    private static void handleTasks(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        //region Manage POST /tasks
        if ("POST".equals(method) && "/tasks".equals(path)) {
            Task input = JsonUtils.deserialize(new String(exchange.getRequestBody().readAllBytes(), UTF_8), Task.class);
            Task createdTask = dao.save(input);

            exchange.getResponseHeaders().add("Location", "/tasks/" + createdTask.id());
            sendResponse(exchange, 201, JsonUtils.serialize(createdTask));
            return;
        }

        //endregion

        //region Manage GET /tasks/{id}
        Matcher m = ID_PATH.matcher(path);
        if ("GET".equals(method) && m.matches()) {
            int id = Integer.parseInt(m.group(1));
            Optional<Task> task = dao.findById(id);
            if (task.isPresent()) {
                sendResponse(exchange, 200, JsonUtils.serialize(task.get()));
            } else {
                sendResponse(exchange, 404, null);
            }
            return;
        }
        //endregion

        //region Manage DELETE /tasks/{id}
        // m = ID_PATH.matcher(path);
        if ("DELETE".equals(method) && m.matches()) {
            int id = Integer.parseInt(m.group(1));
            boolean deleted = dao.delete(id);
            sendResponse(exchange, deleted ? 204 : 404, null);
            return;
        }
        //endregion

        //region Manage PUT /tasks/{id}
        m = ID_PATH.matcher(path);

        if ("PUT".equals(method) && m.matches()) {

            int id = Integer.parseInt(m.group(1));

            Optional<Task> task = dao.findById(id);

            if (task.isPresent()) {
                sendResponse(exchange, 200, JsonUtils.serialize(task.get()));
            } else {
                sendResponse(exchange, 404, null);
            }

            return;
        }

        // GET /tasks
        if ("GET".equals(method) && "/tasks".equals(path)) {

            List<Task> tasks = dao.findAll();

            sendResponse(exchange, 200, JsonUtils.serialize(tasks));
            return;
        }

        // 404
        sendResponse(exchange, 404, null);


    }


    private static void sendResponse (HttpExchange exchange,int status, String json) throws IOException {
        if (nonNull(json)) {
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            byte[] bytes = json.getBytes(UTF_8);
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } else {
            exchange.sendResponseHeaders(status, -1);
        }
    }}

