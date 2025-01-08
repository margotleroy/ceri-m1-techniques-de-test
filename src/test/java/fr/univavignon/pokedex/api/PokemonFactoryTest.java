package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PokemonFactoryTest {

    private PokemonFactory pokemonFactory;

    @Before
    public void setUp() {
        pokemonFactory = new PokemonFactory();
    }

    @Test
    public void testCreatePokemon() {
        // Données d'entrée pour la création d'un Pokémon
        int index = 1;
        int cp = 500;
        int hp = 100;
        int dust = 50;
        int candy = 10;

        // Création du Pokémon
        Pokemon pokemon = pokemonFactory.createPokemon(index, cp, hp, dust, candy);

        // Vérifications des attributs du Pokémon créé
        assertNotNull("Le Pokémon créé ne doit pas être nul", pokemon);
        assertEquals("L'index du Pokémon doit correspondre à l'index fourni", index, pokemon.getIndex());
        assertEquals("Les CP du Pokémon doivent correspondre aux CP fournis", cp, pokemon.getCp());
        assertEquals("Les HP du Pokémon doivent correspondre aux HP fournis", hp, pokemon.getHp());
        assertEquals("La poussière du Pokémon doit correspondre à la valeur fournie", dust, pokemon.getDust());
        assertEquals("Les bonbons du Pokémon doivent correspondre à la valeur fournie", candy, pokemon.getCandy());
        assertEquals("L'IV du Pokémon doit être initialisé à zéro", 0, pokemon.getIv(), 0);

        // Vérifications des métadonnées du Pokémon via le fournisseur de métadonnées
        assertEquals("Le nom du Pokémon doit correspondre aux métadonnées", "Bulbizarre", pokemon.getName());
        assertEquals("L'attaque du Pokémon doit correspondre aux métadonnées", 126, pokemon.getAttack());
        assertEquals("La défense du Pokémon doit correspondre aux métadonnées", 126, pokemon.getDefense());
        assertEquals("L'endurance du Pokémon doit correspondre aux métadonnées", 90, pokemon.getStamina());
    }

    @Test(expected = RuntimeException.class)
    public void testCreatePokemonWithInvalidIndex() {
        // Test de création d'un Pokémon avec un index invalide
        int invalidIndex = 999;
        pokemonFactory.createPokemon(invalidIndex, 500, 100, 50, 10);
    }
}
