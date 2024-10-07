package fr.univavignon.pokedex.api;

import org.mockito.Mockito;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;



public class IPokemonMetadataProviderTest {

    private IPokemonMetadataProvider metadataProvider;
    private PokemonMetadata metadata;

    @Before
    public void setUp() throws PokedexException {
        metadataProvider = Mockito.mock(IPokemonMetadataProvider.class);
        metadata = new PokemonMetadata(1, "Bulbasaur", 12, 10, 15);

        Mockito.when(metadataProvider.getPokemonMetadata(1)).thenReturn(metadata);
    }

    @Test
    public void testGetPokemonMetadata() throws PokedexException {
        PokemonMetadata result = metadataProvider.getPokemonMetadata(1);
        assertEquals(metadata, result);
    }

    @Test
    public void testGetPokemonMetadataInvalidIndex() {
        assertThrows(PokedexException.class, () -> {
            metadataProvider.getPokemonMetadata(-1);
        });
    }
}