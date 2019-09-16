using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApiDesafio.Models
{
    public class Usuario{
        public int Codigo { get; set; }
        public int Senha { get; set; }
        public String Nome { get; set; }
        public String Email { get; set; }
        public String Empresa { get; set; }
        public String Cargo { get; set; }
    }
}