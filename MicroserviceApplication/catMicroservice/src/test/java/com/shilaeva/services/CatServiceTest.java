package com.shilaeva.services;

import com.shilaeva.dto.BirthdateDto;
import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;
import com.shilaeva.exceptions.CatException;
import com.shilaeva.models.Cat;
import com.shilaeva.models.CatColor;
import com.shilaeva.models.Owner;
import com.shilaeva.repositories.CatRepository;
import com.shilaeva.repositories.OwnerRepository;
import com.shilaeva.services.implementations.CatServiceImpl;
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

class CatServiceTest {
    @Mock
    private CatRepository catRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @InjectMocks
    private CatServiceImpl catService;
    private MockitoSession mockitoSession;

    private List<Cat> cats;
    private CatDto catPersikDto;
    private CatDto catVasyaDto;
    private Owner owner;
    private OwnerDto ownerDto;
    private Calendar catPersikBirthday;
    private Calendar catVasyaBirthday;

    @BeforeEach
    void setUp() {
        mockitoSession = Mockito.mockitoSession().initMocks(this).startMocking();

        Calendar ownerBirthday = new Calendar.Builder().setDate(2003, Calendar.MARCH, 20).build();
        owner = new Owner("Nadezhda", ownerBirthday.getTime());
        owner.setId(1L);
        ownerDto = OwnerDto.asDto(owner);

        catPersikBirthday = new Calendar.Builder().setDate(2010, Calendar.JULY, 1).build();
        Cat catPersik = new Cat("Persik", catPersikBirthday.getTime(), "domestic", CatColor.RED);
        catPersik.setId(1L);
        catPersik.setOwner(owner);
        catPersikDto = CatDto.asDto(catPersik);

        catVasyaBirthday = new Calendar.Builder().setDate(2018, Calendar.APRIL, 5).build();
        Cat catVasya = new Cat("Vasya", catVasyaBirthday.getTime(), "British", CatColor.GREY);
        catVasya.setId(2L);
        catVasya.setOwner(owner);
        catVasyaDto = CatDto.asDto(catVasya);

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
    void getById_getCat() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));

        CatDto catDto = catService.getById(1L);

        assertEquals(catPersikDto, catDto);
    }

    @Test
    void getByName_getCat() {
        Mockito.when(catRepository.findByName("Persik")).thenReturn(List.of(cats.get(0)));

        List<CatDto> catDtoList = catService.getByName("Persik");

        assertEquals(List.of(catPersikDto), catDtoList);
    }

    @Test
    void getByNameAndOwner_getCat() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(catRepository.findByNameAndOwner("Persik", owner)).thenReturn(List.of(cats.get(0)));

        List<CatDto> catDtoList = catService.getByNameAndOwner("Persik", 1L);

        assertEquals(List.of(catPersikDto), catDtoList);
    }

    @Test
    void getByBirthdate_getCat() {
        Mockito.when(catRepository.findByBirthdate(catPersikBirthday.getTime())).thenReturn(List.of(cats.get(0)));

        List<CatDto> catDtoList = catService.getByBirthdate(catPersikBirthday.getTime());

        assertEquals(List.of(catPersikDto), catDtoList);
    }

    @Test
    void getByBirthdateAndOwner_getCat() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(catRepository.findByBirthdateAndOwner(catPersikBirthday.getTime(), owner))
                .thenReturn(List.of(cats.get(0)));

        List<CatDto> catDtoList = catService.getByBirthdateAndOwner(catPersikBirthday.getTime(), 1L);

        assertEquals(List.of(catPersikDto), catDtoList);
    }

    @Test
    void getByBreed_throwsException() {
        Mockito.when(catRepository.findByBreed("Scotland")).thenReturn(List.of());

        assertThrows(CatException.class, () -> catService.getByBreed("Scotland"));
    }

    @Test
    void getByBreedAndOwner_throwsException() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(catRepository.findByBreedAndOwner("Scotland", owner)).thenReturn(List.of(cats.get(0)));

        List<CatDto> catDtoList = catService.getByBreedAndOwner("Scotland", 1L);

        assertEquals(List.of(catPersikDto), catDtoList);
    }

    @Test
    void getByColor_getCat() {
        Mockito.when(catRepository.findByColor(CatColor.RED)).thenReturn(List.of(cats.get(0)));

        List<CatDto> catDtoList = catService.getByColor("red");

        assertEquals(List.of(catPersikDto), catDtoList);
    }

    @Test
    void getByColorAndOwner_getCat() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(catRepository.findByColorAndOwner(CatColor.RED, owner)).thenReturn(List.of(cats.get(0)));

        List<CatDto> catDtoList = catService.getByColorAndOwner("red", 1L);

        assertEquals(List.of(catPersikDto), catDtoList);
    }

    @Test
    void getByOwner_getListOfCats() {
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Mockito.when(catRepository.findByOwner(owner)).thenReturn(cats);

        List<CatDto> catDtoList = catService.getByOwner(1L);

        assertEquals(List.of(catPersikDto, catVasyaDto), catDtoList);
    }

    @Test
    void getAll_getListOfAllCats() {
        Mockito.when(catRepository.findAll()).thenReturn(cats);

        List<CatDto> catDtoList = catService.getAll();

        assertEquals(List.of(catPersikDto, catVasyaDto), catDtoList);
    }

    @Test
    void getOwner_getCatOwner() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));

        OwnerDto catOwnerDto = catService.getOwner(1L);

        assertEquals(ownerDto, catOwnerDto);
    }

    @Test
    void findFriends_getCatFriends() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));

        List<CatDto> catDtoList = catService.getFriends(1L);

        assertEquals(List.of(catVasyaDto), catDtoList);
    }

    @Test
    void createCat_getCreatedCat() {
        Mockito.when(catRepository.save(any(Cat.class))).thenReturn(cats.get(0));

        CatDto newCatDto = catService.createCat("Persik", catPersikBirthday.getTime(), "domestic", "red");

        assertEquals(catPersikDto, newCatDto);
    }

    @Test
    void setName_getCatWithNewName() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));
        Mockito.when(catRepository.save(cats.get(0))).thenReturn(cats.get(0));

        CatDto catDto = catService.setName(1L, "Alex");

        assertEquals(new CatDto(1L, "Alex", BirthdateDto.asBirthdateDto(catPersikBirthday.getTime()),
                "domestic", "RED"), catDto);
    }

    @Test
    void setBirthdate_getCatWithNewBirthdate() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));
        Mockito.when(catRepository.save(cats.get(0))).thenReturn(cats.get(0));

        CatDto catDto = catService.setBirthdate(1L, catVasyaBirthday.getTime());

        assertEquals(new CatDto(1L, "Persik", BirthdateDto.asBirthdateDto(catVasyaBirthday.getTime()),
                "domestic", "RED"), catDto);
    }

    @Test
    void setBreed_getCatWithNewBreed() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));
        Mockito.when(catRepository.save(cats.get(0))).thenReturn(cats.get(0));

        CatDto catDto = catService.setBreed(1L, "Persian");

        assertEquals(new CatDto(1L, "Persik", BirthdateDto.asBirthdateDto(catPersikBirthday.getTime()),
                "Persian", "RED"), catDto);
    }

    @Test
    void setColor_getCatWithNewColor() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));
        Mockito.when(catRepository.save(cats.get(0))).thenReturn(cats.get(0));

        CatDto catDto = catService.setColor(1L, "WHITE");

        assertEquals(new CatDto(1L, "Persik", BirthdateDto.asBirthdateDto(catPersikBirthday.getTime()),
                "domestic", "WHITE"), catDto);
    }

    @Test
    void addFriend_alreadyFriends_throwsException() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));
        Mockito.when(catRepository.findById(2L)).thenReturn(Optional.of(cats.get(1)));

        assertThrows(CatException.class, () -> catService.addFriend(1L, 2L));
    }

    @Test
    void addFriend_sameCat_throwsException() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));

        assertThrows(CatException.class, () -> catService.addFriend(1L, 1L));
    }

    @Test
    void addFriend_addFriendToOther_throwsException() {
        Cat catAlex = new Cat("Alex", catPersikBirthday.getTime(), "British", CatColor.BLACK);

        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));
        Mockito.when(catRepository.findById(3L)).thenReturn(Optional.of(catAlex));

        catService.addFriend(1L, 3L);

        assertThrows(CatException.class, () -> catService.addFriend(3L, 1L));
    }

    @Test
    void removeFriend_catsNotFriends_throwsException() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));
        Mockito.when(catRepository.findById(3L)).thenReturn(Optional
                .of(new Cat("Alex", catPersikBirthday.getTime(), "British", CatColor.BLACK)));

        assertThrows(CatException.class, () -> catService.removeFriend(1L, 3L));
    }

    @Test
    void removeFriend_removeFriendFromOther_throwsException() {
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(cats.get(0)));
        Mockito.when(catRepository.findById(2L)).thenReturn(Optional.of(cats.get(1)));

        catService.removeFriend(1L, 2L);

        assertThrows(CatException.class, () -> catService.removeFriend(2L, 1L));
    }

    @Test
    void deleteCat_methodWasInvoked() {
        Mockito.doNothing().when(catRepository).deleteById(1L);

        catService.deleteCat(1L);

        Mockito.verify(catRepository).deleteById(1L);
    }
}