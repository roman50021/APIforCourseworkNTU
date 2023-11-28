package com.example.apiforcourseworkntu.services;

import com.example.apiforcourseworkntu.dto.*;
import com.example.apiforcourseworkntu.models.Menu;
import com.example.apiforcourseworkntu.repositories.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuService {
    private final MenuRepository repository;
    public ResponseEntity<Message> addNew(AddMenu request) {
        Optional<Menu> existingMenu = repository.findByName(request.getName());
        if (existingMenu.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Message.builder()
                            .message("We have this dish in our database!")
                            .build());
        }else {
            var dish = Menu.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .servingWeight(request.getServingWeight())
                    .price(request.getPrice())
                    .build();
            repository.save(dish);
            return ResponseEntity.ok()
                    .body(Message.builder()
                            .message("The dish has been added to the database!")
                            .build());
        }
    }
    public ResponseEntity<InfoMenu> getById(ById request) {
        Optional<Menu> existingMenu = repository.findById(request.getId());
        if(existingMenu.isPresent()){
            return ResponseEntity.ok()
                    .body(InfoMenu.builder()
                            .id(existingMenu.get().getId())
                            .name(existingMenu.get().getName())
                            .description(existingMenu.get().getDescription())
                            .servingWeight(existingMenu.get().getServingWeight())
                            .price(existingMenu.get().getPrice())
                            .message("We have this in menu!")
                            .build());
        }else{
            return ResponseEntity.badRequest()
                    .body(InfoMenu.builder()
                            .message("We don't have this in menu")
                            .build());
        }
    }
    public ResponseEntity<List<Menu>> getAll() {
        List<Menu> menus = repository.findAll();
        return ResponseEntity.ok(menus);
    }
    public ResponseEntity<Message> deleteById(ById request) {
        Optional<Menu> existingMenu = repository.findById(request.getId());
        if(existingMenu.isPresent()){
            repository.deleteById(existingMenu.get().getId());
            return ResponseEntity.ok()
                    .body(Message.builder()
                            .message("The dish has been removed from the menu!")
                            .build());
        }else{
            return ResponseEntity.badRequest()
                    .body(Message.builder()
                            .message("We do not have a dish with this ID!")
                            .build());
        }
    }
    public ResponseEntity<Message> update(UpdateMenu request) {
        Optional<Menu> existingMenu = repository.findById(request.getId());
        if (existingMenu.isPresent()){
            Menu menuToUpdate = existingMenu.get();
            menuToUpdate.setName(request.getName());
            menuToUpdate.setDescription(request.getDescription());
            menuToUpdate.setServingWeight(request.getServingWeight());
            menuToUpdate.setPrice(request.getPrice());
            repository.save(menuToUpdate);
            return ResponseEntity.ok()
                    .body(Message.builder()
                            .message("This dish was updated!")
                            .build());
        }else{
            return ResponseEntity.badRequest()
                    .body(Message.builder()
                            .message("We do not have a dish with this ID!")
                            .build());
        }
    }
}
