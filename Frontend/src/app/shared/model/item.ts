import { Produto } from "./produto";

export class Item {
  id: number;
  idCarrinho: number;
  produto: Produto;
  preco: number;
  quantidade: number;
}