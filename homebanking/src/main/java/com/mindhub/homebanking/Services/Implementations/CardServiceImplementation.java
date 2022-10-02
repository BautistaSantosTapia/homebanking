package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.modals.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImplementation implements CardService {

    @Autowired
    private CardRepository cardRepository;


    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Card getCardId(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
