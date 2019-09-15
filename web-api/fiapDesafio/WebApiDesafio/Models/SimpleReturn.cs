using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApiDesafio.Models
{
    public class SimpleReturn{

        public SimpleReturn(string mensagem, bool sucesso){
            this.Mensagem = mensagem;
            this.Sucesso = sucesso;
        }
        public bool Sucesso { get; set; }
        public String Mensagem { get; set; }

    }
}