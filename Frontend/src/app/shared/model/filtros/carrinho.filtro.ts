import { Usuario } from "../usuario";

export class CarrinhoFiltro {
  dataInicial: Date;
  dataFinal: Date;
  // formaDePagamento: FormaDePagamento;
  cliente: Usuario;
  pagina: number;
  limite: number;
}
