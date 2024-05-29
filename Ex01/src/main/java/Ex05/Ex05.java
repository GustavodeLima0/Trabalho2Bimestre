////5 - Agora a empresa de transportes adicionou 5 pilhas no seu depósito, desenvolva o
////aplicativo para efetuar as ações do exercício anterior, porém será necessário selecionar
////qual pilha será retirado/adicionado uma nova caixa. no painel deverá ser exibido as 5
////pilhas.

package Ex05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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

public class Ex05 {
    private Stack<Produto>[] pilhasDeProdutos;
    private final int NUMERO_DE_PILHAS = 5;
    private final int LIMITE = 10;
    private JTextArea[] produtosTextAreas;

    public Ex05() {
        pilhasDeProdutos = new Stack[NUMERO_DE_PILHAS];
        for (int i = 0; i < NUMERO_DE_PILHAS; i++) {
            pilhasDeProdutos[i] = new Stack<>();
        }
    }

    public void addProduto(int indicePilha, int codProduto, String descricao, String dataEntrada, String ufOrigem, String ufDestino) {
        if (pilhasDeProdutos[indicePilha].size() < LIMITE) {
            Produto produto = new Produto(codProduto, descricao, dataEntrada, ufOrigem, ufDestino);
            pilhasDeProdutos[indicePilha].push(produto);
            produtosTextAreas[indicePilha].append("Produto adicionado: " + produto + "\n");
        } else {
            produtosTextAreas[indicePilha].append("A pilha está cheia. Não é possível adicionar mais produtos.\n");
        }
        listarProdutos(indicePilha);
    }

    public void retirarProduto(int indicePilha) {
        if (pilhasDeProdutos[indicePilha].isEmpty()) {
            produtosTextAreas[indicePilha].append("Não há produtos na pilha para retirar.\n");
        } else {
            Produto produto = pilhasDeProdutos[indicePilha].pop();
            produtosTextAreas[indicePilha].append("Produto retirado: " + produto + "\n");
        }
        listarProdutos(indicePilha);
    }

    public void listarProdutos(int indicePilha) {
        produtosTextAreas[indicePilha].append("\nProdutos na pilha " + (indicePilha + 1) + ":\n");
        if (pilhasDeProdutos[indicePilha].isEmpty()) {
            produtosTextAreas[indicePilha].append("A pilha está vazia.\n");
        } else {
            for (Produto produto : pilhasDeProdutos[indicePilha]) {
                produtosTextAreas[indicePilha].append(produto + "\n");
            }
        }
        produtosTextAreas[indicePilha].append("\n");
    }

    public void criarMenu() {
        JFrame frame = new JFrame("Depósito - Sistema de Controle de Produtos Multiplo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton btnAdicionarProduto = new JButton("Adicionar Produto");
        JButton btnRetirarProduto = new JButton("Retirar Produto");
        JButton btnSair = new JButton("Sair");

        produtosTextAreas = new JTextArea[NUMERO_DE_PILHAS];
        JPanel pilhasPanel = new JPanel();
        pilhasPanel.setLayout(new GridLayout(1, NUMERO_DE_PILHAS));

        for (int i = 0; i < NUMERO_DE_PILHAS; i++) {
            produtosTextAreas[i] = new JTextArea(20, 20);
            produtosTextAreas[i].setEditable(false);
            JScrollPane scrollPane = new JScrollPane(produtosTextAreas[i]);
            JPanel pilhaPanel = new JPanel();
            pilhaPanel.setLayout(new BorderLayout());
            pilhaPanel.add(new JLabel("Pilha " + (i + 1)), BorderLayout.NORTH);
            pilhaPanel.add(scrollPane, BorderLayout.CENTER);
            pilhasPanel.add(pilhaPanel);
        }

        btnAdicionarProduto.addActionListener((ActionEvent e) -> {
            JTextField codProdutoField = new JTextField(5);
            JTextField descricaoField = new JTextField(10);
            JTextField dataEntradaField = new JTextField(10);
            JTextField ufOrigemField = new JTextField(2);
            JTextField ufDestinoField = new JTextField(2);
            JComboBox<String> pilhaComboBox = new JComboBox<>();
            for (int i = 0; i < NUMERO_DE_PILHAS; i++) {
                pilhaComboBox.addItem("Pilha " + (i + 1));
            }
            
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(6, 2));
            inputPanel.add(new JLabel("Pilha:"));
            inputPanel.add(pilhaComboBox);
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
                    int indicePilha = pilhaComboBox.getSelectedIndex();
                    
                    if (!descricao.isEmpty() && !dataEntrada.isEmpty() && !ufOrigem.isEmpty() && !ufDestino.isEmpty()) {
                        addProduto(indicePilha, codProduto, descricao, dataEntrada, ufOrigem, ufDestino);
                    } else {
                        produtosTextAreas[indicePilha].append("Todos os campos devem ser preenchidos. Tente novamente.\n");
                    }
                } catch (NumberFormatException ex) {
                    produtosTextAreas[0].append("Código do Produto deve ser um número inteiro. Tente novamente.\n");
                }
            }
        });

        btnRetirarProduto.addActionListener((ActionEvent e) -> {
            JComboBox<String> pilhaComboBox = new JComboBox<>();
            for (int i = 0; i < NUMERO_DE_PILHAS; i++) {
                pilhaComboBox.addItem("Pilha " + (i + 1));
            }
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(1, 2));
            inputPanel.add(new JLabel("Pilha:"));
            inputPanel.add(pilhaComboBox);
            
            int result = JOptionPane.showConfirmDialog(frame, inputPanel,
                    "Retirar Produto", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int indicePilha = pilhaComboBox.getSelectedIndex();
                retirarProduto(indicePilha);
            }
        });

        btnSair.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        panel.add(btnAdicionarProduto);
        panel.add(btnRetirarProduto);
        panel.add(btnSair);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(pilhasPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Ex05().criarMenu();
        });
    }
}