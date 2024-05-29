//4 - Uma empresa de transportes precisa controlar o fluxo de produtos no seu depósito. Para auxiliar
//na organização,
//as caixas são empilhadas no máximo 10 caixas. Quando vão despachar o produto, a empilhadeira
//sempre retira e adiciona caixas
//em cima da pilha. Eles necessitam que seja exibido no painel quais produtos estão aguardando o
//despache em ordem, e no momento de retirada da caixa, qual produto está sendo despachado.
//Obs: no painel sempre depois de cada ação, mostra a posição atual da pilha, ou seja, quais
//produtos estão na pilha.
//Produto
//- codProduto
//- descricao
//- dataEntrada
//- ufOrigem
//- ufDestino

package Ex04;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

class Produto {
    private int codProduto;
    private String descricao;
    private String dataEntrada;
    private String ufOrigem;
    private String ufDestino;

    public Produto(int codProduto, String descricao, String dataEntrada, String ufOrigem, String ufDestino) {
        this.codProduto = codProduto;
        this.descricao = descricao;
        this.dataEntrada = dataEntrada;
        this.ufOrigem = ufOrigem;
        this.ufDestino = ufDestino;
    }

    @Override
    public String toString() {
        return "Código: " + codProduto + ", Descrição: " + descricao + ", Data de Entrada: " + dataEntrada +
                ", UF Origem: " + ufOrigem + ", UF Destino: " + ufDestino;
    }
}

public class Ex04 {
    private Stack<Produto> pilhaDeProdutos;
    private final int LIMITE = 10;
    private JTextArea produtosTextArea;

    public Ex04() {
        this.pilhaDeProdutos = new Stack<>();
    }

    public void addProduto(int codProduto, String descricao, String dataEntrada, String ufOrigem, String ufDestino) {
        if (pilhaDeProdutos.size() < LIMITE) {
            Produto produto = new Produto(codProduto, descricao, dataEntrada, ufOrigem, ufDestino);
            pilhaDeProdutos.push(produto);
            produtosTextArea.append("Produto adicionado: " + produto + "\n");
        } else {
            produtosTextArea.append("A pilha está cheia. Não é possível adicionar mais produtos.\n");
        }
        listarProdutos();
    }

    public void retirarProduto() {
        if (pilhaDeProdutos.isEmpty()) {
            produtosTextArea.append("Não há produtos na pilha para retirar.\n");
        } else {
            Produto produto = pilhaDeProdutos.pop();
            produtosTextArea.append("Produto retirado: " + produto + "\n");
        }
        listarProdutos();
    }

    public void listarProdutos() {
        produtosTextArea.append("\nProdutos na pilha:\n");
        if (pilhaDeProdutos.isEmpty()) {
            produtosTextArea.append("A pilha está vazia.\n");
        } else {
            for (Produto produto : pilhaDeProdutos) {
                produtosTextArea.append(produto + "\n");
            }
        }
        produtosTextArea.append("\n");
    }

    public void criarMenu() {
        JFrame frame = new JFrame("Depósito - Sistema de Controle de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton btnAdicionarProduto = new JButton("Adicionar Produto");
        JButton btnRetirarProduto = new JButton("Retirar Produto");
        JButton btnListarProdutos = new JButton("Listar Produtos");
        JButton btnSair = new JButton("Sair");

        produtosTextArea = new JTextArea(20, 50);
        produtosTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(produtosTextArea);

        btnAdicionarProduto.addActionListener((ActionEvent e) -> {
            JTextField codProdutoField = new JTextField(5);
            JTextField descricaoField = new JTextField(10);
            JTextField dataEntradaField = new JTextField(10);
            JTextField ufOrigemField = new JTextField(2);
            JTextField ufDestinoField = new JTextField(2);
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(5, 2));
            inputPanel.add(new JLabel("Código do Produto:"));
            inputPanel.add(codProdutoField);
            inputPanel.add(new JLabel("Descrição:"));
            inputPanel.add(descricaoField);
            inputPanel.add(new JLabel("Data de Entrada:"));
            inputPanel.add(dataEntradaField);
            inputPanel.add(new JLabel("UF Origem:"));
            inputPanel.add(ufOrigemField);
            inputPanel.add(new JLabel("UF Destino:"));
            inputPanel.add(ufDestinoField);
            
            int result = JOptionPane.showConfirmDialog(frame, inputPanel,
                    "Adicionar Produto", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int codProduto = Integer.parseInt(codProdutoField.getText().trim());
                    String descricao = descricaoField.getText().trim();
                    String dataEntrada = dataEntradaField.getText().trim();
                    String ufOrigem = ufOrigemField.getText().trim();
                    String ufDestino = ufDestinoField.getText().trim();
                    
                    if (!descricao.isEmpty() && !dataEntrada.isEmpty() && !ufOrigem.isEmpty() && !ufDestino.isEmpty()) {
                        addProduto(codProduto, descricao, dataEntrada, ufOrigem, ufDestino);
                    } else {
                        produtosTextArea.append("Todos os campos devem ser preenchidos. Tente novamente.\n");
                    }
                } catch (NumberFormatException ex) {
                    produtosTextArea.append("Código do Produto deve ser um número inteiro. Tente novamente.\n");
                }
            }
        });

        btnRetirarProduto.addActionListener((ActionEvent e) -> {
            retirarProduto();
        });

        btnListarProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProdutos();
            }
        });

        btnSair.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        panel.add(btnAdicionarProduto);
        panel.add(btnRetirarProduto);
        panel.add(btnListarProdutos);
        panel.add(btnSair);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Ex04()::criarMenu);
    }
}
