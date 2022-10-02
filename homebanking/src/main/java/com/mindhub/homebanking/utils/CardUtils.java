package com.mindhub.homebanking.utils;

public final class CardUtils { // impide que otras clases puedan heredar de esta

    String number = getCardNumber();
    int cvv = getCVV();

    private CardUtils() {} // constructor por defecto en privado para que no se pueda instanciar la clase

    public static int getCVV() { // no necesita tener la clase instanciada para poder usarse
        int cvv = (int) ((Math.random() * (999 - 100))+100);
        return cvv;
    }

    public static String getCardNumber() {
        String number = (int) ((Math.random() * (9999 - 1000))+1000) + "-" + (int) ((Math.random() * (9999 - 1000))+1000) +
                        "-"+ (int) ((Math.random() * (9999 - 1000))+1000) + "-" + (int) ((Math.random() * (9999 - 1000))+1000);
        return number;
    }
}
