import { Injectable, NgModule } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterModule, RouterStateSnapshot, Routes, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginComponent } from './components/user/login/login.component';
import { RegistrationComponent } from './components/user/registration/registration.component';
import { UserService } from './services/User.service';








@Injectable()
export class CanActivateTeam implements CanActivate {
  constructor(
    private userService: UserService,
    private router: Router,
    ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean|UrlTree>|Promise<boolean|UrlTree>|boolean|UrlTree {
    if (!this.userService.hasLoggedInUser()){
      return this.router.parseUrl('/login');
    }
    return true;
  }
}



const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch:'full'},
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: '**', redirectTo: '/login', pathMatch:'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [CanActivateTeam]
})
export class AppRoutingModule { }
