import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Produto {
    protected String nome;
    protected double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String etiquetaDePreco() {
        DecimalFormat df = new DecimalFormat("0.00");
        return nome + " R$ " + df.format(preco);
    }
}

class ProdutoImportado extends Produto {
    private double taxaAlfandega;

    public ProdutoImportado(String nome, double preco, double taxaAlfandega) {
        super(nome, preco);
        this.taxaAlfandega = taxaAlfandega;
    }

    
    public String etiquetaDePreco() {
        DecimalFormat df = new DecimalFormat("0.00");
        return nome + " R$ " + df.format(precoTotal()) + " (Taxa alfândega: R$ " + df.format(taxaAlfandega) + ")";
    }

    public double precoTotal() {
        return preco + taxaAlfandega;
    }
}

class ProdutoUsado extends Produto {
    private Date dataFabricacao;

    public ProdutoUsado(String nome, double preco, Date dataFabricacao) {
        super(nome, preco);
        this.dataFabricacao = dataFabricacao;
    }

    
    public String etiquetaDePreco() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return nome + " (usado) R$ " + preco + " (Data de fabricação: " + sdf.format(dataFabricacao) + ")";
    }
}

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<Produto> produtos = new ArrayList<>();

        System.out.print("Digite o número de produtos: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Dados do produto #" + (i + 1) + ":");
            System.out.print("Comum, usado ou importado (c/u/i)? ");
            char tipo = sc.next().charAt(0);

            System.out.print("Nome: ");
            sc.nextLine();
            String nome = sc.nextLine();

            System.out.print("Preço: ");
            double preco = sc.nextDouble();

            if (tipo == 'i') {
                System.out.print("Taxa alfândega: ");
                double taxaAlfandega = sc.nextDouble();
                produtos.add(new ProdutoImportado(nome, preco, taxaAlfandega));
            } else if (tipo == 'u') {
                System.out.print("Data de fabricação (DD/MM/YYYY): ");
                Date dataFabricacao = sdf.parse(sc.next());
                produtos.add(new ProdutoUsado(nome, preco, dataFabricacao));
            } else {
                produtos.add(new Produto(nome, preco));
            }
        }

        System.out.println();
        System.out.println("ETIQUETAS DE PREÇO:");
        for (Produto produto : produtos) {
            System.out.println(produto.etiquetaDePreco());
        }

        sc.close();
    }
}
