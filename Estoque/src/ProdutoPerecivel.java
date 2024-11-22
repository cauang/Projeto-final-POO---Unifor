public class ProdutoPerecivel extends Produto {
    private String dataValidade;

    public ProdutoPerecivel(String nome, int quantidade, String dataValidade) {
        super(nome, quantidade);
        this.dataValidade = dataValidade;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Produto Perecível: " + getNome() + ", Quantidade: " + getQuantidade() + ", Validade: " + dataValidade);
    }

    @Override
    public String toString() {
        return super.toString() + ", Data de Validade: " + dataValidade;
    }
}
