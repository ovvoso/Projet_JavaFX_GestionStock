package com.example.examenjpapersistence.ExceptionError;

public class ProduitNotFoundException extends RuntimeException{

        public ProduitNotFoundException(String message) { // <-- Ajout d’un constructeur avec message
            super(message);
        }


}
