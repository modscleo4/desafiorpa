package br.dev.modscleo4.marketplace;

public class MercadoLivreTest extends MarketplaceTest<MercadoLivre> {
    @Override
    protected MercadoLivre createMarketplace() {
        return new MercadoLivre();
    }
}
