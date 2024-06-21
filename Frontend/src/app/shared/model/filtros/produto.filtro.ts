import { Categoria } from "../categoria";
import { Marca } from "../marca";

export class ProdutoFiltro {
  nome: string;
  codigoDeBarras: string;
  precoMinimo: number;
  precoMaximo: number;
  estoqueMinimo: number;
  estoqueMaximo: number;
  categoria: Categoria;
  marca: Marca;
  pagina: number;
  limite: number;
}
