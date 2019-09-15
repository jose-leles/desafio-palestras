using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApiDesafio.Models
{
    public class Categoria{
        public int Codigo { get; set; }
        public String Descricao { get; set; }
        public List<Palestra> Palestras = new List<Palestra>();
    }
}