import {Injectable} from '@angular/core';
import {HttpClient, HttpContext} from "@angular/common/http";
import {User} from "../models/User";
import {Observable, of} from "rxjs";
import {environment} from "../../environments/environment";
import { IS_TOKEN_ENABLED } from './auth.interceptor';

export class LoginResultDTO {
  public user: User;
  public token: string; 
}

@Injectable({
  providedIn: 'root'
})
export class UserApiService {

constructor(
  private http: HttpClient
) { }

public login(userName: string, password: string): Observable<LoginResultDTO> {
  return this.http.post<LoginResultDTO>(environment.serverBaseHref + `/login?user=${userName}&password=${password}`, null, {
    context: new HttpContext().set(IS_TOKEN_ENABLED, false)
    
  });
}

public register(newUser: User): Observable<User> {
  return this.http.post<User>(environment.serverBaseHref + "/user/create", newUser, {
    context: new HttpContext().set(IS_TOKEN_ENABLED, false)
  });
}

public isUserNameUnique(user:string): Observable<boolean>{
  return this.http.get<boolean>(environment.serverBaseHref + "/user/isUsernameUnique", {
    params: {name: user},
    context: new HttpContext().set(IS_TOKEN_ENABLED, false)});
}

public getUsers(){
  return this.http.get(environment.serverBaseHref + "/users");
}

}
