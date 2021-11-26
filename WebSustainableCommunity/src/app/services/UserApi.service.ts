import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../models/User";
import {Observable, of} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserApiService {

constructor(
  private http: HttpClient
) { }


public register(newUser: User): Observable<User> {
  return this.http.post<User>(environment.serverBaseHref + "/user/create", newUser)
}

public isUserNameUnique(user:string): Observable<boolean>{
  return this.http.get<boolean>(environment.serverBaseHref + "/user/isUsernameUnique", {params: {name: user}})
}

public getUsers(){
  return this.http.get(environment.serverBaseHref + "/users",)
}

}
