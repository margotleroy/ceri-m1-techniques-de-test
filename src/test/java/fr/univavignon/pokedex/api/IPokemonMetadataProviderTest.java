package fr.univavignon.pokedex.api;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IPokemonMetadataProviderTest extends TestCase {

    private IPokemonMetadataProvider metadataProvider;
    private PokemonLoader pokemonLoader = new PokemonLoader();
    private List<Pokemon> pokemons;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // Initialisation du mock pour le fournisseur de métadonnées
        metadataProvider = mock(IPokemonMetadataProvider.class);

        // Chargement des Pokémons depuis le fichier
        pokemons = pokemonLoader.loadPokemons("pokedexfile");
    }

    @Test
    public void testGetPokemonMetadata() throws PokedexException {
        for (Pokemon pokemon : pokemons) {
            int index = pokemon.getIndex();
            // Création des métadonnées attendues pour chaque Pokémon
            PokemonMetadata expectedMetadata = new PokemonMetadata(
                pokemon.getIndex(),
                pokemon.getName(),
                pokemon.getAttack(),
                pokemon.getDefense(),
                pokemon.getStamina()
            );

            // Simulation du comportement du mock pour chaque Pokémon
            when(metadataProvider.getPokemonMetadata(index)).thenReturn(expectedMetadata);

            // Appel de la méthode à tester
            PokemonMetadata actualMetadata = metadataProvider.getPokemonMetadata(index);

            // Vérifications des résultats
            assertNotNull("Les métadonnées ne doivent pas être nulles", actualMetadata); // Vérifie que les métadonnées ne sont pas nulles
            assertEquals("L'index du Pokémon ne correspond pas", expectedMetadata.getIndex(), actualMetadata.getIndex());
            assertEquals("Le nom du Pokémon ne correspond pas", expectedMetadata.getName(), actualMetadata.getName());
            assertEquals("L'attaque du Pokémon ne correspond pas", expectedMetadata.getAttack(), actualMetadata.getAttack());
            assertEquals("La défense du Pokémon ne correspond pas", expectedMetadata.getDefense(), actualMetadata.getDefense());
            assertEquals("L'endurance du Pokémon ne correspond pas", expectedMetadata.getStamina(), actualMetadata.getStamina());
        }
    }

    @Test
    public void testGetPokemonMetadataInvalidIndex() throws PokedexException {
        int invalidIndex = -1;

        // Simulation d'une exception levée pour un index invalide
        when(metadataProvider.getPokemonMetadata(invalidIndex)).thenThrow(new PokedexException("Index invalide"));

        // Vérification que l'exception est bien levée pour un index invalide
        try {
            metadataProvider.getPokemonMetadata(invalidIndex);
            fail("Une exception PokedexException était attendue");
        } catch (PokedexException e) {
            // Vérifie le message de l'exception
            assertEquals("Index invalide", e.getMessage());
        }
    }
}
