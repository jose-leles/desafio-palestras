using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace WebApiDesafio.DAO
{
    public class DatabaseConnection{

        public static SqlConnection GetConnection(){
            return new SqlConnection(ConfigurationManager.ConnectionStrings["conexaoProvaMobile"].ConnectionString);
        }

        public static void CloseConnection(SqlConnection conn){
            if(conn != null && conn.State != System.Data.ConnectionState.Closed){
                conn.Close();
            }
        }
    }
}