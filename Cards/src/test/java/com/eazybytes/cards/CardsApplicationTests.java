package com.eazybytes.cards;

import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.impl.CardsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.smartcardio.Card;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CardsApplicationTests {

    @Mock
    private CardsRepository cardsRepository;

    @InjectMocks
    private CardsServiceImpl cardsService;

    private Cards cards;
    private CardsDto cardsDto;
    @BeforeEach
void setUp(){
     cards= new Cards();
    cards.setCardId(1L);
    cards.setCardNumber("1234567890123456");
    cards.setMobileNumber("9999999999");
    cards.setCardType("CREDIT_CARD");
    cards.setTotalLimit(10000);
    cards.setAmountUsed(0);
    cards.setAvailableAmount(10000);

     cardsDto= new CardsDto();
    cardsDto.setCardNumber("1234567890123456");
    cardsDto.setMobileNumber("9999999999");
    cardsDto.setCardType("CREDIT_CARD");
    cardsDto.setTotalLimit(10000);
    cardsDto.setAmountUsed(0);
    cardsDto.setAvailableAmount(10000);
}
	@Test
	void createCardWhenMobileNumberNotPresent() {

        when(cardsRepository.findByMobileNumber("9999999999")).thenReturn(Optional.empty());
	    cardsService.createCard("9999999999");

        //when(cardsRepository.findByMobileNumber("9999999999")).thenReturn(Optional.ofNullable(null));
        //assertThrows(CardAlreadyExistsException.class,()->cardsService.createCard("9999999999"));

    }

    @Test
    void createCardWhenMobileNumberPresent() {

        when(cardsRepository.findByMobileNumber("9999999999")).thenReturn(Optional.of(cards));
        assertThrows(CardAlreadyExistsException.class,()->cardsService.createCard("9999999999"));

    }

    //fetch card

    @Test
    void fetchCardWhenMobileNumberPresent() {

        when(cardsRepository.findByMobileNumber("9999999999")).thenReturn(Optional.of(cards));
        CardsDto result=cardsService.fetchCard("9999999999");
        assertNotNull(result);
        assertEquals("1234567890123456", result.getCardNumber());
        assertEquals("9999999999", result.getMobileNumber());

    }

    @Test
    void fetchCardWhenMobileNumberNotPresent() {

        when(cardsRepository.findByMobileNumber("9999999999")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->cardsService.fetchCard("9999999999"));

    }

    @Test
    void testUpdateCardWhenCardNumberPresent() {
        when(cardsRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.of(cards));
        boolean isUpdated=cardsService.updateCard(cardsDto);
        assertTrue(isUpdated);
    }

    @Test
    void testUpdateCardWhenCardNumberNotPresent() {
        when(cardsRepository.findByCardNumber("1234567890123456")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->cardsService.updateCard(cardsDto));
    }

    @Test
    void testDeleteCardWhenCardNumberPresent() {
       when(cardsRepository.findByMobileNumber("9999999999")).thenReturn(Optional.of(cards));
       boolean isDeleted=cardsService.deleteCard("9999999999");
       assertTrue(isDeleted);

       verify(cardsRepository,times(1)).deleteById(1L);
        // Implement delete card test when delete method is available
    }

    @Test
    void testDeleteCardWhenCardNumberNotPresent() {
        when(cardsRepository.findByMobileNumber("9999999999")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->cardsService.deleteCard("9999999999"));
    }

}
