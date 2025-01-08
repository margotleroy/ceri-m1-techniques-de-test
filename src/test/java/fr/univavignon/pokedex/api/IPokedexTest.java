package fr.univavignon.pokedex.api;

import org.junit.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IPokedexTest {

    private IPokedex pokedex;
    private PokemonLoader pokemonLoader = new PokemonLoader();
    private List<Pokemon> pokemons;

    @Before
    public void setUp() {
        // Initialisation du mock pour le Pokedex
        pokedex = Mockito.mock(IPokedex.class);
        // Chargement des Pokémons depuis un fichier
        pokemons = pokemonLoader.loadPokemons("pokedexfile");
    }

    @Test
    public void testSize() {
        // Simulation de la taille initiale du Pokedex
        when(pokedex.size()).thenReturn(0);
        assertEquals(0, pokedex.size());

        // Ajout des Pokémons et vérification incrémentielle de la taille
        for (int i = 0; i < pokemons.size(); i++) {
            Pokemon pokemon = pokemons.get(i);

            // Simulation de la taille après ajout
            when(pokedex.size()).thenReturn(i + 1);

            pokedex.addPokemon(pokemon);
            assertEquals(i + 1, pokedex.size());
        }
    }

    @Test
    public void testAddPokemon() {
        // Vérification de l'ajout des Pokémons avec leurs indices
        for (Pokemon pokemon : pokemons) {
            when(pokedex.addPokemon(pokemon)).thenReturn(pokemon.getIndex());
            assertEquals(pokemon.getIndex(), pokedex.addPokemon(pokemon));
        }
    }

    @Test
    public void testGetPokemon() throws PokedexException {
        // Vérification de la récupération de chaque Pokémon par son index
        for (Pokemon pokemon : pokemons) {
            int index = pokemon.getIndex();
            when(pokedex.getPokemon(index)).thenReturn(pokemon);
            assertEquals(pokemon, pokedex.getPokemon(index));
        }
    }

    @Test
    public void testGetPokemonWithInvalidIndex() {
        int invalidIndex1 = -1;
        int invalidIndex2 = 152;

        // Simulation d'une exception pour des indices invalides
        when(pokedex.getPokemon(invalidIndex1)).thenThrow(new PokedexException("Invalid index"));
        when(pokedex.getPokemon(invalidIndex2)).thenThrow(new PokedexException("Invalid index"));

        // Vérification des exceptions pour chaque cas d'index invalide
        assertThrows(PokedexException.class, () -> pokedex.getPokemon(invalidIndex1));
        assertThrows(PokedexException.class, () -> pokedex.getPokemon(invalidIndex2));
    }

    @Test
    public void testGetPokemons() {
        List<Pokemon> expectedPokemons = new ArrayList<>();

        // Simulation du comportement pour récupérer la liste des Pokémons
        when(pokedex.getPokemons()).thenReturn(expectedPokemons);

        // Ajout des 10 premiers Pokémons
        for (int i = 0; i < 10; i++) {
            Pokemon pokemon = pokemons.get(i);
            pokedex.addPokemon(pokemon);
            expectedPokemons.add(pokemon);
        }

        assertEquals(expectedPokemons, pokedex.getPokemons());
        assertEquals(expectedPokemons.size(), pokedex.getPokemons().size());

        // Ajout des 50 Pokémons suivants
        for (int i = 10; i < 50; i++) {
            Pokemon pokemon = pokemons.get(i);
            pokedex.addPokemon(pokemon);
            expectedPokemons.add(pokemon);
        }

        assertEquals(expectedPokemons, pokedex.getPokemons());
        assertEquals(expectedPokemons.size(), pokedex.getPokemons().size());
    }

    @Test
    public void testGetPokemonsWithOrder() {
        // Simulation de la liste triée par index
        List<Pokemon> sortedByIndex = new ArrayList<>(pokemons);
        when(pokedex.getPokemons(any(Comparator.class))).thenReturn(sortedByIndex);

        // Vérification de l'ordre par index
        List<Pokemon> pokemonsFromPokedex = pokedex.getPokemons(PokemonComparators.INDEX);
        assertEquals(sortedByIndex.size(), pokemonsFromPokedex.size());

        for (int i = 0; i < sortedByIndex.size(); i++) {
            assertEquals(sortedByIndex.get(i), pokemonsFromPokedex.get(i));
        }

        // Simulation de la liste triée par nom
        sortedByIndex.sort(PokemonComparators.NAME);
        when(pokedex.getPokemons(any(Comparator.class))).thenReturn(sortedByIndex);

        // Vérification de l'ordre par nom
        pokemonsFromPokedex = pokedex.getPokemons(PokemonComparators.NAME);
        assertEquals(sortedByIndex.size(), pokemonsFromPokedex.size());

        for (int i = 0; i < sortedByIndex.size(); i++) {
            assertEquals(sortedByIndex.get(i), pokemonsFromPokedex.get(i));
        }
    }
}
