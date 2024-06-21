import { Categoria } from "./categoria";
import { Marca } from "./marca";

export class Produto {
  id: number;
  nome: string;
  marca: Marca;
  codigoDeBarras: string;
  preco: number;
  estoque: number;
  categoria: Categoria;
}