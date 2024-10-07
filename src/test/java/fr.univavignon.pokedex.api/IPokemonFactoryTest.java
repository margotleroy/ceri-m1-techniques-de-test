package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.mockito.Mockito;

public class IPokemonFactoryTest {
    @Test
    public void testCreatePokemon() {
        // Crée un Pokémon en utilisant le mock
        IPokemonFactory pokemonFactory = Mockito.mock(IPokemonFactory.class);

        Pokemon pokemon = new Pokemon(0, "Bulbizarre", 126, 126, 90,613,64,4000,4,56);
        Mockito.when(pokemonFactory.createPokemon(1, 150, 120, 100, 50))
                .thenReturn(pokemon);

        Pokemon result = pokemonFactory.createPokemon(1, 150, 120, 100, 50);

        // Vérifie que le Pokémon créé n'est pas nul
        assertNotNull(result);

        // Vérifie que les informations du Pokémon créé sont correctes
        assertEquals(1, result.getIndex());
        assertEquals(150, result.getCp());
        assertEquals(120, result.getHp());
        assertEquals(100, result.getDust());
        assertEquals(50, result.getCandy());
    }
}