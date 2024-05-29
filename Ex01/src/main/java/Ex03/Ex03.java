//3 - Desenvolva um algoritmo para controlar uma pilha de livros de uma biblioteca. Deverá conter um
//método para adicionar o livro a pilha,
//Um método para listar os livros da pilha, um método para retirar o livro da pilha, nesse último
//método deverá ser exibido qual livro está sendo removido. Crie um menu para exibir as opções.

package Ex03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Stack;

class Livro {
    private String titulo;
    private String autor;

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + ", Autor: " + autor;
    }
}

public class Ex03 {
    private Stack<Livro> pilhaDeLivros;
    private JTextArea livrosTextArea;

    public Ex03() {
        this.pilhaDeLivros = new Stack<>();
    }

    public void addLivro(String titulo, String autor) {
        Livro livro = new Livro(titulo, autor);
        pilhaDeLivros.push(livro);
        livrosTextArea.append("Livro adicionado: " + livro + "\n");
    }

    public void listarLivros() {
        livrosTextArea.append("Livros na pilha:\n");
        if (pilhaDeLivros.isEmpty()) {
            livrosTextArea.append("A pilha está vazia.\n");
        } else {
            for (Livro livro : pilhaDeLivros) {
                livrosTextArea.append(livro + "\n");
            }
        }
    }

    public void retirarLivro() {
        if (pilhaDeLivros.isEmpty()) {
            livrosTextArea.append("Não há livros na pilha para remover.\n");
        } else {
            Livro livro = pilhaDeLivros.pop();
            livrosTextArea.append("Livro removido: " + livro + "\n");
        }
    }

    public void criarMenu() {
        JFrame frame = new JFrame("Biblioteca - Sistema de Controle de Livros");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton btnAdicionarLivro = new JButton("Adicionar Livro");
        JButton btnListarLivros = new JButton("Listar Livros");
        JButton btnRetirarLivro = new JButton("Retirar Livro");
        JButton btnSair = new JButton("Sair");

        livrosTextArea = new JTextArea(15, 40);
        livrosTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(livrosTextArea);

        btnAdicionarLivro.addActionListener((ActionEvent e) -> {
            JTextField tituloField = new JTextField(10);
            JTextField autorField = new JTextField(10);
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(2, 2));
            inputPanel.add(new JLabel("Título:"));
            inputPanel.add(tituloField);
            inputPanel.add(new JLabel("Autor:"));
            inputPanel.add(autorField);
            
            int result = JOptionPane.showConfirmDialog(frame, inputPanel,
                    "Adicionar Livro", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String titulo = tituloField.getText().trim();
                String autor = autorField.getText().trim();
                if (!titulo.isEmpty() && !autor.isEmpty()) {
                    addLivro(titulo, autor);
                } else {
                    livrosTextArea.append("Título ou autor inválido. Tente novamente.\n");
                }
            }
        });

        btnListarLivros.addActionListener((ActionEvent e) -> {
            listarLivros();
        });

        btnRetirarLivro.addActionListener((ActionEvent e) -> {
            retirarLivro();
        });

        btnSair.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        panel.add(btnAdicionarLivro);
        panel.add(btnListarLivros);
        panel.add(btnRetirarLivro);
        panel.add(btnSair);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Ex03().criarMenu();
        });
    }
}
