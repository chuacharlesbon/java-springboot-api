package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MyModel;

@RestController
public class HelloController {

    // Mock database
    private final Map<Integer, MyModel> dataList = new HashMap<>();
    private int nextId = 1;

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> sayHello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, Spring Boot!");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/hello/{id}")
    public ResponseEntity<?> getData(@PathVariable int id) {
        MyModel data = dataList.get(id);
        if (data == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Data not found"));
        }
        return ResponseEntity.ok(data);
    }

    // ✅ POST (with request body)
    @PostMapping("/hello")
    public ResponseEntity<?> createData(@RequestBody MyModel newData) {
        int id = nextId++;
        newData.setId(id);
        dataList.put(id, newData);
        return ResponseEntity.status(201).body(newData);
    }

    // ✅ PUT (with path param + request body)
    @PutMapping("/hello/{id}")
    public ResponseEntity<?> updateData(@PathVariable int id, @RequestBody MyModel updatedData) {
        MyModel existing = dataList.get(id);
        if (existing == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Data not found"));
        }
        updatedData.setId(id);
        dataList.put(id, updatedData);
        return ResponseEntity.ok(updatedData);
    }

    // ✅ DELETE (with path param)
    @DeleteMapping("/hello/{id}")
    public ResponseEntity<?> deleteData(@PathVariable int id) {
        MyModel removed = dataList.remove(id);
        if (removed == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Data not found"));
        }
        return ResponseEntity.ok(Map.of("message", "Data deleted successfully"));
    }

    // ✅ GET all data (no params)
    @GetMapping("/hello/all")
    public ResponseEntity<?> getAllData() {
        return ResponseEntity.ok(dataList.values());
    }

    // ✅ DELETE (delete all data)
    @DeleteMapping("/hello/all")
    public ResponseEntity<?> deleteAllData() {
        dataList.clear();
        return ResponseEntity.ok(Map.of("message", "All data deleted successfully"));
    }
}
