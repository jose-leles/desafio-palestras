using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApiDesafio.Models
{
    public class Palestra{
        public int Codigo { get; set; }
        public int CodigoTipoCategoria { get; set; }
        public String Imagem { get; set; }
        public String Titulo { get; set; }
        public String Palestrante { get; set; }
        public String Descricao { get; set; }
        public String Data { get; set; }
        public String Hora { get; set; }
        public int QtdVagasDisponiveis { get; set; }

        //when listed by a user, must have this atributes on it
        public bool EmailCadastrado { get; set; }
        public String DataInscricao { get; set; }
        public String HoraInscricao { get; set; }
    }
}