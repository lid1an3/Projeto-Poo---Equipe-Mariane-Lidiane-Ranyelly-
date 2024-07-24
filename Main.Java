import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Produto {
    protected String nome;
    protected double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String etiquetaPreco() {
        return nome + " R$ " + String.format("%.2f", preco);
    }
}

class ProdutoImportado extends Produto {
    private double taxaAlfandega;

    public ProdutoImportado(String nome, double preco, double taxaAlfandega) {
        super(nome, preco);
        this.taxaAlfandega = taxaAlfandega;
    }

    public double getPreco() {
        return super.getPreco() + taxaAlfandega;
    }

    public String etiquetaPreco() {
        return nome + " R$ " + String.format("%.2f", getPreco()) + " (Taxa alfândega: R$ " + String.format("%.2f", taxaAlfandega) + ")";
    }
}

class ProdutoUsado extends Produto {
    private String dataFabricacao;

    public ProdutoUsado(String nome, double preco, String dataFabricacao) {
        super(nome, preco);
        this.dataFabricacao = dataFabricacao;
    }

    public String etiquetaPreco() {
        return nome + " (usado) R$ " + String.format("%.2f", preco) + " (Data de fabricação: " + dataFabricacao + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Produto> produtos = new ArrayList<>();

        System.out.print("Digite a quantidade de produtos: ");
        int N = scanner.nextInt();

        for (int i = 0; i < N; i++) {
            System.out.println("Dados do produto #" + (i + 1) + ":");
            System.out.print("Comum, usado ou importado (c/u/i)? ");
            char tipo = scanner.next().charAt(0);
            scanner.nextLine(); 
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Preço: ");
            double preco = scanner.nextDouble();

            if (tipo == 'c' || tipo == 'C') {
                produtos.add(new Produto(nome, preco));
            } else if (tipo == 'u' || tipo == 'U') {
                scanner.nextLine();
                System.out.print("Data de fabricação (DD/MM/YYYY): ");
                String dataFabricacao = scanner.nextLine();
                produtos.add(new ProdutoUsado(nome, preco, dataFabricacao));
            } else if (tipo == 'i' || tipo == 'I') {
                System.out.print("Taxa alfândega: ");
                double taxaAlfandega = scanner.nextDouble();
                produtos.add(new ProdutoImportado(nome, preco, taxaAlfandega));
            }
        }

        System.out.println("\nETIQUETAS DE PREÇO:");
        for (Produto prod : produtos) {
            System.out.println(prod.etiquetaPreco());
        }

        scanner.close();
    }
}
