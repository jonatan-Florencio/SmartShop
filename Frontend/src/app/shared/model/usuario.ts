import { Bloco } from './bloco';
import { Apartamento } from './apartamento';
import { TipoUsuario } from './tipoUsuario';

export class Usuario {
  id: number;
  nome: string;
  cpf: string;
  telefone: string;
  email: string;
  bloco: Bloco;
  apartamento: Apartamento;
  tipoUsuario: TipoUsuario;
  senha: string;
  editMode: boolean = false;
}
