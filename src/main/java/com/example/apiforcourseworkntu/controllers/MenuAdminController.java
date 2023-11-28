package com.example.apiforcourseworkntu.controllers;

import com.example.apiforcourseworkntu.auth.RegisterRequest;
import com.example.apiforcourseworkntu.auth.dtos.AuthenticationResponse;
import com.example.apiforcourseworkntu.dto.*;
import com.example.apiforcourseworkntu.models.Menu;
import com.example.apiforcourseworkntu.services.MenuService;
import com.example.apiforcourseworkntu.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/menu")
@RequiredArgsConstructor
public class MenuAdminController {
    private final MenuService service;
    @PostMapping("/add")
    public ResponseEntity<Message> createMenu(@RequestBody AddMenu request){
        return service.addNew(request);
    }
    @PostMapping("/get")
    public ResponseEntity<InfoMenu> getMenu(@RequestBody ById request){
        return service.getById(request);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Menu>> getAllMenu() {
        return service.getAll();
    }

    @PostMapping("/delete")
    public ResponseEntity<Message> deleteMenu(@RequestBody ById request){
        return service.deleteById(request);
    }
    @PostMapping("/update")
    public ResponseEntity<Message> updateMenu(@RequestBody UpdateMenu request){
        return service.update(request);
    }
}
