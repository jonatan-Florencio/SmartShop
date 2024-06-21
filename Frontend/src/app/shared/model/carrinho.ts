import { FormaDePagamento } from "./formaDePagamento";
import { Usuario } from "./usuario";

export class Carrinho {
  id: number;
  data: Date;
  total: number;
  cliente: Usuario;
  formaDePagamento: FormaDePagamento;
  // itens: Item[];
}