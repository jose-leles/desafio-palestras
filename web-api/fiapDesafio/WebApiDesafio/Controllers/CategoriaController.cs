using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebApiDesafio.DAO;
using WebApiDesafio.Models;

namespace WebApiDesafio.Controllers{

    public class CategoriaController : ApiController{
        [HttpGet]
        [Route("categorias/listar")]
        public IHttpActionResult ListaCategoria(){
            List<Categoria> lista = new CategoriaDAO().ListarCategorias();
            return Json(lista);
        }

       
    }
}
