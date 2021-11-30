import { Component } from '@angular/core';
import { UserService } from './services/User.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'WebSustainableCommunity';

  constructor(
    public userService: UserService,
   ) { }

   public logOut(){
    this.userService.logOut();
  }


}
