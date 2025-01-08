package fr.univavignon.pokedex.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IPokedexFactoryTest {

    private IPokedexFactory pokedexFactory;
    private IPokemonMetadataProvider metadataProvider;
    private IPokemonFactory pokemonFactory;

    @BeforeEach
    void setUp() {
        // Création des mocks pour les dépendances
        metadataProvider = mock(IPokemonMetadataProvider.class);
        pokemonFactory = mock(IPokemonFactory.class);
        pokedexFactory = mock(IPokedexFactory.class);
    }

    @Test
    void createPokedex() {
        // Mock du Pokedex créé par la factory
        IPokedex mockPokedex = mock(IPokedex.class);

        // Définition du comportement de la méthode createPokedex
        when(pokedexFactory.createPokedex(metadataProvider, pokemonFactory)).thenReturn(mockPokedex);

        // Appel de la méthode à tester
        IPokedex pokedex = pokedexFactory.createPokedex(metadataProvider, pokemonFactory);

        // Vérifications
        assertNotNull(pokedex, "Le pokedex ne doit pas être null");
        assertTrue(pokedex instanceof IPokedex, "L'objet créé doit être une instance de IPokedex");
    }
}
