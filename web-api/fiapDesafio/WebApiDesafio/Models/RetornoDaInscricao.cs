using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApiDesafio.Models
{
    public enum RetornoDaInscricao{
        CADASTRADO_SUCESSO=0,
        EMAIL_JA_CADASTRADO=1,
        PALESTRA_NAO_EXISTE=2,
        FALHA_EXECUCAO=3,

    }
}