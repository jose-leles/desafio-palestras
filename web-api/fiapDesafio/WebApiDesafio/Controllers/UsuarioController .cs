using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebApiDesafio.DAO;
using WebApiDesafio.Models;

namespace WebApiDesafio.Controllers{

    public class UsuarioController : ApiController{
        [HttpPost]
        [Route("usuario/logar")]
        public IHttpActionResult LogarUsuario([FromBody] Usuario toAutenticar){
            Usuario autenticado = new UsuarioDAO().AutenticarUsuario(toAutenticar);
            if(autenticado != null){
                return Json(autenticado);
            }else{
                return Json(new SimpleReturn("Credenciais invalidas", false));
            }
            
        }

        [HttpPost]
        [Route("usuario/cadastrar")]
        public IHttpActionResult CadastrarUsuario([FromBody] Usuario toCadastrar){
            bool cadastrado = new UsuarioDAO().CadastrarUsuario(toCadastrar);
            if(cadastrado){
                Usuario autenticado = new UsuarioDAO().AutenticarUsuario(toCadastrar);
                return Json(autenticado);
            }else{
                return Json(new SimpleReturn("Não foi possivel cadastrar-se", false));
            }
            
        }

       
    }
}
