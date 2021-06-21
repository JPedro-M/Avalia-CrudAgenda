package br.ulbra.dao;

import br.ulbra.model.Agenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author troll
 */
public class AgendaDao {
    
    Connection con;
    
    public AgendaDao() throws SQLException{
        con = ConnectionFactory.getConnection();
    }
    
    public boolean checkLogin(String email, String senha){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        
        try{
            stmt = con.prepareStatement("SELECT * FROM tbagenda where email = ? AND senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                check = true;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro no agendaDao: "+e.getMessage());
        }
        return check;
    }
    
    public void create(Agenda u){
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement("INSERT INTO tbagenda (nome,email,senha,telefone,recado) VALUE (?,?,?,?,?)");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getFone());
            stmt.setString(5, u.getRecado());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, u.getNome()+" registrado com sucesso.");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    public void update(Agenda u){
        PreparedStatement stmt = null;
        try{
            stmt = con.prepareStatement("UPDATE tbagenda SET nome = ?, email = ?, senha = ?, telefone = ?, recado = ?");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getFone());
            stmt.setString(5, u.getRecado());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pessoa atualizada com sucesso.");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    public void delete(Agenda u){
        PreparedStatement stmt = null;
        try{
            stmt = con.prepareStatement("DELETE FROM tbagenda WHERE id = ?");
            stmt.setInt(1, u.getId());
            if(JOptionPane.showConfirmDialog(null, "Exclusão", "Tem certeza que deseja escluir a pessoa: "+u.getNome()+"?", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null, "Pessoa excluida com sucesso.");
                stmt.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(null, "A exclusão foi cancelada.");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    public ArrayList<Agenda> read(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Agenda> agendas = new ArrayList<Agenda>();
        try{
            stmt = con.prepareStatement("SELECT * FROM tbagenda");
            rs = stmt.executeQuery();
            while(rs.next()){
                Agenda agenda = new Agenda();
                agenda.setId(rs.getInt("id"));
                agenda.setNome(rs.getString("nome"));
                agenda.setEmail(rs.getString("email"));
                agenda.setSenha(rs.getString("senha"));
                agenda.setFone(rs.getString("telefone"));
                agenda.setRecado(rs.getString("recado"));
                agendas.add(agenda);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return (ArrayList<Agenda>) agendas;
    }
    public ArrayList<Agenda> readPesq(String nome){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Agenda> agendas = new ArrayList<Agenda>();
        try{
            stmt = con.prepareStatement("SELECT * FROM tbagenda WHERE nome LIKE ?");
            stmt.setString(1, "%"+nome+"%");
            rs = stmt.executeQuery();
            while(rs.next()){
                Agenda agenda = new Agenda();
                agenda.setId(rs.getInt("id"));
                agenda.setNome(rs.getString("nome"));
                agenda.setEmail(rs.getString("email"));
                agenda.setSenha(rs.getString("senha"));
                agenda.setFone(rs.getString("telefone"));
                agenda.setRecado(rs.getString("recado"));
                agendas.add(agenda);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return (ArrayList<Agenda>) agendas;
    }
}
