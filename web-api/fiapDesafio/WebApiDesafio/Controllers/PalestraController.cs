using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebApiDesafio.DAO;
using WebApiDesafio.Models;

namespace WebApiDesafio.Controllers
{

    public class PalestraController : ApiController
    {

        // para montar a estrutura pedida {"Categorias": []}

        [HttpGet]
        [Route("palestra/listar")]
        public IHttpActionResult ListarPalestras()
        {
            List<Categoria> lista = new CategoriaDAO().ListarCategorias();
            List<Palestra> listaPalestras = new PalestraDAO().ListarPalestras();
            lista.ForEach(categoria => {
                for (int i = 0; i < listaPalestras.Count; i++)
                {
                    if (listaPalestras[i].CodigoTipoCategoria == categoria.Codigo)
                    {
                        categoria.Palestras.Add(listaPalestras[i]);
                        listaPalestras.RemoveAt(i);
                        i--;
                    }
                }
            });
            return Json(new { Categorias = lista });
        }

        [HttpGet]
        [Route("palestra/detalhes/{id}")]
        public IHttpActionResult VerDetalhes(int id, int idUsuario)
        {
            if (id > 0)
            {
                Palestra palestra = new PalestraDAO().GetById(id, idUsuario);
                return Json(palestra);
            }
            return Json(new SimpleReturn("codigo de palestra invalido", false));
        }

        [HttpGet]
        [Route("palestra/listar")]
        public IHttpActionResult MinhasInscricoes(int idUsuario)
        { //TODO: chave da tabela 
            List<Categoria> lista = new CategoriaDAO().ListarCategorias();
            List<Palestra> listaPalestras = new PalestraDAO().ListarPaleastraPorIdUsuario(idUsuario);
            lista.ForEach(categoria => {
                for (int i = 0; i < listaPalestras.Count; i++)
                {
                    if (listaPalestras[i].CodigoTipoCategoria == categoria.Codigo)
                    {
                        categoria.Palestras.Add(listaPalestras[i]);
                        listaPalestras.RemoveAt(i);
                        i--;
                    }
                }
            });
            return Json(new { Categorias = lista });
        }

        [HttpPost]
        [Route("palestra/inscrever/{id}")]
        public IHttpActionResult Inscricao(int id, [FromBody] Usuario usuario)
        {
            int retorno;
            retorno = new PalestraDAO().InscreverUsuario(id, usuario);
            switch (retorno)
            {
                case (int)RetornoDaInscricao.CADASTRADO_SUCESSO:
                    return Json(new SimpleReturn("Cadastrado com Sucesso", true));

                case (int)RetornoDaInscricao.EMAIL_JA_CADASTRADO:
                    return Json(new SimpleReturn("Email já esta inscrito", false));

                case (int)RetornoDaInscricao.PALESTRA_NAO_EXISTE:
                    return Json(new SimpleReturn("Palestra não existe", false));

                default:
                    return Json(new SimpleReturn("Não foi possivel efetuar seu cadastro", false));

            }
        }
    }

    
}

