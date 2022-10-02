package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.modals.Card;

import java.util.List;

public interface CardService {

    public List<Card> getAllCards();

    public Card getCardId(Long id);

    public void saveCard(Card card);

}
