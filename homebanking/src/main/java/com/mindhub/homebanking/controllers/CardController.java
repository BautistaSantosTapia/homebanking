package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.modals.Card;
import com.mindhub.homebanking.modals.CardColor;
import com.mindhub.homebanking.modals.CardType;
import com.mindhub.homebanking.modals.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mindhub.homebanking.modals.CardColor.*;
import static com.mindhub.homebanking.modals.CardType.credito;
import static com.mindhub.homebanking.modals.CardType.debito;

@RestController
public class CardController {

    /*@Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;*/

    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;


    @GetMapping("/api/cards")
    public List<CardDTO> getCardsDTO(){
        return cardService.getAllCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @GetMapping("/api/cards/{id}")
    public CardDTO getCardsDTOId(@PathVariable Long id){
        return new CardDTO(cardService.getCardId(id));
    }



    @PostMapping("/api/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType type, @RequestParam CardColor color, Authentication authentication) {

        /*if(type == null || color == null){
            return new ResponseEntity<>("Complete los parametros",HttpStatus.FORBIDDEN);
        }*/

        Client client = clientService.getClientMail(authentication.getName());

        List<Card> cartasActivas = client.getCards().stream().filter(carta -> carta.isActiveCard() == true).collect(Collectors.toList());

        List<Card> tipoDeCarta = cartasActivas.stream().filter(carta-> carta.getType().equals(type)).collect(Collectors.toList());
        List<Card> tipoDeCartaColor = tipoDeCarta.stream().filter(colo -> colo.getColor().equals(color)).collect(Collectors.toList());


        if (tipoDeCarta.stream().count()>=3){
            return new ResponseEntity<>("No podes tener mas de 3 tarjetas de "+type,HttpStatus.FORBIDDEN); //
        }

        if (tipoDeCartaColor.stream().count()>=1){
            return new ResponseEntity<>("No podes tener mas de 1 tarjetas de "+color+" de "+type,HttpStatus.FORBIDDEN); //
        }


        String number = CardUtils.getCardNumber();
        int cvv = CardUtils.getCVV();

        Card card = new Card(type, color, number,client.getNombre() + " " + client.getApellido(), LocalDateTime.now(),
                LocalDateTime.now().plusYears(5), cvv, client,true);


        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PatchMapping("/api/clients/current/cards/{id}")
    public ResponseEntity<Object> modificar(@PathVariable Long id){

        Card card = cardService.getCardId(id);
        card.setActiveCard(false);

        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.ACCEPTED); //HttpStatus.OK
    }

    /*@GetMapping("/api/clients/current/cards/{id}")
    public CardDTO getCurrentCardId(@PathVariable Long id){
        return new CardDTO(cardService.getCardId(id));
    }*/
    /*@DeleteMapping("/api/clients/current/cards/{id}")
    public void deleteCard(@PathVariable Long id){
        cardService.deleteCard(id);
    }*/
}
