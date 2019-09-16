using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using WebApiDesafio.Models;

namespace WebApiDesafio.DAO{

    public class UsuarioDAO{

        public Usuario AutenticarUsuario(Usuario usuario){
            SqlConnection conn;
            Usuario autenticado = null;
            String query = @"EXEC spVerificaUsuario @Senha, @Email";
            try{
                conn = DatabaseConnection.GetConnection();
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("Senha", usuario.Senha);
                cmd.Parameters.AddWithValue("Email", usuario.Email);
                conn.Open();
                SqlDataReader reader =  cmd.ExecuteReader();
                while (reader.Read()){
                    autenticado = new Usuario{
                        Codigo = Convert.ToInt32(reader["Codigo"]),
                        Nome = reader["Nome"].ToString(),
                        Email = reader["Email"].ToString(),
                        Empresa = reader["Empresa"].ToString(),
                        Cargo = reader["Cargo"].ToString()
                    };
                }
                DatabaseConnection.CloseConnection(conn);
            }catch(Exception e){
                return null;
            }
            return autenticado;
        }

        public bool CadastrarUsuario(Usuario usuario){
            SqlConnection conn;
            String query = @"DECLARE @retorno smallint;
                            EXEC spCadastrarUsuario @Senha, @Nome, @Email, @Empresa, @Cargo, @retorno;
                            SELECT @retorno";
            try{
                conn = DatabaseConnection.GetConnection();
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("Senha", usuario.Senha);
                cmd.Parameters.AddWithValue("Email", usuario.Email);
                cmd.Parameters.AddWithValue("Nome", usuario.Nome);
                cmd.Parameters.AddWithValue("Empresa", usuario.Empresa);
                cmd.Parameters.AddWithValue("Cargo", usuario.Cargo);
                conn.Open();
                SqlDataReader reader =  cmd.ExecuteReader();
                if (reader.Read()){
                    return Convert.ToInt32(reader[0].ToString()) == 0; // 0 == sucesso
                }
                DatabaseConnection.CloseConnection(conn);
            }catch(Exception e){
                return false;
            }
            return false;
        }


    }

}