public class ProdutoNaoPerecivel extends Produto {

    public ProdutoNaoPerecivel(String nome, int quantidade) {
        super(nome, quantidade);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Produto Não Perecível: " + getNome() + ", Quantidade: " + getQuantidade());
    }
}
