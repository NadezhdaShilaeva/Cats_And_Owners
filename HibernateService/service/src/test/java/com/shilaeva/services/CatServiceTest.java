package com.shilaeva.services;

import com.shilaeva.dao.CatDao;
import com.shilaeva.dao.CatDaoImpl;
import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;
import com.shilaeva.exceptions.CatException;
import com.shilaeva.models.Cat;
import com.shilaeva.models.CatColor;
import com.shilaeva.models.Owner;
import com.shilaeva.services.implementations.CatServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatServiceTest {
    @Mock
    private CatDao catDao = new CatDaoImpl();
    private CatService catService;
    private MockitoSession mockitoSession;

    private List<Cat> cats;
    private OwnerDto ownerDto;
    private Calendar ownerBirthday;
    private Calendar catPersikBirthday;
    private Calendar catVasyaBirthday;

    @BeforeEach
    void setUp() {
        mockitoSession = Mockito.mockitoSession().initMocks(this).startMocking();
        catService = new CatServiceImpl(catDao);

        ownerBirthday = new Calendar.Builder().setDate(2003, Calendar.MARCH, 20).build();
        Owner owner = new Owner("Nadezhda", ownerBirthday.getTime());
        owner.setId(1L);
        ownerDto = new OwnerDto(1L, "Nadezhda", ownerBirthday.getTime());

        catPersikBirthday = new Calendar.Builder().setDate(2010, Calendar.JULY, 1).build();
        Cat catPersik = new Cat("Persik", catPersikBirthday.getTime(), CatColor.RED);
        catPersik.setId(1L);
        catPersik.setOwner(owner);

        catVasyaBirthday = new Calendar.Builder().setDate(2018, Calendar.APRIL, 5).build();
        Cat catVasya = new Cat("Vasya", catVasyaBirthday.getTime(), CatColor.GREY);
        catVasya.setId(2L);
        catVasya.setOwner(owner);

        catPersik.getFriends().add(catVasya);
        catVasya.getFriends().add(catPersik);

        cats = new ArrayList<>(List.of(catPersik, catVasya));
        owner.setCats(cats);
    }

    @AfterEach
    void tearDown() {
        mockitoSession.finishMocking();
    }

    @Test
    void findById_getCat() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));

        CatDto catDto = catService.findById(1L);

        assertEquals(new CatDto(1L, "Persik", catPersikBirthday.getTime(), null, "RED",
                new OwnerDto(1L, "Nadezhda", ownerBirthday.getTime())), catDto);
    }

    @Test
    void findAll_getListOfAllCats() {
        Mockito.when(catDao.findAll()).thenReturn(cats);

        List<CatDto> catDtoList = catService.findAll();

        assertEquals(List.of(new CatDto(1L, "Persik", catPersikBirthday.getTime(), null, "RED", ownerDto),
                new CatDto(2L, "Vasya", catVasyaBirthday.getTime(), null, "GREY", ownerDto)), catDtoList);
    }

    @Test
    void findOwner_getCatOwner() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));

        OwnerDto catOwnerDto = catService.findOwner(1L);

        assertEquals(ownerDto, catOwnerDto);
    }

    @Test
    void findFriends_getCatFriends() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));

        List<CatDto> catDtoList = catService.findFriends(1L);

        assertEquals(List.of(new CatDto(2L, "Vasya", catVasyaBirthday.getTime(), null, "GREY", ownerDto)), catDtoList);
    }

    @Test
    void setName_getCatWithNewName() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));
        Mockito.when(catDao.update(cats.get(0))).thenReturn(cats.get(0));

        CatDto catDto = catService.setName(1L, "Alex");

        assertEquals(new CatDto(1L, "Alex", catPersikBirthday.getTime(), null, "RED", ownerDto), catDto);
    }

    @Test
    void setBirthdate_getCatWithNewBirthdate() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));
        Mockito.when(catDao.update(cats.get(0))).thenReturn(cats.get(0));

        CatDto catDto = catService.setBirthdate(1L, catVasyaBirthday.getTime());

        assertEquals(new CatDto(1L, "Persik", catVasyaBirthday.getTime(), null, "RED", ownerDto), catDto);
    }

    @Test
    void setBreed_getCatWithNewBreed() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));
        Mockito.when(catDao.update(cats.get(0))).thenReturn(cats.get(0));

        CatDto catDto = catService.setBreed(1L, "domestic");

        assertEquals(new CatDto(1L, "Persik", catPersikBirthday.getTime(), "domestic", "RED", ownerDto), catDto);
    }

    @Test
    void setColor_getCatWithNewColor() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));
        Mockito.when(catDao.update(cats.get(0))).thenReturn(cats.get(0));

        CatDto catDto = catService.setColor(1L, "WHITE");

        assertEquals(new CatDto(1L, "Persik", catPersikBirthday.getTime(), null, "WHITE", ownerDto), catDto);
    }

    @Test
    void addFriend_throwsException() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));
        Mockito.when(catDao.findById(2L)).thenReturn(cats.get(1));

        assertThrows(CatException.class, () -> catService.addFriend(1L, 2L));
    }

    @Test
    void removeFriend_throwsException() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));
        Mockito.when(catDao.findById(3L)).thenReturn(new Cat("Alex", catPersikBirthday.getTime(), CatColor.WHITE));

        assertThrows(CatException.class, () -> catService.removeFriend(1L, 3L));
    }

    @Test
    void deleteCat_methodWasInvoked() {
        Mockito.when(catDao.findById(1L)).thenReturn(cats.get(0));
        Mockito.doNothing().when(catDao).delete(cats.get(0));

        catService.deleteCat(1L);

        Mockito.verify(catDao).delete(cats.get(0));
    }
}