package utilitarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import entidades.Conta;
import entidades.Pessoa;

public class DataBaseService {

    private static final String url = "jdbc:postgresql://localhost:5432/";
    private static final String user = "user";
    private static final String password = "password";
    private static final String dbName = "agencia_db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url + dbName, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(url + "postgres", user, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE " + dbName);

            System.out.println("Database criado com sucesso!");
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("Database já existe, prosseguindo com a execução.");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS contas("
                + "id SERIAL PRIMARY KEY,"
                + "nome VARCHAR(50),"
                + "cpf VARCHAR(11),"
                + "email VARCHAR(50),"
                + "saldo NUMERIC(15, 2)"
                + ")";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(SQL);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void criarConta(Conta conta) {
        String SQL = "INSERT INTO contas(nome, cpf, email, saldo) VALUES(?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, conta.getPessoa().getNome());
            pstmt.setString(2, conta.getPessoa().getCpf());
            pstmt.setString(3, conta.getPessoa().getEmail());
            pstmt.setDouble(4, conta.getSaldo());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void atualizarSaldoConta(int id, double novoSaldo) {
        String SQL = "UPDATE contas SET saldo = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setDouble(1, novoSaldo);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Conta buscarConta(int id) {
        String SQL = "SELECT * FROM contas WHERE id = ?";
        Conta conta = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Pessoa pessoa = new Pessoa(rs.getString("nome"), rs.getString("cpf"), rs.getString("email"));
                conta = new Conta(pessoa);
                conta.setNumeroConta(rs.getInt("id"));
                conta.setSaldo(rs.getDouble("saldo"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return conta;
    }

    public List<Conta> buscarTodasContas() {
        String SQL = "SELECT * FROM contas";
        List<Conta> contas = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(rs.getString("nome"), rs.getString("cpf"), rs.getString("email"));
                Conta conta = new Conta(pessoa);
                conta.setNumeroConta(rs.getInt("id"));
                conta.setSaldo(rs.getDouble("saldo"));
                contas.add(conta);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contas;
    }

    public void depositar(int numeroConta, double valor) {
        String sql = "UPDATE contas SET saldo = saldo + ? WHERE numero_conta = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, valor);
            pstmt.setInt(2, numeroConta);

            pstmt.executeUpdate();
            System.out.println("Valor depositado com sucesso!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sacar(int numeroConta, double valor) {
        String sql = "UPDATE contas SET saldo = saldo - ? WHERE numero_conta = ? AND saldo >= ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, valor);
            pstmt.setInt(2, numeroConta);
            pstmt.setDouble(3, valor);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Valor sacado com sucesso!");
            } else {
                System.out.println("Saldo insuficiente para saque!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferir(int numeroContaOrigem, int numeroContaDestino, double valor) {
        try {
            sacar(numeroContaOrigem, valor);
            depositar(numeroContaDestino, valor);
            System.out.println("Transferência realizada com sucesso");
        } catch (Exception e) {
            System.out.println("Não foi possível realizar a transferência!");
        }
    }
}
