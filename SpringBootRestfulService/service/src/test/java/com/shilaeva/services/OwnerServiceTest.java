package com.shilaeva.services;

import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;
import com.shilaeva.exceptions.OwnerException;
import com.shilaeva.models.Cat;
import com.shilaeva.models.CatColor;
import com.shilaeva.models.Owner;
import com.shilaeva.repositories.CatRepository;
import com.shilaeva.repositories.OwnerRepository;
import com.shilaeva.services.implementations.OwnerServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class OwnerServiceTest {
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private CatRepository catRepository;
    @InjectMocks
    private OwnerServiceImpl ownerService;
    private MockitoSession mockitoSession;

    private Owner owner;
    private OwnerDto ownerDto;
    private Calendar ownerBirthday;
    private Calendar catPersikBirthday;
    private Calendar catVasyaBirthday;

    @BeforeEach
    void setUp() {
        mockitoSession = Mockito.mockitoSession().initMocks(this).startMocking();

        ownerBirthday = new Calendar.Builder().setDate(2003, Calendar.MARCH, 20).build();
        owner = new Owner("Nadezhda", ownerBirthday.getTime());
        owner.setId(1L);
        ownerDto = OwnerDto.asDto(owner);

        catPersikBirthday = new Calendar.Builder().setDate(2010, Calendar.JULY, 1).build();
        Cat catPersik = new Cat("Persik", catPersikBirthday.getTime(), "domestic", CatColor.RED);
        catPersik.setId(1L);
        catPersik.setOwner(owner);

        catVasyaBirthday = new Calendar.Builder().setDate(2018, Calendar.APRIL, 5).build();
        Cat catVasya = new Cat("Vasya", catVasyaBirthday.getTime(), "British", CatColor.GREY);
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
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

        OwnerDto foundOwnerDto = ownerService.getById(1L);

        assertEquals(ownerDto, foundOwnerDto);
    }

    @Test
    void getByName_throwsException() {
        Mockito.when(ownerRepository.findByName("Natalia")).thenReturn(List.of());

        assertThrows(OwnerException.class, () -> ownerService.getByName("Natalia"));
    }

    @Test
    void getByBirthdate_getOwner() {
        Mockito.when(ownerRepository.findByBirthdate(ownerBirthday.getTime())).thenReturn(List.of(owner));

        List<OwnerDto> ownerDtoList = ownerService.getByBirthdate(ownerBirthday.getTime());

        assertEquals(List.of(ownerDto), ownerDtoList);
    }

    @Test
    void findAll_getListOfAllOwners() {
        Mockito.when(ownerRepository.findAll()).thenReturn(List.of(owner));

        List<OwnerDto> ownerDtoList = ownerService.getAll();

        assertEquals(List.of(ownerDto), ownerDtoList);
    }

    @Test
    void findCats_getListOfOwnerCats() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

        List<CatDto> catDtoList = ownerService.getCats(1L);

        CatDto catPersikDto = new CatDto(1L, "Persik", catPersikBirthday.getTime(), "domestic", "RED");
        CatDto catVasyaDto = new CatDto(2L, "Vasya", catVasyaBirthday.getTime(), "British", "GREY");

        assertEquals(List.of(catPersikDto, catVasyaDto), catDtoList);
    }

    @Test
    void createOwner_getCreatedOwner() {
        Mockito.when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        OwnerDto newOwnerDto = ownerService.createOwner("Nadezhda", ownerBirthday.getTime());

        assertEquals(ownerDto, newOwnerDto);
    }

    @Test
    void setName_getOwnerWithNewName() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(ownerRepository.save(owner)).thenReturn(owner);

        OwnerDto ownerDto = ownerService.setName(1L, "Natalya");

        assertEquals(new OwnerDto(1L, "Natalya", ownerBirthday.getTime()), ownerDto);
    }

    @Test
    void setBirthdate_getOwnerWithNewBirthdate() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(ownerRepository.save(owner)).thenReturn(owner);

        Calendar newBirthday = new Calendar.Builder().setDate(30, Calendar.AUGUST, 2000).build();
        OwnerDto ownerDto = ownerService.setBirthdate(1L, newBirthday.getTime());

        assertEquals(new OwnerDto(1L, "Nadezhda", newBirthday.getTime()), ownerDto);
    }

    @Test
    void addCat_catAlreadyExists_throwsException() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(owner.getCats().get(0)));

        assertThrows(OwnerException.class, () -> ownerService.addCat(1L, 1L));
    }

    @Test
    void addCat_addCatSecondTime_throwsException() {
        Cat catAlex = new Cat("Alex", catPersikBirthday.getTime(), "British", CatColor.BLACK);

        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(catRepository.findById(3L)).thenReturn(Optional.of(catAlex));

        ownerService.addCat(1L, 3L);

        assertThrows(OwnerException.class, () -> ownerService.addCat(1L, 3L));
    }

    @Test
    void removeCat_ownerHasNotCat_throwsException() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(catRepository.findById(3L)).thenReturn(Optional
                .of(new Cat("Alex", catPersikBirthday.getTime(), "British", CatColor.BLACK)));

        assertThrows(OwnerException.class, () -> ownerService.removeCat(1L, 3L));
    }

    @Test
    void removeCat_removeCatSecondTime_throwsException() {
        Cat catPersik = owner.getCats().get(0);

        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(catPersik));

        ownerService.removeCat(1L, 1L);

        assertThrows(OwnerException.class, () -> ownerService.removeCat(1L, 1L));
    }

    @Test
    void deleteOwner_methodWasInvoked() {
        Mockito.doNothing().when(ownerRepository).deleteById(1L);

        ownerService.deleteOwner(1L);

        Mockito.verify(ownerRepository).deleteById(1L);
    }
}