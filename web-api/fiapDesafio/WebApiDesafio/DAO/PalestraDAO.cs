using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using WebApiDesafio.Models;

namespace WebApiDesafio.DAO{

    public class PalestraDAO{

        public List<Palestra> ListarPalestras(){
            SqlConnection conn;
            List<Palestra> lista = new List<Palestra>();
            String query = @"SELECT * FROM vPalestra 
                        ORDER BY vPalestra.Data, vPalestra.Hora ASC";
            try{
                conn = DatabaseConnection.GetConnection();
                SqlCommand cmd = new SqlCommand(query, conn);
                conn.Open();
                SqlDataReader reader =  cmd.ExecuteReader();
                while (reader.Read()){
                    lista.Add(new Palestra {
                        Codigo = Convert.ToInt32(reader["Codigo"]),
                        CodigoTipoCategoria = Convert.ToInt32(reader["CodigoTipoCategoria"]),
                        Imagem = reader["Imagem"].ToString(),
                        Titulo = reader["Titulo"].ToString(),
                        Palestrante = reader["Palestrante"].ToString(),
                        Descricao = reader["Descricao"].ToString(),
                        Data = reader["Data"].ToString(),
                        Hora = reader["Hora"].ToString(),
                        QtdVagasDisponiveis = Convert.ToInt32(reader["QtdVagasDisponiveis"])
                    });
                }
                DatabaseConnection.CloseConnection(conn);
            }catch(Exception e){
                return null;
            }
            return lista;

        }

        public Palestra GetById(int id){
            SqlConnection conn;
            Palestra palestra = null;
            String query = "SELECT * FROM vPalestra WHERE Codigo = @id";
            try
            {
                conn = DatabaseConnection.GetConnection();
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("id", id);
                conn.Open();
                SqlDataReader reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    palestra = new Palestra
                    {
                        Codigo = Convert.ToInt32(reader["Codigo"]),
                        CodigoTipoCategoria = Convert.ToInt32(reader["CodigoTipoCategoria"]),
                        Imagem = reader["Imagem"].ToString(),
                        Titulo = reader["Titulo"].ToString(),
                        Palestrante = reader["Palestrante"].ToString(),
                        Descricao = reader["Descricao"].ToString(),
                        Data = reader["Data"].ToString(),
                        Hora = reader["Hora"].ToString(),
                        QtdVagasDisponiveis = Convert.ToInt32(reader["QtdVagasDisponiveis"])
                    };
                }
                DatabaseConnection.CloseConnection(conn);
            }
            catch (Exception e)
            {
                return null;
            }
            return palestra;
        }

        public Palestra GetById(int id, int idUsuario){
            SqlConnection conn;
            Palestra palestra = null;
            String query = @"SELECT * FROM vPalestra 
                        LEFT JOIN vInscricao ON (vPalestra.Codigo = vInscricao.CodigoPalestra AND vInscricao.CodigoUsuario = @idUsuario )
                        WHERE vPalestra.Codigo = @id";
            try
            {
                conn = DatabaseConnection.GetConnection();
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("id", id);
                cmd.Parameters.AddWithValue("idUsuario", idUsuario);
                conn.Open();
                SqlDataReader reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    palestra = new Palestra
                    {
                        Codigo = Convert.ToInt32(reader["Codigo"]),
                        CodigoTipoCategoria = Convert.ToInt32(reader["CodigoTipoCategoria"]),
                        Imagem = reader["Imagem"].ToString(),
                        Titulo = reader["Titulo"].ToString(),
                        Palestrante = reader["Palestrante"].ToString(),
                        Descricao = reader["Descricao"].ToString(),
                        Data = reader["Data"].ToString(),
                        Hora = reader["Hora"].ToString(),
                        QtdVagasDisponiveis = Convert.ToInt32(reader["QtdVagasDisponiveis"])
                    };
                    if(reader["DataCadastro"] != null && !reader["DataCadastro"].ToString().Equals("") && !reader["DataCadastro"].ToString().Equals("null"))
                    {
                        palestra.EmailCadastrado = true;
                        palestra.DataInscricao = reader["DataCadastro"].ToString();
                        palestra.HoraInscricao = reader["HoraCadastro"].ToString();
                    }
                }
                DatabaseConnection.CloseConnection(conn);
            }
            catch (Exception e)
            {
                return null;
            }
            return palestra;
        }

        public List<Palestra> ListarPaleastraPorIdUsuario(int idUsuario)
        {
            SqlConnection conn;
            List<Palestra> lista = new List<Palestra>();
            String query = @"SELECT * FROM vPalestra 
                            INNER JOIN vInscricao 
                            ON (vPalestra.Codigo = vInscricao.CodigoPalestra) 
                            WHERE vInscricao.CodigoUsuario = @idUsuario
                            ORDER BY vPalestra.Data DESC";
            try{
                conn = DatabaseConnection.GetConnection();
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("idUsuario", idUsuario);
                conn.Open();
                SqlDataReader reader =  cmd.ExecuteReader();
                while (reader.Read()){
                    lista.Add(new Palestra {
                        Codigo = Convert.ToInt32(reader["Codigo"]),
                        CodigoTipoCategoria = Convert.ToInt32(reader["CodigoTipoCategoria"]),
                        Imagem = reader["Imagem"].ToString(),
                        Titulo = reader["Titulo"].ToString(),
                        Palestrante = reader["Palestrante"].ToString(),
                        Descricao = reader["Descricao"].ToString(),
                        Data = reader["Data"].ToString(),
                        Hora = reader["Hora"].ToString(),
                        QtdVagasDisponiveis = Convert.ToInt32(reader["QtdVagasDisponiveis"]),
                        EmailCadastrado = true,
                        DataInscricao = reader["DataCadastro"].ToString(),
                        HoraInscricao = reader["HoraCadastro"].ToString()
                    });
                }
                DatabaseConnection.CloseConnection(conn);
            }catch(Exception e){
                return null;
            }
            return lista;
        }

        public int InscreverUsuario(int id, Usuario usuario){
            SqlConnection conn;
            List<Palestra> lista = new List<Palestra>();
            String query = @"DECLARE @retorno smallint;
                        EXEC spInscricao @idPalestra, @CodigoUsuario, @retorno OUTPUT;
                        SELECT @retorno; ";
            try{
                conn = DatabaseConnection.GetConnection();
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("idPalestra", id);
                cmd.Parameters.AddWithValue("CodigoUsuario", usuario.Codigo);
                conn.Open();
                SqlDataReader reader =  cmd.ExecuteReader();
                if (reader.Read()){
                    return Convert.ToInt32(reader[0].ToString());  
                }
                DatabaseConnection.CloseConnection(conn);
            }catch(Exception e){
                return (int)RetornoDaInscricao.FALHA_EXECUCAO;
            }
            return (int)RetornoDaInscricao.FALHA_EXECUCAO;
        }
    }

}