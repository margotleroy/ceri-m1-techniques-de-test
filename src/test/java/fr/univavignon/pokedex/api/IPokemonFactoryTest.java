package fr.univavignon.pokedex.api;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class IPokemonFactoryTest extends TestCase {

    private IPokemonFactory pokemonFactory;
    private PokemonLoader pokemonLoader = new PokemonLoader();
    private List<Pokemon> pokemons;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialisation du mock pour la fabrique de Pokémons
        pokemonFactory = mock(IPokemonFactory.class);

        // Chargement des Pokémons depuis le fichier
        pokemons = pokemonLoader.loadPokemons("pokedexfile");
    }

    @Test
    public void testCreatePokemon() {
        for (Pokemon loadedPokemon : pokemons) {
            // Simulation de la création de Pokémon par la fabrique
            when(pokemonFactory.createPokemon(
                    loadedPokemon.getIndex(),
                    loadedPokemon.getCp(),
                    loadedPokemon.getHp(),
                    loadedPokemon.getDust(),
                    loadedPokemon.getCandy()
            )).thenReturn(loadedPokemon);

            // Appel à la méthode createPokemon
            Pokemon pokemon = pokemonFactory.createPokemon(
                    loadedPokemon.getIndex(),
                    loadedPokemon.getCp(),
                    loadedPokemon.getHp(),
                    loadedPokemon.getDust(),
                    loadedPokemon.getCandy()
            );

            // Vérification des attributs du Pokémon créé
            assertNotNull(pokemon); // Vérifie que l'objet Pokémon n'est pas null
            assertEquals(loadedPokemon, pokemon); // Vérifie que le Pokémon créé correspond au Pokémon attendu
            assertEquals(loadedPokemon.getIndex(), pokemon.getIndex());
            assertEquals(loadedPokemon.getCp(), pokemon.getCp());
            assertEquals(loadedPokemon.getHp(), pokemon.getHp());
            assertEquals(loadedPokemon.getDust(), pokemon.getDust());
            assertEquals(loadedPokemon.getCandy(), pokemon.getCandy());
        }
    }
}
