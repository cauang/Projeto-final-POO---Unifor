import java.io.*;
import java.util.*;

public class SistemaEstoque implements Movimentacao, Relatorio, Auditoria {
    private List<Produto> produtos;
    private Map<String, String> historicoMovimentacao;

    public SistemaEstoque() {
        produtos = new ArrayList<>();
        historicoMovimentacao = new HashMap<>();
    }

    @Override
    public void movimentarProduto(String nome, int quantidade, String tipoMovimentacao) throws ProdutoNaoEncontradoException, EstoqueInsuficienteException {
        Produto produto = encontrarProduto(nome);
        if (produto != null) {
            if (tipoMovimentacao.equals("entrada")) {
                produto.setQuantidade(produto.getQuantidade() + quantidade);
            } else if (tipoMovimentacao.equals("saida")) {
                if (produto.getQuantidade() >= quantidade) {;
                    produto.setQuantidade(produto.getQuantidade() - quantidade);
                } else {
                    throw new EstoqueInsuficienteException("Estoque insuficiente para a saída.");
                }
            }
            historicoMovimentacao.put(nome, tipoMovimentacao);
        }
    }

    @Override
    public void gerarRelatorio() {
        System.out.println("Relatório de Estoque:");
        for (Produto produto : produtos) {
            produto.exibirDetalhes();
        }
    }

    @Override
    public void gerarAuditoria() {
        System.out.println("Histórico de Movimentações:");
        for (Map.Entry<String, String> entry : historicoMovimentacao.entrySet()) {
            System.out.println("Produto: " + entry.getKey() + " - Movimentação: " + entry.getValue());
        }
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    private Produto encontrarProduto(String nome) throws ProdutoNaoEncontradoException {
        for (Produto produto : produtos) {
            if (produto.getNome().equals(nome)) {
                return produto;
            }
        }
        throw new ProdutoNaoEncontradoException("Produto não encontrado no estoque.");
    }

    public void gravarEstoqueEmArquivo(String nomeArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Produto produto : produtos) {
                writer.write(produto.getNome() + ";" + produto.getQuantidade());
                writer.newLine();
            }
        }
    }

    public void carregarEstoqueDeArquivo(String nomeArquivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                String nomeProduto = dados[0];
                int quantidade = Integer.parseInt(dados[1]);
                Produto produto = new ProdutoNaoPerecivel(nomeProduto, quantidade);
                produtos.add(produto);
            }
        }
    }

    public void gravarHistoricoMovimentacaoEmArquivo(String nomeArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Map.Entry<String, String> entry : historicoMovimentacao.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue());
                writer.newLine();
            }
        }
    }

    public void apagarDados() {
        produtos.clear();
        historicoMovimentacao.clear();
        System.out.println("Todos os dados foram apagados com sucesso.");
    }

    public void carregarHistoricoMovimentacaoDeArquivo(String nomeArquivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                String nomeProduto = dados[0];
                String tipoMovimentacao = dados[1];
                historicoMovimentacao.put(nomeProduto, tipoMovimentacao);
            }
        }
    }
}
