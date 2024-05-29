//1 - Uma clínica precisa de um sistema para organizar a fila de pacientes, para cada dia
//é disponibilizado 20 senhas para consulta. Eles necessitam de um sistema onde é
//informado o nome do paciente em ordem de chegada e uma opção para chamar o
//próximo paciente. Desenvolva um algoritmo de Fila, contendo um menu com as
//opções: 1 - Adicionar paciente, 2 - Chamar próximo paciente. A opção 1 deverá exibir
//um campo para informar o nome do paciente assim que ele chega no consultório, e a
//opção 2, ao selecionar deverá exibir o nome do próximo paciente na fila.///



package Ex01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Queue;

public class Ex01 {
    private final Queue<String> fila;
    private final int LIMITE = 5;
    private JTextArea filaTextArea;

    public Ex01() {
        this.fila = new LinkedList<>();
    }

    public void addPaciente(String nome) {
        if (fila.size() < LIMITE) {
            fila.add(nome);
            filaTextArea.append("Paciente " + nome + " adicionado a fila.\n");
        } else {
            filaTextArea.append("A fila esta cheia. Nao é possivel adicionar mais pacientes hoje.\n");
        }
    }

    public void ProxPaciente() {
        if (!fila.isEmpty()) {
            String proximoPaciente = fila.poll();
            filaTextArea.append("Proximo paciente: " + proximoPaciente + "\n");
        } else {
            filaTextArea.append("Nao ha pacientes na fila.\n");
        }
    }

    public void criarMenu() {
        JFrame frame = new JFrame("Clinica - Sistema de Fila de Pacientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton btnAdicionarPaciente = new JButton("Adicionar Paciente");
        JButton btnChamarPaciente = new JButton("Chamar Proximo Paciente");
        JButton btnSair = new JButton("Sair");

        filaTextArea = new JTextArea(10, 30);
        filaTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(filaTextArea);

        btnAdicionarPaciente.addActionListener((ActionEvent e) -> {
            String nome = JOptionPane.showInputDialog(frame, "Informe o nome do paciente:");
            if (nome != null && !nome.trim().isEmpty()) {
                addPaciente(nome);
            } else {
                filaTextArea.append("Nome invalido. Tente novamente.\n");
            }
        });

        btnChamarPaciente.addActionListener((ActionEvent e) -> {
            ProxPaciente();
        });

        btnSair.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        panel.add(btnAdicionarPaciente);
        panel.add(btnChamarPaciente);
        panel.add(btnSair);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Ex01()::criarMenu);
    }
}
