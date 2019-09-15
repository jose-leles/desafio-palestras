using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using WebApiDesafio.Models;

namespace WebApiDesafio.DAO{

    public class CategoriaDAO{

        public List<Categoria> ListarCategorias(){
            SqlConnection conn;
            List<Categoria> lista = new List<Categoria>();
            String query = "SELECT * FROM vTipoCategoria";
            try{
                conn = DatabaseConnection.GetConnection();
                SqlCommand cmd = new SqlCommand(query, conn);
                conn.Open();
                SqlDataReader reader =  cmd.ExecuteReader();
                while (reader.Read()){
                    lista.Add(new Categoria{
                        Codigo = Convert.ToInt32(reader["Codigo"]),
                        Descricao = reader["Descricao"].ToString()
                    });
                }
                DatabaseConnection.CloseConnection(conn);
            }catch(Exception e){
                return null;
            }
            return lista;

        }
    }

}