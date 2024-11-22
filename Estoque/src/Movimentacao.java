public interface Movimentacao {

    void movimentarProduto(String nome, int quantidade, String tipoMovimentacao) throws ProdutoNaoEncontradoException, EstoqueInsuficienteException;

}
