package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

public class PokedexFactoryTest {

    private PokedexFactory pokedexFactory;
    private IPokemonMetadataProvider metadataProvider;
    private IPokemonFactory pokemonFactory;

    @Before
    public void setUp() {
        // Initialisation des instances
        pokedexFactory = new PokedexFactory();
        metadataProvider = new PokemonMetadataProvider();
        pokemonFactory = new PokemonFactory();
    }

    @Test
    public void testCreatePokedex() {
        // Création d'une instance de Pokedex via la PokedexFactory
        IPokedex pokedex = pokedexFactory.createPokedex(metadataProvider, pokemonFactory);

        // Vérification que le Pokedex n'est pas null
        assertNotNull(pokedex, "La création du Pokedex ne doit pas retourner une instance null");
        // Vérification que l'objet retourné est bien une instance de Pokedex
        assertTrue(pokedex instanceof Pokedex, "L'objet retourné doit être une instance de Pokedex");

        // Vérification que le Pokedex est initialement vide
        assertEquals(0, pokedex.size(), "Le Pokedex doit être vide après sa création");

        // Test de l'ajout d'un Pokémon et des dépendances internes
        Pokemon pokemon = pokemonFactory.createPokemon(1, 500, 100, 50, 10);
        pokedex.addPokemon(pokemon);
        assertEquals(1, pokedex.size(), "Le Pokedex doit contenir un Pokémon après un ajout");
    }
}
