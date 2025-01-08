package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class PokemonMetadataProviderTest {

    private final PokemonMetadataProvider metadataProvider = new PokemonMetadataProvider();
    private final PokemonLoader pokemonLoader = new PokemonLoader();
    private List<PokemonMetadata> pokemonsMetadata;

    @Before
    public void setUp() {
        // Chargement des métadonnées des Pokémon à partir du fichier
        pokemonsMetadata = pokemonLoader.loadPokemonsMetadata("pokedexfile");
    }

    @Test
    public void testGetPokemonMetadata() throws PokedexException {
        int index = 1;
        // Vérification des métadonnées pour chaque Pokémon
        for (PokemonMetadata expectedMetadata : pokemonsMetadata) {
            // Appel de la méthode à tester pour récupérer les métadonnées d'un Pokémon par index
            PokemonMetadata actualMetadata = metadataProvider.getPokemonMetadata(index);

            // Vérifications des valeurs des métadonnées retournées
            assertNotNull("Les métadonnées ne doivent pas être nulles", actualMetadata);
            assertEquals("L'index des métadonnées doit correspondre à celui du Pokémon", expectedMetadata.getIndex(), actualMetadata.getIndex());
            assertEquals("Le nom du Pokémon doit correspondre aux métadonnées", expectedMetadata.getName(), actualMetadata.getName());
            assertEquals("L'attaque du Pokémon doit correspondre aux métadonnées", expectedMetadata.getAttack(), actualMetadata.getAttack());
            assertEquals("La défense du Pokémon doit correspondre aux métadonnées", expectedMetadata.getDefense(), actualMetadata.getDefense());
            assertEquals("L'endurance du Pokémon doit correspondre aux métadonnées", expectedMetadata.getStamina(), actualMetadata.getStamina());

            index++; // Passage au Pokémon suivant
        }
    }

    @Test
    public void testGetPokemonMetadataInvalidIndex() throws PokedexException {
        int invalidIndex = -1;

        // Vérification qu'une exception est bien levée pour un index invalide
        try {
            metadataProvider.getPokemonMetadata(invalidIndex);
            fail("Une PokedexException était attendue");
        } catch (PokedexException e) {
            // Vérification du message d'exception
            assertEquals("Le message d'erreur doit correspondre à 'Invalid index'", "Invalid index", e.getMessage());
        }
    }
}
