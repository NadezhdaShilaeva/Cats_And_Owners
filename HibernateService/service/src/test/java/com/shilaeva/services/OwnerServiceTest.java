package com.shilaeva.services;

import com.shilaeva.dao.CatDao;
import com.shilaeva.dao.CatDaoImpl;
import com.shilaeva.dao.OwnerDao;
import com.shilaeva.dao.OwnerDaoImpl;
import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;
import com.shilaeva.exceptions.OwnerException;
import com.shilaeva.models.Cat;
import com.shilaeva.models.CatColor;
import com.shilaeva.models.Owner;
import com.shilaeva.services.implementations.OwnerServiceImpl;
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

class OwnerServiceTest {
    @Mock
    private OwnerDao ownerDao = new OwnerDaoImpl();
    @Mock
    private CatDao catDao = new CatDaoImpl();
    private OwnerService ownerService;
    private MockitoSession mockitoSession;

    private Owner owner;
    private Calendar ownerBirthday;
    private Calendar catPersikBirthday;
    private Calendar catVasyaBirthday;

    @BeforeEach
    void setUp() {
        mockitoSession = Mockito.mockitoSession().initMocks(this).startMocking();
        ownerService = new OwnerServiceImpl(ownerDao, catDao);

        ownerBirthday = new Calendar.Builder().setDate(2003, Calendar.MARCH, 20).build();
        owner = new Owner("Nadezhda", ownerBirthday.getTime());
        owner.setId(1L);

        catPersikBirthday = new Calendar.Builder().setDate(2010, Calendar.JULY, 1).build();
        Cat catPersik = new Cat("Persik", catPersikBirthday.getTime(), CatColor.RED);
        catPersik.setId(1L);
        catPersik.setOwner(owner);

        catVasyaBirthday = new Calendar.Builder().setDate(2018, Calendar.APRIL, 5).build();
        Cat catVasya = new Cat("Vasya", catVasyaBirthday.getTime(), CatColor.GREY);
        catVasya.setId(2L);
        catVasya.setOwner(owner);

        List<Cat> cats = new ArrayList<>(List.of(catPersik, catVasya));
        owner.setCats(cats);
    }

    @AfterEach
    void tearDown() {
        mockitoSession.finishMocking();
    }

    @Test
    void findById_getOwner() {
        Mockito.when(ownerDao.findById(1L)).thenReturn(owner);

        OwnerDto ownerDto = ownerService.findById(1L);

        assertEquals(new OwnerDto(1L, "Nadezhda", ownerBirthday.getTime()), ownerDto);
    }

    @Test
    void findAll_getListOfAllOwners() {
        Mockito.when(ownerDao.findAll()).thenReturn(List.of(owner));

        List<OwnerDto> owners = ownerService.findAll();

        assertEquals(List.of(new OwnerDto(1L, "Nadezhda", ownerBirthday.getTime())), owners);
    }

    @Test
    void findCats_getListOfOwnerCats() {
        Mockito.when(ownerDao.findById(1L)).thenReturn(owner);

        List<CatDto> cats = ownerService.findCats(1L);

        OwnerDto ownerDto = new OwnerDto(1L, "Nadezhda", ownerBirthday.getTime());
        CatDto catPersikDto = new CatDto(1L, "Persik", catPersikBirthday.getTime(), null, "RED", ownerDto);
        CatDto catVasyaDto = new CatDto(2L, "Vasya", catVasyaBirthday.getTime(), null, "GREY", ownerDto);

        assertEquals(List.of(catPersikDto, catVasyaDto), cats);
    }

    @Test
    void setName_getOwnerWithNewName() {
        Mockito.when(ownerDao.findById(1L)).thenReturn(owner);
        Mockito.when(ownerDao.update(owner)).thenReturn(owner);

        OwnerDto ownerDto = ownerService.setName(1L, "Natalya");

        assertEquals(new OwnerDto(1L, "Natalya", ownerBirthday.getTime()), ownerDto);
    }

    @Test
    void setBirthdate_getOwnerWithNewBirthdate() {
        Mockito.when(ownerDao.findById(1L)).thenReturn(owner);
        Mockito.when(ownerDao.update(owner)).thenReturn(owner);

        Calendar newBirthday = new Calendar.Builder().setDate(30, Calendar.AUGUST, 2000).build();
        OwnerDto ownerDto = ownerService.setBirthdate(1L, newBirthday.getTime());

        assertEquals(new OwnerDto(1L, "Nadezhda", newBirthday.getTime()), ownerDto);
    }

    @Test
    void addCat_throwsException() {
        Mockito.when(ownerDao.findById(1L)).thenReturn(owner);
        Mockito.when(catDao.findById(1L)).thenReturn(owner.getCats().get(0));

        assertThrows(OwnerException.class, () -> ownerService.addCat(1L, 1L));
    }

    @Test
    void removeCat_catWithoutOwner() {
        Mockito.when(ownerDao.findById(1L)).thenReturn(owner);
        Mockito.when(catDao.findById(3L)).thenReturn(new Cat("Alex", catPersikBirthday.getTime(), CatColor.WHITE));

        assertThrows(OwnerException.class, () -> ownerService.removeCat(1L, 3L));
    }

    @Test
    void deleteOwner_methodWasInvoked() {
        Mockito.when(ownerDao.findById(1L)).thenReturn(owner);
        Mockito.doNothing().when(ownerDao).delete(owner);

        ownerService.deleteOwner(1L);

        Mockito.verify(ownerDao).delete(owner);
    }
}