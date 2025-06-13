export type LoginResponse = {
  access_token: string;
  user: LoggedUser;
};

export type LoggedUser = {
  id: number;
  id_rol: number;
  correo: string;
  nombre: string;
};
