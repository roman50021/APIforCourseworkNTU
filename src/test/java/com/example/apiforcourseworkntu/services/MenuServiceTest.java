package com.example.apiforcourseworkntu.services;

import com.example.apiforcourseworkntu.dto.AddMenu;
import com.example.apiforcourseworkntu.dto.ById;
import com.example.apiforcourseworkntu.dto.InfoMenu;
import com.example.apiforcourseworkntu.dto.Message;
import com.example.apiforcourseworkntu.dto.UpdateMenu;
import com.example.apiforcourseworkntu.models.Menu;
import com.example.apiforcourseworkntu.repositories.MenuRepository;
import com.example.apiforcourseworkntu.services.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewMenuSuccess() {
        AddMenu addMenuRequest = new AddMenu("DishName", "Description", 200, 10.99);

        when(menuRepository.findByName("DishName")).thenReturn(Optional.empty());
        ResponseEntity<Message> responseEntity = menuService.addNew(addMenuRequest);

        assertEquals("The dish has been added to the database!", responseEntity.getBody().getMessage());
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    public void testAddNewMenuAlreadyExists() {
        AddMenu addMenuRequest = new AddMenu("DishName", "Description", 200, 10.99);

        when(menuRepository.findByName("DishName")).thenReturn(Optional.of(new Menu()));
        ResponseEntity<Message> responseEntity = menuService.addNew(addMenuRequest);

        assertEquals("We have this dish in our database!", responseEntity.getBody().getMessage());
        assertEquals(400, responseEntity.getStatusCodeValue());
        verify(menuRepository, never()).save(any(Menu.class));
    }

    @Test
    public void testDeleteByIdNotFound() {
        ById byIdRequest = new ById(1);

        when(menuRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<Message> responseEntity = menuService.deleteById(byIdRequest);

        assertEquals("We do not have a dish with this ID!", responseEntity.getBody().getMessage());
        assertEquals(400, responseEntity.getStatusCodeValue());
        verify(menuRepository, never()).deleteById(1);
    }

    // Add tests for other methods...

    @Test
    public void testGetAllMenus() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu());
        menuList.add(new Menu());

        when(menuRepository.findAll()).thenReturn(menuList);
        ResponseEntity<List<Menu>> responseEntity = menuService.getAll();

        assertEquals(menuList, responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}