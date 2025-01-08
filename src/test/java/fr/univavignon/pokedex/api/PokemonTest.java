package fr.univavignon.pokedex.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    // Création d'une instance de Bulbizarre pour les tests
    private final Pokemon bulbizarre = new Pokemon(1, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, 56);

    @Test
    void testGetCp() {
        // Vérification que le CP de Bulbizarre est correct
        assertEquals(613, bulbizarre.getCp(), "Le CP de Bulbizarre doit être égal à 613");
    }

    @Test
    void testGetHp() {
        // Vérification que les HP de Bulbizarre sont corrects
        assertEquals(64, bulbizarre.getHp(), "Les HP de Bulbizarre doivent être égaux à 64");
    }

    @Test
    void testGetDust() {
        // Vérification que la quantité de poussière de Bulbizarre est correcte
        assertEquals(4000, bulbizarre.getDust(), "La quantité de poussière de Bulbizarre doit être égale à 4000");
    }

    @Test
    void testGetCandy() {
        // Vérification que le nombre de bonbons de Bulbizarre est correct
        assertEquals(4, bulbizarre.getCandy(), "Le nombre de bonbons de Bulbizarre doit être égal à 4");
    }

    @Test
    void testGetIv() {
        // Vérification que l'IV de Bulbizarre est correct
        assertEquals(56, bulbizarre.getIv(), "L'IV de Bulbizarre doit être égal à 56");
    }
}
