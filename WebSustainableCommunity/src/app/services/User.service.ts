import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public users: User[] = [];

constructor(
  public router: Router
) { }


public currentUser: User = null;
  public get loggedin(): boolean {
    return !!this.currentUser;
  }


  login(name: string, pass: string) {
    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].username === name && this.users[i].password === pass) {
        this.currentUser = this.users[i];
        console.log(this.currentUser);
        this.router.navigateByUrl("/productList");
        return;
      }

    }
    alert($localize `Wrong username or password`);
    return;

  }

  logOut() {
    this.currentUser = null;
    this.router.navigateByUrl("/login");
    console.log(this.currentUser);


  }

  public hasLoggedInUser(): boolean {
    return !!this.currentUser;  
  }



  getUserNameById(userid: number): string {   // getUserById volt az eredeti neve
    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].id === userid) {
        return this.users[i].username;
      }
    }
    return null;
  }

  getUserById(userid: number): User {
    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].id === userid) {
        return this.users[i];
      }
    }
    return null;
  }



  UsedNameChecker(name: string) {
    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].username === name) {
        alert("This name is allready in use")
        return false;
      }
    }
    return true;
  }

  samePassword(pass1: string,pass2: string) {
    for (let i = 0; i < this.users.length; i++) {
      if (pass1 !== pass2) {
        alert("The passwords are not the same")
        return false;
      }
    }
    return true;
  }

  registration(entryName: string, pass: string,email: string,passAgain:string) {
    if (this.UsedNameChecker(entryName)&& this.samePassword(pass,passAgain)) {
      let maxId = 1;
      for (let i = 0; i < this.users.length; i++) {
        if (this.users[i].id > maxId) {
          maxId = this.users[i].id
        }
      }
       let userId:number = maxId + 1;


      this.users.push( {id: userId, email:email, username: entryName, password: pass, passAgain:passAgain});


      alert($localize `Successful registration!`)
      console.log(this.users.length)
    //  console.log(this.users)

    }

  }

  changeUser(user: User) {
    for (let i = 0; i < this.users.length; i++) {
        if (this.users[i].id === user.id) {
            this.users[i] = user;
            console.log(user)
        }
    }
}


}
