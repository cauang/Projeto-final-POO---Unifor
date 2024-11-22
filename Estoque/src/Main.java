import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaEstoque sistema = new SistemaEstoque();

        boolean continuar = true;

        try {
            sistema.carregarEstoqueDeArquivo("estoque.txt");
            sistema.carregarHistoricoMovimentacaoDeArquivo("historico.txt");
        } catch (IOException e) {
            System.out.println("Erro ao carregar os arquivos: " + e.getMessage());
        }

        while (continuar) {
            System.out.println("\n===== Sistema de Controle de Estoque =====");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Realizar Movimentação");
            System.out.println("3. Exibir Relatório");
            System.out.println("4. Exibir Histórico de Movimentação");
            System.out.println("5. Salvar Estoque e Histórico em Arquivos");
            System.out.println("6. Carregar Estoque e Histórico de Arquivos");
            System.out.println("7. Sair");
            System.out.println("8. Apagar dados");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do produto: ");
                    String nomeProduto = scanner.nextLine();
                    System.out.print("Digite a quantidade do produto: ");
                    int quantidadeProduto = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Digite a data de validade (apenas para produtos perecíveis, ou deixe em branco): ");
                    String validade = scanner.nextLine();

                    Produto novoProduto;
                    if (validade.isEmpty()) {
                        novoProduto = new ProdutoNaoPerecivel(nomeProduto, quantidadeProduto);
                    } else {
                        novoProduto = new ProdutoPerecivel(nomeProduto, quantidadeProduto, validade);
                    }

                    sistema.adicionarProduto(novoProduto);
                    System.out.println("Produto adicionado com sucesso.");
                    break;

                case 2:
                    System.out.print("Digite o nome do produto para movimentação: ");
                    String nomeMovimentacao = scanner.nextLine();
                    System.out.print("Digite a quantidade: ");
                    int quantidadeMovimentacao = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Digite o tipo de movimentação (entrada/saida): ");
                    String tipoMovimentacao = scanner.nextLine();

                    try {
                        sistema.movimentarProduto(nomeMovimentacao, quantidadeMovimentacao, tipoMovimentacao);
                        System.out.println("Movimentação realizada com sucesso.");
                    } catch (ProdutoNaoEncontradoException | EstoqueInsuficienteException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 3:
                    sistema.gerarRelatorio();
                    break;

                case 4:
                    sistema.gerarAuditoria();
                    break;

                case 5:
                    try {
                        sistema.gravarEstoqueEmArquivo("estoque.txt");
                        sistema.gravarHistoricoMovimentacaoEmArquivo("historico.txt");
                        System.out.println("Dados salvos com sucesso.");
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar os arquivos: " + e.getMessage());
                    }
                    break;

                case 6:
                    try {
                        sistema.carregarEstoqueDeArquivo("estoque.txt");
                        sistema.carregarHistoricoMovimentacaoDeArquivo("historico.txt");
                        System.out.println("Dados carregados com sucesso.");
                    } catch (IOException e) {
                        System.out.println("Erro ao carregar os arquivos: " + e.getMessage());
                    }
                    break;

                case 7:
                    continuar = false;
                    System.out.println("Saindo do sistema...");
                    break;

                case 8:
                    sistema.apagarDados();
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
}
