package fr.univavignon.pokedex.api;

import org.junit.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class PokedexTest {

    private PokemonMetadataProvider pokemonMetadataProvider;
    private PokemonFactory pokemonFactory;
    private Pokedex pokedex;
    private PokemonLoader pokemonLoader;
    private List<Pokemon> pokemons;
    private List<PokemonMetadata> pokemonsMetadata;

    @Before
    public void setUp() {
        // Initialisation des objets nécessaires
        pokemonMetadataProvider = new PokemonMetadataProvider();
        pokemonFactory = new PokemonFactory();
        pokedex = new Pokedex(pokemonMetadataProvider, pokemonFactory);
        pokemonLoader = new PokemonLoader();
        pokemons = pokemonLoader.loadPokemons("pokedexfile");
        pokemonsMetadata = pokemonLoader.loadPokemonsMetadata("pokedexfile");
    }

    @Test
    public void testSize() {
        // Vérification de la taille initiale du Pokedex
        assertEquals("Le Pokedex devrait être vide au départ", 0, pokedex.size());

        // Ajout de Pokémon et vérification de la taille après chaque ajout
        for (int i = 0; i < pokemons.size(); i++) {
            pokedex.addPokemon(pokemons.get(i));
            assertEquals("La taille du Pokedex devrait augmenter après l'ajout d'un Pokémon", i + 1, pokedex.size());
        }
    }

    @Test
    public void testAddPokemon() {
        // Vérification de l'ajout de chaque Pokémon
        for (Pokemon pokemon : pokemons) {
            int index = pokedex.addPokemon(pokemon);
            assertEquals("L'index retourné devrait correspondre à l'index du Pokémon", pokemon.getIndex(), index);
        }
    }

    @Test
    public void testGetPokemon() throws PokedexException {
        // Ajout et récupération de chaque Pokémon
        for (Pokemon pokemon : pokemons) {
            pokedex.addPokemon(pokemon);
            Pokemon retrievedPokemon = pokedex.getPokemon(pokemon.getIndex());
            assertEquals("Le Pokémon récupéré devrait correspondre au Pokémon ajouté", pokemon, retrievedPokemon);
        }
    }

    @Test
    public void testGetPokemonWithInvalidIndex() {
        // Test d'accès à des indices invalides
        int[] invalidIndices = {0, 152};

        for (int invalidIndex : invalidIndices) {
            try {
                pokedex.getPokemon(invalidIndex);
                fail("Une exception PokedexException était attendue");
            } catch (PokedexException e) {
                assertEquals("L'index est invalide", e.getMessage());
            }
        }
    }

    @Test
    public void testGetPokemons() {
        List<Pokemon> expectedPokemons = new ArrayList<>();

        // Ajout de Pokémon dans le Pokedex et dans la liste attendue
        for (int i = 0; i < 10; i++) {
            Pokemon pokemon = pokemons.get(i);
            pokedex.addPokemon(pokemon);
            expectedPokemons.add(pokemon);
        }

        // Vérification de la liste des Pokémon
        assertEquals("Le contenu du Pokedex devrait correspondre à la liste attendue", expectedPokemons, pokedex.getPokemons());
        assertEquals("La taille du Pokedex devrait correspondre à la taille de la liste attendue", expectedPokemons.size(), pokedex.getPokemons().size());
    }

    @Test
    public void testGetPokemonsWithOrder() {
        // Ajout des Pokémon au Pokedex
        for (Pokemon pokemon : pokemons) {
            pokedex.addPokemon(pokemon);
        }

        // Test tri par index
        List<Pokemon> sortedByIndex = new ArrayList<>(pokemons);
        List<Pokemon> pokemonsFromPokedex = pokedex.getPokemons(PokemonComparators.INDEX);
        assertEquals("Les Pokémon devraient être triés par index", sortedByIndex, pokemonsFromPokedex);

        // Test tri par nom
        sortedByIndex.sort(Comparator.comparing(Pokemon::getName));
        pokemonsFromPokedex = pokedex.getPokemons(PokemonComparators.NAME);
        assertEquals("Les Pokémon devraient être triés par nom", sortedByIndex, pokemonsFromPokedex);
    }

    @Test
    public void testCreatePokemon() {
        // Données de test pour créer un Pokémon
        int index = 1, cp = 500, hp = 100, dust = 50, candy = 10;

        Pokemon pokemon = pokemonFactory.createPokemon(index, cp, hp, dust, candy);

        // Vérifications des propriétés du Pokémon créé
        assertNotNull("Le Pokémon créé ne doit pas être null", pokemon);
        assertEquals("L'index devrait correspondre à celui fourni", index, pokemon.getIndex());
        assertEquals("Le CP devrait correspondre à celui fourni", cp, pokemon.getCp());
        assertEquals("Le HP devrait correspondre à celui fourni", hp, pokemon.getHp());
        assertEquals("Le Dust devrait correspondre à celui fourni", dust, pokemon.getDust());
        assertEquals("Le Candy devrait correspondre à celui fourni", candy, pokemon.getCandy());
        assertEquals("L'IV devrait être zéro au départ", 0, pokemon.getIv(), 0);

        // Vérification des métadonnées via le metadataProvider
        assertEquals("Le nom devrait correspondre aux métadonnées", "Bulbizarre", pokemon.getName());
        assertEquals("L'attaque devrait correspondre aux métadonnées", 126, pokemon.getAttack());
        assertEquals("La défense devrait correspondre aux métadonnées", 126, pokemon.getDefense());
        assertEquals("L'endurance devrait correspondre aux métadonnées", 90, pokemon.getStamina());
    }

    @Test(expected = RuntimeException.class)
    public void testCreatePokemonWithInvalidIndex() {
        // Test de création d'un Pokémon avec un index invalide
        pokemonFactory.createPokemon(999, 500, 100, 50, 10);
    }

    @Test
    public void testGetPokemonMetadata() throws PokedexException {
        int index = 1;
        for (PokemonMetadata expectedMetadata : pokemonsMetadata) {
            PokemonMetadata actualMetadata = pokemonMetadataProvider.getPokemonMetadata(index);

            // Vérification des métadonnées récupérées
            assertNotNull("Les métadonnées ne doivent pas être nulles", actualMetadata);
            assertEquals("L'index devrait correspondre", expectedMetadata.getIndex(), actualMetadata.getIndex());
            assertEquals("Le nom devrait correspondre", expectedMetadata.getName(), actualMetadata.getName());
            assertEquals("L'attaque devrait correspondre", expectedMetadata.getAttack(), actualMetadata.getAttack());
            assertEquals("La défense devrait correspondre", expectedMetadata.getDefense(), actualMetadata.getDefense());
            assertEquals("L'endurance devrait correspondre", expectedMetadata.getStamina(), actualMetadata.getStamina());

            index++;
        }
    }

    @Test
    public void testGetPokemonMetadataInvalidIndex() {
        // Test d'accès à des métadonnées avec un index invalide
        int invalidIndex = -1;
        try {
            pokemonMetadataProvider.getPokemonMetadata(invalidIndex);
            fail("Une exception PokedexException était attendue");
        } catch (PokedexException e) {
            assertEquals("L'index est invalide", e.getMessage());
        }
    }
}
