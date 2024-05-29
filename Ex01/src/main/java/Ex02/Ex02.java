//2 - Um banco necessita de uma sistema para controlar a fila de pagamentos no caixa.
//Para o atendimento é obedecido a lei de prioridade, onde a cada 2 clientes prioritários,
//um cliente normal é atendido. Desenvolva um algoritmo para controlar a fila de
//atendimento, para isso deverá ser criado 2 filas, uma de atendimento prioritário e outra
//de atendimento normal. Na fila será cadastrado os seguintes dados do cliente:
//Cliente
//- int senha;
//- String nome;
//- int anoNascimento;
//Deverá ser determinado pela a idade qual fila será inserido o cliente, acima de 65 anos
//fila prioritária, os demais na fila normal. Crie um menu com uma opção para adicionar o
//cliente, e outra para chamar o cliente. Lembre-se deverá seguir a regra de ao ser
//atendido 2 clientes prioritários será atendido um cliente normal.

package Ex02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Queue;

class Cliente {
    int senha;
    String nome;
    int anoNascimento;

    public Cliente(int senha, String nome, int anoNascimento) {
        this.senha = senha;
        this.nome = nome;
        this.anoNascimento = anoNascimento;
    }

    public int getIdade() {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return currentYear - this.anoNascimento;
    }

    @Override
    public String toString() {
        return "Senha: " + senha + ", Nome: " + nome + ", Ano de Nascimento: " + anoNascimento;
    }
}

public class Ex02 {
    private final Queue<Cliente> filaPrioritaria;
    private final Queue<Cliente> filaNormal;
    private int contadorPrioritario;
    private int senhaAtual;
    private JTextArea filaTextArea;

    public Ex02() {
        this.filaPrioritaria = new LinkedList<>();
        this.filaNormal = new LinkedList<>();
        this.contadorPrioritario = 0;
        this.senhaAtual = 1;
    }

    public void addCliente(String nome, int anoNascimento) {
        Cliente cliente = new Cliente(senhaAtual++, nome, anoNascimento);
        if (cliente.getIdade() >= 65) {
            filaPrioritaria.add(cliente);
            filaTextArea.append("Cliente " + cliente + " adicionado à fila prioritária.\n");
        } else {
            filaNormal.add(cliente);
            filaTextArea.append("Cliente " + cliente + " adicionado à fila normal.\n");
        }
    }

    public void chamarCliente() {
        if (!filaPrioritaria.isEmpty() && contadorPrioritario < 2) {
            Cliente cliente = filaPrioritaria.poll();
            filaTextArea.append("Cliente prioritário atendido: " + cliente + "\n");
            contadorPrioritario++;
        } else if (!filaNormal.isEmpty()) {
            Cliente cliente = filaNormal.poll();
            filaTextArea.append("Cliente normal atendido: " + cliente + "\n");
            contadorPrioritario = 0; // Reset contador após atender um cliente normal
        } else if (!filaPrioritaria.isEmpty()) {
            Cliente cliente = filaPrioritaria.poll();
            filaTextArea.append("Cliente prioritário atendido: " + cliente + "\n");
        } else {
            filaTextArea.append("Não há clientes na fila para atendimento.\n");
        }
    }

    public void criarMenu() {
        JFrame frame = new JFrame("Banco - Sistema de Fila de Pagamentos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton btnAdicionarCliente = new JButton("Adicionar Cliente");
        JButton btnChamarCliente = new JButton("Chamar Cliente");
        JButton btnSair = new JButton("Sair");

        filaTextArea = new JTextArea(15, 40);
        filaTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(filaTextArea);

        btnAdicionarCliente.addActionListener((ActionEvent e) -> {
            JTextField nomeField = new JTextField(10);
            JTextField anoField = new JTextField(4);
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(2, 2));
            inputPanel.add(new JLabel("Nome:"));
            inputPanel.add(nomeField);
            inputPanel.add(new JLabel("Ano de Nascimento:"));
            inputPanel.add(anoField);
            
            int result = JOptionPane.showConfirmDialog(frame, inputPanel,
                    "Adicionar Cliente", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String nome = nomeField.getText().trim();
                int anoNascimento;
                try {
                    anoNascimento = Integer.parseInt(anoField.getText().trim());
                    if (!nome.isEmpty()) {
                        addCliente(nome, anoNascimento);
                    } else {
                        filaTextArea.append("Nome invalido. Tente novamente.\n");
                    }
                } catch (NumberFormatException ex) {
                    filaTextArea.append("Ano de nascimento invalido. Tente novamente.\n");
                }
            }
        });

        btnChamarCliente.addActionListener((ActionEvent e) -> {
            chamarCliente();
        });

        btnSair.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        panel.add(btnAdicionarCliente);
        panel.add(btnChamarCliente);
        panel.add(btnSair);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Ex02().criarMenu();
        });
    }
}
