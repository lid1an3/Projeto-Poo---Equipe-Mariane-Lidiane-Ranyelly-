import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

class Produto {
    protected String nome;
    protected Double preco;

    public Produto(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String etiquetaPreco() {
        return nome + " R$ " + String.format("%.2f", preco);
    }
}

class ProdutoImportado extends Produto {
    private Double taxaAlfandega;

    public ProdutoImportado(String nome, Double preco, Double taxaAlfandega) {
        super(nome, preco);
        this.taxaAlfandega = taxaAlfandega;
    }

    public Double precoTotal() {
        return preco + taxaAlfandega;
    }

    
    public String etiquetaPreco() {
        return nome + " R$ " + String.format("%.2f", precoTotal()) + " (Taxa alfândega: R$ " + String.format("%.2f", taxaAlfandega) + ")";
    }
}

class ProdutoUsado extends Produto {
    private Date dataFabricacao;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ProdutoUsado(String nome, Double preco, Date dataFabricacao) {
        super(nome, preco);
        this.dataFabricacao = dataFabricacao;
    }

    
    public String etiquetaPreco() {
        return nome + " (usado) R$ " + String.format("%.2f", preco) + " (Data de fabricação: " + sdf.format(dataFabricacao) + ")";
    }
}

public class Main {
    public static void main(String[] args) throws ParseException {
        Locale.setDefault(new Locale("pt", "BR"));
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<Produto> produtos = new ArrayList<>();

        System.out.print("Digite o número de produtos: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.println("Dados do produto #" + i + ":");
            System.out.print("Comum, usado ou importado (c/u/i)? ");
            char tipo = sc.next().charAt(0);
            System.out.print("Nome: ");
            sc.nextLine();
            String nome = sc.nextLine();
            System.out.print("Preço: ");
            double preco = sc.nextDouble();

            if (tipo == 'c') {
                produtos.add(new Produto(nome, preco));
            } else if (tipo == 'u') {
                System.out.print("Data de fabricação (DD/MM/YYYY): ");
                Date data = sdf.parse(sc.next());
                produtos.add(new ProdutoUsado(nome, preco, data));
            } else if (tipo == 'i') {
                System.out.print("Taxa alfândega: ");
                double taxaAlfandega = sc.nextDouble();
                produtos.add(new ProdutoImportado(nome, preco, taxaAlfandega));
            }
        }

        System.out.println();
        System.out.println("ETIQUETAS DE PREÇO:");
        for (Produto prod : produtos) {
            System.out.println(prod.etiquetaPreco());
        }

        sc.close();
    }
}
